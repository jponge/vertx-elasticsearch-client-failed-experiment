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

import io.github.jponge.vertx.elasticsearch.reactivex.DocumentApi;

import io.reactivex.Single;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.reactivex.core.Vertx;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test the document API")
@ExtendWith(VertxExtension.class)
class DocumentApiIT {

  private static final ElasticsearchClientOptions OPTIONS = new ElasticsearchClientOptions()
    .setHostname("localhost")
    .setPort(9200);

  @Nested
  @DisplayName("Perform and test CRUD operations on the document API")
  class CrudOperations {

    private String index;
    @Deprecated
    private String deprecatedType;
    private String id;

    private JsonObject document = new JsonObject()
      .put("abc", "123")
      .put("def", "456")
      .put("ghi", "789");

    private io.github.jponge.vertx.elasticsearch.reactivex.ElasticsearchClient client;

    @BeforeEach
    void prepare(Vertx vertx) {
      index = UUID.randomUUID().toString();
      deprecatedType = UUID.randomUUID().toString();
      id = Integer.toString(Math.abs(new Random().nextInt()));
      client = io.github.jponge.vertx.elasticsearch.reactivex.ElasticsearchClient.create(vertx, OPTIONS);
    }

    @Test
    @DisplayName("Insert a document with a known ID and deprecated type")
    void insertDocWithIdAndDeprecatedType(VertxTestContext testContext) {
      client.documentApi()
        .rxAddOrUpdate(index, deprecatedType, id, document)
        .subscribe(response -> {
          testContext.verify(() -> assertEquals("created", response.getString("result")));
          testContext.completeNow();
        }, testContext::failNow);
    }

    @Test
    @DisplayName("Insert a document with a known ID")
    void insertDocWithId(VertxTestContext testContext) {
      client.documentApi()
        .rxAddOrUpdate(index, id, document)
        .subscribe(response -> {
          testContext.verify(() -> assertEquals("created", response.getString("result")));
          testContext.completeNow();
        }, testContext::failNow);
    }

    @Test
    @DisplayName("Check inserting then updating a document at a given ID with deprecated type")
    void insertDocThenUpdateWithDeprecatedType(VertxTestContext testContext) {
      DocumentApi documentApi = client.documentApi();
      documentApi
        .rxAddOrUpdate(index, deprecatedType, id, document)
        .flatMap(r1 -> {
          testContext.verify(() -> assertEquals("created", r1.getString("result")));
          return documentApi.rxAddOrUpdate(index, deprecatedType, id, document);
        })
        .subscribe(r2 -> {
          testContext.verify(() -> assertEquals("updated", r2.getString("result")));
          testContext.completeNow();
        }, testContext::failNow);
    }

    @Test
    @DisplayName("Check inserting then updating a document at a given ID")
    void insertDocThenUpdate(VertxTestContext testContext) {
      DocumentApi documentApi = client.documentApi();
      documentApi
        .rxAddOrUpdate(index, id, document)
        .flatMap(r1 -> {
          testContext.verify(() -> assertEquals("created", r1.getString("result")));
          return documentApi.rxAddOrUpdate(index, id, document);
        })
        .subscribe(r2 -> {
          testContext.verify(() -> assertEquals("updated", r2.getString("result")));
          testContext.completeNow();
        }, testContext::failNow);
    }

    @Test
    @DisplayName("Check updating with a version")
    void insertDocThenUpdateWithVersion(VertxTestContext testContext) {
      DocumentApi documentApi = client.documentApi();
      documentApi
        .rxAddOrUpdate(index, id, document)
        .flatMap(r1 -> {
          testContext.verify(() -> assertEquals("created", r1.getString("result")));
          return documentApi.rxAddOrUpdate(index, id, new DocumentApiOptions().version(1), document);
        })
        .subscribe(r2 -> {
          testContext.verify(() -> assertEquals("updated", r2.getString("result")));
          testContext.completeNow();
        }, testContext::failNow);
    }

