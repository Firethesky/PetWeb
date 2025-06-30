import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'
import { defineConfig } from 'vite'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src'),
    },
  },
  server: {
    port: 3001,
    proxy: {
      '/api': {
        target: 'http://localhost:8088',
        changeOrigin: true,
      }
    }
  }
}) 