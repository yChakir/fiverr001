import Vue from 'vue'

export default {
  fetchProfile: function ({
    commit
  }) {
    return new Promise((resolve, reject) => {
      Vue.http.get('api/v1/profile').then(
        response => {
          const profile = response.body
          commit('setProfile', profile)
          resolve(profile)
        },
        response => {
          reject(response.body)
        }
      )
    })
  },
  changePassword: function ({ commit }, vo) {
    return new Promise((resolve, reject) => {
      Vue.http.post('api/v1/profile/change-password', vo).then(
        () => {
          resolve()
          commit()
        },
        response => {
          reject(response.body)
        }
      )
    })
  }

}
