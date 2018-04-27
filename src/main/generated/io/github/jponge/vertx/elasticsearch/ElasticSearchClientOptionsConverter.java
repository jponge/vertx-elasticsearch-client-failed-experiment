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
 * Converter for {@link io.github.jponge.vertx.elasticsearch.ElasticsearchClientOptions}.
 *
 * NOTE: This class has been automatically generated from the {@link io.github.jponge.vertx.elasticsearch.ElasticsearchClientOptions} original class using Vert.x codegen.
 */
public class ElasticsearchClientOptionsConverter {

  public static void fromJson(JsonObject json, ElasticsearchClientOptions obj) {
    if (json.getValue("hostname") instanceof String) {
      obj.setHostname((String)json.getValue("hostname"));
    }
    if (json.getValue("port") instanceof Number) {
      obj.setPort(((Number)json.getValue("port")).intValue());
    }
  }

  public static void toJson(ElasticsearchClientOptions obj, JsonObject json) {
    if (obj.getHostname() != null) {
      json.put("hostname", obj.getHostname());
    }
    json.put("port", obj.getPort());
  }
}