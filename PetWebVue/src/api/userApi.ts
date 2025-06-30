import api from './request'

// 用户信息类型
export interface UserInfo {
  id: number
  username: string
  email?: string
}

// 登录响应类型
export interface LoginResponse {
  token: string
  user: UserInfo
}

// 登录请求类型
export interface LoginData {
  username: string
  password: string
}

// 注册请求类型
export interface RegisterData {
  username: string
  password: string
  email: string
}

// 用户API
export const userApi = {
  // 用户登录
  login(data: LoginData): Promise<LoginResponse> {
    return api.post<LoginResponse>('/users/login', data)
  },
  
  // 用户注册
  register(data: RegisterData): Promise<any> {
    return api.post('/users/register', data)
  },
  
  // 获取当前用户信息
  getUserInfo(): Promise<UserInfo> {
    return api.get<UserInfo>('/users/current')
  }
} 