/**
 * Define all of your application routes here
 * for more information on routes, see the
 * official documentation https://router.vuejs.org/en/
 */
const prefix = "Core.Router";

export default [
  {
    path: '/',
    name: `${prefix}.home`,
    view: 'Home'
  },
  {
    path: '/user-profile',
    name: `${prefix}.user-profile`,
    view: 'UserProfile'
  },
  {
    path: '/sign-up',
    name: `${prefix}.sign-up`,
    view: 'SignUp'
  },
  {
    path: '/login',
    name: `${prefix}.login`,
    view: 'Login'
  },
  {
    path: '/email-validation',
    name: `${prefix}.email-validation`,
    view: 'EmailValidation'
  },
  {
    path: '/forgot-password',
    name: `${prefix}.forgot-password`,
    view: 'ForgotPassword'
  },
  {
    path: '/reset-password',
    name: `${prefix}.reset-password`,
    view: 'ResetPassword'
  },
  {
    path: '/logout',
    name: `${prefix}.logout`,
    view: 'Logout'
  },
  {
    path: '/faq',
    name: `${prefix}.faq`,
    view: 'FAQ'
  },
  {
    path: '/contact',
    name: `${prefix}.contact`,
    view: 'Contact'
  }
]
