<template>
  <div class="pets-container">
    <!-- 顶部标题区域 -->
    <div class="pets-header">
      <div class="title-section">
        <h1>我的宠物</h1>
        <p>管理您的爱宠档案信息</p>
      </div>
      <div class="add-pet-btn">
        <button>
          <span class="icon">+</span>
          添加宠物
        </button>
      </div>
    </div>

    <!-- 宠物列表部分 -->
    <h2 class="list-title">宠物列表</h2>
    
    <div class="pets-list">
      <!-- 宠物项 -->
      <div class="pet-item">
        <div class="pet-avatar">
          <img src="" alt="宠物头像" />
        </div>
        <div class="pet-info">
          <h3>毛毛</h3>
          <p>金毛犬</p>
        </div>
      </div>

      <!-- 选中的宠物项 -->
      <div class="pet-item active">
        <div class="pet-avatar">
          <img src="" alt="宠物头像" />
        </div>
        <div class="pet-info">
          <h3>咪咪</h3>
          <p>英国短毛猫</p>
        </div>
      </div>
    </div>

    <!-- 宠物详情部分 -->
    <div class="pet-detail">
      <div class="pet-detail-header">
        <div class="pet-profile">
          <div class="pet-avatar-large">
            <img src="" alt="宠物头像" />
          </div>
          <div class="pet-header-info">
            <h2>咪咪</h2>
            <p>英国短毛猫</p>
            <div class="pet-tags">
              <span class="tag">母</span>
              <span class="tag">1岁8个月</span>
              <span class="tag">已绝育</span>
            </div>
          </div>
        </div>
        <div class="edit-btn">
          <button>编辑</button>
        </div>
      </div>

      <!-- 宠物详情导航 -->
      <div class="pet-tabs">
        <div class="tab active">概览</div>
        <div class="tab">健康档案</div>
        <div class="tab">成长相册</div>
        <div class="tab">成长曲线</div>
      </div>

      <!-- 宠物详情内容区域 -->
      <div class="pet-content">
        <!-- 基本信息 -->
        <div class="info-card">
          <h3>基本信息</h3>
          <div class="info-row">
            <span class="info-label">体重</span>
            <span class="info-value">4.2kg</span>
          </div>
          <div class="info-row">
            <span class="info-label">收养日期</span>
            <span class="info-value">2022-08-10</span>
          </div>
          <div class="info-row">
            <span class="info-label">健康指数</span>
            <span class="info-value">92%</span>
          </div>
          <div class="health-bar">
            <div class="health-progress" style="width: 92%"></div>
          </div>
        </div>

        <!-- 最近记录 -->
        <div class="info-card">
          <h3>最近记录</h3>
          <div class="record-row">
            <span class="record-label">上次喂食</span>
            <span class="record-time">2小时前</span>
          </div>
          <div class="record-row">
            <span class="record-label">上次遛狗</span>
            <span class="record-time">今天 08:30</span>
          </div>
          <div class="record-row">
            <span class="record-label">上次洗澡</span>
            <span class="record-time">3天前</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
// 这里可以添加组件逻辑
</script>

<style scoped>
/* 整体容器 */
.pets-container {
  padding: 20px;
  color: #333;
  font-family: Arial, sans-serif;
}

/* 顶部标题区域 */
.pets-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.title-section h1 {
  font-size: 24px;
  margin: 0;
}

.title-section p {
  margin: 5px 0 0;
  color: #666;
  font-size: 14px;
}

.add-pet-btn button {
  background-color: #ff7a22;
  color: white;
  border: none;
  border-radius: 4px;
  padding: 8px 16px;
  font-size: 14px;
  display: flex;
  align-items: center;
  cursor: pointer;
}

.add-pet-btn .icon {
  margin-right: 5px;
  font-size: 18px;
}

/* 宠物列表 */
.list-title {
  font-size: 18px;
  margin: 20px 0 10px;
}

.pets-list {
  display: flex;
  flex-direction: column;
  width: 300px;
  float: left;
  margin-right: 20px;
}

.pet-item {
  display: flex;
  align-items: center;
  padding: 12px;
  border-radius: 8px;
  margin-bottom: 10px;
  cursor: pointer;
}

.pet-item.active {
  background-color: #fff1e6;
}

.pet-avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background-color: #eee;
  margin-right: 12px;
  overflow: hidden;
}

.pet-info h3 {
  margin: 0;
  font-size: 16px;
}

.pet-info p {
  margin: 5px 0 0;
  color: #666;
  font-size: 14px;
}

/* 宠物详情 */
.pet-detail {
  margin-left: 320px;
  background-color: #fff;
  border-radius: 12px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  padding: 20px;
}

.pet-detail-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.pet-profile {
  display: flex;
  align-items: center;
}

.pet-avatar-large {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  background-color: #eee;
  margin-right: 20px;
}

.pet-header-info h2 {
  margin: 0;
  font-size: 20px;
}

.pet-header-info p {
  margin: 5px 0 10px;
  color: #666;
  font-size: 14px;
}

.pet-tags {
  display: flex;
  gap: 8px;
}

.tag {
  background-color: #f1f1f1;
  padding: 3px 10px;
  border-radius: 20px;
  font-size: 12px;
}

.edit-btn button {
  background: none;
  border: 1px solid #ccc;
  border-radius: 4px;
  padding: 6px 12px;
  cursor: pointer;
}

/* 宠物详情导航 */
.pet-tabs {
  display: flex;
  border-bottom: 1px solid #eee;
  margin: 20px 0;
}

.tab {
  padding: 10px 20px;
  cursor: pointer;
}

.tab.active {
  border-bottom: 2px solid #ff7a22;
  color: #ff7a22;
}

/* 宠物详情内容区域 */
.pet-content {
  display: flex;
  gap: 20px;
}

.info-card {
  flex: 1;
  background-color: #f9f9f9;
  border-radius: 8px;
  padding: 15px;
}

.info-card h3 {
  margin-top: 0;
  margin-bottom: 15px;
  font-size: 16px;
}

.info-row, .record-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.health-bar {
  height: 10px;
  background-color: #eee;
  border-radius: 5px;
  margin-top: 10px;
}

.health-progress {
  height: 100%;
  border-radius: 5px;
  background-color: #4caf50;
}

.record-time {
  color: #666;
}
</style> 