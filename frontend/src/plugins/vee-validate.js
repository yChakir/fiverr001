import Vue from 'vue';
import VeeValidate from 'vee-validate';
import en from 'vee-validate/dist/locale/en';
import fr from 'vee-validate/dist/locale/fr';


Vue.use(VeeValidate, {
    dictionary: {
        en: en,
        fr: fr
    }
});
