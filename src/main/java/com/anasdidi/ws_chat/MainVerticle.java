package com.anasdidi.ws_chat;

import com.anasdidi.ws_chat.common.AppConfig;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.bridge.BridgeEventType;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.reactivex.config.ConfigRetriever;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.StaticHandler;
import io.vertx.reactivex.ext.web.handler.sockjs.SockJSHandler;

public class MainVerticle extends AbstractVerticle {

  // private static int counter = 0;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    ConfigRetriever retriever = ConfigRetriever.create(vertx, new ConfigRetrieverOptions()//
        .addStore(new ConfigStoreOptions().setType("env")));

    retriever.rxGetConfig().subscribe(config -> {
      AppConfig appConfig = AppConfig.create(config);
      Router router = Router.router(vertx);

      router.mountSubRouter("/eventbus", eventBusHandler());
      router.route().handler(staticHandler());

      vertx.createHttpServer().requestHandler(router).listen(appConfig.getAppPort(),
          appConfig.getAppHost(), server -> {
            if (server.succeeded()) {
              System.out.println("HTTP server started on port " + appConfig.getAppPort());
              startPromise.complete();
            } else {
              startPromise.fail(server.cause());
            }
          });
    }, e -> startPromise.fail(e));
  }

  private Router eventBusHandler() {
    SockJSBridgeOptions options = new SockJSBridgeOptions()
        .addOutboundPermitted(new PermittedOptions().setAddressRegex("out"))
        .addOutboundPermitted(new PermittedOptions().setAddress("ws-refresh-chat"))
        .addInboundPermitted(new PermittedOptions().setAddressRegex("in"))
        .addInboundPermitted(new PermittedOptions().setAddress("ws-send-message"));

    return SockJSHandler.create(vertx).bridge(options, event -> {
      System.out.println("event.type=" + event.type());
      if (event.getRawMessage() != null) {
        System.out.println(event.getRawMessage().encodePrettily());
      }

      if (event.type() == BridgeEventType.SOCKET_CREATED) {
      }

      if (event.type() == BridgeEventType.SEND) {
        JsonObject rawMessage = event.getRawMessage();
        String address = rawMessage.getString("address");

        if (address.equals("ws-send-message")) {
          JsonObject body = rawMessage.getJsonObject("body");
          String username = body.getString("username");
          String message = body.getString("message");

          vertx.eventBus().publish("ws-refresh-chat",
              new JsonObject().put("username", username).put("message", message));
        }
      }

      event.complete(true);
    });
  }

  private StaticHandler staticHandler() {
    return StaticHandler.create().setCachingEnabled(false);
  }
}
