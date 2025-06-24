import axios from 'axios';

const API_URL = '/api/users'; // Adjust if your API base URL is different

const apiClient = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export default {
  login(credentials) {
    // credentials: { username, password }
    return apiClient.post('/login', credentials);
  },
  register(userData) {
    // userData: { username, password, email, nickname (optional) }
    return apiClient.post('/register', userData);
  },
  // Potentially add logout, getCurrentUser methods here later
};
