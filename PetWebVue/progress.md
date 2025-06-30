# 宠物管理平台前端开发进度

## 开发计划

### 阶段一：项目初始化与基础架构搭建 (The Foundation)
**目标**：创建项目骨架

**计划任务**：
- 使用Vite创建Vue 3 + TypeScript项目
- 安装核心依赖：vue-router, pinia, axios, echarts
- 在main.ts中配置vue-router和pinia
- 创建清晰的src目录结构：api, assets, components, router, stores, views

### 阶段二：核心路由与页面骨架 (The Skeleton)
**目标**：定义应用的页面导航结构

**计划任务**：
- 配置Vue Router路由实例
- 创建核心路由：
  - /login -> LoginPage.vue
  - /register -> RegisterPage.vue
  - /dashboard -> Dashboard.vue (用户主页，展示宠物列表)
  - /pet/:id -> PetDetailPage.vue (宠物详情)
  - /community -> CommunityFeed.vue (社区帖子)
- 创建对应的页面组件占位符
- 修改App.vue，放置router-view

### 阶段三：数据服务与状态管理 (The "Plumbing and Wiring")
**目标**：建立与后端API的通信，管理用户登录状态

**计划任务**：
- 封装Axios实例(request.ts)，配置baseURL、请求/响应拦截器
- 创建用户API模块(userApi.ts)，实现注册和登录功能
- 创建用户Store(userStore.ts)，管理用户状态：
  - State: token和userInfo
  - Actions: loginAction, registerAction, logoutAction
  - Getters: isLoggedIn

### 阶段四：核心功能实现 (Building The Rooms)
**目标**：实现仪表盘展示用户宠物列表功能

**计划任务**：
- 创建宠物API模块(petApi.ts)，实现获取用户宠物列表功能
- 开发Dashboard.vue页面：
  - 调用getMyPets API获取数据
  - 展示宠物列表
  - 处理加载状态和空数据状态

### 阶段五：UI美化与高级组件集成 (Interior Design)
**目标**：美化界面，集成ECharts图表展示宠物健康数据

**计划任务**：
- 美化仪表盘，将宠物列表改造成卡片式布局
- 创建HealthChart.vue组件，使用ECharts绘制宠物体重变化趋势图
- 集成图表到宠物详情页：
  - 创建健康API模块(healthApi.ts)
  - 在PetDetailPage.vue中获取并展示宠物健康数据

## 当前进度

### 2024-05-24
- 项目计划制定完成
- 完成阶段一：项目初始化与基础架构搭建
  - 更新package.json，添加所需依赖
  - 配置TypeScript支持
  - 创建项目目录结构
- 完成阶段二：核心路由与页面骨架
  - 创建路由配置(router/index.ts)
  - 创建基础页面组件(LoginPage, RegisterPage, Dashboard等)
  - 更新App.vue，添加路由视图
- 完成阶段三：数据服务与状态管理
  - 封装Axios请求模块(api/request.ts)
  - 创建API模块(userApi.ts, petApi.ts, healthApi.ts)
  - 创建状态管理Store(userStore.ts, petStore.ts)

### 2024-05-25
- 添加UI导航改进：
  - 创建可折叠的侧边栏组件(components/layout/Sidebar.vue)
  - 集成侧边栏到App.vue，适配路由导航
  - 更新路由配置，添加提醒页面路由
  - 创建RemindersPage.vue组件
- 开始阶段四：实现仪表盘展示用户宠物列表功能
  - 完善Dashboard.vue页面，增加宠物列表展示
  - 添加宠物详情页功能，改进页面交互
  - 添加加载状态、错误处理和空数据处理

### 2024-05-26
- 持续优化布局组件：
  - 将App.vue中的页脚(footer)提取为单独的组件(components/layout/Footer.vue)
  - 重构页脚样式，改善组件的可复用性
  - 优化了响应式设计，确保在不同屏幕尺寸上的一致性显示
- 继续实施组件化战略，提高代码的可维护性和复用性

### 下一步计划
- 继续完善阶段四：
  - 实现添加和编辑宠物的表单功能
  - 完善交互体验
- 开始阶段五：UI美化与高级组件集成
  - 创建健康记录图表组件
  - 集成健康记录到宠物详情页

## 已完成

- 项目基础架构搭建
  - Vue 3 + TypeScript + Vite 配置
  - 路由配置
  - Pinia 状态管理
  - API 请求封装
- 用户认证模块
  - 登录页面（完成）
    - 表单验证
    - 记住用户功能
    - 登录错误提示
    - 界面美化
  - 注册页面（基础结构）

## 进行中

- 完善注册页面
- 仪表盘页面开发

## 待完成

- 宠物管理功能
  - 宠物列表
  - 宠物详情
  - 宠物添加/编辑
- 健康记录功能
- 提醒功能
- 社区功能
- [x] 修复登录页面登录失败时页面闪烁且无错误提示的问题。
- [x] 修复因无效token导致登录失败后页面跳转的问题。 