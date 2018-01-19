package org.learn.google.protobuf.demo.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import sz.learn.protobuf.SubscribeReq;

@ChannelHandler.Sharable
public class SubReqClientHandler extends ChannelHandlerAdapter {

    public SubReqClientHandler() {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive server response [" + msg + "]");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; ++i) {
            ctx.write(subreq(i));
        }
        ctx.flush();
    }

    private SubscribeReq.SubscribeRequest subreq(int i) {
        SubscribeReq.SubscribeRequest.Builder builder = SubscribeReq.SubscribeRequest.newBuilder();
        builder.setSubReqID(i);
        builder.setUserName("Lilinfeng");
        builder.setProductName("Netty book For protoBuf");
        builder.setAddress("Nanjing Yuhuating");
        return builder.build();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
