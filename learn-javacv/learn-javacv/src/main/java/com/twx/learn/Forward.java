package com.twx.learn;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.ffmpeg.avcodec.AVCodecParameters;
import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.ffmpeg.avformat.AVStream;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.*;

import java.util.concurrent.TimeUnit;

import static org.bytedeco.ffmpeg.global.avcodec.av_packet_unref;

/**
 * rtsp=>rtmp
 * 将rtsp流推到rtmp
 */
@Slf4j
public class Forward
{
    public static void main( String[] args )
    {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber("rtsp://admin:szy12345@172.26.5.15:554/Streaming/Channels/101");

        log.info("开始初始化帧抓取器...");
        long startTime = System.currentTimeMillis();
        try {
            // tcp用于解决丢包问题
            grabber.setOption("rtsp_transport", "tcp");
            // 设置采集器构造超时时间
            grabber.setOption("stimeout", "2000000");
            grabber.setOption("rtsp_flags", "prefer_tcp");
            // 如果入参等于true，还会调用avformat_find_stream_info方法获取流的信息，
            // 放入AVFormatContext类型的成员变量oc中
            grabber.start(true);
            log.info("帧抓取器初始化完成，耗时[{}]毫秒", System.currentTimeMillis()-startTime);
        } catch (FFmpegFrameGrabber.Exception e) {
            log.error(e.getMessage(), e);
        }
        //初始化的解码器信息存在放在grabber的成员变量oc中
        AVFormatContext formatContext = grabber.getFormatContext();

        int streamNum = formatContext.nb_streams();
        // 没有媒体流就不用继续了
        if (streamNum<1) {
            log.error("文件内不存在媒体流");
            return;
        }
        // 取得视频的帧率
        double videoFrameRate = grabber.getVideoFrameRate();

        log.info("视频帧率[{}]，视频时长[{}]秒，媒体流数量[{}]",
                videoFrameRate,
                formatContext.duration()/1000000,
                streamNum);

        for (int i = 0; i < streamNum; i++) {
            AVStream avStream = formatContext.streams(i);
            AVCodecParameters codecpar = avStream.codecpar();
            int codecType = codecpar.codec_type();
            int codecId = codecpar.codec_id();
            log.info("流的索引[{}]，编码器类型[{}]，编码器ID[{}]", i, codecType, codecId);
        }

        int height = grabber.getImageHeight();
        int width = grabber.getImageWidth();
        int audioChannels = grabber.getAudioChannels();
        int pixelFormat = grabber.getPixelFormat();

        log.info("视频宽度[{}]，视频高度[{}]，音频通道数[{}], pixelFormat: [{}]", width, height, audioChannels, pixelFormat);

        log.info("开始初始化帧抓取器");
        startTime = System.currentTimeMillis();

        // 实例化FFmpegFrameRecorder
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("rtmp://172.26.1.22:19351/source/cv1",
                width, height, audioChannels);
        // 画面质量参数，0~51；18~28是一个合理范围
        recorder.setVideoOption("crf", "28");
        recorder.setVideoBitrate(grabber.getVideoBitrate());
        // 设置编码格式；默认是AV_CODEC_ID_NONE:0
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
//        recorder.setVideoCodec(grabber.getVideoCodec());
        // 设置封装格式
        recorder.setFormat("flv");
//        recorder.setPixelFormat(AV_PIX_FMT_YUVJ444P);
        //设置帧率;默认30帧
        recorder.setFrameRate(videoFrameRate);
        // 两个关键帧之间的帧数
        recorder.setGopSize((int)videoFrameRate);
        try {
            // 初始化帧录制器，例如数据结构（音频流、视频流指针，编码器），
            // 调用av_guess_format方法，确定视频输出时的封装方式，
            // 媒体上下文对象的内存分配，
            // 编码器的各项参数设置
            recorder.start(formatContext);
            log.info("帧录制初始化完成，耗时[{}]毫秒", System.currentTimeMillis()-startTime);

            log.info("开始推流...");
            startTime = System.currentTimeMillis();

            // 假设一秒钟15帧，那么两帧间隔就是(1000/15)毫秒
            int interVal = 1000/(int)videoFrameRate;
            // 发送完一帧后sleep的时间，不能完全等于(1000/frameRate)，不然会卡顿，
            // 要更小一些，这里取八分之一
            interVal/=8;

            //抓取一帧数据
            AVPacket pkt = null;
            long oldDts = 0;
            while ((pkt = grabber.grabPacket()) != null) {
                long dts = pkt.dts();
                if (oldDts > dts) {
                    continue;
                }
                oldDts = dts;
                // 取出的每一帧，都推送到SRS
                recorder.recordPacket(pkt);
                av_packet_unref(pkt);
                // 停顿一下再推送
                TimeUnit.MILLISECONDS.sleep(interVal);
            }
        } catch (FFmpegFrameRecorder.Exception e) {
            log.error(e.getMessage(), e);
            return;
        } catch (FFmpegFrameGrabber.Exception e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }



        try {
            // 关闭帧录制器
            recorder.close();
            // 关闭帧录制器
            grabber.close();
        } catch (FrameGrabber.Exception e) {
            throw new RuntimeException(e);
        } catch (FrameRecorder.Exception e) {
            throw new RuntimeException(e);
        }
    }
}
