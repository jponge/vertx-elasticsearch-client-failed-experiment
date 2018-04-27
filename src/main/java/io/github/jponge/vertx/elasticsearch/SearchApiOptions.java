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

import java.util.HashMap;
import java.util.Map;

@DataObject(generateConverter = true)
public class SearchApiOptions {

  private Map<String, Object> options = new HashMap<>();

  public SearchApiOptions() {
  }

  public SearchApiOptions(SearchApiOptions other) {
    this.options = new HashMap<>(other.options);
  }

  public SearchApiOptions(JsonObject jsonObject) {
    this.options = new HashMap<>(jsonObject.getMap());
  }

  public Map<String, Object> getOptions() {
    return options;
  }

  public void setOptions(Map<String, Object> options) {
    this.options = options;
  }

  public SearchApiOptions routing(String routing) {
    options.put("routing", routing);
    return this;
  }

  public SearchApiOptions waitForAllActiveShards() {
    options.put("wait_for_active_shards", "all");
    return this;
  }

  public SearchApiOptions waitForActiveShards(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException("The number of active shards to wait for must be > 0");
    }
    options.put("wait_for_active_shards", n);
    return this;
  }

  public SearchApiOptions timeout(String spec) {
    options.put("timeout", spec);
    return this;
  }
}
