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

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class ElasticsearchClientOptions {

  public static final String HOSTNAME = "hostname";
  public static final String PORT = "port";

  private String hostname = "localhost";
  private int port = 9200;

  public ElasticsearchClientOptions() {
  }

  public ElasticsearchClientOptions(ElasticsearchClientOptions other) {
    this.hostname = other.hostname;
    this.port = other.port;
  }

  public ElasticsearchClientOptions(JsonObject jsonObject) {
    this.hostname = jsonObject.getString(HOSTNAME);
    this.port = jsonObject.getInteger(PORT);
  }

  public String getHostname() {
    return hostname;
  }

  public ElasticsearchClientOptions setHostname(String hostname) {
    this.hostname = hostname;
    return this;
  }

  public int getPort() {
    return port;
  }

  public ElasticsearchClientOptions setPort(int port) {
    this.port = port;
    return this;
  }

  public JsonObject toJson() {
    return new JsonObject()
      .put(HOSTNAME, hostname)
      .put(PORT, port);
  }
}
