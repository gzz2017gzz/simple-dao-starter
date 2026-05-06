SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint NOT NULL COMMENT '主键',
  `parent_id` bigint NOT NULL COMMENT '上级主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机构名称',
  `type` tinyint NULL DEFAULT NULL COMMENT '机构类型 1单位 2部门',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机构编码',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态(1启用，0不启用)',
  `order_num` float NULL DEFAULT NULL COMMENT '排序编号',
  `leaf` tinyint NULL DEFAULT NULL COMMENT '是否叶子',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `dr` tinyint NOT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '组织机构' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, 0, '公司总部', 1, NULL, 1, 100000000, 0, NULL, '2024-11-05 00:00:00', 10000, '2026-05-03 12:48:16', 10000, 0);
INSERT INTO `sys_dept` VALUES (3687530136464261120, 1, '财务部', 2, '2', 1, 2, 1, NULL, '2026-05-03 12:48:16', 10000, NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典名称',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典编码',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `dr` tinyint NULL DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (504670504825786512, '性别', 'gender', '性别', '2023-11-18 13:56:05', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict` VALUES (504670504825786513, '状态', 'status', '状态', '2023-11-18 13:56:05', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict` VALUES (504670504825786514, '机构类型', 'deptType', '机构类型', '2023-11-18 13:56:05', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict` VALUES (504670504825786515, '请求类型', 'requestType', '请求类型', '2023-11-18 13:56:05', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict` VALUES (694787338525474816, '有无状态', 'isHave', '有无状态', '2023-11-18 13:56:05', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict` VALUES (710671776396345344, '功能类型', 'funcType', '功能类型', '2023-11-18 13:56:05', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict` VALUES (895478322174099456, '问题类型', 'questionType', '问题类型', '2024-04-03 09:11:09', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict` VALUES (895481872530800640, '优先级', 'level', '优先级', '2024-04-03 09:25:16', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict` VALUES (895482419312852992, '问题状态', 'questionStatus', '问题状态', '2024-04-03 09:27:26', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict` VALUES (918483057294442496, '岗位', 'posts', '*注意岗位编码必须为两位数字*', '2024-06-05 20:43:45', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict` VALUES (918682477847904256, '分校类型', 'schoolType', '分校类型', '2024-06-06 09:56:11', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict` VALUES (922795949149913088, '是否', 'yesNo', NULL, '2024-06-17 18:21:39', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict` VALUES (3454841862899105792, '用户权限类型', 'userAuthType', NULL, '2024-07-30 10:28:08', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict` VALUES (3470043390266048512, '用户状态', 'userStatus', NULL, '2024-09-10 09:13:35', 10000, NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item`  (
  `id` bigint NOT NULL COMMENT '主键',
  `dict_id` bigint NULL DEFAULT NULL COMMENT '字典主键',
  `text` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典项文本',
  `item_value` int NULL DEFAULT NULL COMMENT '字典项值',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `order_num` float NULL DEFAULT NULL COMMENT '排序编号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `dr` tinyint NOT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典项' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
INSERT INTO `sys_dict_item` VALUES (504670504796426241, 504670504825786512, '女', 0, '女', 0, '2023-11-18 13:56:05', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (504670504796426242, 504670504825786512, '男', 1, '男', 1, '2023-11-18 13:56:05', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (504670504796426243, 504670504825786513, '启用', 1, '启用', 1, '2023-11-18 13:56:05', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (504670504796426244, 504670504825786513, '停用', 0, '停用', 0, '2023-11-18 13:56:05', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (504670504796426245, 504670504825786514, '单位', 1, '单位', 1, '2023-11-18 13:56:05', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (504670504796426246, 504670504825786514, '部门', 2, '部门', 2, '2023-11-18 13:56:05', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (504670504796426247, 504670504825786515, '前端文件', 1, '前端文件', NULL, '2023-11-18 13:56:05', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (504670504796426248, 504670504825786515, '后端接口', 2, '后端接口', NULL, '2023-11-18 13:56:05', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (694787431718715392, 694787338525474816, '有', 1, '有', 1, '2023-11-18 13:56:05', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (694787518234624000, 694787338525474816, '无', 0, '无', 0, '2023-11-18 13:56:05', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (710671858151718912, 710671776396345344, '菜单', 1, '菜单', 1, '2023-11-18 13:56:05', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (710675880745107456, 710671776396345344, '按钮', 2, '按钮', 2, '2023-11-18 13:56:05', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (845642062303854592, 845641485712883712, '个人权限', 1, '个人权限', 1, '2023-11-18 13:56:05', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (845642142964514816, 845641485712883712, '部门权限', 2, '部门权限', 2, '2023-11-18 13:56:05', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (845642221821624320, 845641485712883712, '分配部门', 3, '分配部门', 3, '2023-11-18 13:56:05', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (895481461929410560, 895478322174099456, '需求问题', 1, '需求问题', 1, '2024-04-03 09:23:38', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (895481535291981824, 895478322174099456, '设计问题', 2, '设计问题', 2, '2024-04-03 09:23:55', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (895481622168600576, 895478322174099456, '程序BUG', 3, '程序BUG', 3, '2024-04-03 09:24:16', 10000, '2025-04-11 09:30:07', 10000, 0);
INSERT INTO `sys_dict_item` VALUES (895481938079383552, 895481872530800640, '高', 1, '高', 1, '2024-04-03 09:25:31', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (895481984787152896, 895481872530800640, '中', 2, '中', 2, '2024-04-03 09:25:42', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (895482032161816576, 895481872530800640, '低', 3, '低', 3, '2024-04-03 09:25:54', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (895482517459566592, 895482419312852992, '未解决', 1, '未解决', 1, '2024-04-03 09:27:49', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (895482604214550528, 895482419312852992, '进行中', 2, '进行中', 2, '2024-04-03 09:28:10', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (895482679703633920, 895482419312852992, '已解决', 3, '已解决', 3, '2024-04-03 09:28:28', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (895482805436284928, 895482419312852992, '延期解决', 4, '延期解决', 4, '2024-04-03 09:28:58', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (895501645129973760, 895482419312852992, '待沟通', 5, '待沟通', 5, '2024-04-03 10:43:50', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (897079083647631360, 895482419312852992, '沟通过', 6, '沟通过', 6, '2024-04-07 19:12:00', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (918483243437654016, 918483057294442496, '课程顾问', 11, '课程顾问', 11, '2024-06-05 20:44:30', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (918483303890157568, 918483057294442496, '课程顾问主管', 12, '课程顾问主管', 12, '2024-06-05 20:44:44', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (918483378007703552, 918483057294442496, '宿管主管', 14, '宿管宿管', 14, '2024-06-05 20:45:02', 10000, '2024-12-18 10:25:04', 10000, 0);
INSERT INTO `sys_dict_item` VALUES (918483445804433408, 918483057294442496, '学管', 15, '学管', 15, '2024-06-05 20:45:18', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (918483529329803264, 918483057294442496, '学管主管', 16, '学管主管', 16, '2024-06-05 20:45:38', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (918483619436036096, 918483057294442496, '专业课老师', 17, '专业课老师', 17, '2024-06-05 20:45:59', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (918483710037196800, 918483057294442496, '班主任', 19, '班主任', 19, '2024-06-05 20:46:21', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (922796095199772672, 922795949149913088, '是', 1, NULL, 1, '2024-06-17 18:22:14', 10000, '2025-06-21 15:07:03', 10000, 0);
INSERT INTO `sys_dict_item` VALUES (922796115546341376, 922795949149913088, '否', 0, NULL, 0, '2024-06-17 18:22:19', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (924970315707056128, 918483057294442496, '财务', 18, '财务', 18, '2024-06-23 18:21:48', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (3453020142894579712, 918483057294442496, '教学老师', 20, '教学老师', 20, '2024-07-25 09:49:16', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (3454841914686177280, 3454841862899105792, '个人', 0, '只能看自己的数据', 0, '2024-07-30 10:28:20', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (3454841940606976000, 3454841862899105792, '校区', 1, '可以看校区的数据', 1, '2024-07-30 10:28:27', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (3454883515970093056, 3454841862899105792, '全局', 2, '可以看所有校区的数据', 2, '2024-07-30 13:13:39', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (3454926329495945216, 918483057294442496, '课程顾问组长', 13, '课程顾问组长', 13, '2024-07-30 16:03:46', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (3460704072112275456, 918483057294442496, '门店店长', 21, NULL, 21, '2024-08-15 14:42:28', 10000, '2024-12-12 09:17:53', 10000, 0);
INSERT INTO `sys_dict_item` VALUES (3460704133114232832, 918483057294442496, '财务经理', 22, NULL, 22, '2024-08-15 14:42:42', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (3460704337624301568, 918483057294442496, '门店负责人', 23, NULL, 23, '2024-08-15 14:43:31', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (3460704485410603008, 918483057294442496, '市场部校长', 24, NULL, 24, '2024-08-15 14:44:06', 10000, '2024-12-10 15:53:32', 10000, 0);
INSERT INTO `sys_dict_item` VALUES (3460704521129295872, 918483057294442496, '分校校长', 25, NULL, 25, '2024-08-15 14:44:15', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (3461095243443601408, 3454841862899105792, '组员', 3, '可看自己和组员的数据', 3, '2024-08-16 16:36:50', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (3464720383301648384, 918483057294442496, '板块负责人', 26, NULL, 26, '2024-08-26 16:41:51', 10000, '2026-03-13 17:49:59', 10000, 0);
INSERT INTO `sys_dict_item` VALUES (3470043445811216384, 3470043390266048512, '已停用', 0, NULL, 0, '2024-09-10 09:13:48', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (3470043480208703488, 3470043390266048512, '已激活', 1, NULL, 1, '2024-09-10 09:13:56', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (3470043508188905472, 3470043390266048512, '已禁用', 2, NULL, 2, '2024-09-10 09:14:03', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (3470043551511871488, 3470043390266048512, '未激活', 4, NULL, 4, '2024-09-10 09:14:13', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (3470043582289674240, 3470043390266048512, '已退出', 5, NULL, 5, '2024-09-10 09:14:20', 10000, '2025-01-18 17:01:21', 10000, 0);
INSERT INTO `sys_dict_item` VALUES (3471573000019509248, 918483057294442496, '学管负责人', 27, NULL, 27, '2024-09-14 14:31:42', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (3474050361709297664, 918483057294442496, '校区校长', 28, NULL, 28, '2024-09-21 10:35:51', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (3474127954479415296, 918483057294442496, '课程顾问副主管', 29, NULL, 29, '2024-09-21 15:44:11', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (3513629139116490752, 918483057294442496, '人力总监', 30, '人力总监', 30, '2025-01-08 15:47:47', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (3513629275997601792, 918483057294442496, '人力操作人', 31, '人力操作人', 31, '2025-01-08 15:48:19', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (3513915385915113472, 918483057294442496, '门店财务', 32, NULL, 32, '2025-01-09 10:45:13', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (3513961248519618560, 918483057294442496, '门店课程顾问主管', 33, NULL, 33, '2025-01-09 13:47:28', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (3551230536158543872, 918483057294442496, '宿管', 34, '宿舍管理员', 34, '2025-04-22 10:02:18', 10000, '2025-04-22 10:02:25', 10000, 0);
INSERT INTO `sys_dict_item` VALUES (3605330988223168512, 918483057294442496, '维修', 35, NULL, 35, '2025-09-18 16:58:11', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (3640124968617902080, 918483057294442496, '管理员', 99, NULL, 99, '2025-12-23 17:17:03', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (3645518033503522816, 918483057294442496, '渠道专员', 36, NULL, 36, '2026-01-07 14:27:10', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (3645518064281190400, 918483057294442496, '渠道主管', 37, NULL, 37, '2026-01-07 14:27:17', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (3651627203734671360, 918483057294442496, '课程顾问大主管', 38, '渠道审核用，给小雅的', 38, '2026-01-24 11:02:49', 10000, '2026-01-24 11:03:15', 10000, 0);
INSERT INTO `sys_dict_item` VALUES (3652381222425989120, 918483057294442496, '数据专员', 39, NULL, 39, '2026-01-26 12:59:01', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (3666115828455182336, 918483057294442496, '第三方渠道专员', 40, '第三方渠道专员', 40, '2026-03-05 10:35:27', 10000, NULL, NULL, 0);
INSERT INTO `sys_dict_item` VALUES (3666548735766302720, 918483057294442496, '会计', 41, NULL, 41, '2026-03-06 15:15:40', 10000, NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_field_conf
-- ----------------------------
DROP TABLE IF EXISTS `sys_field_conf`;
CREATE TABLE `sys_field_conf`  (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `component` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组件名',
  `cond` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自定义条件',
  `grid` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自定义表格',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_user_component`(`user_id` ASC, `component` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字段配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_field_conf
-- ----------------------------
INSERT INTO `sys_field_conf` VALUES (846024627330220032, 10000, 'FieldConfPage', 'userId,component,remark', 'detail,userId,component,cond,grid,remark,operate', NULL);
INSERT INTO `sys_field_conf` VALUES (894907534542897152, 10000, 'StudentPage', 'deptId,name,code,cardNo,loginName,password,phone,gender,birthdayStart,birthdayEnd,status,remark', 'choice,detail,deptId,name,code,cardNo,loginName,password,phone,gender,birthday,status,remark,operate', NULL);
INSERT INTO `sys_field_conf` VALUES (898206227715063808, 898006557462102016, 'FieldConfPage', 'userId,component,remark', 'detail,userId,component,cond,grid,remark,operate', NULL);
INSERT INTO `sys_field_conf` VALUES (923480747317133312, 10000, 'StandardPage', 'standardName,remark', 'choice,detail,standardName,remark,operate', NULL);
INSERT INTO `sys_field_conf` VALUES (923492349483417600, 10000, 'RoomPage', 'schoolId,schoolYearId,standardId,buildNumber,roomNumber,gender,roomSupervisor,remark', 'choice,schoolId,schoolYearId,standardId,buildNumber,roomNumber,gender,roomSupervisor,remark,operate', NULL);
INSERT INTO `sys_field_conf` VALUES (923571270560251904, 10000, 'BedRecordPage', 'sourceId,studentId,bedId,recordType,startDate,endDate,status,remark', 'choice,detail,sourceId,studentId,bedId,recordType,startDate,endDate,status,remark,operate', NULL);
INSERT INTO `sys_field_conf` VALUES (923571334938624000, 10000, 'BedPage', 'roomId,bedConfig,bedBunk,bedNumber,bookedStudentId,stayedStudentId,remark', 'choice,detail,roomId,bedConfig,bedBunk,bedNumber,bookedStudentId,stayedStudentId,remark,operate', NULL);

-- ----------------------------
-- Table structure for sys_func
-- ----------------------------
DROP TABLE IF EXISTS `sys_func`;
CREATE TABLE `sys_func`  (
  `func_id` bigint NOT NULL COMMENT '主键',
  `parent_id` bigint NOT NULL COMMENT '上级主键',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '名称',
  `type` tinyint NULL DEFAULT NULL COMMENT '类型(1:目录,2:菜单,3:按钮)',
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路径',
  `param` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '参数',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图标',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态',
  `orders` float NULL DEFAULT NULL COMMENT '排序',
  `component` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件路径',
  `leaf` tinyint NULL DEFAULT NULL COMMENT '叶子',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `dr` tinyint NOT NULL COMMENT '删除标记',
  PRIMARY KEY (`func_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '功能' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_func
-- ----------------------------
INSERT INTO `sys_func` VALUES (1000010, 0, '系统管理', 1, NULL, NULL, 'el-icon-s-tools', 1, 100, NULL, 0, '2024-11-05 00:00:00', 10000, NULL, NULL, 0);
INSERT INTO `sys_func` VALUES (1000050, 1000010, '菜单维护', 1, '/sys/func/FuncTree', NULL, 'el-icon-s-unfold', 1, 600, 'sys/func/FuncTree', 1, '2024-11-05 00:00:00', 10000, NULL, NULL, 0);
INSERT INTO `sys_func` VALUES (1000060, 1000010, '字典管理', 1, '/sys/dict/DictPage', '{\"billType\":17,\"id\":456}', 'el-icon-reading', 1, 500, 'sys/dict/DictPage', 1, '2024-11-05 00:00:00', 10000, NULL, NULL, 0);
INSERT INTO `sys_func` VALUES (1000070, 1000010, '部门管理', 1, '/sys/dept/DeptTree', NULL, 'el-icon-office-building', 1, 100, 'sys/dept/DeptTree', 1, '2024-11-05 00:00:00', 10000, NULL, NULL, 0);
INSERT INTO `sys_func` VALUES (1000080, 1000010, '用户管理', 1, '/sys/user/UserPage', NULL, 'el-icon-user-solid', 1, 200, 'sys/user/UserPage', 1, '2024-11-05 00:00:00', 10000, NULL, NULL, 0);
INSERT INTO `sys_func` VALUES (1000090, 1000010, '角色管理', 1, '/sys/role/RolePage', NULL, 'el-icon-school', 1, 300, 'sys/role/RolePage', 1, '2024-11-05 00:00:00', 10000, NULL, NULL, 0);
INSERT INTO `sys_func` VALUES (1000100, 1000010, '接口性能', 1, '/sys/request/RequestList', NULL, 'el-icon-info', 1, 700, 'sys/request/RequestList', 1, '2024-11-05 00:00:00', 10000, NULL, NULL, 0);
INSERT INTO `sys_func` VALUES (1000110, 1000010, '系统日志', 1, '/sys/log/LogPage', NULL, 'el-icon-date', 1, 800, 'sys/log/LogPage', 1, '2024-11-05 00:00:00', 10000, NULL, NULL, 0);
INSERT INTO `sys_func` VALUES (1000120, 1000010, '系统参数', 1, '/sys/parameter/ParameterPage', NULL, 'el-icon-odometer', 1, 810, 'sys/parameter/ParameterPage', 1, '2024-11-05 00:00:00', 10000, NULL, NULL, 0);
INSERT INTO `sys_func` VALUES (1000130, 1000010, '数据检查', 1, '/sys/tableref/TableRefPage', NULL, 'el-icon-view', 0, 820, 'sys/tableref/TableRefPage', 1, '2024-11-05 00:00:00', 10000, NULL, NULL, 0);
INSERT INTO `sys_func` VALUES (1000131, 1000010, '字段配置', 1, '/sys/fieldconf/FieldConfPage', NULL, 'el-icon-view', 0, 830, 'sys/fieldconf/FieldConfPage', 1, '2024-11-05 00:00:00', 10000, NULL, NULL, 0);
INSERT INTO `sys_func` VALUES (879649834980605952, 1000010, '需求管理', 1, '/base/notebook/NoteBookPage', NULL, 'el-icon-s-unfold', 1, 840, 'base/notebook/NoteBookPage', 1, '2024-11-05 00:00:00', 10000, '2025-02-25 12:45:40', 10000, 1);

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint NOT NULL COMMENT '主键',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志标题',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户主键',
  `user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `ip` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `method` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'java类.方法',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求路径',
  `request_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求类型',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求参数',
  `operate_time` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `result` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '返回结果',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_note_book
-- ----------------------------
DROP TABLE IF EXISTS `sys_note_book`;
CREATE TABLE `sys_note_book`  (
  `id` bigint NOT NULL COMMENT '主键',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内容',
  `finish_date` date NULL DEFAULT NULL COMMENT '预计完成时间',
  `real_date` date NULL DEFAULT NULL COMMENT '实际完成时间',
  `depart` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门',
  `author` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作者',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态',
  `level` tinyint NULL DEFAULT NULL COMMENT '优先级',
  `type` tinyint NULL DEFAULT NULL COMMENT '问题类型',
  `dr` tinyint NULL DEFAULT 0 COMMENT '删除标记',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `days` int NULL DEFAULT NULL COMMENT '预计工期',
  `order_num` int NULL DEFAULT NULL COMMENT '排序编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '记事本' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_note_book
-- ----------------------------
INSERT INTO `sys_note_book` VALUES (3535820942334754816, '虚拟退费', '确保在收费时可用', NULL, NULL, '财务部', '韩志红', 3, 3, 1, 0, NULL, '2025-03-10 21:30:05', 10000, '2025-04-11 09:19:32', 10000, NULL, 10010);

-- ----------------------------
-- Table structure for sys_parameter
-- ----------------------------
DROP TABLE IF EXISTS `sys_parameter`;
CREATE TABLE `sys_parameter`  (
  `id` bigint NOT NULL COMMENT '主键',
  `group_name` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组名称',
  `param_name` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数名称',
  `param_key` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数键',
  `param_value` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数值',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `dr` tinyint NULL DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统参数' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_parameter
-- ----------------------------
INSERT INTO `sys_parameter` VALUES (3531302250741760000, '客户端版本号', '客户端版本号', 'version', '1775115977112', '客户端版本号', '2025-02-26 10:14:25', 10000, '2026-04-02 15:47:07', 10000, 0);

-- ----------------------------
-- Table structure for sys_request
-- ----------------------------
DROP TABLE IF EXISTS `sys_request`;
CREATE TABLE `sys_request`  (
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '接口路径',
  `app_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用名',
  `sum_count` bigint NULL DEFAULT NULL COMMENT '请求次数',
  `sum_milli_second` bigint NULL DEFAULT NULL COMMENT '请求总时长',
  `avg_milli_second` decimal(12, 2) NULL DEFAULT NULL COMMENT '请求平均时长',
  `max_milli_second` int NULL DEFAULT NULL COMMENT '请求最大时长',
  `min_milli_second` int NULL DEFAULT NULL COMMENT '请求最小时长',
  `type` tinyint NULL DEFAULT NULL COMMENT '1后台2前端',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint NOT NULL DEFAULT 0 COMMENT '创建人ID',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` bigint NOT NULL DEFAULT 0 COMMENT '修改人ID',
  `dr` tinyint NOT NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`path`, `app_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '请求时长统计' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_request
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint NOT NULL COMMENT '主键',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `role_auth` tinyint NULL DEFAULT 1 COMMENT '角色权限',
  `order_num` int NULL DEFAULT 0 COMMENT '排序',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `dr` tinyint NULL DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (3460759242149986333, '财务经理', 1, 110, '财务经理', '2024-08-15 10:38:25', 10000, '2024-10-15 17:06:46', 10000, 0);

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
  `role_dept_id` bigint NOT NULL COMMENT '主键',
  `role_id` bigint NOT NULL COMMENT '角色id',
  `dept_id` bigint NOT NULL COMMENT '部门id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  PRIMARY KEY (`role_dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色部门关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_func
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_func`;
CREATE TABLE `sys_role_func`  (
  `role_func_id` bigint NOT NULL COMMENT '主键',
  `role_id` bigint NOT NULL COMMENT '角色主键',
  `func_id` bigint NOT NULL COMMENT '权限主键',
  PRIMARY KEY (`role_func_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色功能关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_func
-- ----------------------------
INSERT INTO `sys_role_func` VALUES (3687530339112058880, 3460759242149986333, 1000010);
INSERT INTO `sys_role_func` VALUES (3687530339112058881, 3460759242149986333, 1000070);
INSERT INTO `sys_role_func` VALUES (3687530339112058882, 3460759242149986333, 1000080);
INSERT INTO `sys_role_func` VALUES (3687530339112058883, 3460759242149986333, 1000090);
INSERT INTO `sys_role_func` VALUES (3687530339112058884, 3460759242149986333, 1000060);
INSERT INTO `sys_role_func` VALUES (3687530339112058885, 3460759242149986333, 1000050);
INSERT INTO `sys_role_func` VALUES (3687530339112058886, 3460759242149986333, 1000100);
INSERT INTO `sys_role_func` VALUES (3687530339112058887, 3460759242149986333, 1000110);
INSERT INTO `sys_role_func` VALUES (3687530339112058888, 3460759242149986333, 1000120);

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user`  (
  `user_role_id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户主键',
  `role_id` bigint NOT NULL COMMENT '角色主键',
  PRIMARY KEY (`user_role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色用户关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------

-- ----------------------------
-- Table structure for sys_table_ref
-- ----------------------------
DROP TABLE IF EXISTS `sys_table_ref`;
CREATE TABLE `sys_table_ref`  (
  `id` bigint NOT NULL COMMENT '主键',
  `main_table` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主表表名',
  `main_info` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主表描述',
  `sub_table` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '子表表名',
  `sub_info` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '子表描述',
  `ref_id` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '外键名称',
  `del_flag` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标记',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人主键',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '修改人主键',
  `dr` tinyint NULL DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '表引用关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_table_ref
-- ----------------------------
INSERT INTO `sys_table_ref` VALUES (1, 'sys_role', '角色表', 'sys_role_user', '角色用户关联表', 'role_id', '', '2022-10-01 00:00:00', -1, '2023-10-07 17:08:42', 10000, 0);
INSERT INTO `sys_table_ref` VALUES (2, 'sys_role', '角色表', 'sys_role_dept', '角色部门关联表', 'role_id', '', '2022-10-01 00:00:00', -1, '2023-10-07 17:08:46', 10000, 0);
INSERT INTO `sys_table_ref` VALUES (3, 'sys_role', '角色表', 'sys_role_func', '角色菜单关联表', 'role_id', '', '2022-10-01 00:00:00', -1, '2023-10-07 17:08:50', 10000, 0);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint NOT NULL COMMENT '主键',
  `dept_id` bigint NOT NULL COMMENT '机构ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '帐号',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `login_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录名',
  `qw_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '企微ID',
  `status` tinyint NOT NULL COMMENT '状态:1启用0禁用',
  `posts` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '岗位集',
  `school_ids` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '校区集',
  `exclude_school_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '排除校区集',
  `manage` tinyint NULL DEFAULT NULL COMMENT '分管部门1高中2初中',
  `auth_type` tinyint NULL DEFAULT 0 COMMENT '权限类型：0个人，1校区',
  `proxy_user_id` bigint NULL DEFAULT NULL COMMENT '代理人UID',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint NOT NULL COMMENT '创建人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `dr` tinyint NOT NULL COMMENT '删除标记',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (10000, 1, '管理员', 'e10adc3949ba59abbe56e057f20f883e', 'admin', NULL, 1, '12,11,13,14,15,16,18,19,20,21,22,23,24,25,26,27,34,99,17,28,29,30,31,32,33,35,36,37,38,39,40,41', '923095700449787904,922837001055502336,923851496620032000,923851434129096704,923851373588512768,923851311953215488,923851080872230912,923490149583552512,923489795861118976,923850982700351488,3496563379215007744,3531377566197743616,3571514348142727168,3571514628947185664,3571514348142727169,3573713032452378624,3590729273071108096,3605576494207729664,3664282045904982016,3676308771023491072,3676309076121358336', '', 2, 2, 0, NULL, '2023-11-18 18:16:01', 10000, '2026-04-27 14:11:58', 10000, 0);
INSERT INTO `sys_user` VALUES (3460748214590767104, 3687530136464261120, '张全秀', 'e10adc3949ba59abbe56e057f20f883e', 'zhangquanxiu', '612765160', 1, '32,41', '', '', 0, 1, 0, '', '2024-08-15 18:00:00', 10000, '2026-05-03 12:48:27', 10000, 0);

SET FOREIGN_KEY_CHECKS = 1;
