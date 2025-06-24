<template>
  <form @submit.prevent="handleSubmit" class="register-form">
    <div class="form-group">
      <label for="username">Username:</label>
      <input type="text" id="username" v-model="form.username" required />
    </div>
    <div class="form-group">
      <label for="password">Password:</label>
      <input type="password" id="password" v-model="form.password" required />
    </div>
    <div class="form-group">
      <label for="confirmPassword">Confirm Password:</label>
      <input type="password" id="confirmPassword" v-model="form.confirmPassword" required />
    </div>
    <div class="form-group">
      <label for="email">Email:</label>
      <input type="email" id="email" v-model="form.email" required />
    </div>
    <div class="form-group">
      <label for="nickname">Nickname (Optional):</label>
      <input type="text" id="nickname" v-model="form.nickname" />
    </div>
    <button type="submit">Register</button>
  </form>
</template>

<script>
export default {
  name: 'RegisterForm',
  data() {
    return {
      form: {
        username: '',
        password: '',
        confirmPassword: '',
        email: '',
        nickname: '',
      },
    };
  },
  methods: {
    handleSubmit() {
      if (this.form.password !== this.form.confirmPassword) {
        alert("Passwords do not match!");
        return;
      }
      // Emit all form data except confirmPassword
      const { confirmPassword, ...userData } = this.form;
      this.$emit('submit', userData);
    },
  },
};
</script>

<style scoped>
.register-form {
  display: flex;
  flex-direction: column;
  gap: 15px;
}
.form-group {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.form-group label {
  margin-bottom: 5px;
  font-weight: bold;
}
.form-group input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}
button {
  padding: 10px 15px;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}
button:hover {
  background-color: #1e7e34;
}
</style>
