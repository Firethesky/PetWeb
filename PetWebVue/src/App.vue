<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import Header from './components/layout/Header.vue';
import Sidebar from './components/layout/Sidebar.vue';
import { useUserStore } from './stores/userStore';

const route = useRoute();
const userStore = useUserStore();

// 侧边栏折叠状态
const sidebarCollapsed = ref(false);
const toggleSidebar = () => {
  sidebarCollapsed.value = !sidebarCollapsed.value;
};

// 判断当前页面是否需要显示侧边栏（登录和注册页面不需要）
const showSidebar = computed(() => {
  // 添加调试输出
  // console.log('当前登录状态:', userStore.isLoggedIn)
  // console.log('当前路由名称:', route.name)
  // console.log('localStorage中的token:', localStorage.getItem('token'))
  
  // return userStore.isLoggedIn && !['Login', 'Register'].includes(route.name as string);
  return !['Login', 'Register'].includes(route.name as string);
});

// 判断是否显示Header（登录和注册页面不需要）
const showHeader = computed(() => {
  return !['Login', 'Register'].includes(route.name as string);
});

// 页面加载后，如果已登录但没有用户信息，则获取用户信息
onMounted(async () => {
  // 调试信息，检查localStorage中的token
  console.log('页面加载时localStorage中的token:', localStorage.getItem('token'));
  
  if (userStore.isLoggedIn) {
    console.log('检测到登录状态，尝试获取用户信息...');
    try {
      await userStore.getUserInfoAction();
      console.log('成功获取用户信息', userStore.userInfo);
    } catch (error) {
      console.error('获取用户信息失败', error);
      // 如果获取用户信息失败，可能是token过期，清除登录状态
      userStore.logoutAction();
      console.log('已清除登录状态');
    }
  } else {
    console.log('用户未登录或token不存在');
  }
});
</script>

<template>
  <div class="app-container">
    <!-- 头部 -->
    <Header v-if="showHeader" :collapsed="sidebarCollapsed" @toggle-sidebar="toggleSidebar" />
    
    <!-- 侧边栏 -->
    <Sidebar v-if="showSidebar" :collapsed="sidebarCollapsed" />

    <!-- 主内容区 -->
    <div class="main-content" :class="{ 'with-sidebar': showSidebar && !sidebarCollapsed, 'with-header': showHeader }">
      <router-view />
    </div>
    
    <!-- 页脚 -->
    <!-- <Footer :withSidebar="showSidebar" /> -->
  </div>
</template>

<style>
/* 全局样式 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  margin: 0;
  font-family: Arial, sans-serif;
  background-color: #ffffff;
  color: #333;
}

.app-container {
  display: block;
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  position: relative;
}

.main-content {
  flex: 1;
  padding: 0; /* 移除内边距，让子组件自行控制内边距 */
  min-height: calc(100vh - 60px); /* 减去Header高度 */
  transition: margin 0.3s ease;
  display: flex;
  flex-direction: column;
}

.main-content.with-sidebar {
  margin-left: 200px; /* 侧边栏宽度 */
}

.main-content.with-header {
  margin-top: 60px; /* Header高度 */
}

/* 同时有侧边栏和头部时 */
.main-content.with-sidebar.with-header {
  margin-left: 200px;
  margin-top: 60px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .main-content.with-sidebar {
    margin-left: 64px;
  }
  
  .main-content.with-sidebar.with-header {
    margin-left: 64px;
  }
}
</style>
