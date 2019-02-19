/**
 * Define all of your application routes here
 * for more information on routes, see the
 * official documentation https://router.vuejs.org/en/
 */
export default [
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
    view: 'Login',
    name: 'Login'
  },
  {
    path: '/email-validation',
    name: 'Email Validation',
    view: 'EmailValidation'
  },
  {
    path: '/faq',
    view: 'FAQ',
    name: 'FAQ'
  },
  {
    path: '/contact',
    view: 'Contact',
    name: 'Contact'
  }
]
