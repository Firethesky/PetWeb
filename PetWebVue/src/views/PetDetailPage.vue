<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { usePetStore } from '../stores/petStore';

const route = useRoute();
const router = useRouter();
const petStore = usePetStore();

// 响应式变量
const isLoading = ref(true);
const error = ref<string | null>(null);
const petId = ref<number>(parseInt(route.params.id as string));

// 获取宠物详情
onMounted(async () => {
  if (isNaN(petId.value)) {
    error.value = '无效的宠物ID';
    isLoading.value = false;
    return;
  }

  try {
    await petStore.fetchPetDetail(petId.value);
    isLoading.value = false;
  } catch (err: any) {
    error.value = err.message || '无法获取宠物详情';
    isLoading.value = false;
  }
});

// 返回宠物列表
const goBack = () => {
  router.push('/pets');
};
</script>

<template>
  <div class="pet-detail-page">
    <div class="page-header">
      <button class="back-btn" @click="goBack">← 返回</button>
      <h1>宠物详情</h1>
    </div>

    <!-- 加载状态 -->
    <div v-if="isLoading" class="loading-state">
      <p>正在加载宠物信息...</p>
    </div>

    <!-- 错误状态 -->
    <div v-else-if="error || !petStore.currentPet" class="error-state">
      <p>{{ error || '找不到此宠物信息' }}</p>
      <button @click="goBack">返回宠物列表</button>
    </div>

    <!-- 宠物详情 -->
    <div v-else-if="petStore.currentPet" class="pet-detail-card">
      <div class="pet-profile">
        <div class="pet-avatar" :style="petStore.currentPet.imageUrl ? { backgroundImage: `url(${petStore.currentPet.imageUrl})` } : {}">
          <span v-if="!petStore.currentPet.imageUrl" class="pet-placeholder">{{ petStore.currentPet.name[0] }}</span>
        </div>
        <div class="pet-name">
          <h2>{{ petStore.currentPet.name }}</h2>
          <p>{{ petStore.currentPet.type }} · {{ petStore.currentPet.breed }}</p>
        </div>
      </div>

      <div class="pet-attributes">
        <div class="attribute">
          <span class="attribute-label">年龄</span>
          <span class="attribute-value">{{ petStore.currentPet.age }}岁</span>
        </div>
        <div class="attribute">
          <span class="attribute-label">性别</span>
          <span class="attribute-value">{{ petStore.currentPet.gender }}</span>
        </div>
        <div class="attribute">
          <span class="attribute-label">体重</span>
          <span class="attribute-value">{{ petStore.currentPet.weight }}kg</span>
        </div>
      </div>

      <div v-if="petStore.currentPet.description" class="pet-description">
        <h3>描述</h3>
        <p>{{ petStore.currentPet.description }}</p>
      </div>
      
      <div class="pet-actions">
        <button class="edit-btn">编辑信息</button>
        <button class="health-records-btn">健康记录</button>
        <button class="reminder-btn">设置提醒</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.pet-detail-page {
  padding: 20px;
}

.page-header {
  display: flex;
  align-items: center;
  margin-bottom: 30px;
}

.back-btn {
  background: none;
  border: none;
  font-size: 16px;
  color: #4CAF50;
  cursor: pointer;
  padding: 0;
  margin-right: 15px;
}

.page-header h1 {
  margin: 0;
  color: #34495e;
}

.loading-state, 
.error-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 50px;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.error-state {
  border-left: 4px solid #f44336;
}

.pet-detail-card {
  background-color: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.pet-profile {
  display: flex;
  align-items: center;
  margin-bottom: 30px;
}

.pet-avatar {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  background-color: #e0e0e0;
  background-size: cover;
  background-position: center;
  margin-right: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.pet-placeholder {
  font-size: 40px;
  color: #9e9e9e;
}

.pet-name h2 {
  margin: 0;
  color: #34495e;
}

.pet-name p {
  margin: 5px 0 0 0;
  color: #666;
}

.pet-attributes {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
  padding: 15px 0;
  border-top: 1px solid #e0e0e0;
  border-bottom: 1px solid #e0e0e0;
}

.attribute {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.attribute-label {
  font-size: 14px;
  color: #9e9e9e;
  margin-bottom: 5px;
}

.attribute-value {
  font-size: 18px;
  font-weight: bold;
  color: #34495e;
}

.pet-description {
  margin-bottom: 30px;
}

.pet-description h3 {
  font-size: 18px;
  color: #34495e;
  margin-bottom: 10px;
}

.pet-description p {
  color: #666;
  line-height: 1.5;
}

.pet-actions {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 15px;
}

.pet-actions button {
  padding: 12px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
}

.edit-btn {
  background-color: #4CAF50;
  color: white;
}

.health-records-btn {
  background-color: #2196F3;
  color: white;
}

.reminder-btn {
  background-color: #FF9800;
  color: white;
}
</style> 