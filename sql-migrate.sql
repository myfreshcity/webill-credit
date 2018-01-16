----------2018-01-02----------
--慢慢花——订单状态改动

ALTER TABLE `order_insurant`
ADD COLUMN `job`  varchar(64) NULL COMMENT '职业id' AFTER `prov_city_text`,
ADD COLUMN `fiscal_resident_identity`  varchar(64) NULL COMMENT '税收居民身份1：仅为中国税收居民2：仅为非居民3：既是中国税收居民又是其他国家（地区）税收居民' AFTER `beneficiary_type`;
ALTER TABLE `order_applicant`
MODIFY COLUMN `job`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职业信息，职业id使用“-”拼接如：101414-101415-101416' AFTER `email`,
ADD COLUMN `job_text`  varchar(255) NULL COMMENT '职业名称' AFTER `job`;

ALTER TABLE `product`
ADD COLUMN `recommend_url`  varchar(512) NULL COMMENT '为你推荐图片Url地址' AFTER `img_url_show`;

----------2017-12-28----------
--慢慢花——订单状态改动
ALTER TABLE `t_order`
MODIFY COLUMN `t_status`  tinyint(4) NOT NULL DEFAULT 0 COMMENT '订单状态：-1：已删除、0：新建、10：待付款、20：已支付、30：出单中（已承保）、40：已出单、50：退保中、60：已退保、80：已关闭、90：已失效' AFTER `insure_issue_time`;

----------2017-12-28----------
--慢慢花：添加产品保障项目表

DROP TABLE IF EXISTS `product_protect_item`;
CREATE TABLE `product_protect_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `prod_id` int(11) DEFAULT NULL COMMENT '产品ID',
  `protect_item_id` int(11) DEFAULT NULL COMMENT '保障项目ID',
  `sort` int(11) DEFAULT NULL COMMENT '展示顺序',
  `name` varchar(255) DEFAULT NULL COMMENT '保障项目名称',
  `default_value` varchar(255) DEFAULT NULL COMMENT '默认值',
  `description` varchar(2048) DEFAULT NULL COMMENT '描述信息',
  `relate_coverage` varchar(255) DEFAULT NULL COMMENT '保额关联试算因子',
  `t_status` tinyint(4) DEFAULT '0' COMMENT '状态： -1-逻辑删除 0-正常数据',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品保障项目表';

----------2017-12-28----------
--慢慢花：产品表添加支付方式字段
ALTER TABLE `product`
ADD COLUMN `auto_pay`  int(11) NULL DEFAULT 0 COMMENT '支付方式：0-手动支付 1-银行代扣 ' AFTER `description`;

----------2017-12-27----------
--慢慢花用户表改动
ALTER TABLE `user`
MODIFY COLUMN `c_name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '必填  中文名' AFTER `password`,
MODIFY COLUMN `card_type`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '必填  证件类型，证件类型：1：身份证、2:护照、3：出生证、4：驾照、5：港澳通行证、6：军官证、7：台胞证、8：警官证、9：港澳台回乡证10：组织机构代码证、11：营业执照、12：税务登记号、13：三证合一、14：统一信用代码证、99：其他、' AFTER `e_name`,
MODIFY COLUMN `card_code`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '必填  证件号' AFTER `card_type`,
MODIFY COLUMN `sex`  varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '必填  性别 0：女 1：男' AFTER `card_code`,
MODIFY COLUMN `birthday`  datetime NULL COMMENT '必填  出生日期 格式：yyyy-MM-dd' AFTER `sex`,
MODIFY COLUMN `mobile`  varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '必填  手机号码' AFTER `birthday`,
MODIFY COLUMN `email`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '必填  邮箱' AFTER `mobile`,
MODIFY COLUMN `is_staff`  tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否为企业员工 0:否，1:是' AFTER `weight`,
MODIFY COLUMN `subscribe_flag`  tinyint(4) NOT NULL DEFAULT 1 COMMENT '是否已关注：0-否；1-是' AFTER `t_status`;

ALTER TABLE `order_applicant`
MODIFY COLUMN `card_type`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '必填  证件类型，证件类型：1：身份证、2:护照、3：出生证、4：驾照、5：港澳通行证、6：军官证、7：台胞证、8：警官证、9：港澳台回乡证10：组织机构代码证、11：营业执照、12：税务登记号、13：三证合一、14：统一信用代码证、99：其他、' AFTER `e_name`,
MODIFY COLUMN `card_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '必填  证件号' AFTER `card_type`,
MODIFY COLUMN `birthday`  datetime NULL COMMENT '必填  出生日期 格式：yyyy-MM-dd' AFTER `sex`,
MODIFY COLUMN `mobile`  varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '必填  手机号码' AFTER `birthday`,
MODIFY COLUMN `email`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '必填  邮箱' AFTER `mobile`,
ADD COLUMN `order_id`  int(11) NOT NULL COMMENT '订单id' AFTER `id`;

ALTER TABLE `product`
MODIFY COLUMN `img_url_src`  varchar(512) DEFAULT NULL COMMENT '图片Url地址（齐欣云服原始地址）' AFTER `second_category`,
MODIFY COLUMN `img_url_show`  varchar(512) DEFAULT NULL COMMENT '图片Url地址（平台展示地址）' AFTER `img_url_src`,
MODIFY COLUMN `prod_read`  varchar(1024) DEFAULT NULL COMMENT '产品解读' AFTER `insur_date_limit`,
MODIFY COLUMN `premium_table`  varchar(512) DEFAULT NULL COMMENT '费率表地址' AFTER `prod_read`;

----------2017-12-26----------
--慢慢花订单表拆分成订单和投保人

DROP TABLE IF EXISTS `product_order`;
CREATE TABLE `t_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `product_id` varchar(32) NOT NULL COMMENT '产品id对应product表的主键id',
  `user_id` int(11) DEFAULT NULL COMMENT '对应user表的主键id',
  `other_info_id` int(11) DEFAULT NULL COMMENT '其他信息表对应的id',
  `trans_no` varchar(64) NOT NULL COMMENT '订单号，齐欣的交易流水号',
  `in_order_no` varchar(64) DEFAULT NULL COMMENT '投保单号',
  `total_num` int(11) NOT NULL DEFAULT '0' COMMENT '总份数',
  `start_date` datetime DEFAULT NULL COMMENT '起保日期 格式：yyyy-MM-dd',
  `end_date` datetime DEFAULT NULL COMMENT '终保日期 格式：yyyy-MM-dd',
  `deadline` varchar(32) NOT NULL COMMENT '保障期限',
  `deadline_text` varchar(2000) DEFAULT NULL COMMENT '保障期限说明',
  `issue_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '出单状态 0：未出单 1：已出单 2：延时出单 3：取消出单',
  `effective_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '生效（退保）状态 0：未生效 1：已生效 2：退保中 3：已退保',
  `pay_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '支付状态 0：未支付 1：已支付 2：不能支付 3：扣款中 4：扣款失败 5：扣款成功',
  `pay_amount` bigint(20) NOT NULL COMMENT '保单总保费（单位：分）',
  `category_name` varchar(255) DEFAULT NULL COMMENT '产品二级分类名称',
  `insure_time` datetime DEFAULT NULL COMMENT '投保时间 格式：yyyy-MM-dd HH:mm:ss',
  `client_type` tinyint(4) DEFAULT '0' COMMENT '客户端类型 0：默认 1：PC，2：H5',
  `gate_way` tinyint(4) DEFAULT '0' COMMENT '支付类型 0：默认 1：支付宝 3：银联 21:微信',
  `pay_time` datetime DEFAULT NULL COMMENT '成功支付完成时间',
  `order_issue_time` datetime DEFAULT NULL COMMENT '出单时间,保险公司承保时间',
  `insure_issue_time` datetime DEFAULT NULL COMMENT '生成保单时间',
  `t_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '订单状态：-1：已删除、0：默认状态、10：待付款、15：待发货、20：已出单、80：已关闭、90：已失效',
  `insure_down_url` varchar(200) DEFAULT NULL COMMENT '保单下载地址',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='订单/投保单表';

