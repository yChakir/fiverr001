import Vue from 'vue'

// Lib imports
import VueResource from "vue-resource";
import router from '@/router'
import {api_endpoint} from '@/config'

Vue.use(VueResource);

Vue.http.options.root = api_endpoint;

Vue.http.interceptors.push((request, next) => {
    next(response => {
        if (response.status == 401 || response.status == 403) {
            store.dispatch("auth/logout").then(() => router.push("login"));
        }
    });
});