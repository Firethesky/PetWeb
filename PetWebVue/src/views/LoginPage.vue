<script setup lang="ts">
import { ElButton, ElCheckbox, ElForm, ElFormItem, ElInput, ElMessage } from 'element-plus'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../stores/userStore'

// 登录页面组件
const router = useRouter()
const userStore = useUserStore()

// 表单数据
const loginForm = ref({
  username: '',
  password: '',
  rememberMe: false
})

// 表单验证状态
const usernameError = ref('')
const passwordError = ref('')
const isLoading = ref(false)
// 登录错误信息
const loginError = ref('')

// 表单规则
const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ]
}

// 表单引用
const loginFormRef = ref()

// 验证表单
const validateForm = () => {
  let isValid = true
  
  // 重置错误信息
  usernameError.value = ''
  passwordError.value = ''
  loginError.value = ''
  
  // 验证用户名
  if (!loginForm.value.username.trim()) {
    usernameError.value = '请输入用户名'
    isValid = false
  }
  
  // 验证密码
  if (!loginForm.value.password) {
    passwordError.value = '请输入密码'
    isValid = false
  } else if (loginForm.value.password.length < 6) {
    passwordError.value = '密码长度不能少于6位'
    isValid = false
  }
  
  return isValid
}

// 登录方法
const handleLogin = async () => {
  loginError.value = ''
  
  if (loginFormRef.value) {
    await loginFormRef.value.validate(async (valid: boolean) => {
      if (valid) {
        try {
          isLoading.value = true
          await userStore.loginAction(loginForm.value.username, loginForm.value.password)
          
          // 记住用户名
          if (loginForm.value.rememberMe) {
            localStorage.setItem('rememberedUsername', loginForm.value.username)
          } else {
            localStorage.removeItem('rememberedUsername')
          }
          
          // 确保登录状态已保存
          console.log('登录状态:', userStore.isLoggedIn, '保存的token:', localStorage.getItem('token'))
          
          // 确保token存在再跳转
          if (userStore.isLoggedIn) {
            // 登录成功，跳转到首页
            ElMessage.success('登录成功')
            
            // 短暂延迟，确保状态已更新
            setTimeout(() => {
              router.push('/dashboard')
            }, 100)
          } else {
            throw new Error('登录状态保存失败')
          }
        } catch (error: any) {
          // 错误处理
          console.error('登录错误:', error)
          
          // 设置具体的错误信息
          if (error.response) {
            // 服务器返回了错误状态
            if (error.response.status === 401) {
              loginError.value = '用户名或密码错误'
              ElMessage.error('用户名或密码错误')
            } else if (error.response.status === 429) {
              loginError.value = '登录尝试次数过多，请稍后再试'
              ElMessage.error('登录尝试次数过多，请稍后再试')
            } else {
              loginError.value = `登录失败: ${error.response.data?.message || '服务器错误'}`
              ElMessage.error(loginError.value)
            }
          } else if (error.request) {
            // 请求发出但没有收到响应
            loginError.value = '网络错误，无法连接到服务器'
            ElMessage.error(loginError.value)
          } else {
            // 请求配置出错
            loginError.value = error.message || '登录失败，请重试'
            ElMessage.error(loginError.value)
          }
          
          // 如果是用户名或密码错误，清空密码
          if (loginError.value === '用户名或密码错误') {
            loginForm.value.password = ''
            userStore.token = ""
          }
        } finally {
          isLoading.value = false
        }
      }
    })
  }
}

// 初始化 - 检查是否有记住的用户名
const initForm = () => {
  const rememberedUsername = localStorage.getItem('rememberedUsername')
  if (rememberedUsername) {
    loginForm.value.username = rememberedUsername
    loginForm.value.rememberMe = true
  }
  // 如果token存在，主动清理
  if (localStorage.getItem('token')) {
    localStorage.removeItem('token')
  }
}

// 页面加载时执行
initForm()
</script>

<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-header">
        <h1>宠物管理系统</h1>
        <p>欢迎回来，请登录您的账号</p>
      </div>
      
      <div class="login-form">
        <el-form
          ref="loginFormRef"
          :model="loginForm"
          :rules="rules"
          label-position="top"
        >
          <!-- 登录错误提示 -->
          <div v-if="loginError" class="login-error-alert">
            <i class="el-icon-warning"></i>
            {{ loginError }}
          </div>
          
          <!-- 用户名输入框 -->
          <el-form-item label="用户名" prop="username">
            <el-input 
              v-model="loginForm.username"
              placeholder="请输入用户名"
              prefix-icon="el-icon-user"
            />
          </el-form-item>
          
          <!-- 密码输入框 -->
          <el-form-item label="密码" prop="password">
            <el-input 
              v-model="loginForm.password"
              type="password"
              placeholder="请输入密码"
              prefix-icon="el-icon-lock"
              @keyup.enter="handleLogin"
            />
          </el-form-item>
          
          <!-- 记住我和忘记密码 -->
          <div class="remember-forgot">
            <el-checkbox v-model="loginForm.rememberMe">记住我</el-checkbox>
            <a class="forgot-password" href="#">忘记密码?</a>
          </div>
          
          <!-- 登录按钮 -->
          <el-button 
            type="primary" 
            class="login-button" 
            @click="handleLogin"
            :loading="isLoading"
            round
          >
            登录
          </el-button>
          
          <!-- 没有账户？注册 -->
          <div class="register-link">
            没有账户？ <router-link to="/register">立即注册</router-link>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #ffffff;
}

.login-box {
  width: 420px;
  padding: 40px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.login-header {
  text-align: center;
  margin-bottom: 30px;
}

.login-header h1 {
  margin: 0;
  color: #409eff;
  font-size: 28px;
  margin-bottom: 10px;
}

.login-header p {
  color: #909399;
  margin: 0;
}

.login-form {
  margin-top: 30px;
}

.login-error-alert {
  padding: 10px 15px;
  background-color: #fef0f0;
  color: #f56c6c;
  border-radius: 4px;
  margin-bottom: 20px;
  font-size: 14px;
  display: flex;
  align-items: center;
}

.login-error-alert i {
  margin-right: 8px;
  font-size: 16px;
}

.remember-forgot {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.forgot-password {
  font-size: 14px;
  color: #409eff;
  text-decoration: none;
}

.login-button {
  width: 100%;
  margin-top: 10px;
}

.register-link {
  margin-top: 20px;
  text-align: center;
  font-size: 14px;
  color: #606266;
}

.register-link a {
  color: #409eff;
  text-decoration: none;
}
</style> 