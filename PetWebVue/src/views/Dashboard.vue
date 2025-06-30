<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { usePetStore } from '../stores/petStore';

const petStore = usePetStore();
const router = useRouter();

// 响应式变量
const isLoading = ref(true);
const error = ref<string | null>(null);
const currentDate = ref(new Date());

// 宠物健康指数
const petHealthIndex: Record<string, number> = {
  '毛毛': 85,
  '咪咪': 92
};

// 待办任务列表
const tasks = ref([
  { id: 1, time: '09:00', title: '给毛毛喂早餐', petName: '毛毛', status: '已完成' },
  { id: 2, time: '14:00', title: '咪咪疫苗接种', petName: '咪咪', status: '待完成' },
  { id: 3, time: '18:00', title: '遛狗时间', petName: '毛毛', status: '待完成' },
  { id: 4, time: '20:00', title: '给咪咪梳毛', petName: '咪咪', status: '待完成' }
]);

// 获取宠物列表
onMounted(async () => {
  try {
    await petStore.fetchPets();
    isLoading.value = false;
  } catch (err: any) {
    error.value = err.message || '无法获取宠物列表';
    isLoading.value = false;
  }
});

// 查看宠物详情
const viewPetDetail = (petId: number) => {
  router.push(`/pet/${petId}`);
};

// 添加新宠物
const addNewPet = () => {
  router.push('/add-pet');
};

// 记录喂食
const recordFeeding = () => {
  // 实现记录喂食的功能
  console.log('记录喂食');
};

// 遛狗打卡
const walkDogCheckin = () => {
  // 实现遛狗打卡功能
  console.log('遛狗打卡');
};

// 拍照记录
const takePhotoRecord = () => {
  // 实现拍照记录功能
  console.log('拍照记录');
};

// 添加提醒
const addReminder = () => {
  // 实现添加提醒功能
  console.log('添加提醒');
};

// 格式化日期
const formatDate = (date: Date): string => {
  return date.toLocaleDateString('zh-CN', { month: 'long', day: 'numeric' });
};
</script>

<template>
  <div class="dashboard">
    <!-- 顶部欢迎区域 -->
    <div class="welcome-header">
      <div class="welcome-text">
        <h1>欢迎回来！</h1>
        <p>今天是美好的一天，和爱宠一起享受吧 </p>
      </div>
      <div class="weather-icon">
        🌞
      </div>
    </div>

    <!-- 宠物卡片区域 -->
    <div class="pet-cards-container">
      <!-- 宠物卡片 -->
      <div class="pet-card" v-for="pet in petStore.petList" :key="pet.id" @click="viewPetDetail(pet.id)">
        <div class="pet-avatar">
          <span class="pet-icon">{{ pet.type === '狗' ? '🐕' : '🐈' }}</span>
        </div>
        <div class="pet-info">
          <h3>{{ pet.name }}</h3>
          <p>{{ pet.breed }} · {{ pet.age }}岁</p>
        </div>
        <div class="health-bar-container">
          <p>健康指数</p>
          <div class="health-bar">
            <div class="health-progress" :style="{ width: `${petHealthIndex[pet.name] || 80}%` }"></div>
          </div>
          <span class="health-percentage">{{ petHealthIndex[pet.name] || 80 }}%</span>
        </div>
      </div>

      <!-- 添加新宠物卡片 -->
      <div class="add-pet-card" @click="addNewPet">
        <div class="add-icon">
          <span>+</span>
        </div>
        <p>添加新宠物</p>
      </div>
    </div>

    <!-- 待办事项和快捷操作区域 -->
    <div class="dashboard-bottom">
      <!-- 待办事项 -->
      <div class="tasks-container">
        <div class="tasks-header">
          <div class="tasks-icon">🔔</div>
          <h2>今日待办</h2>
        </div>
        <p class="tasks-subheader">今天有 {{ tasks.filter(task => task.status === '待完成').length }} 项任务待完成</p>

        <div class="tasks-list">
          <div class="task-item" v-for="task in tasks" :key="task.id">
            <div class="task-time">{{ task.time }}</div>
            <div class="task-content">
              <h4>{{ task.title }}</h4>
              <p>{{ task.petName }}</p>
            </div>
            <div class="task-status" :class="task.status === '已完成' ? 'completed' : 'pending'">
              {{ task.status }}
            </div>
          </div>
        </div>
      </div>

      <!-- 快捷操作 -->
      <div class="quick-actions">
        <h2>快捷操作</h2>
        <p>常用功能快速入口</p>

        <div class="action-buttons">
          <div class="action-button" @click="recordFeeding">
            <div class="action-icon">🍽️</div>
            <p>记录喂食</p>
          </div>
          <div class="action-button" @click="walkDogCheckin">
            <div class="action-icon">📍</div>
            <p>遛狗打卡</p>
          </div>
          <div class="action-button" @click="takePhotoRecord">
            <div class="action-icon">📸</div>
            <p>拍照记录</p>
          </div>
          <div class="action-button" @click="addReminder">
            <div class="action-icon">🔔</div>
            <p>添加提醒</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.dashboard {
  padding: 16px;
  background-color: #ffffff;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  height: 100%;
  width: 100%;
  box-sizing: border-box;
}

