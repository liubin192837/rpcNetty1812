package clientCommon;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import modes.Constants;
import modes.RequestRpc;
import modes.ResponseRpc;
import protocols.RpcDecoder;
import protocols.RpcEncoder;


public class ClientHelper {
    private static ClientHelper clientHelper;
    private volatile boolean haveInit = false;
    private volatile EventLoopGroup group;
    private volatile Channel channel;
    private String result = null;
    private String host = Constants.HOST;
    private int port = Constants.PORT;
    private ClientHandler clientHandler = new ClientHandler();

    private ClientHelper() {
        this.initConnect();
    }

    public static ClientHelper getClientHelper() {
        if (clientHelper == null) {
            System.out.println("clientHelper == null");
            clientHelper = new ClientHelper();
        }
        return clientHelper;
    }

    public boolean send(RequestRpc requestRpc) {
        boolean result = false;
        try {
            //阻塞发送
            channel.writeAndFlush(requestRpc).sync();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String send2(RequestRpc requestRpc) {
        if (host == null || port == -1) {
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
                    pipeline.addLast(clientHandler);
                }
            });
            bootstrap.option(ChannelOption.TCP_NODELAY, true);

            ChannelFuture future = bootstrap.connect(host, port).sync();

            Channel channel = future.channel();
            channel.writeAndFlush(requestRpc).sync();
            System.out.println("result 1:" + result);
            if (null == result) {
                channel.closeFuture().sync();
            }

            System.out.println("result 2:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
        return result;
    }

    private void initConnect() {
        System.out.println("initConnect");
        if (host == null || port == -1) {
            throw new RuntimeException("host or port can't  null");
        }
        group = new NioEventLoopGroup();
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
                    pipeline.addLast(clientHandler);
                }
            });
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            ChannelFuture future = bootstrap.connect(host, port).sync();
            channel = future.channel();
            haveInit = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //group.shutdownGracefully();
        }
    }

    public void close() {
    /*    System.out.println("close:"+(clientHandler != null));
        if (clientHandler != null) {
            clientHandler.close();
        }*/
        if(group!=null){
            group.shutdownGracefully();
        }
    }
}
