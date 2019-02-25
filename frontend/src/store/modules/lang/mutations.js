
// https://vuex.vuejs.org/en/mutations.html
import Vue from 'vue'
import i18n from '@/i18n'
import { Validator } from 'vee-validate';

export default {
  setLanguage: function(state, language) {
    state = {...state, current: language};
    localStorage.setItem('language', language)
    i18n.locale = language
    Validator.localize(language)
    Vue.http.headers.common['Accept-Language'] = language
  }
}
