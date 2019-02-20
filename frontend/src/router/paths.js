/**
 * Define all of your application routes here
 * for more information on routes, see the
 * official documentation https://router.vuejs.org/en/
 */
export default [
  {
    path: '/',
    name: 'Home',
    view: 'Home'
  },
  {
    path: '/user-profile',
    name: 'User Profile',
    view: 'UserProfile'
  },
  {
    path: '/sign-up',
    name: 'Sign up',
    view: 'SignUp'
  },
  {
    path: '/login',
    name: 'Login',
    view: 'Login'
  },
  {
    path: '/email-validation',
    name: 'Email Validation',
    view: 'EmailValidation'
  },
  {
    path: '/logout',
    name: 'logout',
    view: 'Logout'
  },
  {
    path: '/faq',
    name: 'FAQ',
    view: 'FAQ'
  },
  {
    path: '/contact',
    name: 'Contact',
    view: 'Contact'
  }
]
