/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50620
Source Host           : localhost:3306
Source Database       : mhly

Target Server Type    : MYSQL
Target Server Version : 50620
File Encoding         : 65001

Date: 2016-08-29 00:43:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for wxm_mhly_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `wxm_mhly_sys_menu`;
CREATE TABLE `wxm_mhly_sys_menu` (
  `id` char(36) NOT NULL COMMENT '菜单ID',
  `menuName` varchar(32) NOT NULL COMMENT '菜单名称',
  `parentId` char(36) NOT NULL DEFAULT '00000000-0000-0000-0000-000000000000' COMMENT '父ID',
  `menuUrl` varchar(255) DEFAULT NULL COMMENT '菜单URL',
  `picUrl` varchar(255) DEFAULT NULL COMMENT '图片URL',
  `menuCss` varchar(32) DEFAULT NULL COMMENT '菜单CSS样式',
  `menuIconCss` varchar(32) DEFAULT NULL COMMENT '菜单Icon样式',
  `menuOrder` int(11) DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `createdBy` char(36) NOT NULL COMMENT '创建者ID',
  `createdAt` char(19) NOT NULL COMMENT '创建日期',
  `reviserId` char(36) NOT NULL COMMENT '更新者ID',
  `reviseDate` char(19) NOT NULL COMMENT '更新日期',
  `isValid` char(1) NOT NULL DEFAULT '1' COMMENT '是否有效[0:否][1:是]',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='系统菜单';

-- ----------------------------
-- Table structure for wxm_mhly_sys_menu_operate
-- ----------------------------
DROP TABLE IF EXISTS `wxm_mhly_sys_menu_operate`;
CREATE TABLE `wxm_mhly_sys_menu_operate` (
  `id` char(36) NOT NULL COMMENT '菜单操作ID',
  `menuId` char(36) NOT NULL COMMENT '菜单ID。取自WXM_MHLY_SYS_MENU.id',
  `opId` char(36) NOT NULL COMMENT '操作ID。取自WXM_MHLY_SYS_OPERATE.id',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='系统菜单操作';

-- ----------------------------
-- Table structure for wxm_mhly_sys_operate
-- ----------------------------
DROP TABLE IF EXISTS `wxm_mhly_sys_operate`;
CREATE TABLE `wxm_mhly_sys_operate` (
  `id` char(36) NOT NULL COMMENT '操作ID',
  `opCode` varchar(16) NOT NULL COMMENT '操作代码',
  `opName` varchar(32) NOT NULL COMMENT '操作名称',
  `opOrder` int(11) DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `createdBy` char(36) NOT NULL COMMENT '创建者ID',
  `createdAt` char(19) NOT NULL COMMENT '创建日期',
  `reviserId` char(36) NOT NULL COMMENT '更新者ID',
  `reviseDate` char(19) NOT NULL COMMENT '更新日期',
  `isValid` char(1) NOT NULL DEFAULT '1' COMMENT '是否有效[0:否][1:是]',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='系统操作';

-- ----------------------------
-- Table structure for wxm_mhly_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `wxm_mhly_sys_role`;
CREATE TABLE `wxm_mhly_sys_role` (
  `id` char(36) NOT NULL COMMENT '角色ID',
  `roleCode` varchar(32) NOT NULL COMMENT '角色编号',
  `roleName` varchar(32) NOT NULL COMMENT '角色名称',
  `roleIconCss` varchar(32) DEFAULT NULL COMMENT '角色Icon样式',
  `roleOrder` int(11) DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `createdBy` char(36) NOT NULL COMMENT '创建者ID',
  `createdAt` char(19) NOT NULL COMMENT '创建日期',
  `reviserId` char(36) NOT NULL COMMENT '更新者ID',
  `reviseDate` char(19) NOT NULL COMMENT '更新日期',
  `isValid` char(1) NOT NULL DEFAULT '1' COMMENT '是否有效[0:否][1:是]',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='系统角色';

-- ----------------------------
-- Table structure for wxm_mhly_sys_role_menu_operate
-- ----------------------------
DROP TABLE IF EXISTS `wxm_mhly_sys_role_menu_operate`;
CREATE TABLE `wxm_mhly_sys_role_menu_operate` (
  `id` char(36) NOT NULL COMMENT '角色菜单操作ID',
  `roleId` char(36) NOT NULL COMMENT '角色ID。取自WXM_MHLY_SYS_ROLE.id',
  `menuOpId` char(36) NOT NULL COMMENT '菜单ID。WXM_MHLY_SYS_MENU.id',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wxm_mhly_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `wxm_mhly_sys_user`;
CREATE TABLE `wxm_mhly_sys_user` (
  `id` char(36) NOT NULL COMMENT '用户ID',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` char(32) NOT NULL COMMENT '密码',
  `salt` varchar(10) DEFAULT 'CYBELE' COMMENT '盐值',
  `cnName` varchar(32) DEFAULT NULL COMMENT '中文姓名',
  `enName` varchar(32) DEFAULT NULL COMMENT '英文姓名',
  `cellphone` varchar(14) DEFAULT NULL COMMENT '手机号码',
  `userTel` varchar(12) DEFAULT NULL COMMENT '固话号码',
  `userEmail` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `userAddr` varchar(255) DEFAULT NULL COMMENT '联系地址',
  `userGender` char(1) DEFAULT '0' COMMENT '性别[0:未知][1:男][2:女]',
  `userBirthday` char(10) DEFAULT NULL COMMENT '生日',
  `userPicUrl` varchar(255) DEFAULT NULL COMMENT '用户头像地址',
  `isSysAdmin` char(1) NOT NULL DEFAULT '0' COMMENT '是否超级管理员[0,否][1,是]',
  `isLocked` char(1) NOT NULL DEFAULT '0' COMMENT '是否锁定[0:否][1:是]',
  `accountExpiredDate` char(19) DEFAULT NULL COMMENT '账号过期日期。为空则永久',
  `credentialsExpiredDate` char(19) DEFAULT NULL COMMENT '密码过期日期。为空则永久',
  `userOrder` int(11) DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `createdBy` char(36) NOT NULL COMMENT '创建者ID',
  `createdAt` char(19) NOT NULL COMMENT '创建日期',
  `reviserId` char(36) NOT NULL COMMENT '更新者ID',
  `reviseDate` char(19) NOT NULL COMMENT '更新日期',
  `isValid` char(1) NOT NULL DEFAULT '1' COMMENT '是否有效[0:否][1:是]',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Table structure for wxm_mhly_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `wxm_mhly_sys_user_role`;
CREATE TABLE `wxm_mhly_sys_user_role` (
  `id` char(36) NOT NULL,
  `userId` char(36) NOT NULL,
  `roleId` char(36) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='用户角色关联';
