package server;

import org.eclipse.jetty.websocket.api.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static spark.Spark.init;
import static spark.Spark.staticFiles;
import static spark.Spark.webSocket;

/**
 * @author Oliver Lea
 */
public class Server {


    public Server(int port) {
        staticFiles.location("public");
        staticFiles.expireTime(600);
        webSocket("/sim", SimulationHandler.class);
    }

    public void run() {
        init();
    }
}
