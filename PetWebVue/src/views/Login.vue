<template>
  <div class="login-view">
    <h2>Login</h2>
    <LoginForm @submit="handleLogin" />
    <p>
      Don't have an account? <router-link to="/register">Register here</router-link>
    </p>
  </div>
</template>

<script>
import LoginForm from '@/components/user/LoginForm.vue';

export default {
  name: 'LoginView',
  components: {
    LoginForm,
  },
  methods: {
    async handleLogin(credentials) {
      try {
        // Call authService.login(credentials)
        // Redirect to dashboard or home on success
        console.log('Login credentials:', credentials);
        alert('Login attempt with: ' + JSON.stringify(credentials));
        // Replace with actual login logic using authService
        // Example: this.$router.push('/');
      } catch (error) {
        console.error('Login failed:', error);
        alert('Login failed: ' + (error.response?.data?.error || error.message));
      }
    },
  },
};
</script>

<style scoped>
.login-view {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  text-align: center;
}
.login-view h2 {
  margin-bottom: 20px;
}
.login-view p {
  margin-top: 15px;
}
</style>