DROP TABLE IF EXISTS `order_applicant`;
CREATE TABLE `order_applicant` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `c_name` varchar(255)  NOT NULL COMMENT '必填  中文名',
  `e_name` varchar(255) DEFAULT NULL COMMENT '拼音或英文名，境外旅游险必填',
  `card_type` varchar(32) NOT NULL DEFAULT '1' COMMENT '必填  证件类型，证件类型：1：身份证、2:护照、3：出生证、4：驾照、5：港澳通行证、6：军官证、7：台胞证、8：警官证、9：港澳台回乡证10：组织机构代码证、11：营业执照、12：税务登记号、13：三证合一、14：统一信用代码证、99：其他、',
  `card_code` varchar(32) NOT NULL DEFAULT '' COMMENT '必填  证件号',
  `sex` varchar(6) NOT NULL COMMENT '必填  性别 0：女 1：男',
  `birthday` datetime NOT NULL COMMENT '必填  出生日期 格式：yyyy-MM-dd',
  `mobile` varchar(11) NOT NULL COMMENT '必填  手机号码',
  `email` varchar(255) NOT NULL COMMENT '必填  邮箱',
  `job` varchar(255) DEFAULT NULL COMMENT '职业信息，职业id使用“-”拼接如：101414-101415-101416',
  `country` varchar(500) DEFAULT NULL COMMENT '国籍',
  `prov_city_id` varchar(255) DEFAULT NULL COMMENT '居住省市，地区编码使用“-”拼接如：320000-320100-320104',
  `contact_address` varchar(255) DEFAULT NULL COMMENT '联系地址',
  `contact_post` varchar(255) DEFAULT NULL COMMENT '联系地址邮编',
  `applicant_type` varchar(4) DEFAULT NULL COMMENT '投保人类型 0：个人（默认） 1：公司',
  `card_period` datetime DEFAULT NULL COMMENT '证件有效期，格式yyyy-MM-dd',
  `marry_state` varchar(32) DEFAULT NULL COMMENT '婚姻状态 1：已婚 2：未婚 3：离婚 4：丧偶 5：其他',
  `yearly_income` varchar(255) DEFAULT NULL COMMENT '年收入',
  `office_address` varchar(255) DEFAULT NULL COMMENT '工作单位地址（办公地址）',
  `tel` varchar(255) DEFAULT NULL COMMENT '  工作单位电话（办公电话）',
  `work_company_name` varchar(255) DEFAULT NULL COMMENT '工作单位名称',
  `work_email` varchar(255) DEFAULT NULL COMMENT '工作单位邮箱',
  `office_post` varchar(6) DEFAULT NULL COMMENT '办公地址邮编',
  `have_medical` varchar(32) DEFAULT '0' COMMENT '是否有医保 0：否 1：是',
  `height` varchar(255) DEFAULT NULL COMMENT '身高',
  `weight` varchar(255) DEFAULT NULL COMMENT '体重',
  `fiscal_resident_identity` varchar(32) DEFAULT NULL COMMENT '税收居民身份1：仅为中国税收居民2：仅为非居民3：既是中国税收居民又是其他国家（地区）税收居民',
  `related_person_house` varchar(32) DEFAULT NULL COMMENT '投保人与房屋关系0：房主1：房主直系亲属2：租户',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='投保人表';



DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `open_id` varchar(255) DEFAULT NULL COMMENT '微信ID',
  `union_id` varchar(255) DEFAULT NULL COMMENT '开放平台唯一ID',
  `weixin_nick` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '微信昵称',
  `head_url` varchar(2000) DEFAULT NULL COMMENT '微信头像地址',
  `username` varchar(50) DEFAULT NULL COMMENT '用户登录名,登录用手机号',
  `password` varchar(50) DEFAULT NULL COMMENT '用户密码',
  `c_name` varchar(255)  NOT NULL COMMENT '必填  中文名',
  `e_name` varchar(255) DEFAULT NULL COMMENT '拼音或英文名，境外旅游险必填',
  `card_type` varchar(32) NOT NULL DEFAULT '1' COMMENT '必填  证件类型，证件类型：1：身份证、2:护照、3：出生证、4：驾照、5：港澳通行证、6：军官证、7：台胞证、8：警官证、9：港澳台回乡证10：组织机构代码证、11：营业执照、12：税务登记号、13：三证合一、14：统一信用代码证、99：其他、',
  `card_code` varchar(32) NOT NULL DEFAULT '' COMMENT '必填  证件号',
  `sex` varchar(6) NOT NULL COMMENT '必填  性别 0：女 1：男',
  `birthday` datetime NOT NULL COMMENT '必填  出生日期 格式：yyyy-MM-dd',
  `mobile` varchar(11) NOT NULL COMMENT '必填  手机号码',
  `email` varchar(255) NOT NULL COMMENT '必填  邮箱',
  `job` varchar(255) DEFAULT NULL COMMENT '职业信息，职业id使用“-”拼接如：101414-101415-101416',
  `country` varchar(500) DEFAULT NULL COMMENT '国籍',
  `prov_city_id` varchar(255) DEFAULT NULL COMMENT '居住省市，地区编码使用“-”拼接如：320000-320100-320104',
  `contact_address` varchar(255) DEFAULT NULL COMMENT '联系地址',
  `contact_post` varchar(255) DEFAULT NULL COMMENT '联系地址邮编',
  `applicant_type` varchar(4) DEFAULT NULL COMMENT '投保人类型 0：个人（默认） 1：公司',
  `card_period` datetime DEFAULT NULL COMMENT '证件有效期，格式yyyy-MM-dd',
  `marry_state` varchar(32) DEFAULT NULL COMMENT '婚姻状态 1：已婚 2：未婚 3：离婚 4：丧偶 5：其他',
  `yearly_income` varchar(255) DEFAULT NULL COMMENT '年收入',
  `office_address` varchar(255) DEFAULT NULL COMMENT '工作单位地址（办公地址）',
  `tel` varchar(255) DEFAULT NULL COMMENT '  工作单位电话（办公电话）',
  `work_company_name` varchar(255) DEFAULT NULL COMMENT '工作单位名称',
  `work_email` varchar(255) DEFAULT NULL COMMENT '工作单位邮箱',
  `office_post` varchar(6) DEFAULT NULL COMMENT '办公地址邮编',
  `have_medical` varchar(32) DEFAULT '0' COMMENT '是否有医保 0：否 1：是',
  `height` varchar(255) DEFAULT NULL COMMENT '身高',
  `weight` varchar(255) DEFAULT NULL COMMENT '体重',
  `is_staff` tinyint(4) DEFAULT '0' COMMENT '是否为企业员工 0:否，1:是',
  `recommend_id` int(11) DEFAULT NULL COMMENT '推荐人ID，关联user表ID',
  `t_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 -1:删除，0:正常',
  `subscribe_flag` tinyint(4) DEFAULT '1' COMMENT '是否已关注：0-否；1-是',
  `login_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '自动登录状态：0-否；1-是',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

