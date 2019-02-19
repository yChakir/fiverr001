import Vue from 'vue'

export default {
  setAccessToken (state, token) {
    localStorage.setItem('token', token)
    state.accessToken = token
    Vue.http.headers.common['Authorization'] = state.accessToken
  },
  login (state) {
    state.authenticated = true
  },
  logout (state) {
    state.authenticated = false
  }
}
