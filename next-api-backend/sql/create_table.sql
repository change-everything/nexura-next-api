# 数据库初始化
# @author <a href="https://github.com/liyupi">程序员鱼皮</a>
# @from <a href="https://yupi.icu">编程导航知识星球</a>

-- 创建库
create database if not exists next_api_db;

-- 切换库
use next_api_db;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    user_account  varchar(256)                           not null comment '账号',
    user_password varchar(512)                           not null comment '密码',
    union_id      varchar(256)                           null comment '微信开放平台id',
    mp_open_id     varchar(256)                           null comment '公众号openId',
    user_name     varchar(256)                           null comment '用户昵称',
    user_avatar   varchar(1024)                          null comment '用户头像',
    user_profile  varchar(512)                           null comment '用户简介',
    user_role     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    create_time   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete     tinyint      default 0                 not null comment '是否删除',
    index idx_union_id (union_id)
) comment '用户' collate = utf8mb4_unicode_ci;


-- 接口信息
create table if not exists `interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `name` varchar(256) not null comment '名称',
    `description` varchar(256) null comment '描述',
    `url` varchar(512) not null comment '接口地址',
    `request_header` text null comment '请求头',
    `response_header` text null comment '响应头',
    `status` int default 0 not null comment '接口状态（0-关闭，1-开启）',
    `method` varchar(256) not null comment '请求类型',
    `user_id` bigint not null comment '创建人',
    `create_time` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `is_delete` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
) comment '接口信息';

-- 用户调用接口关系表
create table if not exists next_api_db.`user_interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `user_id` bigint not null comment '调用用户 id',
    `interface_info_id` bigint not null comment '接口 id',
    `total_num` int default 0 not null comment '总调用次数',
    `left_num` int default 0 not null comment '剩余调用次数',
    `status` int default 0 not null comment '0-正常，1-禁用',
    `create_time` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `is_delete` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
) comment '用户调用接口关系';


INSERT INTO next_api_db.interface_info (name, description, url, request_header, response_header, status, method, user_id, create_time, update_time, is_delete, request_params) VALUES ('getUserNameByPost', '获取用户名，使用post请求(测试)', 'http://127.0.0.1:9178/api/name/json/', '{"Content-Type":  "application/json"}', '{
  "Content-Type": "application/json"
}', 1, 'POST', 1748163269452926978, '2024-01-24 18:37:44', '2024-01-29 15:35:09', 0, '{
    "userName": string
}');
INSERT INTO next_api_db.interface_info (name, description, url, request_header, response_header, status, method, user_id, create_time, update_time, is_delete, request_params) VALUES ('getNameUsingGet', '使用get请求获取名称', 'http://127.0.0.1:9178/api/name/', '{
  "Content-Type": "application/json"
}', '{
  "Content-Type": "application/json"
}', 1, 'GET', 1748163269452926978, '2024-01-29 15:36:48', '2024-01-29 17:42:25', 0, '{
  "userName": string
}');
INSERT INTO next_api_db.interface_info (name, description, url, request_header, response_header, status, method, user_id, create_time, update_time, is_delete, request_params) VALUES ('getNameUsingPost', '用POST方法获取名字', 'http://127.0.0.1:9178/api/name/', '{
  "Content-Type": "application/json"
}', '{
  "Content-Type": "application/json"
}', 1, 'POST', 1748163269452926978, '2024-01-29 17:43:44', '2024-01-29 17:43:49', 0, '{
  "userName": string
}');

create table if not exists `interface_params`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `param_name` varchar(256) not null comment '参数名',
    `description` varchar(256) null comment '描述',
    `is_must` tinyint default 0 not null comment '是否必填(0-必填, 1-非必填)',
    `example_value` text null comment '示例值',
    `create_time` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `is_delete` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
) comment '接口参数信息';


create table if not exists `interface_info_params`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `interface_info_id` bigint not null comment '接口ID',
    `interface_param_id` bigint not null comment '参数ID'
) comment '接口参数信息关联表';

create table if not exists `interface_response`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `response_name` varchar(256) not null comment '响应名称',
    `description` varchar(256) null comment '描述',
    `response_type` varchar(32) default 0 not null comment '响应类型',
    `example_value` text null comment '示例值',
    `create_time` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `is_delete` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
) comment '接口参数信息';
