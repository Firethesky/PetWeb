# 宠物管理平台后端API

## 项目简介
这是一个基于Spring Boot的宠物管理平台后端API项目，为宠物主人提供管理宠物健康、日常提醒、社区分享等功能的接口服务。

## 技术栈
- Spring Boot 3.5
- Spring Data JPA
- MySQL 8.0
- Jackson (JSON处理)
- Maven

## 核心功能
1. **用户管理**：注册、登录、个人信息管理
2. **宠物管理**：添加、编辑宠物信息
3. **健康记录**：记录宠物疫苗、体检等健康信息
4. **提醒功能**：设置喂食、遛狗等日常提醒事项
5. **社区功能**：发布帖子、评论互动

## 数据模型
项目包含以下主要实体：
- User：用户信息
- Pet：宠物信息
- HealthRecord：宠物健康记录
- Reminder：提醒事项
- Post：社区帖子
- Comment：评论

## 安装与运行
1. 克隆项目
```bash
git clone https://github.com/yourusername/PetWebApi.git
```

2. 配置数据库连接
编辑`src/main/resources/application.properties`文件：
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/petweb?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. 构建和运行项目
```bash
cd PetWebApi
mvn spring-boot:run
```

4. API访问
服务启动后，API接口默认可通过`http://localhost:8088/api/`访问

## API文档
主要API端点：

### 用户接口
- `GET /api/users` - 获取所有用户
- `GET /api/users/{id}` - 获取指定用户
- `POST /api/users/register` - 注册新用户
- `POST /api/users/login` - 用户登录

**登录接口详情：**
- 请求体：`{"username": "用户名", "password": "密码"}`
- 成功响应：`{"token": "token_xxx", "user": {...}}`
- 失败响应：`{"error": "错误信息"}`

### 宠物接口
- `GET /api/pets`