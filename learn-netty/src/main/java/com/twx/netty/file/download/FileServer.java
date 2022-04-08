package com.twx.netty.file.download;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * @author tangwx@soyuan.com.cn
 * @date 2019-05-22 23:58
 */
public class FileServer {
    private final int port;

    public FileServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        //设置端口值
        int port = Integer.parseInt(args[0]);
        new FileServer(port).start();
    }

    private void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //创建EventLoopGroup
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        //创建ServerBootstrap
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workerGroup)
                 //指定所使用的NIO传输Channel
                //使用NIO是因为它的可扩展性和彻底的异步性，它是目前使用最广泛的传输
                //如果你想要在自己的服务器中使用OIO传输，
                //将需要指定OioServerSocketChannel和OioEventLoopGroup 。
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                //使用指定的端口设置套接字地址
                .localAddress(new InetSocketAddress(port))
                .handler(new LoggingHandler(LogLevel.INFO))
                //添加一个EchoServerHandler到子Channel的ChannelPipeline
                //当有一个新的连接进入时，新的子Channel将会被创建
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //EchoServerHandler被标注为@Shareable，所以我们可以总是使用同样的实例
                        ChannelPipeline pipeline = ch.pipeline();
//                        pipeline.addLast(new IdleStateHandler(0, 500, 0, TimeUnit.MICROSECONDS));
//                        pipeline.addLast(new LineBasedFrameDecoder(8192));
                        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                        pipeline.addLast(new ChunkedWriteHandler());
                        pipeline.addLast(new FileServerHandler());
//                        pipeline.addLast(new HttpRequestDecoder());
//                        pipeline.addLast(new HttpResponseEncoder());
//                        pipeline.addLast(new CustomHttpServerHandler());

//                        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
//                        pipeline.addLast(new EchoServerHandler());
                    }
                });

        try {
            //异步绑定服务器：调用sync()方法阻塞等待直到绑定完成
            ChannelFuture future = bootstrap.bind().sync();
            //获取Channel的CloseFuture，并且阻塞当前线程直到它完成
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //关闭EventLoopGroup，释放所有的资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
