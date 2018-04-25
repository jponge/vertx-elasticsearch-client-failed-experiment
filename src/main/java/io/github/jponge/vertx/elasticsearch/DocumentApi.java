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

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

@VertxGen
public interface DocumentApi {

  @Fluent
  DocumentApi addOrUpdate(String index, String type, String id, JsonObject document, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  DocumentApi addOrUpdate(String index, String type, String id, DocumentApiOptions options, JsonObject document, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  DocumentApi add(String index, String type, JsonObject document, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  DocumentApi exists(String index, String type, String id, Handler<AsyncResult<Boolean>> handler);

  @Fluent
  DocumentApi get(String index, String type, String id, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  DocumentApi get(String index, String type, String id, DocumentApiOptions options, Handler<AsyncResult<JsonObject>> handler);
}