----------2017-12-26----------
--慢慢花：订单表，添加操作备注字段

ALTER TABLE `product_order`
ADD COLUMN `remark`  varchar(255) NULL COMMENT '备注' AFTER `insure_down_url`;

----------2017-12-25----------
--慢慢花User表改动
ALTER TABLE `user`
MODIFY COLUMN `sex`  tinyint(1) NULL DEFAULT 0 COMMENT '性别，来自投保人 0：女 1：男' AFTER `head_url`,
MODIFY COLUMN `country`  varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '中国' COMMENT '国籍' AFTER `birthday`,
MODIFY COLUMN `marry_state`  tinyint(4) NULL DEFAULT 0 COMMENT '婚姻状态 0：未知 1：已婚 2：未婚 3：离婚 4：丧偶 5：其他' AFTER `tel`,
MODIFY COLUMN `have_medical`  tinyint(1) NULL DEFAULT 0 COMMENT '是否有医保 0：否 1：是' AFTER `property_post`;
----------2017-12-24----------
--慢慢花数据库表改动

ALTER TABLE `order_beneficiary`
MODIFY COLUMN `relation`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '受益人与被保险人关系说明 ' AFTER `proportion`;
ALTER TABLE `other_info`
CHANGE COLUMN `health_answerid` `health_answer_id`  int(11) NULL DEFAULT NULL COMMENT '健康告知id' AFTER `policy_password`;

----------2017-12-22----------
--慢慢花数据库表改动
ALTER TABLE `product_order`
MODIFY COLUMN `total_num`  int(11) NOT NULL DEFAULT '0' COMMENT '总份数' AFTER `in_order_no`,
MODIFY COLUMN `sex`  varchar(4) NOT NULL COMMENT '性别 0：女 1：男' AFTER `card_number`,
MODIFY COLUMN `marry_state`  tinyint(1) NOT NULL COMMENT '婚姻状态 1：已婚 2：未婚 3：离婚 4：丧偶 5：其他' AFTER `email`,
MODIFY COLUMN `have_medical`  tinyint(1) NULL DEFAULT 0 COMMENT '是否有医保 0：否 1：是' AFTER `property_post`;


ALTER TABLE `order_insurant`
MODIFY COLUMN `card_type`  varchar(32) NOT NULL COMMENT '证件类型：1：身份证、2:护照、3：出生证、4：驾照、5：港澳通行证、6：军官证、7：台胞证、8：警官证、9：港澳台回乡证10：组织机构代码证、11：营业执照、12：税务登记号、13：三证合一、14：统一信用代码证、99：其他、' AFTER `e_name`,
MODIFY COLUMN `sex`  varchar(4) NOT NULL COMMENT '性别 0：女 1：男' AFTER `card_number`,
MODIFY COLUMN `marry_state`  varchar(32) NULL DEFAULT NULL COMMENT '婚姻状态 1：已婚 2：未婚 3：离婚 4：丧偶 5：其他' AFTER `email`,
MODIFY COLUMN `have_medical`  varchar(6) NULL DEFAULT NULL COMMENT '是否有医保 0：否 1：是' AFTER `property_post`,
MODIFY COLUMN `buy_amount`  int(11) NULL DEFAULT 0 COMMENT '购买份数' AFTER `yearly_income`,
MODIFY COLUMN `relation_id`  varchar(32) NOT NULL COMMENT '被保人与投保人关系id 1:本人、2：妻子、3：丈夫、4：儿子、5：女儿、6：父亲、7：母亲、8：兄弟、9：姐妹、10：祖父/祖母/外祖父/外祖母、11：孙子/孙女/外孙/外孙女、12：叔父/伯父/舅舅、13：婶/姨/姑、14：侄子/侄女/外甥/外甥女、15：堂兄弟/堂姐妹/表兄弟/表姐妹、16：岳父、17：岳母、18：同事、19：朋友、20：雇主、21：雇员、22：法定监护人、23：其他' AFTER `single_price`,
MODIFY COLUMN `beneficiary_type`  tinyint(2) NOT NULL DEFAULT 0 COMMENT '受益人类型 0：无 1：法定 2：指定' AFTER `relation_id`;


ALTER TABLE `order_beneficiary`
MODIFY COLUMN `card_type`  varchar(32) NULL DEFAULT '0' COMMENT '证件类型：1：身份证、2:护照、3：出生证、4：驾照、5：港澳通行证、6：军官证、7：台胞证、8：警官证、9：港澳台回乡证10：组织机构代码证、11：营业执照、12：税务登记号、13：三证合一、14：统一信用代码证、99：其他、' AFTER `e_name`,
MODIFY COLUMN `card_number`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '证件号码' AFTER `card_type`,
MODIFY COLUMN `sex`  varchar(6) NOT NULL COMMENT '性别 0：女 1：男' AFTER `card_number`,
MODIFY COLUMN `serial`  int(32) NOT NULL COMMENT '受益序列 ' AFTER `birthday`;

ALTER TABLE `other_info`
MODIFY COLUMN `trip_purpose`  varchar(32) NULL DEFAULT NULL COMMENT '出行目的，境外旅游险必填（1:旅游、2:商务、3:探亲、4:留学、5:务工、6:其他）' AFTER `travel_no`,
MODIFY COLUMN `renewal_bank`  varchar(64) NULL DEFAULT NULL COMMENT '续保银行-银行名称（1:工商银行、2:建设银行、3:储蓄银行、4:农业银行、5:民生银行、6:招商银行7:兴业银行、8:中国银行、9:中信银行、10:交通银行、11:平安银行、12:光大银行、）' AFTER `health_answerid`,
MODIFY COLUMN `renewal_pay_bank`  varchar(64) NULL DEFAULT NULL COMMENT '续期缴费银行-银行名称（1:工商银行、2:建设银行、3:储蓄银行、4:农业银行、5:民生银行、6:招商银行、7:兴业银行、8:中国银行、9:中信银行、10:交通银行、11:平安银行、12:光大银行、）' AFTER `renewal_bank_addr`,
MODIFY COLUMN `withhold_bank`  varchar(64) NULL DEFAULT NULL COMMENT '代扣缴费银行-银行名称' AFTER `renewal_pay_branch`,
MODIFY COLUMN `pet_owner`  varchar(32) NULL DEFAULT NULL COMMENT '宠物主人 1：本人' AFTER `withhold_account`;

ALTER TABLE `product_order`
MODIFY COLUMN `marry_state`  varchar(32) NOT NULL COMMENT '婚姻状态 1：已婚 2：未婚 3：离婚 4：丧偶 5：其他' AFTER `email`,
MODIFY COLUMN `have_medical`  varchar(32) NULL DEFAULT '0' COMMENT '是否有医保 0：否 1：是' AFTER `property_post`;


ALTER TABLE `product_log`
ADD COLUMN `prod_id`  int(11) NOT NULL COMMENT '产品ID' AFTER `id`;

