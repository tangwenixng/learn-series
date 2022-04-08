package com.twx.netty.file.download.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.time.LocalDateTime;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author tangwx@soyuan.com.cn
 * @date 2021/12/20 下午4:17
 */
public class FileDownloadHandler extends ChannelInboundHandlerAdapter {

    private String savePath;
    private String targetFile;

//    private Boolean success=true;

//    private BlockingQueue<Boolean> promiseQueue = new ArrayBlockingQueue<>(1);

    public FileDownloadHandler(String savePath,String targetFile) {
        this.savePath = savePath;
        this.targetFile = targetFile;
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端channelActive>>>"+ctx.channel().hashCode());
        File file = new File(savePath);
        if (file.exists()) {
            file.delete();
        }
        ByteBuf byteBuf = Unpooled.copiedBuffer(targetFile, CharsetUtil.UTF_8);
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        if (byteBuf.getInt(0) == 404) {
            System.out.println("客户端: 服务器文件不存在....");
        }else {
            try (FileOutputStream out = new FileOutputStream(savePath, true)) {
                FileChannel channel = out.getChannel();

                ByteBuffer nioBuffer = byteBuf.nioBuffer();
                channel.write(nioBuffer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
//            IdleStateEvent e = (IdleStateEvent) evt;
//            if (e.state() == IdleState.READER_IDLE) {
//                ctx.close();
//                promiseQueue.offer(success);
//            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    /*public boolean getResult() {
        try {
            return promiseQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }*/
}
