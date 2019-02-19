import Vue from 'vue'

// Lib imports
import VueResource from 'vue-resource'
import router from '@/router'
import store from '@/store'
import { API_ENDPOINT } from '@/config'

Vue.use(VueResource)

Vue.http.options.root = API_ENDPOINT

Vue.http.interceptors.push((request, next) => {
  next(response => {
    if (response.status === 401 || response.status === 403) {
      store.dispatch('auth/logout').then(() => router.push('login'))
    }
  })
})
