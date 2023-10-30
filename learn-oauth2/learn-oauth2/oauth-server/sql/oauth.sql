/*
 Navicat Premium Data Transfer

 Source Server         : 172.26.10.100
 Source Server Type    : MySQL
 Source Server Version : 80019 (8.0.19)
 Source Host           : 172.26.10.100:3306
 Source Schema         : oauth

 Target Server Type    : MySQL
 Target Server Version : 80019 (8.0.19)
 File Encoding         : 65001

 Date: 30/10/2023 15:25:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(48) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int DEFAULT NULL,
  `refresh_token_validity` int DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
BEGIN;
INSERT INTO `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES ('implicit', 'res2', '$2a$10$BGRoWYPm9B1CJRcHJYV9BOyGbWVKOr9S.E5OFotqw9a9nsR0zeH8u', 'all', 'implicit,refresh_token', 'http://localhost:8081/index.html', NULL, 7200, 259200, NULL, NULL);
INSERT INTO `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES ('javaboy', 'res1', '$2a$10$BGRoWYPm9B1CJRcHJYV9BOyGbWVKOr9S.E5OFotqw9a9nsR0zeH8u', 'all', 'authorization_code,refresh_token', 'http://localhost:8081/index.html', NULL, 7200, 259200, NULL, NULL);
INSERT INTO `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES ('twx', 'res1', '$2a$10$BGRoWYPm9B1CJRcHJYV9BOyGbWVKOr9S.E5OFotqw9a9nsR0zeH8u', 'all', 'authorization_code,refresh_token', 'http://localhost:8081/index.html', NULL, 7200, 259200, NULL, NULL);
INSERT INTO `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES ('u_client', 'res1,res4', '$2a$10$BGRoWYPm9B1CJRcHJYV9BOyGbWVKOr9S.E5OFotqw9a9nsR0zeH8u', 'all', 'client_credentials,refresh_token', 'http://localhost:8081/index.html', NULL, 7200, 259200, NULL, NULL);
INSERT INTO `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES ('u_pwd', 'res3', '$2a$10$BGRoWYPm9B1CJRcHJYV9BOyGbWVKOr9S.E5OFotqw9a9nsR0zeH8u', 'all', 'password,refresh_token', 'http://localhost:8081/index.html', NULL, 7200, 259200, NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
