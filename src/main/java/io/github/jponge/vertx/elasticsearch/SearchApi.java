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
public interface SearchApi {

  @Fluent
  SearchApi search(String index, JsonObject query, Handler<AsyncResult<JsonObject>> handler);

  /**
   * Search in elasticsearch
   * @param index     Index name. Can be null.
   * @param type      Type (types are deprecated in elasticsearch). Can be null.
   * @param options   Options
   * @param query     Optional query
   * @param handler   Response handler
   * @return this
   * @see @{{@link #search(String, JsonObject, Handler)} if you are using default type
   */
  @Fluent
  SearchApi search(String index, String type, SearchApiOptions options, JsonObject query,
                        Handler<AsyncResult<JsonObject>> handler);
}
