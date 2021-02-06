package com.anasdidi.ws_chat;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.anasdidi.ws_chat.common.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.reactivex.core.Vertx;

@ExtendWith(VertxExtension.class)
public class TestMainVerticle {

  @BeforeEach
  void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
    vertx.deployVerticle(new MainVerticle(),
        testContext.succeeding(id -> testContext.completeNow()));
  }

  @Test
  void verticle_deployed(Vertx vertx, VertxTestContext testContext) throws Throwable {
    testContext.completeNow();
  }

  @Test
  void testAppConfigSuccess(Vertx vertx, VertxTestContext testContext) throws Exception {
    AppConfig appConfig = AppConfig.instance();

    testContext.verify(() -> {
      assertNotNull(appConfig.getAppHost());
      assertNotNull(appConfig.getAppPort());

      testContext.completeNow();
    });
  }
}
