<script setup lang="ts">
// 个人中心页面组件
import { ref } from 'vue';

// 当前激活的标签页
const activeTab = ref('profile'); // profile, notification, security, dataManagement

const switchTab = (tab: string) => {
  activeTab.value = tab;
};
</script>

<template>
  <div class="personal-center">
    <div class="header">
      <h1 class="title">个人中心</h1>
      <div class="subtitle">管理您的账户设置和偏好</div>
    </div>
    
    <div class="tab-container">
      <div class="tabs">
        <div class="tab" :class="{ active: activeTab === 'profile' }" @click="switchTab('profile')">个人资料</div>
        <div class="tab" :class="{ active: activeTab === 'notification' }" @click="switchTab('notification')">通知设置</div>
        <div class="tab" :class="{ active: activeTab === 'security' }" @click="switchTab('security')">账户安全</div>
        <div class="tab" :class="{ active: activeTab === 'dataManagement' }" @click="switchTab('dataManagement')">数据管理</div>
      </div>
    </div>

    <div class="content-container">
      <!-- 个人资料标签页内容 -->
      <div v-if="activeTab === 'profile'" class="profile-content">
        <div class="profile-header">
          <div class="profile-icon">
            <i class="user-icon">👤</i>
          </div>
          <div class="profile-title">
            <h2>个人资料</h2>
            <div class="description">管理您的基本信息</div>
          </div>
        </div>

        <div class="main-content">
          <div class="avatar-section">
            <div class="avatar">
              <div class="avatar-image"></div>
            </div>
            <div class="avatar-actions">
              <button class="avatar-upload">
                <i class="camera-icon">📷</i>
                更换头像
              </button>
              <div class="avatar-tip">支持 JPG、PNG 格式，建议尺寸 200x200</div>
            </div>
          </div>

          <div class="divider"></div>

          <div class="form-section">
            <div class="form-row">
              <div class="form-group">
                <label>昵称</label>
                <input type="text" value="宠物爱好者" />
              </div>
              
              <div class="form-group">
                <label>邮箱</label>
                <input type="email" value="user@example.com" />
              </div>
            </div>

            <div class="form-row">
              <div class="form-group">
                <label>手机号</label>
                <input type="text" value="138****1234" />
              </div>

              <div class="form-group">
                <label>所在地区</label>
                <input type="text" value="北京市朝阳区" />
              </div>
            </div>

            <div class="buttons">
              <button class="save-btn">保存更改</button>
              <button class="cancel-btn">取消</button>
            </div>
          </div>

          <div class="divider"></div>

          <div class="stats-section">
            <div class="stat-card">
              <div class="stat-left">
                <div class="stat-icon pet-icon">🐾</div>
              </div>
              <div class="stat-right">
                <div class="stat-value blue">2</div>
                <div class="stat-label">我的宠物</div>
              </div>
            </div>
            
            <div class="stat-card">
              <div class="stat-left">
                <div class="stat-icon record-icon">📝</div>
              </div>
              <div class="stat-right">
                <div class="stat-value green">156</div>
                <div class="stat-label">记录总数</div>
              </div>
            </div>
            
            <div class="stat-card">
              <div class="stat-left">
                <div class="stat-icon reminder-icon">📅</div>
              </div>
              <div class="stat-right">
                <div class="stat-value purple">28</div>
                <div class="stat-label">本月提醒</div>
              </div>
            </div>
            
            <div class="stat-card">
              <div class="stat-left">
                <div class="stat-icon usage-icon">⏰</div>
              </div>
              <div class="stat-right">
                <div class="stat-value orange">365</div>
                <div class="stat-label">使用天数</div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 通知设置标签页内容 -->
      <div v-if="activeTab === 'notification'" class="profile-content">
        <div class="profile-header">
          <div class="profile-icon">
            <i class="notification-icon">🔔</i>
          </div>
          <div class="profile-title">
            <h2>通知设置</h2>
            <div class="description">管理您接收通知的方式和时间</div>
          </div>
        </div>

        <div class="main-content">
          <div class="notification-section">
            <div class="notification-item">
              <div class="notification-info">
                <div class="notification-title">推送通知</div>
                <div class="notification-desc">接收应用内推送通知</div>
              </div>
              <div class="toggle-switch">
                <input type="checkbox" id="push" class="toggle-input" checked />
                <label for="push" class="toggle-label"></label>
              </div>
            </div>

            <div class="divider"></div>

            <div class="notification-item">
              <div class="notification-info">
                <div class="notification-title">邮件通知</div>
                <div class="notification-desc">接收重要事件的邮件提醒</div>
              </div>
              <div class="toggle-switch">
                <input type="checkbox" id="email" class="toggle-input" checked />
                <label for="email" class="toggle-label"></label>
              </div>
            </div>

            <div class="divider"></div>

            <div class="notification-item">
              <div class="notification-info">
                <div class="notification-title">短信通知</div>
                <div class="notification-desc">接收紧急事件的短信提醒</div>
              </div>
              <div class="toggle-switch">
                <input type="checkbox" id="sms" class="toggle-input" />
                <label for="sms" class="toggle-label"></label>
              </div>
            </div>

            <div class="divider"></div>
            
            <div class="notification-category">
              <h3>通知类型</h3>

              <div class="notification-item">
                <div class="notification-info">
                  <div class="notification-title">健康提醒</div>
                  <div class="notification-desc">疫苗、体检等健康相关提醒</div>
                </div>
                <div class="toggle-switch">
                  <input type="checkbox" id="health" class="toggle-input" checked />
                  <label for="health" class="toggle-label"></label>
                </div>
              </div>

              <div class="divider"></div>

              <div class="notification-item">
                <div class="notification-info">
                  <div class="notification-title">日常提醒</div>
                  <div class="notification-desc">喂食、遛狗等日常活动提醒</div>
                </div>
                <div class="toggle-switch">
                  <input type="checkbox" id="daily" class="toggle-input" checked />
                  <label for="daily" class="toggle-label"></label>
                </div>
              </div>

              <div class="divider"></div>

              <div class="notification-item">
                <div class="notification-info">
                  <div class="notification-title">社区动态</div>
                  <div class="notification-desc">领养信息、知识分享等</div>
                </div>
                <div class="toggle-switch">
                  <input type="checkbox" id="community" class="toggle-input" />
                  <label for="community" class="toggle-label"></label>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 账户安全标签页内容 -->
      <div v-if="activeTab === 'security'" class="profile-content">
        <div class="profile-header">
          <div class="profile-icon">
            <i class="security-icon">🛡️</i>
          </div>
          <div class="profile-title">
            <h2>账户安全</h2>
            <div class="description">保护您的账户安全</div>
          </div>
        </div>

        <div class="main-content">
          <div class="security-section">
            <div class="security-item">
              <div class="security-info">
                <div class="security-title">修改密码</div>
                <div class="security-desc">定期更换密码以保护账户安全</div>
              </div>
              <button class="action-btn">修改密码</button>
            </div>

            <div class="divider"></div>

            <div class="security-item">
              <div class="security-info">
                <div class="security-title">绑定手机</div>
                <div class="security-desc">138****1234</div>
              </div>
              <button class="action-btn">更换手机</button>
            </div>

            <div class="divider"></div>

            <div class="security-item">
              <div class="security-info">
                <div class="security-title">绑定邮箱</div>
                <div class="security-desc">user@example.com</div>
              </div>
              <button class="action-btn">更换邮箱</button>
            </div>

            <div class="divider"></div>

            <div class="security-record">
              <h3>登录记录</h3>

              <div class="record-item">
                <div class="record-info">
                  <div class="device-info">iPhone • 北京市</div>
                </div>
                <div class="record-time">2024-01-10 14:30</div>
              </div>

              <div class="divider"></div>

              <div class="record-item">
                <div class="record-info">
                  <div class="device-info">Chrome • 上海市</div>
                </div>
                <div class="record-time">2024-01-09 09:15</div>
              </div>

              <div class="divider"></div>

              <div class="record-item">
                <div class="record-info">
                  <div class="device-info">Android • 广州市</div>
                </div>
                <div class="record-time">2024-01-08 20:45</div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 数据管理标签页内容 -->
      <div v-if="activeTab === 'dataManagement'" class="profile-content">
        <div class="profile-header">
          <div class="profile-icon">
            <i class="data-icon">📊</i>
          </div>
          <div class="profile-title">
            <h2>数据管理</h2>
            <div class="description">管理您的数据和隐私</div>
          </div>
        </div>

        <div class="main-content">
          <div class="data-section">
            <div class="data-item">
              <div class="data-info">
                <div class="data-title">导出数据</div>
                <div class="data-desc">导出您的所有宠物数据和记录</div>
              </div>
              <button class="action-btn export-btn">
                <i class="export-icon">⬇️</i>
                导出数据
              </button>
            </div>

            <div class="divider"></div>

            <div class="data-item">
              <div class="data-info">
                <div class="data-title">数据备份</div>
                <div class="data-desc">最后备份时间: 2024-01-10</div>
              </div>
              <button class="action-btn backup-btn">立即备份</button>
            </div>

            <div class="divider"></div>

            <div class="data-danger">
              <h3>危险操作</h3>

              <div class="data-item danger">
                <div class="data-info">
                  <div class="data-title danger-text">清空所有数据</div>
                  <div class="data-desc">删除所有宠物信息和记录，此操作不可恢复</div>
                </div>
                <button class="action-btn danger-btn">清空数据</button>
              </div>

              <div class="divider"></div>

              <div class="data-item danger">
                <div class="data-info">
                  <div class="data-title danger-text">注销账户</div>
                  <div class="data-desc">永久删除账户和所有相关数据</div>
                </div>
                <button class="action-btn danger-btn">注销账户</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.personal-center {
  width: 100%;
  min-height: 100vh;
  background-color: #f7f7f7;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
}

