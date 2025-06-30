<script setup lang="ts">
import { computed } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '../../stores/userStore';

const props = defineProps({
  collapsed: {
    type: Boolean,
    default: false
  }
});

const userStore = useUserStore();
const router = useRouter();

// 计算侧边栏的宽度
const sidebarWidth = computed(() => props.collapsed ? '0px' : '200px');

// 处理登出
const handleLogout = () => {
  userStore.logoutAction();
  router.push('/login');
};
</script>

<template>
  <div class="sidebar" :style="{ width: sidebarWidth }">
    <div class="sidebar-header">
      <h1 v-if="!collapsed">导航栏</h1>
    </div>

    <div class="sidebar-menu">
      <router-link to="/dashboard" class="menu-item" :class="{ 'collapsed': collapsed }">
        <span class="menu-icon">🏠</span>
        <span class="menu-text" v-if="!collapsed">首页</span>
      </router-link>

      <router-link to="/pets" class="menu-item" :class="{ 'collapsed': collapsed }">
        <span class="menu-icon">🐾</span>
        <span class="menu-text" v-if="!collapsed">我的宠物</span>
      </router-link>

      <router-link to="/health" class="menu-item" :class="{ 'collapsed': collapsed }">
        <span class="menu-icon">💊</span>
        <span class="menu-text" v-if="!collapsed">健康记录</span>
      </router-link>

      <router-link to="/community" class="menu-item" :class="{ 'collapsed': collapsed }">
        <span class="menu-icon">👥</span>
        <span class="menu-text" v-if="!collapsed">社区</span>
      </router-link>
      
      <router-link to="/reminders" class="menu-item" :class="{ 'collapsed': collapsed }">
        <span class="menu-icon">⏰</span>
        <span class="menu-text" v-if="!collapsed">提醒</span>
      </router-link>

      <router-link to="/profile" class="menu-item" :class="{ 'collapsed': collapsed }">
        <span class="menu-icon">👤</span>
        <span class="menu-text" v-if="!collapsed">个人中心</span>
      </router-link>
    </div>

    <div class="sidebar-footer">
      <div class="menu-item" @click="handleLogout" :class="{ 'collapsed': collapsed }">
        <span class="menu-icon">🚪</span>
        <span class="menu-text" v-if="!collapsed">登出</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.sidebar {
  display: flex;
  flex-direction: column;
  background-color: #f0f0f0;
  color: rgb(0, 0, 0);
  height: calc(100vh - 60px); /* 减去Header高度 */
  transition: width 0.3s ease;
  position: fixed;
  left: 0;
  top: 60px; /* Header高度 */
  z-index: 90; /* 低于Header的z-index */
  overflow: hidden;
}

.sidebar-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 15px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.sidebar-header h1 {
  font-size: 1.2rem;
  margin: 0;
  white-space: nowrap;
  overflow: hidden;
}

.sidebar-menu {
  flex: 1;
  padding: 15px 0;
  overflow-y: auto;
}

.menu-item {
  display: flex;
  align-items: center;
  padding: 12px 15px;
  color: rgba(119, 119, 119, 0.7);
  text-decoration: none;
  transition: all 0.3s;
  cursor: pointer;
  white-space: nowrap;
  overflow: hidden;
}

.menu-item:hover, 
.menu-item.router-link-active {
  background-color: rgba(0, 0, 0, 0.1);
  color: rgb(0, 0, 0);
}

.menu-icon {
  font-size: 1.2rem;
  margin-right: 15px;
  width: 24px;
  text-align: center;
}

.menu-text {
  white-space: nowrap;
  overflow: hidden;
}

.sidebar-footer {
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  padding: 15px 0;
}

.menu-item.collapsed {
  justify-content: center;
  padding: 12px 0;
}

.menu-item.collapsed .menu-icon {
  margin-right: 0;
}
</style> 