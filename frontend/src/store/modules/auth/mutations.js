import Vue from 'vue'

export default {
  setAccessToken (state, token) {
    localStorage.setItem('token', token)
    state.accessToken = token
    state.authenticated = true
    Vue.http.headers.common['Authorization'] = state.accessToken
  },
  login (state) {
    state.authenticated = true
  },
  logout (state) {
    Vue.http.headers.common['Authorization'] = null
    localStorage.removeItem('token')
    state.authenticated = false
  }
}
