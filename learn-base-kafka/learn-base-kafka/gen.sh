#!/bin/bash
#运行此脚本生成ssl证书和keystore

#修改域名
# CN="172.26.5.123"
CN="*.soyuan.com.cn"
#修改保存路径
SAVE_DIR="/Users/twx/Downloads/ssl"
#生成keystore
keytool -keystore "$SAVE_DIR"/kafka.keystore.jks -alias kafka -validity 365 -genkey -keyalg RSA -storepass 123456 -keypass 123456 -dname "CN=$CN, OU=kafka, O=kafka, L=suzhou, ST=jiangsu, C=CN"

# 创建CA证书
openssl req -new -x509 -keyout "$SAVE_DIR"/ca-key -out "$SAVE_DIR"/ca-cert -days 365 -passin pass:123456 -passout pass:123456 -subj "/C=CN/ST=jiangsu/L=suzhou/O=kafka/CN=$CN"
#生成服务端证书: kafka-cert
keytool -keystore "$SAVE_DIR"/kafka.keystore.jks -alias kafka -certreq -file "$SAVE_DIR"/kafka-cert -storepass "123456" -keypass "123456"
# 创建信任证书: kafka.truststore.jks
keytool -keystore "$SAVE_DIR/kafka.truststore.jks" -alias CARoot -import -file "$SAVE_DIR/ca-cert" -storepass "123456" -keypass "123456" -noprompt

# 服务端证书签名: kafka-cert-signed
openssl x509 -req -CA "$SAVE_DIR"/ca-cert -CAkey "$SAVE_DIR"/ca-key -in "$SAVE_DIR"/kafka-cert -out "$SAVE_DIR"/kafka-cert-signed -days 365 -CAcreateserial -passin pass:"123456"
# 将CA证书导入到服务端keystore
keytool -keystore "$SAVE_DIR"/kafka.keystore.jks -alias CARoot -import -file "$SAVE_DIR"/ca-cert -storepass "123456" -keypass "123456" -noprompt
# 将签名的服务端证书(kafka-cert-signed)导入到服务器keystore
keytool -keystore "$SAVE_DIR"/kafka.keystore.jks -alias kafka -import -file "$SAVE_DIR"/kafka-cert-signed -storepass "123456" -keypass "123456"