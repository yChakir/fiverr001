import Vue from 'vue'

export default {
  login: function ({
    commit
  }, user) {
    return new Promise((resolve, reject) => {
      Vue.http.post('login', user).then(
        response => {
          const authorization = response.headers.map.authorization === undefined ? response.headers.map.Authorization : response.headers.map.authorization
          commit('setAccessToken', authorization[0])
        },
        error => {
          reject(error)
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
  }

}
