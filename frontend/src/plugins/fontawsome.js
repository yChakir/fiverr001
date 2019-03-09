import Vue from 'vue'
import { library } from '@fortawesome/fontawesome-svg-core'
import { 
    faGlobe,
    faHome,
    faBars,
    faQuestion,
    faAddressBook,
    faUser,
    faSignOutAlt,
    faCheck,
    faInfo,
    faTimes,
    faCamera
 } from '@fortawesome/free-solid-svg-icons'
import {
    faGoogle,
    faFacebookF
} from '@fortawesome/free-brands-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

library.add(
    faGlobe, faBars,
    faHome, faQuestion, faAddressBook, faUser, faSignOutAlt,
    faGoogle, faFacebookF,
    faInfo, faCheck, faTimes, faCamera
)

Vue.component('fa-icon', FontAwesomeIcon)

Vue.config.productionTip = false