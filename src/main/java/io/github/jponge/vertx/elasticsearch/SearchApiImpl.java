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

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.cli.UsageMessageFormatter;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.ext.web.codec.BodyCodec;

import static io.vertx.core.Future.failedFuture;
import static io.vertx.core.Future.succeededFuture;

class SearchApiImpl implements SearchApi {

  private static final SearchApiOptions EMPTY_OPTIONS = new SearchApiOptions();

  private final Vertx vertx;
  private final ElasticsearchClientOptions options;
  private final WebClient webClient;

  SearchApiImpl(Vertx vertx, ElasticsearchClientOptions options) {
    this.vertx = vertx;
    this.options = options;
    webClient = WebClient.create(vertx, new WebClientOptions()
      .setKeepAlive(true)
      .setDefaultHost(options.getHostname())
      .setDefaultPort(options.getPort()));
  }

  @Override
  public SearchApi search(String index, JsonObject query, Handler<AsyncResult<JsonObject>> handler) {
    return search(index, null, EMPTY_OPTIONS, query, handler);
  }

  @Override
  public SearchApi search(String index, String type, SearchApiOptions options, JsonObject query,
                          Handler<AsyncResult<JsonObject>> handler) {
    String url = "";
    if (index != null) {
      url = index + "/";
      if (type != null) {
        url = url + type + "/";
      }
    }
    url = url + "_search";

    HttpRequest<JsonObject> request;
    request = webClient
      .get(url)
      .as(BodyCodec.jsonObject());
    applyOptions(options, request);
    request.sendJsonObject(query, ar -> {
      if (ar.succeeded()) {
        handler.handle(succeededFuture(ar.result().body()));
      } else {
        handler.handle(failedFuture(ar.cause()));
      }
    });
    return this;
  }

  private void applyOptions(SearchApiOptions options, HttpRequest<JsonObject> request) {
    options.getOptions().forEach((k, v) -> request.addQueryParam(k, v.toString()));
  }
}
