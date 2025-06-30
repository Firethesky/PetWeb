# 宠物管理平台项目结构

## 项目概述
基于Spring Boot的宠物管理平台后端API，提供用户管理、宠物管理、健康记录、提醒功能和社区功能。

## 目录结构
```
PetWebApi/
├── src/
│   ├── main/
│   │   ├── java/usst/hsy/PetWebApi/
│   │   │   ├── config/           # 配置类
│   │   │   ├── controller/       # 控制器层
│   │   │   ├── entity/          # 实体类
│   │   │   ├── repository/      # 数据访问层
│   │   │   ├── service/         # 业务逻辑层
│   │   │   └── PetWebApiApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/                    # 测试代码
├── pom.xml                      # Maven配置
├── README.md                    # 项目说明
├── progress.md                  # 项目进度
├── bugs.md                      # 问题修复记录
└── structure.md                 # 项目结构说明
```

## 核心模块

### 1. 用户管理模块
- **控制器**: `UserController.java`
- **服务层**: `UserService.java`
- **实体类**: `User.java`
- **功能**: 用户注册、登录、信息管理
- **最新更新**: 添加了登录token处理功能

### 2. 宠物管理模块
- **控制器**: `PetController.java`
- **服务层**: `PetService.java`
- **实体类**: `Pet.java`
- **功能**: 宠物信息管理

### 3. 健康记录模块
- **控制器**: `HealthRecordController.java`
- **服务层**: `HealthRecordService.java`
- **实体类**: `HealthRecord.java`
- **功能**: 宠物健康记录管理

### 4. 提醒功能模块
- **控制器**: `ReminderController.java`
- **服务层**: `ReminderService.java`
- **实体类**: `Reminder.java`
- **功能**: 日常提醒事项管理

### 5. 社区功能模块
- **控制器**: `PostController.java`, `CommentController.java`
- **服务层**: `PostService.java`, `CommentService.java`
- **实体类**: `Post.java`, `Comment.java`
- **功能**: 帖子发布、评论互动

## 技术架构
- **框架**: Spring Boot 3.5
- **数据访问**: Spring Data JPA
- **数据库**: MySQL 8.0
- **JSON处理**: Jackson
- **构建工具**: Maven

## 最近更新 (2024-12-19)
- 在UserController中添加了登录token处理功能
- 使用UUID生成唯一token标识
- 返回格式：{"token": "token_xxx", "user": {...}}
- 支持前端登录逻辑的token存储需求 