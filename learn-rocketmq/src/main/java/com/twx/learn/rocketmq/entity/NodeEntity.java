package com.twx.learn.rocketmq.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
public class NodeEntity {

    private static final DateTimeFormatter f1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private final String producer = "SIMP";
    private final String from = "MQ-SIMP-005";
    private String bizId;
    private String time;
    private NodeData data;

    public NodeEntity(String event) {
        LocalDateTime now = LocalDateTime.now();
        this.time = f1.format(now);

        String uuid = UUID.randomUUID().toString();

        this.bizId = "XIY-SIMP-TURNAROUNDTUNING-" + f2.format(now) + "-" + uuid.split("-")[0];

        this.data = new NodeData(event);
    }

    @Data
    public static class NodeData {
        Integer resultCode = 200;
        Long timeStamp = System.currentTimeMillis();
        NodeResult result;

        public NodeData(String event) {
            this.result = new NodeResult(event);
        }
    }

    @Data
    public static class NodeResult {
        NodeBody output;

        public NodeResult(String event) {
            this.output = new NodeBody(event);
        }
    }

    @Data
    public static class NodeBody {
        String cameraId = "TJP-BZJD-JW235-QJ-1";
        String standNo = "235";
        String event;
        String type = "NEW";

        public NodeBody(String event) {
            this.event = event;
        }
    }
}
