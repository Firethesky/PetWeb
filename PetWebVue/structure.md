# PetWebVue 前端结构

本文档概述了基于后端 API 的 PetWebVue 应用程序的前端结构设计。

## 整体架构

前端将是一个单页应用程序 (SPA)，可能会使用像 Vue.js 这样的现代 JavaScript 框架。它将通过后端 API (`/api`) 进行数据管理和交互。

## 关键模块/功能

根据后端控制器分析，前端需要支持以下主要模块：

1.  **用户管理:**
    *   注册
    *   登录
    *   用户个人资料查看/编辑
    *   (潜在功能) 用户删除

2.  **宠物管理:**
    *   查看宠物列表 (全部宠物和特定用户的宠物)
    *   查看特定宠物的详细信息
    *   添加新宠物
    *   编辑现有宠物的详细信息
    *   删除宠物 (软删除和永久删除)
    *   查看用户拥有的宠物数量

3.  **帖子管理 (社区/论坛):**
    *   查看所有帖子的分页列表 (带排序选项)
    *   查看特定用户发布的帖子 (分页)
    *   查看热门帖子 (按点赞数、评论数排序)
    *   查看特定帖子的详细信息
    *   创建新帖子
    *   编辑现有帖子
    *   点赞/取消点赞帖子
    *   删除帖子

4.  **评论管理 (针对帖子):**
    *   查看所有评论
    *   查看特定帖子的评论 (根评论和回复)
    *   查看对特定父评论的回复
    *   查看特定用户发表的评论
    *   通过ID查看特定评论
    *   在帖子上创建根评论
    *   创建对另一条评论的回复
    *   编辑现有评论
    *   删除评论
    *   查看帖子的评论数量

5.  **健康记录管理 (针对宠物):**
    *   查看所有健康记录
    *   查看特定宠物的健康记录
    *   按类型筛选宠物的健康记录 (例如，疫苗接种、驱虫)
    *   按日期范围筛选宠物的健康记录
    *   查看即将到期的健康记录 (例如，下次疫苗接种日期)
    *   查看特定健康记录的详细信息
    *   为宠物创建新的健康记录
    *   编辑现有的健康记录
    *   删除健康记录

6.  **提醒管理:**
    *   查看所有提醒
    *   查看特定用户的提醒
    *   查看特定宠物的提醒
    *   查看用户的待处理提醒
    *   按日期范围筛选用户的提醒
    *   按事件类型查看待处理提醒 (例如，VACCINATION - 疫苗接种, FEEDING - 喂食)
    *   查看特定提醒的详细信息
    *   创建新提醒 (与宠物和用户关联)
    *   编辑现有提醒
    *   将提醒标记为已完成
    *   删除提醒

## 建议的目录结构 (高层级)

```
PetWebVue/
├── public/
│   ├── index.html
│   └── ... (其他静态资源)
├── src/
│   ├── assets/
│   │   └── ... (图片、全局样式)
│   ├── components/  // 可复用的 UI 组件
│   │   ├── common/ // 通用组件 (按钮、输入框、模态框等)
│   │   ├── pets/
│   │   │   ├── PetCard.vue
│   │   │   ├── PetForm.vue
│   │   │   └── HealthRecordItem.vue
│   │   ├── posts/
│   │   │   ├── PostCard.vue
│   │   │   ├── PostForm.vue
│   │   │   └── CommentItem.vue
│   │   ├── user/
│   │   │   └── UserProfileDisplay.vue
│   │   └── reminders/
│   │       └── ReminderItem.vue
│   ├── views/  // 页面级组件，映射到路由
│   │   ├── Home.vue
│   │   ├── Login.vue
│   │   ├── Register.vue
│   │   ├── UserDashboard.vue
│   │   ├── PetList.vue
│   │   ├── PetDetail.vue
│   │   ├── AddPet.vue
│   │   ├── EditPet.vue
│   │   ├── PostList.vue
│   │   ├── PostDetail.vue
│   │   ├── CreatePost.vue
│   │   ├── EditPost.vue
│   │   ├── HealthRecordList.vue // 特定宠物的健康记录列表
│   │   ├── AddHealthRecord.vue
│   │   ├── ReminderList.vue // 特定用户/宠物的提醒列表
│   │   └── AddReminder.vue
│   ├── router/
│   │   └── index.js // Vue Router 配置
│   ├── store/ // 状态管理 (例如 Vuex 或 Pinia)
│   │   ├── modules/
│   │   │   ├── auth.js       // 认证模块
│   │   │   ├── pets.js       // 宠物模块
│   │   │   ├── posts.js      // 帖子模块
│   │   │   ├── comments.js   // 评论模块
│   │   │   ├── healthRecords.js // 健康记录模块
│   │   │   └── reminders.js  // 提醒模块
│   │   └── index.js
│   ├── services/ // API 交互层
│   │   ├── authService.js
│   │   ├── petService.js
│   │   ├── postService.js
│   │   ├── commentService.js
│   │   ├── healthRecordService.js
│   │   ├── reminderService.js
│   │   └── apiClient.js // 基础 Axios 或 fetch 配置
│   ├── App.vue // 根 Vue 组件
│   └── main.js // 主要入口文件
├── .gitignore
├── babel.config.js
├── package.json
└── README.md (或当前存在的 readme.txt)
```

## 数据流

*   **Views (视图)** 将负责通过 **Services (服务)** 获取数据。
*   **Services (服务)** 将封装对后端的 API 调用。
*   **Store (状态管理)** (Vuex/Pinia) 将管理全局应用程序状态，例如登录用户信息，并可能缓存获取的数据以避免冗余的 API 调用。
*   **Components (组件)** 将从 Views (视图) 或直接从 Store (状态管理) 接收数据作为 props。它们将发出事件以通知父组件或在 Store (状态管理) 中触发操作。

## 后续步骤 (非当前开发阶段)

*   选择特定的 Vue.js 版本和 UI 框架 (例如 Vuetify, Element Plus)。
*   搭建基础项目结构。
*   实现路由功能。
*   实现状态管理。
*   针对每个模块迭代开发组件和视图。

此结构为 PetWebVue 应用程序提供了一个模块化且可扩展的基础。
