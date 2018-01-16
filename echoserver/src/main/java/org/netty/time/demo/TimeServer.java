package org.netty.time.demo;

import org.netty.time.demo.nio.utils.MultiplexerTimeServer;

public class TimeServer {


    public static void main(String[] args) {
        int port = 8081;

        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e) {

            }
        }

        MultiplexerTimeServer timerServer = new MultiplexerTimeServer(port);

        new Thread(timerServer, "NIO-MultiplexerTimeServer-001").start();
    }
}
