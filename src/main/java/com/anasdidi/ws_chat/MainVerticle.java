package com.anasdidi.ws_chat;

import com.anasdidi.ws_chat.common.AppConfig;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Promise;
import io.vertx.ext.bridge.BridgeEventType;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.reactivex.config.ConfigRetriever;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.StaticHandler;
import io.vertx.reactivex.ext.web.handler.sockjs.SockJSHandler;

public class MainVerticle extends AbstractVerticle {

  private static int counter = 0;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    ConfigRetriever retriever = ConfigRetriever.create(vertx, new ConfigRetrieverOptions()//
        .addStore(new ConfigStoreOptions().setType("env")));

    retriever.rxGetConfig().subscribe(config -> {
      AppConfig.create(config);
      Router router = Router.router(vertx);

      router.mountSubRouter("/eventbus", eventBusHandler());
      router.route().handler(staticHandler());

      vertx.createHttpServer().requestHandler(router).listen(8888, "0.0.0.0", server -> {
        if (server.succeeded()) {
          System.out.println("HTTP server started on port 8888");
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
        .addInboundPermitted(new PermittedOptions().setAddressRegex("in"));

    return SockJSHandler.create(vertx).bridge(options, event -> {
      if (event.type() == BridgeEventType.SOCKET_CREATED) {
        vertx.eventBus().publish("out", counter++);
        System.out.println("A socker was created");
      }

      if (event.type() == BridgeEventType.SEND) {
        vertx.eventBus().publish("out", counter++);
        System.out.println("Client to server");
        System.out.println(event.getRawMessage().encodePrettily());
      }

      event.complete(true);
    });
  }

  private StaticHandler staticHandler() {
    return StaticHandler.create().setCachingEnabled(false);
  }
}
