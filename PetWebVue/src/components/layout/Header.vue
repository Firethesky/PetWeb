<script setup lang="ts">
import { computed } from 'vue';
import { useUserStore } from '../../stores/userStore';

const props = defineProps({
  collapsed: {
    type: Boolean,
    default: false
  }
});

const emit = defineEmits(['toggleSidebar']);

const userStore = useUserStore();

// 计算用户名显示
const username = computed(() => {
  // 首先尝试从userStore获取用户名
  if (userStore.userInfo?.username) {
    return userStore.userInfo.username;
  }
  
  // 如果userStore中没有，尝试从localStorage获取
  try {
    const savedUserInfo = localStorage.getItem('userInfo');
    if (savedUserInfo) {
      const userInfo = JSON.parse(savedUserInfo);
      return userInfo.username;
    }
  } catch (e) {
    console.error('解析localStorage中的用户信息失败', e);
  }
  
  // 都没有就显示未登录
  return '未登录';
});

// 触发侧边栏切换
const handleToggleSidebar = () => {
  emit('toggleSidebar');
};
</script>

<template>
  <header class="header">
    <div class="header-left">
      <button class="sidebar-toggle" @click="handleToggleSidebar">
        <span v-if="collapsed">☰</span>
        <span v-else>×</span>
      </button>

      <h1 class="site-title">宠物网</h1>
    </div>
    <div class="header-right">
      <span class="username">{{ username }}</span>
    </div>
  </header>
</template>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  height: 60px;
  background-color: #ffffff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 100; /* 确保Header在最上层 */
}

.header-left {
  display: flex;
  align-items: center;
}

.sidebar-toggle {
  background: none;
  border: none;
  font-size: 1.5rem;
  margin-right: 15px;
  cursor: pointer;
  padding: 5px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #333;
  width: 30px;
  height: 30px;
  border-radius: 4px;
  transition: background-color 0.2s ease;
}

.sidebar-toggle:hover {
  background-color: rgba(0, 0, 0, 0.05);
}

.site-title {
  font-size: 1.5rem;
  font-weight: bold;
  color: #333;
  margin: 0;
}

.header-right {
  display: flex;
  align-items: center;
}

.username {
  font-size: 1rem;
  color: #666;
}
</style> 