ALTER TABLE `order_beneficiary`
CHANGE COLUMN `insurantCname` `insurant_cname`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属被保险人姓名 ' AFTER `insurant_id`;

----------2017-12-21----------
--慢慢花：添加商品日志表
CREATE TABLE `product_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `case_code` varchar(64) DEFAULT NULL COMMENT '方案代码',
  `price` bigint(20) DEFAULT NULL COMMENT '商品价格',
  `off_shelf_status` tinyint(4) DEFAULT NULL COMMENT '上架状态：0-未上架 1-已上架',
  `check_status` tinyint(4) DEFAULT NULL COMMENT '审核状态：0-未审核 1-已审核',
  `operator_id` int(11) DEFAULT NULL COMMENT '操作人id',
  `t_status` tinyint(4) DEFAULT '0' COMMENT '状态： -1-逻辑删除 0-正常数据',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品日志表';

----------2017-12-21----------
--慢慢花订单状态修改
ALTER TABLE `product_order`
MODIFY COLUMN `t_status`  tinyint(4) NOT NULL DEFAULT 0 COMMENT '订单状态：-1：已删除、0：默认状态、10：待付款、15：待发货、20：已出单、80：已关闭、90：已失效' AFTER `insure_issue_time`;

----------2017-12-15----------
--慢慢花：分类表category添加字段，产品表product添加字段
ALTER TABLE `category`
MODIFY COLUMN `created_time`  timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' AFTER `updated_time`,
ADD COLUMN `is_display`  tinyint(4) NULL DEFAULT  COMMENT '是否显示：0-显示，1-不显示' AFTER `level`,
ADD COLUMN `description`  varchar(128) NULL COMMENT '分类描述' AFTER `is_display`;


ALTER TABLE `product`
MODIFY COLUMN `created_time`  timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' AFTER `updated_time`,
ADD COLUMN `min_insur_date`  varchar(255) NULL COMMENT '最低承保年龄' AFTER `default_price`,
ADD COLUMN `max_insur_date`  varchar(255) NULL COMMENT '最高承保年龄' AFTER `min_insur_date`,
ADD COLUMN `insur_date_limit`  varchar(255) NULL COMMENT '保障期限' AFTER `max_insur_date`;

----------2017-12-14----------
--慢慢花数据库，订单日志表修改，证件名称改为证件id
ALTER TABLE `order_log`
ADD COLUMN `order_id`  int(11) NULL COMMENT '订单id,对应订单表id' AFTER `id`;

ALTER TABLE `order_beneficiary`
CHANGE COLUMN `card_name` `card_type`  tinyint(4) NOT NULL DEFAULT 0 COMMENT '证件名称' AFTER `e_name`;

----------2017-12-13----------
--错误修改
ALTER TABLE `product_order`
CHANGE COLUMN `moblie` `mobile`  varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码' AFTER `contact_post`;
ALTER TABLE `order_insurant`
CHANGE COLUMN `moblie` `mobile`  varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码' AFTER `contact_post`;

----------2017-12-12----------
--慢慢花库订单增加日志表
CREATE TABLE `order_log` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`operator_id`  int(11) NULL COMMENT '操作人id' ,
`order_t_status`  tinyint(4) NULL DEFAULT 0 COMMENT '订单状态：-1：已删除、0：默认状态、100：待付款、150：待发货、200：已出单、300：已完成、400：已关闭、900：已失效' ,
`pay_status`  tinyint(4) NULL DEFAULT 0 COMMENT '支付状态 0：未支付 1：已支付 2：不能支付 3：扣款中 4：扣款失败 5：扣款成功' ,
`issue_status`  tinyint(4) NULL DEFAULT 0 COMMENT '出单状态 0：未出单 1：已出单 2：延时出单 3：取消出单' ,
`remark`  varchar(255) NULL COMMENT '备注' ,
`created_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间' ,
PRIMARY KEY (`id`)
)
;

ALTER TABLE `product_order`
ADD COLUMN `client_type`  tinyint(4) NULL DEFAULT 0 COMMENT '客户端类型 0：默认 1：PC，2：H5' AFTER `yearly_income`,
ADD COLUMN `gate_way`  tinyint(4) NULL DEFAULT 0 COMMENT '支付类型 0：默认 1：支付宝 3：银联 21:微信' AFTER `client_type`;

----------2017-12-12----------
--慢慢花订单表增加订单状态字段
ALTER TABLE `product_order`
ADD COLUMN `t_status`  tinyint(4) NOT NULL DEFAULT 0 COMMENT '订单状态：-1：已删除、0：默认状态、100：待付款、150：待发货、200：已出单、300：已完成、400：已关闭、900：已失效' AFTER `insure_issue_time`;

----------2017-12-08----------
--慢花综合险数据库表改动
ALTER TABLE `order_insurant`
CHANGE COLUMN `card_name` `card_type`  tinyint(4) NOT NULL COMMENT '证件类型：1：身份证、2:护照、3：出生证、4：驾照、5：港澳通行证、6：军官证、7：台胞证、8：警官证、9：港澳台回乡证10：组织机构代码证、11：营业执照、12：税务登记号、13：三证合一、14：统一信用代码证、99：其他、' AFTER `e_name`,
MODIFY COLUMN `country`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '国籍' AFTER `birthday`,
MODIFY COLUMN `policy_num`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '保险公司保单号' AFTER `buy_amount`,
CHANGE COLUMN `relation_name` `relation_id`  tinyint(4) NOT NULL COMMENT '被保人与投保人关系id 1:本人、2：妻子、3：丈夫、4：儿子、5：女儿、6：父亲、7：母亲、8：兄弟、9：姐妹、10：祖父/祖母/外祖父/外祖母、11：孙子/孙女/外孙/外孙女、12：叔父/伯父/舅舅、13：婶/姨/姑、14：侄子/侄女/外甥/外甥女、15：堂兄弟/堂姐妹/表兄弟/表姐妹、16：岳父、17：岳母、18：同事、19：朋友、20：雇主、21：雇员、22：法定监护人、23：其他' AFTER `single_price`;


ALTER TABLE `product_order`
DROP COLUMN `insure_no`,
MODIFY COLUMN `in_order_no`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '投保单号' AFTER `trans_no`,
MODIFY COLUMN `deadline_text`  varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '保障期限说明' AFTER `deadline`,
MODIFY COLUMN `issue_status`  tinyint(4) NOT NULL DEFAULT 0 COMMENT '出单状态 0：未出单 1：已出单 2：延时出单 3：取消出单' AFTER `deadline_text`,
MODIFY COLUMN `effective_status`  tinyint(4) NOT NULL DEFAULT 0 COMMENT '生效（退保）状态 0：未生效 1：已生效 2：退保中 3：已退保' AFTER `issue_status`,
MODIFY COLUMN `pay_status`  tinyint(4) NOT NULL DEFAULT 0 COMMENT '支付状态 0：未支付 1：已支付 2：不能支付 3：扣款中 4：扣款失败 5：扣款成功' AFTER `effective_status`,
MODIFY COLUMN `category_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '产品二级分类名称' AFTER `pay_amount`,
CHANGE COLUMN `card_name` `card_type`  tinyint(4) NOT NULL COMMENT '证件类型：1：身份证、2:护照、3：出生证、4：驾照、5：港澳通行证、6：军官证、7：台胞证、8：警官证、9：港澳台回乡证10：组织机构代码证、11：营业执照、12：税务登记号、13：三证合一、14：统一信用代码证、99：其他、' AFTER `e_name`,
MODIFY COLUMN `country`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '国籍' AFTER `birthday`;

----------2017-12-05----------
--增加标签表、标签组表,添加产品和标签关系表
DROP TABLE IF EXISTS `product_label_rel`;
CREATE TABLE `product_label_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `prod_id` int(11) NOT NULL COMMENT '产品ID',
  `label_id` int(11) NOT NULL COMMENT '标签ID',
  `t_status` tinyint(4) DEFAULT '0' COMMENT '状态： -1-逻辑删除 0-正常数据',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品和标签关系表';

