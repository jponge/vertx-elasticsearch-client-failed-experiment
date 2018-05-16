/*
 * Copyright (c) 2014 Red Hat, Inc. and others
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.github.jponge.vertx.elasticsearch;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link io.github.jponge.vertx.elasticsearch.SearchApiOptions}.
 *
 * NOTE: This class has been automatically generated from the {@link io.github.jponge.vertx.elasticsearch.SearchApiOptions} original class using Vert.x codegen.
 */
public class SearchApiOptionsConverter {

  public static void fromJson(JsonObject json, SearchApiOptions obj) {
    if (json.getValue("options") instanceof JsonObject) {
      java.util.Map<String, java.lang.Object> map = new java.util.LinkedHashMap<>();
      json.getJsonObject("options").forEach(entry -> {
        if (entry.getValue() instanceof Object)
          map.put(entry.getKey(), entry.getValue());
      });
      obj.setOptions(map);
    }
  }

  public static void toJson(SearchApiOptions obj, JsonObject json) {
    if (obj.getOptions() != null) {
      JsonObject map = new JsonObject();
      obj.getOptions().forEach((key,value) -> map.put(key, value));
      json.put("options", map);
    }
  }
}