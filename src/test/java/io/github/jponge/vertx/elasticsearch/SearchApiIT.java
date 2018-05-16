/*
 * Copyright (c) 2018 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.jponge.vertx.elasticsearch;

import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.reactivex.core.Vertx;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test the search API")
@ExtendWith(VertxExtension.class)
class SearchApiIT {

  private static final ElasticsearchClientOptions OPTIONS = new ElasticsearchClientOptions()
    .setHostname("localhost")
    .setPort(9200);

  @Nested
  @DisplayName("Perform and test Search operations on the search API")
  class CrudOperations {

    private String index;

    private JsonObject document = new JsonObject()
      .put("abc", "123")
      .put("def", "456")
      .put("ghi", "789");

    private io.github.jponge.vertx.elasticsearch.reactivex.ElasticsearchClient client;

    @BeforeEach
    void prepare(Vertx vertx, VertxTestContext testContext) {
      index = UUID.randomUUID().toString();
      client = io.github.jponge.vertx.elasticsearch.reactivex.ElasticsearchClient.create(vertx, OPTIONS);

      client.documentApi()
        .rxAdd(index, new DocumentApiOptions().refresh("true"), document)
        .subscribe(response -> {
          testContext.verify(() -> assertEquals("created", response.getString("result")));
          testContext.completeNow();
        }, testContext::failNow);

    }

    @Test
    @DisplayName("Search for all documents")
    void searchAll(VertxTestContext testContext) {
      client.searchApi()
        .rxSearch(index, new JsonObject())
        .subscribe(response -> {
          testContext.verify(() -> {
            assertEquals((Integer) 1, response.getJsonObject("hits").getInteger("total"));
          });
          testContext.completeNow();
        }, testContext::failNow);
    }
  }
}
