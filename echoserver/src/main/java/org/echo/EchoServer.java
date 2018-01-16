package org.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.echo.handler.EchoServerHandler;

import java.net.InetSocketAddress;

public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();//1
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();//2

            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)//3
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .localAddress(new InetSocketAddress(port))//4
                    .childHandler(new ChannelInitializer<SocketChannel>() {//5

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
                            socketChannel.pipeline()
                                    .addLast(new DelimiterBasedFrameDecoder(1024, delimiter))
                                    .addLast(new StringDecoder())
                                    .addLast(new EchoServerHandler());
                        }
                    });

            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully().sync();//8
            System.out.println("end!");
        }
    }

    public static void main(String[] args) throws Exception {

        int port = 8881;
        new EchoServer(port).start();
    }
}
