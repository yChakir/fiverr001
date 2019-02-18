import Vue from 'vue'

export default {
    login: function ({
        commit,
        dispatch
      }, user) {
        return new Promise((resolve, reject) => {
          Vue.http.post("login", user).then(
            response => {
                console.debug(response);
              const authorization = response.headers.map.authorization === undefined ? response.headers.map.Authorization : response.headers.map.authorization
              commit('setAccessToken', authorization[0]);
            },
            error => {
              reject(error);
            }
          );
        });
      },
      logout: function ({
        commit
      }) {
        return new Promise((resolve, reject) => {
          localStorage.removeItem('token');
          commit('logout');
          resolve();
        })
      }
  
}