DROP TABLE IF EXISTS `label`;
CREATE TABLE `label` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `label_name` varchar(255) DEFAULT NULL COMMENT '标签名称',
  `group_id` int(11) NOT NULL COMMENT '标签组ID',
  `description` varchar(255) DEFAULT NULL COMMENT '备注',
  `admin_id` int(11) DEFAULT NULL COMMENT '创建人关联ID',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态 0-正常使用 1-禁用',
  `t_status` tinyint(4) DEFAULT '0' COMMENT '状态： -1-逻辑删除 0-正常数据',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签表';

DROP TABLE IF EXISTS `label_group`;
CREATE TABLE `label_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `group_name` varchar(255) DEFAULT NULL COMMENT '标签组名称',
  `description` varchar(255) DEFAULT NULL COMMENT '备注',
  `t_status` tinyint(4) DEFAULT '0' COMMENT '状态： -1-逻辑删除 0-正常数据',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签组表';


----------2017-12-01----------
--新建 other_info表
CREATE TABLE `other_info` (
	`id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
	`travel_no` varchar(32) DEFAULT NULL COMMENT '旅行团号',
	`trip_purpose` tinyint(2) DEFAULT NULL COMMENT '出行目的，境外旅游险必填（1:旅游、2:商务、3:探亲、4:留学、5:务工、6:其他）',
	`destination` varchar(2000) DEFAULT NULL COMMENT '出行目的地，境外旅游险必填，多个目的地使用中文顿号“、”分隔',
	`visa_city` varchar(500) DEFAULT NULL COMMENT '签证办理城市名称，申根签证保险必填',
	`flt_no` varchar(32) DEFAULT NULL COMMENT '航班号，航意险必填',
	`flt_date` datetime DEFAULT NULL COMMENT '起飞日期，航意险必填，格式：yyyy-mm-dd',
	`flight_from_city` varchar(255) DEFAULT NULL COMMENT '航班出发城市名称',
	`flight_to_city` varchar(255) DEFAULT NULL COMMENT '航班到达城市名称',
	`property_address` varchar(255) DEFAULT NULL COMMENT '财产所在地，地区编码使用“-”拼接，具体地址拼接在最后，如：440000-440300-440305-深圳动漫园',
	`property_post` varchar(255) DEFAULT NULL COMMENT '财产所在地邮编',
	`policy_password` varchar(255) DEFAULT NULL COMMENT '保单密码',
	`health_answerid` int(11) DEFAULT NULL COMMENT '健康告知id',
	`renewal_bank` tinyint(2) DEFAULT NULL COMMENT '续保银行-银行名称（1:工商银行、2:建设银行、3:储蓄银行、4:农业银行、5:民生银行、6:招商银行7:兴业银行、8:中国银行、9:中信银行、10:交通银行、11:平安银行、12:光大银行、）',
	`renewal_cardholder` varchar(255) DEFAULT NULL COMMENT '续保银行-持卡人（须为投保人姓名）',
	`renewal_account` varchar(255) DEFAULT NULL COMMENT '续保银行-银行账户',
	`renewal_bank_addr` varchar(255) DEFAULT NULL COMMENT '续保银行-银行地址',
	`renewal_pay_bank` tinyint(2) DEFAULT NULL COMMENT '续期缴费银行-银行名称（1:工商银行、2:建设银行、3:储蓄银行、4:农业银行、5:民生银行、6:招商银行、7:兴业银行、8:中国银行、9:中信银行、10:交通银行、11:平安银行、12:光大银行、）',
	`renewal_pay_cardholder` varchar(255) DEFAULT NULL COMMENT '续期缴费银行-持卡人（须为投保人姓名）',
	`renewal_pay_account` varchar(255) DEFAULT NULL COMMENT '续期缴费银行-银行账户',
	`renewal_pay_bank_addr` varchar(255) DEFAULT NULL COMMENT '续期缴费银行-银行地址',
	`renewal_pay_branch` varchar(255) DEFAULT NULL COMMENT '续期缴费银行-开户支行',
	`withhold_bank` tinyint(2) DEFAULT NULL COMMENT '代扣缴费银行-银行名称',
	`withhold_cardholder` varchar(255) DEFAULT NULL COMMENT '代扣缴费银行-持卡人（须为投保人姓名）',
	`withhold_account` varchar(255) DEFAULT NULL COMMENT '代扣缴费银行-银行账户',
	`pet_owner` tinyint(4) DEFAULT NULL COMMENT '宠物主人 1：本人',
	`dog_license` varchar(255) DEFAULT NULL COMMENT '养犬许可证',
	`dog_immunelicense` varchar(255) DEFAULT NULL COMMENT '犬类免疫证号码',
	`dog_species` varchar(255) DEFAULT NULL COMMENT '宠物犬种类',
	`urgency_contact` varchar(255) DEFAULT NULL COMMENT '紧急联系人',
	`urgency_contact_mobile` varchar(255) DEFAULT NULL COMMENT '紧急联系人手机号',
	`date1` datetime DEFAULT NULL COMMENT '备用日期1，格式：yyyy-mm-dd',
	`date2` datetime DEFAULT NULL COMMENT '备用日期2，格式：yyyy-mm-dd',
	`text1` varchar(500) DEFAULT NULL COMMENT '备用字段1',
	`text2` varchar(500) DEFAULT NULL COMMENT '备用字段2',
	`text3` varchar(500) DEFAULT NULL COMMENT '备用字段3',
	`created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='其它信息表';

ALTER TABLE `product_order`
ADD COLUMN `other_info_id`  int(11) NULL COMMENT '其他信息表对应的id' AFTER `user_id`;

----------2017-11-22----------
--表限制更改

ALTER TABLE `user`
MODIFY COLUMN `open_id`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '微信ID' AFTER `id`,
MODIFY COLUMN `weixin_nick`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '微信昵称' AFTER `union_id`,
MODIFY COLUMN `sex`  tinyint(1) NOT NULL DEFAULT 0 COMMENT '性别，来自投保人 0：女 1：男' AFTER `head_url`,
MODIFY COLUMN `card_name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '证件名称' AFTER `e_name`,
MODIFY COLUMN `card_number`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '证件号码' AFTER `card_name`,
MODIFY COLUMN `country`  varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '中国' COMMENT '国籍' AFTER `birthday`,
MODIFY COLUMN `marry_state`  tinyint(4) NOT NULL DEFAULT 0 COMMENT '婚姻状态 0：未知 1：已婚 2：未婚 3：离婚 4：丧偶 5：其他' AFTER `tel`,
MODIFY COLUMN `have_medical`  tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否有医保 0：否 1：是' AFTER `property_post`,
MODIFY COLUMN `subscribe_flag`  tinyint(4) NULL DEFAULT 1 COMMENT '是否已关注：0-否；1-是' AFTER `t_status`,
MODIFY COLUMN `login_flag`  tinyint(4) NOT NULL DEFAULT 0 COMMENT '自动登录状态：0-否；1-是' AFTER `subscribe_flag`;

