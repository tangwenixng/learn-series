FROM openjdk:8-jre-slim

#解决中文乱码问题
ENV LANG C.UTF-8

ENV TZ=Asia/Shanghai

VOLUME /tmp
ADD ./target/learn-rocketmq-0.0.1-SNAPSHOT.jar /root/app.jar
#ADD ./target/srs-console-xjar.jar /root
#ADD ./target/xjar /root

WORKDIR /root/

EXPOSE 8321

ENTRYPOINT ["java","-jar","/root/app.jar"]

