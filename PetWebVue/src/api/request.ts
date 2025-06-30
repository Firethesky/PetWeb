import axios, { AxiosRequestConfig } from 'axios'

// 创建axios实例
const request = axios.create({
  baseURL: '/api',
  timeout: 10000
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    // 从localStorage获取token
    const token = localStorage.getItem('token')
    
    // 如果有token，添加到请求头
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  response => {
    // 直接返回响应数据，而不是整个响应对象
    return response.data
  },
  error => {
    // 统一处理错误
    if (error.response) {
      switch (error.response.status) {
        case 401:
          // 未授权，清除token并跳转到登录页
          localStorage.removeItem('token')
          window.location.href = '/login'
          break
        case 403:
          // 权限不足
          console.error('没有权限进行此操作')
          break
        case 404:
          // 资源不存在
          console.error('请求的资源不存在')
          break
        case 500:
          // 服务器错误
          console.error('服务器错误')
          break
        default:
          console.error(`请求错误: ${error.response.status}`)
      }
    } else if (error.request) {
      // 请求发出但没有收到响应
      console.error('网络错误，无法连接到服务器')
    } else {
      // 请求配置有误
      console.error('请求配置错误:', error.message)
    }
    
    return Promise.reject(error)
  }
)

// 封装请求方法
const api = {
  request<T = any>(config: AxiosRequestConfig): Promise<T> {
    return request(config) as Promise<T>
  },
  
  get<T = any>(url: string, params?: any): Promise<T> {
    return request.get(url, { params }) as Promise<T>
  },
  
  post<T = any>(url: string, data?: any): Promise<T> {
    return request.post(url, data) as Promise<T>
  },
  
  put<T = any>(url: string, data?: any): Promise<T> {
    return request.put(url, data) as Promise<T>
  },
  
  delete<T = any>(url: string, params?: any): Promise<T> {
    return request.delete(url, { params }) as Promise<T>
  }
}

export default api 