.header {
  background-color: #fff;
  padding: 20px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
}

.title {
  font-size: 24px;
  font-weight: bold;
  margin: 0;
  padding: 0;
}

.subtitle {
  font-size: 14px;
  color: #666;
  margin-top: 5px;
}

.tab-container {
  background-color: #fff;
  border-bottom: 1px solid #eee;
}

.tabs {
  display: flex;
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
}

.tab {
  padding: 15px 20px;
  cursor: pointer;
  font-size: 14px;
  text-align: center;
  flex: 1;
  max-width: 150px;
}

.tab.active {
  color: #000;
  font-weight: 500;
  border-bottom: 2px solid #000;
}

.content-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.profile-content {
  background-color: #fff;
  border-radius: 4px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
}

.profile-header {
  display: flex;
  align-items: center;
  padding: 20px;
}

.profile-icon {
  font-size: 20px;
  margin-right: 10px;
  color: #333;
}

.profile-title h2 {
  font-size: 16px;
  margin: 0;
  padding: 0;
}

.description {
  color: #666;
  font-size: 12px;
  margin-top: 5px;
}

.main-content {
  padding: 0 20px 20px;
}

.avatar-section {
  display: flex;
  padding: 20px 0;
  border-bottom: 1px solid #eee;
  align-items: center;
}

