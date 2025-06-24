<template>
  <div class="home-view">
    <h1>Welcome to PetWeb!</h1>
    <p>This is your dashboard. More features coming soon.</p>

    <div>
      <button @click="logout">Logout</button>
    </div>

    <!-- Example links to other parts of the application -->
    <nav>
      <router-link to="/pets">My Pets</router-link> |
      <router-link to="/posts">Community Posts</router-link> |
      <router-link to="/reminders">My Reminders</router-link>
    </nav>
  </div>
</template>

<script>
export default {
  name: 'HomeView',
  methods: {
    logout() {
      // Placeholder for logout logic
      // This should clear user session/token and redirect to login
      console.log('Logout action triggered');
      alert('Logout clicked. Implement actual logout logic here (e.g., clear token, redirect).');
      // Example: clear token from localStorage, then:
      // this.$router.push('/login');
    }
  },
  // Example of a route guard if you implement auth checks later
  // beforeRouteEnter(to, from, next) {
  //   // const isAuthenticated = checkIfUserIsAuthenticated(); // Implement this
  //   // if (isAuthenticated) {
  //   //   next();
  //   // } else {
  //   //   next('/login');
  //   // }
  //   next(); // Allow access for now
  // }
};
</script>

<style scoped>
.home-view {
  max-width: 800px;
  margin: 50px auto;
  padding: 20px;
  text-align: center;
}

.home-view h1 {
  color: #333;
  margin-bottom: 20px;
}

.home-view p {
  color: #555;
  margin-bottom: 30px;
}

.home-view nav {
  margin-top: 30px;
  padding-top: 20px;
  border-top: 1px solid #eee;
}

.home-view nav a {
  margin: 0 15px;
  color: #007bff;
  text-decoration: none;
  font-weight: bold;
}

.home-view nav a:hover {
  text-decoration: underline;
}

.home-view button {
  padding: 10px 20px;
  background-color: #dc3545;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  margin-bottom: 20px;
}

.home-view button:hover {
  background-color: #c82333;
}
</style>
