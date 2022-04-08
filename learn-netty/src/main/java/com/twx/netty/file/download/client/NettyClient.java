package com.twx.netty.file.download.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author tangwx@soyuan.com.cn
 * @date 2021/12/21 上午9:31
 */
public class NettyClient {

    private static class Holder {
        public static final NettyClient client = new NettyClient();
    }

    public static NettyClient getInstance() {
        return Holder.client;
    }

    public boolean download(String savePath,String targetFile) {
        final FileDownloadHandler downloadHandler = new FileDownloadHandler(savePath, targetFile);
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    //适用于NIO传输的Channel类型
                    .channel(NioSocketChannel.class)
                    //设置服务器的InetSocketAddr-ess
                    //服务端是localAddress
                    .remoteAddress(new InetSocketAddress("localhost",8080))
                    //在创建Channel时，向ChannelPipeline中添加一个EchoClientHandler实例
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new IdleStateHandler(500,0,0, TimeUnit.MILLISECONDS));
                            pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                            pipeline.addLast(downloadHandler);
                        }
                    });
            ChannelFuture cf = bootstrap.connect().sync();
            cf.channel().closeFuture().sync();
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        finally {
            group.shutdownGracefully();
        }
        return false;
    }
}
