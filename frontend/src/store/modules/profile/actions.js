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
          resolve()
        },
        response => {
          reject(response.body)
        }
      )
    })
  },
  changePassword: function ({ commit }, vo) {
    return new Promise((resolve, reject) => {
      Vue.http.patch('api/v1/profile/change-password', vo).then(
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
  editProfile: function ({ commit }, vo) {
    return new Promise((resolve, reject) => {
      Vue.http.patch('api/v1/profile/edit-profile', vo, {
        headers: {
          "Content-Type": "multipart/form-data"
        }
      }).then(
        response => {
          commit('setProfile', response.body)
          resolve()
        },
        response => {
          reject(response.body)
        }
      )
    })
  },
  editAvatar: function ({ commit }, vo) {
    return new Promise((resolve, reject) => {
      Vue.http.post('api/v1/images/upload?avatar=true', vo).then(
        () => {
          resolve()
        },
        response => {
          reject(response.body)
        }
      )
    })
  }

}
