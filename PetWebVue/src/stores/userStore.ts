import { defineStore } from 'pinia'
import { userApi, UserInfo } from '../api/userApi'

// 用户状态类型
interface UserState {
  token: string | null
  userInfo: UserInfo | null
  lastTokenRefresh: number | null
}

// 尝试从localStorage获取用户信息
const getSavedUserInfo = (): UserInfo | null => {
  const savedUserInfo = localStorage.getItem('userInfo')
  if (savedUserInfo) {
    try {
      return JSON.parse(savedUserInfo)
    } catch (e) {
      return null
    }
  }
  return null
}

// 定义用户Store
export const useUserStore = defineStore('user', {
  // 状态
  state: (): UserState => ({
    token: localStorage.getItem('token'),
    userInfo: getSavedUserInfo(),
    lastTokenRefresh: null
  }),
  
  // Getters
  getters: {
    // 是否已登录
    isLoggedIn: (state) => !!state.token
  },
  
  // Actions
  actions: {
    // 保存用户信息到localStorage
    saveUserInfo(userInfo: UserInfo | null) {
      if (userInfo) {
        localStorage.setItem('userInfo', JSON.stringify(userInfo))
      } else {
        localStorage.removeItem('userInfo')
      }
    },
    
    // 登录操作
    async loginAction(username: string, password: string) {
      try {
        const response = await userApi.login({ username, password })
        this.token = response.token
        this.userInfo = response.user
        this.lastTokenRefresh = Date.now()
        
        // 保存token和用户信息到localStorage
        localStorage.setItem('token', response.token)
        this.saveUserInfo(response.user)
        
        return Promise.resolve(response)
      } catch (error) {
        // 登录失败时清理token
        this.token = null
        this.userInfo = null
        this.lastTokenRefresh = null
        localStorage.removeItem('token')
        this.saveUserInfo(null)
        return Promise.reject(error)
      }
    },
    
    // 注册操作
    async registerAction(username: string, password: string, email: string) {
      try {
        const response = await userApi.register({ username, password, email })
        return Promise.resolve(response)
      } catch (error) {
        return Promise.reject(error)
      }
    },
    
    // 获取用户信息
    async getUserInfoAction() {
      if (!this.token) {
        return Promise.reject(new Error('未登录状态，无法获取用户信息'))
      }
      
      try {
        const userInfo = await userApi.getUserInfo()
        this.userInfo = userInfo
        this.lastTokenRefresh = Date.now()
        // 保存用户信息到localStorage以便在页面刷新时使用
        this.saveUserInfo(userInfo)
        return Promise.resolve(userInfo)
      } catch (error) {
        // 如果获取失败，可能是token已失效
        console.error('获取用户信息失败，可能是token已失效', error)
        return Promise.reject(error)
      }
    },
    
    // 登出操作
    logoutAction() {
      this.token = null
      this.userInfo = null
      this.lastTokenRefresh = null
      localStorage.removeItem('token')
      this.saveUserInfo(null)
    }
  }
}) 