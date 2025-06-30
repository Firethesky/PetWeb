import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'

// 路由配置
const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/LoginPage.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/RegisterPage.vue')
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: () => import('../views/Dashboard.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/pets',
    name: 'Pets',
    component: () => import('../views/Pets.vue'), 
    meta: { requiresAuth: true }
  },
  {
    path: '/pet/:id',
    name: 'PetDetail',
    component: () => import('../views/PetDetailPage.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/community',
    name: 'Community',
    component: () => import('../views/CommunityFeed.vue'),
    meta: { requiresAuth: true }
  },  
  {
    path: '/health',
    name: 'Health',
    component: () => import('../views/HealthRecord.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/profile',
    name: 'Profile',
    component: () => import('../views/Profile.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/reminders',
    name: 'Reminders',
    component: () => import('../views/RemindersPage.vue'),
    meta: { requiresAuth: true }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth)
  const isLoggedIn = localStorage.getItem('token') // 简单的登录判断，后续可以改进
  
  // console.log('路由守卫检测到的token:', isLoggedIn)
  // console.log('当前路由:', to.path, '需要认证:', requiresAuth)
  
  if (requiresAuth && !isLoggedIn) {
    next('/login')
  } else {
    // 如果已登录，确保token正确
    if (isLoggedIn) {
      // 这里可以添加检查token有效性的逻辑，如果需要
      // 例如尝试获取用户信息等
      // console.log('用户已登录，允许访问:', to.path)
    }
    next()
  }
})

export default router 