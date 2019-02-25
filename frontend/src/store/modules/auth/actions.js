import Vue from 'vue'

export default {
  login: function ({
    commit
  }, vo) {
    return new Promise((resolve, reject) => {
      Vue.http.post('login', vo).then(
        response => {
          const authorization = response.headers.map.authorization === undefined ? response.headers.map.Authorization : response.headers.map.authorization
          commit('setAccessToken', authorization[0])
          resolve()
        },
        response => {
          reject(response.body)
        }
      )
    })
  },
  logout: function ({
    commit
  }) {
    return new Promise((resolve) => {
      localStorage.removeItem('token')
      commit('logout')
      resolve()
    })
  },
  register: function ({ commit }, vo) {
    return new Promise((resolve, reject) => {
      Vue.http.post('api/v1/account/register', vo).then(
        () => {
          resolve()
          commit()
        },
        response => {
          reject(response.body)
        }
      )
    })
  },
  validateEmail: function ({ commit }, vo) {
    return new Promise((resolve, reject) => {
      Vue.http.post('api/v1/account/email-validation', vo).then(
        () => {
          resolve()
          commit()
        },
        response => reject(response.body)
      )
    })
  },
  forgotPassword: function ({ commit }, vo) {
    return new Promise((resolve, reject) => {
      Vue.http.post('api/v1/account/forgot-password', vo).then(
        () => {
          resolve()
          commit()
        },
        response => reject(response.body)
      )
    })
  },
  resetPassword: function ({ commit }, vo) {
    return new Promise((resolve, reject) => {
      Vue.http.post('api/v1/account/reset-password', vo).then(
        () => {
          resolve()
          commit()
        },
        response => reject(response.body)
      )
    })
  }

}
