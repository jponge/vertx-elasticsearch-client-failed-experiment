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
import java.util.Objects;

@DataObject(generateConverter = true)
public class DocumentApiOptions {

  private Map<String, Object> options = new HashMap<>();

  public DocumentApiOptions() {
  }

  public DocumentApiOptions(DocumentApiOptions other) {
    this.options = new HashMap<>(other.options);
  }

  public DocumentApiOptions(JsonObject jsonObject) {
    this.options = new HashMap<>(jsonObject.getMap());
  }

  public Map<String, Object> getOptions() {
    return options;
  }

  public void setOptions(Map<String, Object> options) {
    this.options = options;
  }

  public DocumentApiOptions version(long version) {
    options.put("version", version);
    return this;
  }

  public DocumentApiOptions versionMatches() {
    options.put("version_type", "internal");
    return this;
  }

  public DocumentApiOptions versionStrictlyHigher() {
    options.put("version_type", "external_gt");
    return this;
  }

  public DocumentApiOptions versionHigher() {
    options.put("version_type", "external_gte");
    return this;
  }

  public DocumentApiOptions opType(String type) {
    options.put("op_type", type);
    return this;
  }

  public DocumentApiOptions routing(String routing) {
    options.put("routing", routing);
    return this;
  }

  public DocumentApiOptions waitForAllActiveShards() {
    options.put("wait_for_active_shards", "all");
    return this;
  }

  public DocumentApiOptions waitForActiveShards(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException("The number of active shards to wait for must be > 0");
    }
    options.put("wait_for_active_shards", n);
    return this;
  }

  public DocumentApiOptions refresh() {
    options.put("refresh", true);
    return this;
  }

  public DocumentApiOptions refreshWaitFor() {
    options.put("refresh", "wait_for");
    return this;
  }

  public DocumentApiOptions timeout(String spec) {
    options.put("timeout", spec);
    return this;
  }

  public DocumentApiOptions realtime(boolean value) {
    options.put("realtime", value);
    return this;
  }
}
