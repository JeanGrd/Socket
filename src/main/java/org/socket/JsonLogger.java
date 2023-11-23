package org.socket;

import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class JsonLogger {

    private static JsonLogger logger = null;
    private PrintWriter writer;
    private Socket socket;

    private JsonLogger() {
        try {
            socket = new Socket("localhost", 3244);
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JsonObject reqToJson(String host, int port, String proto, String type, String login, String result) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("host", host)
                .add("port", port)
                .add("proto", proto)
                .add("type", type)
                .add("login", login)
                .add("result", result)
                .add("date", new Date().toString());
        return builder.build();
    }

    private static JsonLogger getLogger() {
        if (logger == null) {
            logger = new JsonLogger();
        }
        return logger;
    }

    public static void log(String host, int port, String proto, String type, String login, String result) {
        JsonLogger logger = getLogger();
        JsonObject jsonLog = logger.reqToJson(host, port, proto, type, login, result);
        logger.writer.println(jsonLog.toString());
    }

}
