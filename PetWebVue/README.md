# 宠物管理系统

这是一个使用 Vue 3 + TypeScript + Vite 构建的宠物管理系统前端项目。

## 项目设置

### 安装依赖

使用 npm:
```bash
npm install
```

使用 pnpm:
```bash
pnpm install
```

### 安装 Element Plus

项目已在 package.json 中添加了 Element Plus 相关依赖，您可以直接使用 pnpm 安装所有依赖：

```bash
pnpm install
```

如果您需要单独安装 Element Plus：

```bash
# 使用 pnpm 安装 Element Plus
pnpm add element-plus

# 安装 Element Plus 图标
pnpm add @element-plus/icons-vue
```

### 开发环境运行

```bash
pnpm dev
```

### 构建生产版本

```bash
pnpm build
```

## 项目功能

- 用户认证（登录/注册）
- 宠物管理
- 健康记录
- 提醒功能
- 社区交流

## 技术栈

- Vue 3
- TypeScript
- Vite
- Vue Router
- Pinia
- Axios
- Element Plus

## 项目简介
这是一个基于Vue 3 + TypeScript的宠物管理平台前端项目，为宠物主人提供管理宠物健康、日常提醒、社区分享等功能的用户界面。

## 核心功能
- 用户认证：登录/注册系统
- 宠物档案：添加和管理宠物基本信息
- 健康记录：跟踪宠物健康状况，包括疫苗、体检等
- 日常提醒：设置喂食、遛狗、洗澡等提醒
- 社区互动：与其他宠物主人分享经验和照片

## 界面特点
- 响应式设计，适配不同屏幕尺寸
- 可折叠侧边栏导航，提高用户体验
- 卡片式布局展示宠物列表
- 直观的状态展示（加载中、错误、空数据）

## 项目结构
详见[structure.md](./structure.md)文件

## 开发进度
详见[progress.md](./progress.md)文件

## 与后端API对接
前端通过Axios与后端RESTful API进行通信，API基地址在开发环境下默认为'/api'，可通过环境变量配置。

## 浏览器兼容性
- 推荐使用最新版本的Chrome、Firefox、Safari或Edge浏览器
- 不支持IE浏览器

## 登录页面（LoginPage.vue）
- 修复了登录失败时页面闪烁且无错误提示的问题。
- 现在登录失败会正确显示错误信息，且不会因输入框输入而立即消失。
- 登录失败时会自动清理token，避免因无效token导致页面跳转。
- 登录页初始化时也会清理token，保证体验。
