package clientCommon;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import modes.RequestRpc;
import modes.ResponseRpc;
import protocols.RpcDecoder;
import protocols.RpcEncoder;


public class ClientHelper{
    private String result = null;
    private String host = null;
    private int port = -1;


    public ClientHelper(String host, int port){
        this.port = port;
        this.host = host;
    }


    public String send(RequestRpc requestRpc) {
        if(host == null || port == -1){
            return "host or port can't  null";
        }
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel channel) throws Exception {
                    ChannelPipeline pipeline = channel.pipeline();
                    pipeline.addLast(new RpcDecoder(ResponseRpc.class));
                    pipeline.addLast(new RpcEncoder());
                    pipeline.addLast(new ClientHandler());
                }
            });
            bootstrap.option(ChannelOption.TCP_NODELAY, true);

            ChannelFuture future = bootstrap.connect(host, port).sync();

            Channel channel = future.channel();
            channel.writeAndFlush(requestRpc).sync();
            System.out.println("result 1:"+result);
            if(null == result){
               channel.closeFuture().sync();
            }

            System.out.println("result 2:"+result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
        return result;
    }
    public String send2(RequestRpc requestRpc) {
        if(host == null || port == -1){
            return "host or port can't  null";
        }
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel channel) throws Exception {
                    ChannelPipeline pipeline = channel.pipeline();
                    pipeline.addLast(new RpcEncoder());
                    pipeline.addLast(new RpcDecoder(ResponseRpc.class));
                    pipeline.addLast(new ClientHandler());
                }
            });
            bootstrap.option(ChannelOption.TCP_NODELAY, true);

            ChannelFuture future = bootstrap.connect(host, port).sync();

            Channel channel = future.channel();
            channel.writeAndFlush(requestRpc).sync();
            System.out.println("result 1:"+result);
            if(null == result){
                channel.closeFuture().sync();
            }

            System.out.println("result 2:"+result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
        return result;
    }
}
