import { createPinia } from 'pinia'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './style.css'

// 导入Element Plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
// 导入Element Plus图标
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App)

// 使用Pinia状态管理
app.use(createPinia())

// 使用Vue Router
app.use(router)

// 使用Element Plus
app.use(ElementPlus, { size: 'default', zIndex: 3000 })

// 注册所有图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

app.mount('#app') 