    @Test
    @DisplayName("Check updating with a wrong version")
    void insertDocThenUpdateWithWrongVersion(VertxTestContext testContext) {
      DocumentApi documentApi = client.documentApi();
      documentApi
        .rxAddOrUpdate(index, id, document)
        .flatMap(r1 -> {
          testContext.verify(() -> assertEquals("created", r1.getString("result")));
          return documentApi.rxAddOrUpdate(index, id, new DocumentApiOptions().version(58), document);
        })
        .subscribe(r2 -> {
          testContext.verify(() -> {
            assertTrue(r2.containsKey("error"));
            assertEquals(409, r2.getInteger("status").intValue());
            testContext.completeNow();
          });
        }, testContext::failNow);
    }

    @Test
    @DisplayName("Add a document with automatic ID generation and deprecated type")
    void addDocWithDeprecatedType(VertxTestContext testContext) {
      client
        .documentApi()
        .rxAdd(index, deprecatedType, document)
        .subscribe(r -> {
          testContext.verify(() -> {
            assertEquals("created", r.getString("result"));
            assertTrue(r.containsKey("_id"));
          });
          testContext.completeNow();
        }, testContext::failNow);
    }

    @Test
    @DisplayName("Add a document with automatic ID generation")
    void addDoc(VertxTestContext testContext) {
      client
        .documentApi()
        .rxAdd(index, document)
        .subscribe(r -> {
          testContext.verify(() -> {
            assertEquals("created", r.getString("result"));
            assertTrue(r.containsKey("_id"));
          });
          testContext.completeNow();
        }, testContext::failNow);
    }

    @Test
    @DisplayName("Check if a document exists with deprecated type")
    void checkExistsWithDeprecatedType(VertxTestContext testContext) {
      DocumentApi documentApi = client
        .documentApi();

      Single<Boolean> shouldBeTrue = documentApi
        .rxAdd(index, deprecatedType, document)
        .flatMap(r -> documentApi.rxExists(index, deprecatedType, r.getString("_id")));

      Single<Boolean> shouldBeFalse = documentApi
        .rxExists("abcdefghijklmnop", "1234567890", "1-2-3-4");

      Single
        .zip(shouldBeTrue, shouldBeFalse, (b1, b2) -> new boolean[]{b1, b2})
        .subscribe(booleans -> {
          testContext.verify(() -> assertArrayEquals(new boolean[]{true, false}, booleans));
          testContext.completeNow();
        }, testContext::failNow);
    }

    @Test
    @DisplayName("Check if a document exists")
    void checkExists(VertxTestContext testContext) {
      DocumentApi documentApi = client
        .documentApi();

      Single<Boolean> shouldBeTrue = documentApi
        .rxAdd(index, document)
        .flatMap(r -> documentApi.rxExists(index, r.getString("_id")));

      Single<Boolean> shouldBeFalse = documentApi
        .rxExists("abcdefghijklmnop", "1234567890", "1-2-3-4");

      Single
        .zip(shouldBeTrue, shouldBeFalse, (b1, b2) -> new boolean[]{b1, b2})
        .subscribe(booleans -> {
          testContext.verify(() -> assertArrayEquals(new boolean[]{true, false}, booleans));
          testContext.completeNow();
        }, testContext::failNow);
    }

    @Test
    @DisplayName("Get a document with deprecated type")
    void getDocWithDeprecatedType(VertxTestContext testContext) {
      DocumentApi documentApi = client
        .documentApi();
      documentApi
        .rxAdd(index, deprecatedType, document)
        .flatMap(r -> documentApi.rxGet(index, deprecatedType, r.getString("_id")))
        .subscribe(r -> {
          testContext.verify(() -> {
            assertEquals(true, r.getBoolean("found"));
            assertTrue(r.containsKey("_source"));
          });
          testContext.completeNow();
        }, testContext::failNow);
    }

    @Test
    @DisplayName("Get a document")
    void getDoc(VertxTestContext testContext) {
      DocumentApi documentApi = client
        .documentApi();
      documentApi
        .rxAdd(index, document)
        .flatMap(r -> documentApi.rxGet(index, r.getString("_id")))
        .subscribe(r -> {
          testContext.verify(() -> {
            assertEquals(true, r.getBoolean("found"));
            assertTrue(r.containsKey("_source"));
          });
          testContext.completeNow();
        }, testContext::failNow);
    }
  }
}
