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
        error => {
          reject(error)
        }
      )
    })
  }

}
