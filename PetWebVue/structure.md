# 宠物管理平台前端项目结构

## 技术栈
- Vue 3
- TypeScript
- Vite
- Vue Router
- Pinia (状态管理)
- Axios (HTTP请求)
- ECharts (数据可视化)

## 目录结构设计

```
PetWebVue/
├── public/                 # 静态资源目录
├── src/                    # 源代码目录
│   ├── api/                # API接口封装
│   │   ├── request.ts      # Axios实例和拦截器
│   │   ├── userApi.ts      # 用户相关API
│   │   ├── petApi.ts       # 宠物相关API
│   │   └── healthApi.ts    # 健康记录相关API
│   ├── assets/             # 静态资源
│   │   ├── images/         # 图片资源
│   │   └── styles/         # 全局样式
│   ├── components/         # 通用组件
│   │   ├── layout/         # 布局组件
│   │   │   ├── Sidebar.vue # 侧边栏组件
│   │   │   └── Footer.vue  # 页脚组件
│   │   ├── common/         # 通用UI组件
│   │   └── charts/         # 图表组件
│   ├── router/             # 路由配置
│   │   └── index.ts        # 路由定义
│   ├── stores/             # Pinia状态管理
│   │   ├── userStore.ts    # 用户状态
│   │   └── petStore.ts     # 宠物状态
│   ├── types/              # TypeScript类型定义
│   │   ├── user.ts         # 用户相关类型
│   │   └── pet.ts          # 宠物相关类型
│   ├── utils/              # 工具函数
│   │   ├── auth.ts         # 认证相关
│   │   └── format.ts       # 格式化工具
│   ├── views/              # 页面组件
│   │   ├── LoginPage.vue   # 登录页面
│   │   ├── RegisterPage.vue # 注册页面
│   │   ├── Dashboard.vue   # 用户主页/宠物列表
│   │   ├── PetDetailPage.vue # 宠物详情页
│   │   ├── RemindersPage.vue # 提醒事项页面 
│   │   └── CommunityFeed.vue # 社区帖子页
│   ├── App.vue             # 根组件
│   ├── main.ts             # 入口文件
│   └── env.d.ts            # 环境变量类型定义
├── .gitignore              # Git忽略文件
├── index.html              # HTML模板
├── package.json            # 项目依赖
├── tsconfig.json           # TypeScript配置
├── vite.config.ts          # Vite配置
├── README.md               # 项目说明
├── progress.md             # 项目进度记录
└── structure.md            # 项目结构说明(本文件)
```

## 核心模块设计

### 1. 用户认证模块
- 登录/注册功能
- Token管理
- 用户信息存储
- 路由守卫

### 2. 宠物管理模块
- 宠物列表展示
- 宠物详情页
- 宠物健康记录
- 宠物提醒事项

### 3. 社区互动模块
- 帖子列表
- 帖子详情
- 评论功能

### 4. 数据可视化模块
- 宠物健康数据图表
- 体重变化趋势
- 活动记录统计

### 5. 导航与布局
- 响应式侧边栏导航
- 可折叠菜单
- 页面布局适配

## 数据流设计

1. **API层**：封装与后端的通信
2. **Store层**：管理全局状态
3. **组件层**：消费状态并展示UI

## 路由设计

| 路径 | 组件 | 说明 | 权限 |
|------|------|------|------|
| /login | LoginPage | 用户登录 | 公开 |
| /register | RegisterPage | 用户注册 | 公开 |
| /dashboard | Dashboard | 用户主页/宠物列表 | 需登录 |
| /pets | Dashboard | 宠物列表 | 需登录 |
| /pet/:id | PetDetailPage | 宠物详情 | 需登录 |
| /community | CommunityFeed | 社区帖子 | 需登录 |
| /reminders | RemindersPage | 提醒事项 | 需登录 |

## 状态管理设计

### userStore
- 状态：token, userInfo
- 操作：login, register, logout
- 计算属性：isLoggedIn

### petStore
- 状态：petList, currentPet, loading, error
- 操作：fetchPets, fetchPetDetail, addPet, updatePet
- 计算属性：hasPets 

## 登录页面逻辑优化
- 优化：LoginPage.vue 登录失败时错误提示逻辑，提升用户体验。
- userStore和LoginPage.vue增加了token清理逻辑，防止无效token影响路由跳转。 