----------2017-11-21----------
--将order表变更为product_order表

DROP TABLE IF EXISTS `order`;
CREATE TABLE `product_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `product_id` varchar(32) NOT NULL COMMENT '产品id对应product表的主键id',
  `user_id` int(11) DEFAULT NULL COMMENT '对应user表的主键id',
  `trans_no` varchar(64) NOT NULL COMMENT '订单号，齐欣的交易流水号',
   `in_order_no` varchar(64) NOT NULL COMMENT '投保单号',
   `insure_no` varchar(64) NOT NULL COMMENT '保单号',
  `total_num` int(11) NOT NULL COMMENT '总份数',
  `start_date` datetime DEFAULT NULL COMMENT '起保日期 格式：yyyy-MM-dd',
  `end_date` datetime DEFAULT NULL COMMENT '终保日期 格式：yyyy-MM-dd',
  `deadline` varchar(32) NOT NULL COMMENT '保障期限',
  `deadline_text` varchar(2000) NOT NULL COMMENT '保障期限说明',
  `issue_status` tinyint(4) NOT NULL COMMENT '出单状态 0：未出单 1：已出单 2：延时出单 3：取消出单',
  `effective_status` tinyint(4) NOT NULL COMMENT '生效（退保）状态 0：未生效 1：已生效 2：退保中 3：已退保',
  `pay_status` tinyint(4) NOT NULL COMMENT '支付状态 0：未支付 1：已支付 2：不能支付 3：扣款中 4：扣款失败 5：扣款成功',
  `pay_amount` bigint(20) NOT NULL COMMENT '保单总保费（单位：分）',
  `category_name` varchar(255) NOT NULL COMMENT '产品二级分类名称',
  `insure_time` datetime DEFAULT NULL COMMENT '投保时间 格式：yyyy-MM-dd HH:mm:ss',
  `c_name` varchar(255) DEFAULT NULL COMMENT '中文名',
  `e_name` varchar(255) DEFAULT NULL COMMENT '拼音/英文名',
  `card_name` varchar(255) NOT NULL COMMENT '证件名称',
  `card_number` varchar(32) NOT NULL COMMENT '证件号码',
  `sex` tinyint(1) NOT NULL COMMENT '性别 0：女 1：男',
  `birthday` datetime DEFAULT NULL COMMENT '出生日期 格式：yyyy-MM-dd',
  `country` varchar(500) NOT NULL COMMENT '国籍',
  `prov_city_text` varchar(255) DEFAULT NULL COMMENT '居住省市名称',
  `job_text` varchar(255) DEFAULT NULL COMMENT '职业名称',
  `home_address` varchar(255) DEFAULT NULL COMMENT '家庭地址',
  `home_post` varchar(255) DEFAULT NULL COMMENT '家庭地址邮编',
  `office_address` varchar(255) DEFAULT NULL COMMENT '办公地址',
  `office_post` varchar(6) DEFAULT NULL COMMENT '办公地址邮编',
  `tel` varchar(11) DEFAULT NULL COMMENT '办公电话',
  `contact_address` varchar(255) DEFAULT NULL COMMENT '常用联系地址',
  `contact_post` varchar(6) DEFAULT NULL COMMENT '常用联系地址邮编',
  `moblie` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(64) DEFAULT NULL COMMENT '电子邮箱',
  `marry_state` tinyint(4) NOT NULL COMMENT '婚姻状态 1：已婚 2：未婚 3：离婚 4：丧偶 5：其他',
  `house_type_name` varchar(255) DEFAULT NULL COMMENT '房屋类型名称',
  `property_address` varchar(255) DEFAULT NULL COMMENT '财产所在地',
  `property_post` varchar(6) DEFAULT NULL COMMENT '财产所在地邮编',
  `have_medical` tinyint(1) NOT NULL COMMENT '是否有医保 0：否 1：是',
  `height` varchar(255) DEFAULT NULL COMMENT '身高',
  `weight` varchar(255) DEFAULT NULL COMMENT '体重',
  `yearly_income` varchar(255) DEFAULT NULL COMMENT '年收入',
   `pay_time` datetime DEFAULT NULL COMMENT '成功支付完成时间',
  `order_issue_time` datetime DEFAULT NULL COMMENT '出单时间,保险公司承保时间',
   `insure_issue_time` datetime DEFAULT NULL COMMENT '生成保单时间',
  `insure_down_url` varchar(200) DEFAULT NULL COMMENT '保单下载地址',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单/投保单表';


----------2017-11-13----------
--建立库表结构

-- ----------------------------
-- Table structure for `order`
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `product_id` varchar(32) NOT NULL COMMENT '产品id对应product表的主键id',
  `user_id` int(11) DEFAULT NULL COMMENT '对应user表的主键id',
  `trans_no` varchar(64) NOT NULL COMMENT '订单号，齐欣的交易流水号',
   `in_order_no` varchar(64) NOT NULL COMMENT '投保单号',
   `insure_no` varchar(64) NOT NULL COMMENT '保单号',
  `total_num` int(11) NOT NULL COMMENT '总份数',
  `start_date` datetime DEFAULT NULL COMMENT '起保日期 格式：yyyy-MM-dd',
  `end_date` datetime DEFAULT NULL COMMENT '终保日期 格式：yyyy-MM-dd',
  `deadline` varchar(32) NOT NULL COMMENT '保障期限',
  `deadline_text` varchar(2000) NOT NULL COMMENT '保障期限说明',
  `issue_status` tinyint(4) NOT NULL COMMENT '出单状态 0：未出单 1：已出单 2：延时出单 3：取消出单',
  `effective_status` tinyint(4) NOT NULL COMMENT '生效（退保）状态 0：未生效 1：已生效 2：退保中 3：已退保',
  `pay_status` tinyint(4) NOT NULL COMMENT '支付状态 0：未支付 1：已支付 2：不能支付 3：扣款中 4：扣款失败 5：扣款成功',
  `pay_amount` bigint(20) NOT NULL COMMENT '保单总保费（单位：分）',
  `category_name` varchar(255) NOT NULL COMMENT '产品二级分类名称',
  `insure_time` datetime DEFAULT NULL COMMENT '投保时间 格式：yyyy-MM-dd HH:mm:ss',
  `c_name` varchar(255) DEFAULT NULL COMMENT '中文名',
  `e_name` varchar(255) DEFAULT NULL COMMENT '拼音/英文名',
  `card_name` varchar(255) NOT NULL COMMENT '证件名称',
  `card_number` varchar(32) NOT NULL COMMENT '证件号码',
  `sex` tinyint(1) NOT NULL COMMENT '性别 0：女 1：男',
  `birthday` datetime DEFAULT NULL COMMENT '出生日期 格式：yyyy-MM-dd',
  `country` varchar(500) NOT NULL COMMENT '国籍',
  `prov_city_text` varchar(255) DEFAULT NULL COMMENT '居住省市名称',
  `job_text` varchar(255) DEFAULT NULL COMMENT '职业名称',
  `home_address` varchar(255) DEFAULT NULL COMMENT '家庭地址',
  `home_post` varchar(255) DEFAULT NULL COMMENT '家庭地址邮编',
  `office_address` varchar(255) DEFAULT NULL COMMENT '办公地址',
  `office_post` varchar(6) DEFAULT NULL COMMENT '办公地址邮编',
  `tel` varchar(11) DEFAULT NULL COMMENT '办公电话',
  `contact_address` varchar(255) DEFAULT NULL COMMENT '常用联系地址',
  `contact_post` varchar(6) DEFAULT NULL COMMENT '常用联系地址邮编',
  `moblie` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(64) DEFAULT NULL COMMENT '电子邮箱',
  `marry_state` tinyint(4) NOT NULL COMMENT '婚姻状态 1：已婚 2：未婚 3：离婚 4：丧偶 5：其他',
  `house_type_name` varchar(255) DEFAULT NULL COMMENT '房屋类型名称',
  `property_address` varchar(255) DEFAULT NULL COMMENT '财产所在地',
  `property_post` varchar(6) DEFAULT NULL COMMENT '财产所在地邮编',
  `have_medical` tinyint(1) NOT NULL COMMENT '是否有医保 0：否 1：是',
  `height` varchar(255) DEFAULT NULL COMMENT '身高',
  `weight` varchar(255) DEFAULT NULL COMMENT '体重',
  `yearly_income` varchar(255) DEFAULT NULL COMMENT '年收入',
   `pay_time` datetime DEFAULT NULL COMMENT '成功支付完成时间',
  `order_issue_time` datetime DEFAULT NULL COMMENT '出单时间,保险公司承保时间',
   `insure_issue_time` datetime DEFAULT NULL COMMENT '生成保单时间',
  `insure_down_url` varchar(200) DEFAULT NULL COMMENT '保单下载地址',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单/投保单表';

-- ----------------------------
-- Records of order
-- ----------------------------

-- ----------------------------
-- Table structure for `order_beneficiary`
-- ----------------------------
DROP TABLE IF EXISTS `order_beneficiary`;
CREATE TABLE `order_beneficiary` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `insurant_id` varchar(32) NOT NULL COMMENT 'id对应order_insurant表的主键id',
  `insurantCname` varchar(255) NOT NULL COMMENT '所属被保险人姓名 ',
  `c_name` varchar(255) DEFAULT NULL COMMENT '中文名',
  `e_name` varchar(255) DEFAULT NULL COMMENT '拼音/英文名',
  `card_name` varchar(255) NOT NULL COMMENT '证件名称',
  `card_number` varchar(32) NOT NULL COMMENT '证件号码',
  `sex` tinyint(1) NOT NULL COMMENT '性别 0：女 1：男',
  `birthday` datetime DEFAULT NULL COMMENT '出生日期 格式：yyyy-MM-dd',
  `serial` int(5) NOT NULL COMMENT '受益序列 ',
  `proportion` int(3) NOT NULL COMMENT '受益比例 ',
  `relation` varchar(255) NOT NULL COMMENT '受益人与被保险人关系说明 ',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间 ',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='受益人信息表 ';

-- ----------------------------
-- Records of order_beneficiary
-- ----------------------------

-- ----------------------------
-- Table structure for `order_ensure_project`
-- ----------------------------
DROP TABLE IF EXISTS `order_ensure_project`;
CREATE TABLE `order_ensure_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` int(11) NOT NULL COMMENT '订单id对应order表的主键id',
  `project_name` varchar(32) NOT NULL COMMENT '保障项目名称',
  `sum_insured` bigint(20) NOT NULL DEFAULT '0' COMMENT '保额',
  `unit_text` varchar(32) NOT NULL COMMENT '保额单位 1：万元 2：元/天 3：万元/年',
  `start_date` datetime DEFAULT NULL COMMENT '保障开始时间 格式：yyyy-MM-dd',
  `end_date` datetime DEFAULT NULL COMMENT '保障结束时间 格式：yyyy-MM-dd',
  `insured_text` varchar(255) NOT NULL COMMENT '保障内容说明',
  `valid` tinyint(1) NOT NULL COMMENT '高保额生效状态 0：未生效 1：已生效',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间 ',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保障项目表 ';

-- ----------------------------
-- Records of order_ensure
-- ----------------------------

-- ----------------------------
-- Table structure for `order_insurant`
-- ----------------------------
DROP TABLE IF EXISTS `order_insurant`;
CREATE TABLE `order_insurant` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` int(11) NOT NULL COMMENT '订单id对应order表的主键id',
  `c_name` varchar(255) DEFAULT NULL COMMENT '中文名',
  `e_name` varchar(255) DEFAULT NULL COMMENT '拼音/英文名',
  `card_name` varchar(255) NOT NULL COMMENT '证件名称',
  `card_number` varchar(32) NOT NULL COMMENT '证件号码',
  `sex` tinyint(1) NOT NULL COMMENT '性别 0：女 1：男',
  `birthday` datetime DEFAULT NULL COMMENT '出生日期 格式：yyyy-MM-dd',
  `country` varchar(500) NOT NULL COMMENT '国籍',
  `prov_city_text` varchar(255) DEFAULT NULL COMMENT '居住省市名称',
  `job_text` varchar(255) DEFAULT NULL COMMENT '职业名称',
  `home_address` varchar(255) DEFAULT NULL COMMENT '家庭地址',
  `home_post` varchar(255) DEFAULT NULL COMMENT '家庭地址邮编',
  `office_address` varchar(255) DEFAULT NULL COMMENT '办公地址',
  `office_post` varchar(6) DEFAULT NULL COMMENT '办公地址邮编',
  `tel` varchar(11) DEFAULT NULL COMMENT '办公电话',
  `contact_address` varchar(255) DEFAULT NULL COMMENT '常用联系地址',
  `contact_post` varchar(6) DEFAULT NULL COMMENT '常用联系地址邮编',
  `moblie` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(64) DEFAULT NULL COMMENT '电子邮箱',
  `marry_state` tinyint(4) DEFAULT NULL COMMENT '婚姻状态 1：已婚 2：未婚 3：离婚 4：丧偶 5：其他',
  `house_type_name` varchar(255) DEFAULT NULL COMMENT '房屋类型名称',
  `property_address` varchar(255) DEFAULT NULL COMMENT '财产所在地',
  `property_post` varchar(6) DEFAULT NULL COMMENT '财产所在地邮编',
  `have_medical` tinyint(1) DEFAULT NULL COMMENT '是否有医保 0：否 1：是',
  `height` varchar(255) DEFAULT NULL COMMENT '身高',
  `weight` varchar(255) DEFAULT NULL COMMENT '体重',
  `yearly_income` varchar(255) DEFAULT NULL COMMENT '年收入',
  `buy_amount` int(11) NOT NULL DEFAULT '0' COMMENT '购买份数',
  `policy_num` varchar(32) NOT NULL COMMENT '保险公司保单号',
  `single_price` bigint(20) NOT NULL DEFAULT '0' COMMENT '支付价格（单位：分）',
  `relation_name` varchar(32) NOT NULL COMMENT '被保人与投保人关系',
  `beneficiary_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '受益人类型 0：无 1：法定 2：指定',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='被保人信息表';

-- ----------------------------
-- Records of order_insurant
-- ----------------------------




-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `open_id` varchar(255) NOT NULL COMMENT '微信ID',
  `union_id` varchar(255) DEFAULT NULL COMMENT '开放平台唯一ID',
  `weixin_nick` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '微信昵称',
  `head_url` varchar(2000) DEFAULT NULL COMMENT '微信头像地址',
  `sex` tinyint(1) NOT NULL COMMENT '性别，来自投保人 0：女 1：男',
  `username` varchar(50) DEFAULT NULL COMMENT '用户登录名,登录用手机号',
  `password` varchar(50) DEFAULT NULL COMMENT '用户密码',
  `mobile_no` varchar(255) DEFAULT NULL COMMENT '联系移动电话',
  `id_no` varchar(255) DEFAULT NULL COMMENT '身份证号码',
  `real_name` varchar(255) DEFAULT NULL COMMENT '真实姓名',
  `c_name` varchar(255) DEFAULT NULL COMMENT '中文名',
  `e_name` varchar(255) DEFAULT NULL COMMENT '拼音/英文名',
  `card_name` varchar(255) NOT NULL COMMENT '证件名称',
  `card_number` varchar(32) NOT NULL COMMENT '证件号码',
  `birthday` datetime DEFAULT NULL COMMENT '出生日期 格式：yyyy-MM-dd',
  `country` varchar(500) NOT NULL COMMENT '国籍',
  `prov_city_text` varchar(255) DEFAULT NULL COMMENT '居住省市名称',
  `job_text` varchar(255) DEFAULT NULL COMMENT '职业名称',
  `home_address` varchar(255) DEFAULT NULL COMMENT '家庭地址',
  `home_post` varchar(255) DEFAULT NULL COMMENT '家庭地址邮编',
  `office_address` varchar(255) DEFAULT NULL COMMENT '办公地址',
  `office_post` varchar(6) DEFAULT NULL COMMENT '办公地址邮编',
  `tel` varchar(11) DEFAULT NULL COMMENT '办公电话',
  `marry_state` tinyint(4) NOT NULL COMMENT '婚姻状态 1：已婚 2：未婚 3：离婚 4：丧偶 5：其他',
  `house_type_name` varchar(255) DEFAULT NULL COMMENT '房屋类型名称',
  `property_address` varchar(255) DEFAULT NULL COMMENT '财产所在地',
  `property_post` varchar(6) DEFAULT NULL COMMENT '财产所在地邮编',
  `have_medical` tinyint(1) NOT NULL COMMENT '是否有医保 0：否 1：是',
  `height` varchar(255) DEFAULT NULL COMMENT '身高',
  `weight` varchar(255) DEFAULT NULL COMMENT '体重',
  `yearly_income` varchar(255) DEFAULT NULL COMMENT '年收入',
  `is_staff` tinyint(4) DEFAULT '0' COMMENT '是否为企业员工 0:否，1:是',
  `recommend_id` int(11) DEFAULT NULL COMMENT '推荐人ID，关联user表ID',
  `t_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 -1:删除，0:正常',
  `subscribe_flag` tinyint(4) DEFAULT NULL COMMENT '是否已关注：0-否；1-是',
  `login_flag` tinyint(4) NOT NULL COMMENT '自动登录状态：0-否；1-是',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for `user_contact`
