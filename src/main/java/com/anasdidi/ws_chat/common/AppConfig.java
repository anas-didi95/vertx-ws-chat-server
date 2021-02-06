package com.anasdidi.ws_chat.common;

import io.vertx.core.json.JsonObject;

public final class AppConfig {

  private static AppConfig appConfig;
  private final JsonObject json;

  private AppConfig(JsonObject json) {
    this.json = json;
  }

  public static AppConfig create(JsonObject json) {
    appConfig = new AppConfig(json);
    return appConfig;
  }

  public static AppConfig instance() throws Exception {
    if (appConfig == null) {
      throw new Exception("AppConfig is null!");
    }

    return appConfig;
  }

  @Override
  public String toString() {
    return new JsonObject()//
        .put("APP_HOST", getAppHost())//
        .put("APP_PORT", getAppPort())//
        .encodePrettily();
  }

  public String getAppHost() {
    return json.getString("APP_HOST");
  }

  public int getAppPort() {
    return json.getInteger("APP_PORT");
  }
}
