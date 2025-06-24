import { createRouter, createWebHistory } from 'vue-router';
import LoginView from '../views/Login.vue';
import RegisterView from '../views/Register.vue';
import HomeView from '../views/Home.vue'; // Import the HomeView

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: LoginView,
  },
  {
    path: '/register',
    name: 'Register',
    component: RegisterView,
  },
  {
    path: '/home', // Or just '/' if it's the main landing page after login
    name: 'Home',
    component: HomeView,
    // meta: { requiresAuth: true } // Add this later for auth guards
  },
  // Redirect root to home, assuming home is the default authenticated page
  {
    path: '/',
    redirect: '/home',
  }
  // Add other routes here as modules are developed, e.g., /pets, /posts
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL || '/'),
  routes,
});

export default router;
