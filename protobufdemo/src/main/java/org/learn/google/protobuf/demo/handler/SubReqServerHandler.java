package org.learn.google.protobuf.demo.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import sz.learn.protobuf.SubscribeReq;
import sz.learn.protobuf.SubscribeResp;

@ChannelHandler.Sharable
public class SubReqServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        SubscribeReq.SubscribeRequest req = (SubscribeReq.SubscribeRequest) msg;

        if ("Lilinfeng".equalsIgnoreCase(req.getUserName())) {
            System.out.println("Service accept client subscribe req : [" + req.toString() + "]");
            ctx.writeAndFlush(resp(req.getSubReqID()));
        }
    }

    private SubscribeResp.SubscribeResponse resp(int subReqID) {
        SubscribeResp.SubscribeResponse.Builder builder = SubscribeResp.SubscribeResponse.newBuilder();
        builder.setSubReqID(subReqID);
        builder.setRespCode(0);
        builder.setDesc("Netty book order succeed, 3 days later, sent to the designated address");
        return builder.build();

    }

}
