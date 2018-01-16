package org.netty.time.demo;

import org.netty.time.demo.nio.utils.TimeClientHandle;

public class TimeClient {


    public static void main(String[] args) {

        int port = 8081;

        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (Exception e) {
                port = 8081;
            }
        }

        new Thread(new TimeClientHandle("127.0.0.1", port), "TimeClient-001").start();
    }
}