-- ----------------------------
DROP TABLE IF EXISTS `user_contact`;
CREATE TABLE `user_contact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `contact_name` varchar(32) DEFAULT NULL COMMENT '联系人名字',
  `mobile` varchar(11) DEFAULT NULL COMMENT '联系人手机号',
  `email` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `is_default` int(11) DEFAULT NULL COMMENT '是否默认地址：0、否；1、是；',
  `province` varchar(50) DEFAULT NULL COMMENT '省份',
  `city` varchar(50) DEFAULT NULL COMMENT '城市',
  `area` varchar(50) DEFAULT NULL COMMENT '区域',
  `t_status` tinyint(2) NOT NULL COMMENT '状态：-1 作废 1正常 ',
  `address` varchar(128) DEFAULT NULL COMMENT '地址',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户联络方式表';

-- ----------------------------
-- Records of user_contact
-- ----------------------------

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `case_code` varchar(32) NOT NULL COMMENT '方案代码',
  `prod_name` varchar(255) NOT NULL COMMENT '产品名称',
  `plan_name` varchar(255) DEFAULT NULL COMMENT '计划名称',
  `company_name` varchar(255) NOT NULL COMMENT '保险公司名称',
 `company_logo_url` varchar(255) DEFAULT NULL COMMENT '保险公司logo地址',
  `off_shelf` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否下架 0：否 1：是',
  `frist_category` int(11) NOT NULL COMMENT '一级分类（齐欣云服分类）',
  `second_category` int(11) NOT NULL COMMENT '二类分级（齐欣云服分类）',
  `img_url_src` varchar(255) DEFAULT NULL COMMENT '图片Url地址（齐欣云服原始地址）',
  `img_url_show` varchar(255) DEFAULT NULL COMMENT '图片Url地址（平台展示地址）',
  `default_price` bigint(20) DEFAULT NULL COMMENT '默认价格',
  `prod_read` varchar(255) DEFAULT NULL COMMENT '产品解读',
`premium_table` varchar(255) DEFAULT NULL COMMENT '费率表地址',
  `third_p_url` varchar(255) DEFAULT NULL COMMENT '源产品详情地址（齐欣）',
  `description` varchar(255) DEFAULT NULL COMMENT '产品简述',
  `t_status` tinyint(4) DEFAULT '0' COMMENT '状态  -1：逻辑删除 0：正常数据',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='保险产品表';

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `cat_name` varchar(64) DEFAULT NULL COMMENT '分类名称',
  `icon_url` varchar(255) DEFAULT NULL COMMENT '分类图标url地址',
  `sort_index` tinyint(4) DEFAULT NULL COMMENT '排序的索引',
  `parent_id` int(11) DEFAULT NULL COMMENT '父分类的Id，一级分类父分类Id为0',
  `level` int(1) DEFAULT NULL COMMENT '分类级别',
  `t_status` tinyint(4) DEFAULT '0' COMMENT '状态 -1、逻辑删除 0、正常数据 ',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='产品分类表';

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for product_category_rel
-- ----------------------------
DROP TABLE IF EXISTS `product_category_rel`;
CREATE TABLE `product_category_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `prod_id` int(11) NOT NULL COMMENT '产品ID',
  `cat_id` int(11) NOT NULL COMMENT '产品分类ID',
  `t_status` tinyint(4) DEFAULT '0' COMMENT '状态 -1：逻辑删除 0：正常数据 ',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='产品和分类关系表';

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for product_provision
-- ----------------------------
DROP TABLE IF EXISTS `product_provision`;
CREATE TABLE `product_provision` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `prod_id` int(11) NOT NULL COMMENT '产品ID',  
  `prov_type` tinyint(4) NOT NULL COMMENT '条款类型 0-主条款 1-附加条款',
  `title` varchar(255) DEFAULT NULL COMMENT '条款名称',
  `attachment_url` varchar(255) DEFAULT NULL COMMENT '文件地址',
  `content` varchar(255) DEFAULT NULL COMMENT '条款内容',
  `t_status` tinyint(4) DEFAULT '0' COMMENT '状态 -1：逻辑删除 0：正常数据 ',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='产品条款表';



