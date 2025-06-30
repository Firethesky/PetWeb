<script setup lang="ts">
// Footer组件
</script>

<template>
  <footer class="app-footer" :class="{ 'with-sidebar': withSidebar }">
    <p>© 2025 宠物管理平台</p>
  </footer>
</template>

<script lang="ts">
export default {
  props: {
    withSidebar: {
      type: Boolean,
      default: false
    }
  }
}
</script>

<style scoped>
.app-footer {
  background-color: #f5f5f5;
  padding: 20px;
  text-align: center;
  border-top: 1px solid #e0e0e0;
  height: 60px;
}

.app-footer.with-sidebar {
  margin-left: 240px;
  transition: margin-left 0.3s ease;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .app-footer.with-sidebar {
    margin-left: 64px;
  }
}
</style> 