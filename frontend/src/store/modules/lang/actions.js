
export default {
    init: function ({
    commit
  }) {
    return new Promise((resolve) => {
        const storedLanguage = localStorage.getItem('language');

        if(storedLanguage) {
          commit('setLanguage', storedLanguage)
          resolve()
        }
    })
  }

}
