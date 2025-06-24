<template>
  <div class="register-view">
    <h2>Register</h2>
    <RegisterForm @submit="handleRegister" />
    <p>
      Already have an account? <router-link to="/login">Login here</router-link>
    </p>
  </div>
</template>

<script>
import RegisterForm from '@/components/user/RegisterForm.vue';

export default {
  name: 'RegisterView',
  components: {
    RegisterForm,
  },
  methods: {
    async handleRegister(userData) {
      try {
        // Call authService.register(userData)
        // Redirect to login or dashboard on success
        console.log('Registration data:', userData);
        alert('Registration attempt with: ' + JSON.stringify(userData));
        // Replace with actual registration logic using authService
        // Example: this.$router.push('/login');
      } catch (error) {
        console.error('Registration failed:', error);
        alert('Registration failed: ' + (error.response?.data?.error || error.message));
      }
    },
  },
};
</script>

<style scoped>
.register-view {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  text-align: center;
}
.register-view h2 {
  margin-bottom: 20px;
}
.register-view p {
  margin-top: 15px;
}
</style>