/* 顶部欢迎区域 */
.welcome-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  width: 100%;
}

.welcome-text h1 {
  font-size: 24px;
  margin: 0;
  color: #333;
}

.welcome-text p {
  margin: 4px 0 0;
  color: #666;
  font-size: 14px;
}

.weather-icon {
  font-size: 32px;
  background-color: #ffedcc;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 宠物卡片区域 */
.pet-cards-container {
  display: flex;
  gap: 16px;
  padding: 4px;
  margin-bottom: 20px;
  width: 100%;
  overflow-x: auto;
}

.pet-card, .add-pet-card {
  background-color: #fff;
  border-radius: 16px;
  padding: 16px;
  min-width: 280px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  cursor: pointer;
  flex: 1;
}

.pet-avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background-color: #f0f4ff;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 8px;
}

.pet-icon {
  font-size: 28px;
}

.pet-info h3 {
  margin: 0;
  font-size: 18px;
}

.pet-info p {
  margin: 4px 0 12px;
  color: #666;
  font-size: 14px;
}

.health-bar-container {
  margin-top: 12px;
}

.health-bar-container p {
  margin: 0 0 6px;
  font-size: 14px;
}

.health-bar {
  height: 8px;
  background-color: #e9ecef;
  border-radius: 4px;
  margin-bottom: 4px;
  position: relative;
}

.health-progress {
  background-color: #4caf50;
  height: 100%;
  border-radius: 4px;
}

.health-percentage {
  font-size: 14px;
  color: #4caf50;
  font-weight: bold;
}

.add-pet-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  border: 2px dashed #ddd;
  height: 155px;
}

.add-icon {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background-color: #f0f4ff;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 8px;
  font-size: 24px;
  color: #999;
}

.add-pet-card p {
  color: #999;
}

/* 底部布局 */
.dashboard-bottom {
  display: flex;
  gap: 20px;
  width: 100%;
  flex: 1;
}

@media (max-width: 768px) {
  .dashboard-bottom {
    flex-direction: column;
  }
}

/* 待办事项区域 */
.tasks-container {
  flex: 1;
  background-color: #fff;
  border-radius: 16px;
  padding: 20px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  display: flex;
  flex-direction: column;
}

.tasks-header {
  display: flex;
  align-items: center;
  margin-bottom: 4px;
}

.tasks-icon {
  font-size: 20px;
  margin-right: 10px;
}

.tasks-header h2 {
  margin: 0;
  font-size: 18px;
}

.tasks-subheader {
  color: #666;
  font-size: 14px;
  margin: 0 0 20px;
}

.tasks-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
  flex: 1;
  overflow-y: auto;
}

.task-item {
  display: flex;
  align-items: center;
  padding: 16px 0;
  border-bottom: 1px solid #eee;
}

.task-time {
  width: 60px;
  font-size: 16px;
  color: #666;
}

.task-content {
  flex: 1;
}

.task-content h4 {
  margin: 0;
  font-size: 16px;
}

.task-content p {
  margin: 4px 0 0;
  color: #999;
  font-size: 14px;
}

.task-status {
  font-size: 14px;
  padding: 4px 10px;
  border-radius: 12px;
}

.task-status.completed {
  background-color: #e8f5e9;
  color: #4caf50;
}

.task-status.pending {
  background-color: #222;
  color: #fff;
}

/* 快捷操作区域 */
.quick-actions {
  width: 280px;
  background-color: #fff;
  border-radius: 16px;
  padding: 20px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  display: flex;
  flex-direction: column;
}

.quick-actions h2 {
  margin: 0;
  font-size: 18px;
}

.quick-actions p {
  color: #666;
  font-size: 14px;
  margin: 4px 0 20px;
}

.action-buttons {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px;
  flex: 1;
}

.action-button {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 16px;
  border-radius: 12px;
  background-color: #f9f9f9;
  cursor: pointer;
  transition: background-color 0.2s;
}

.action-button:hover {
  background-color: #f0f0f0;
}

.action-icon {
  font-size: 24px;
  margin-bottom: 8px;
}

.action-button p {
  margin: 0;
  font-size: 14px;
  color: #333;
}

@media (max-width: 1024px) {
  .dashboard-bottom {
    flex-direction: column;
  }
  
  .quick-actions {
    width: 100%;
    margin-top: 20px;
  }
}
</style> 