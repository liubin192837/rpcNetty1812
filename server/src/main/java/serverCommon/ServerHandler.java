package serverCommon;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import modes.RequestRpc;
import modes.ResponseRpc;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ChannelInboundHandlerAdapter--channelActive");
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ChannelInboundHandlerAdapter----channelInactive");
        ctx.fireChannelInactive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RequestRpc requestRpc = (RequestRpc)msg;
        System.out.println("ChannelInboundHandlerAdapter:"+requestRpc.getMethodName());
        ResponseRpc responseRpc = new ResponseRpc();
        responseRpc.setResult("Server: I get you");
        ctx.writeAndFlush(responseRpc).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                System.out.println("operationComplete");
            }
        });
        /*.addListener(ChannelFutureListener.CLOSE)*/
        //ReferenceCountUtil.release(msg);

/*        RequestRpc requestRpc = (RequestRpc)msg;
        System.out.println("ChannelInboundHandlerAdapter:"+requestRpc.getMethodName());
        ResponseRpc responseRpc = new ResponseRpc();
        responseRpc.setResult("Server: I get you");*/
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
/*       ctx.writeAndFlush(Unpooled.EMPTY_BUFFER) //flush掉所有写回的数据
                .addListener(ChannelFutureListener.CLOSE); //当flush完成后关闭channel*/
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ChannelInboundHandlerAdapter-----channelWritabilityChanged");
        //ctx.fireChannelWritabilityChanged();
    }
}
