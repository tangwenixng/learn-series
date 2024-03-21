package com.twx.learn;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.ffmpeg.avcodec.AVCodecParameters;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.ffmpeg.avformat.AVStream;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.*;

import java.util.concurrent.TimeUnit;

import static org.bytedeco.ffmpeg.global.avutil.AV_PIX_FMT_YUV420P;

/**
 * 转录MP4
 */
@Slf4j
public class SaveMp4
{
    public static void main( String[] args )
    {
        // ffmepg日志级别
        avutil.av_log_set_level(avutil.AV_LOG_DEBUG);
        FFmpegLogCallback.set();

        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber("/Users/twx/Movies/qyc_jw.mp4");
        log.info("开始初始化帧抓取器...");
        long startTime = System.currentTimeMillis();
        try {
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
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("/Users/twx/Movies/tmp/test-cv1.mp4",
                width, height, audioChannels);
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4);
        // 设置封装格式
        recorder.setFormat("mp4");
//        recorder.setPixelFormat(AV_PIX_FMT_YUV420P);
        //设置帧率;默认30帧
        recorder.setFrameRate(videoFrameRate);
        //设置视频质量 0无损
        recorder.setVideoQuality(0);
        try {
            // 初始化帧录制器，例如数据结构（音频流、视频流指针，编码器），
            // 调用av_guess_format方法，确定视频输出时的封装方式，
            // 媒体上下文对象的内存分配，
            // 编码器的各项参数设置
            recorder.start();
            log.info("帧录制初始化完成，耗时[{}]毫秒", System.currentTimeMillis()-startTime);

            log.info("开始推流...");
            startTime = System.currentTimeMillis();

            // 假设一秒钟15帧，那么两帧间隔就是(1000/15)毫秒
            int interVal = 1000/(int)videoFrameRate;
            // 发送完一帧后sleep的时间，不能完全等于(1000/frameRate)，不然会卡顿，
            // 要更小一些，这里取八分之一
            interVal/=8;

            int videoFrameNum = 0;
            int audioFrameNum = 0;
            int dataFrameNum = 0;

            long videoTS=0;
            //抓取一帧数据
            Frame frame;
            while ((frame = grabber.grab()) != null) {
                videoTS = 1000 * (System.currentTimeMillis() - startTime);
                // 时间戳
                recorder.setTimestamp(videoTS);

                // 有图像，就把视频帧加一
                if (frame.image != null) {
                    videoFrameNum++;
                }
                if (frame.samples != null) {
                    audioFrameNum++;
                }
                // 有数据，就把数据帧加一
                if (frame.data != null) {
                    dataFrameNum++;
                }
                // 取出的每一帧，都推送到SRS
                recorder.record(frame);
                // 停顿一下再推送
                TimeUnit.MILLISECONDS.sleep(interVal);
            }

            log.info("推送完成，视频帧[{}]，音频帧[{}]，数据帧[{}]，耗时[{}]秒",
                    videoFrameNum,
                    audioFrameNum,
                    dataFrameNum,
                    (System.currentTimeMillis()-startTime)/1000);

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
