package clientCommon;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import modes.ResponseRpc;

public class ClientHandler extends SimpleChannelInboundHandler<ResponseRpc> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResponseRpc msg) throws Exception {
        if (msg.getException() != null) {
            System.out.println(msg.getException());
        } else {
            System.out.println(msg.getResult());
        }

    }
}
