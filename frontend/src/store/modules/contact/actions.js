import Vue from 'vue'

export default {
  contact: function ({
    commit
  }, vo) {
    return new Promise((resolve, reject) => {
      Vue.http.post('api/v1/contact', vo).then(
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
