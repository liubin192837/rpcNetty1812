package clientCommon;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import modes.RequestRpc;
import modes.ResponseRpc;

import java.util.concurrent.ConcurrentHashMap;

public class ClientHandler extends SimpleChannelInboundHandler<ResponseRpc> {
    public static ConcurrentHashMap<String, RequestRpc> waitingRPC = new ConcurrentHashMap<>();
    public volatile Channel channel;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResponseRpc msg) throws Exception {
        System.out.println("channelRead0 .........................:"+this);
        if(null != msg.getException()){
            throw new RuntimeException("server run error:"+msg.getException());
        } else {
            RequestRpc requestRpc = waitingRPC.get(msg.getRequestId());
            requestRpc.setResult(msg.getResult());
        }
        //ctx.channel().writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered .........................:"+ctx.channel());
        System.out.println("channelRegistered .........................:"+this);
        channel = ctx.channel();
        ctx.fireChannelRegistered();
    }

    public void close(){
        System.out.println("channelRegistered .........................:"+this);
        System.out.println("2 close:"+(null != channel)+":"+channel);
        if(null != channel){
            channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }
}