.avatar {
  margin-right: 30px;
}

.avatar-image {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  background-color: #eee;
}

.avatar-actions {
  display: flex;
  flex-direction: column;
}

.avatar-upload {
  display: flex;
  align-items: center;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 4px;
  padding: 6px 12px;
  cursor: pointer;
  margin-bottom: 10px;
  font-size: 14px;
}

.camera-icon {
  margin-right: 5px;
  font-size: 14px;
}

.avatar-tip {
  font-size: 12px;
  color: #999;
}

.divider {
  height: 1px;
  background-color: #eee;
  margin: 0;
}

.form-section {
  padding: 20px 0;
}

.form-row {
  display: flex;
  gap: 20px;
  margin-bottom: 15px;
}

.form-group {
  flex: 1;
}

.form-group label {
  display: block;
  margin-bottom: 10px;
  font-size: 14px;
}

.form-group input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
  box-sizing: border-box;
}

.buttons {
  margin-top: 20px;
}

.save-btn {
  background-color: #ff7700;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  margin-right: 10px;
}

.cancel-btn {
  background-color: #fff;
  border: 1px solid #ddd;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
}

.stats-section {
  display: flex;
  gap: 15px;
  padding: 20px 0;
}

.stat-card {
  flex: 1;
  padding: 15px;
  border: 1px solid #eee;
  border-radius: 8px;
  display: flex;
  align-items: center;
}

.stat-left {
  margin-right: 10px;
}

.stat-icon {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
}

.pet-icon {
  background-color: rgba(0, 0, 255, 0.1);
  color: blue;
}

.record-icon {
  background-color: rgba(0, 128, 0, 0.1);
  color: green;
}

.reminder-icon {
  background-color: rgba(128, 0, 128, 0.1);
  color: purple;
}

.usage-icon {
  background-color: rgba(255, 165, 0, 0.1);
  color: orange;
}

.stat-right {
  display: flex;
  flex-direction: column;
}

.stat-value {
  font-size: 20px;
  font-weight: bold;
  line-height: 1;
  margin-bottom: 5px;
}

.stat-label {
  font-size: 12px;
  color: #666;
}

.blue {
  color: blue;
}

.green {
  color: green;
}

.purple {
  color: purple;
}

.orange {
  color: orange;
}

/* 通知设置样式 */
.notification-section {
  padding: 20px 0;
}

.notification-category {
  margin-top: 30px;
}

.notification-category h3 {
  font-size: 16px;
  margin-bottom: 15px;
  font-weight: 500;
}

.notification-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 0;
}

.notification-title {
  font-size: 16px;
  margin-bottom: 5px;
}

.notification-desc {
  color: #666;
  font-size: 14px;
}

/* 开关按钮样式 */
.toggle-switch {
  position: relative;
}

.toggle-input {
  position: absolute;
  opacity: 0;
  width: 0;
  height: 0;
}

.toggle-label {
  position: relative;
  display: inline-block;
  width: 50px;
  height: 26px;
  background-color: #ccc;
  border-radius: 26px;
  transition: background-color 0.2s;
  cursor: pointer;
}

.toggle-label:after {
  content: '';
  position: absolute;
  top: 3px;
  left: 3px;
  width: 20px;
  height: 20px;
  background-color: white;
  border-radius: 50%;
  transition: transform 0.2s;
}

.toggle-input:checked + .toggle-label {
  background-color: #4cd964;
}

.toggle-input:checked + .toggle-label:after {
  transform: translateX(24px);
}

/* 账户安全样式 */
.security-section {
  padding: 20px 0;
}

.security-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 0;
}

.security-title {
  font-size: 16px;
  margin-bottom: 5px;
}

.security-desc {
  color: #666;
  font-size: 14px;
}

.action-btn {
  padding: 8px 15px;
  border-radius: 4px;
  border: 1px solid #ddd;
  background-color: #fff;
  font-size: 14px;
  cursor: pointer;
}

.security-record {
  margin-top: 30px;
}

.security-record h3 {
  font-size: 16px;
  margin-bottom: 15px;
  font-weight: 500;
}

.record-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 0;
}

.device-info {
  font-size: 14px;
}

.record-time {
  color: #666;
  font-size: 14px;
}

/* 数据管理样式 */
.data-section {
  padding: 20px 0;
}

.data-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 0;
}

.data-title {
  font-size: 16px;
  margin-bottom: 5px;
}

.data-desc {
  color: #666;
  font-size: 14px;
}

.export-btn {
  display: flex;
  align-items: center;
}

.export-icon {
  margin-right: 5px;
}

.data-danger {
  margin-top: 30px;
}

.data-danger h3 {
  font-size: 16px;
  margin-bottom: 15px;
  font-weight: 500;
  color: #ff3b30;
}

.danger-text {
  color: #ff3b30;
}

.danger-btn {
  color: #ff3b30;
  border-color: #ff3b30;
}

@media (max-width: 768px) {
  .form-row {
    flex-direction: column;
    gap: 15px;
  }
  
  .stats-section {
    flex-wrap: wrap;
  }
  
  .stat-card {
    min-width: calc(50% - 10px);
  }
}
</style> 