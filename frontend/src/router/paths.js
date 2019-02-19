/**
 * Define all of your application routes here
 * for more information on routes, see the
 * official documentation https://router.vuejs.org/en/
 */
export default [
  {
    path: '/user-profile',
    name: 'User Profile',
    view: 'UserProfile',
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/sign-up',
    name: 'Sign up',
    view: 'SignUp',
    meta: {
      requireAuth: false
    }
  },
  {
    path: '/login',
    name: 'Login',
    view: 'Login',
    meta: {
      requireAuth: false
    }
  },
  {
    path: '/email-validation',
    name: 'Email Validation',
    view: 'EmailValidation',
    meta: {
      requireAuth: false
    }
  },
  {
    path: '/faq',
    name: 'FAQ',
    view: 'FAQ',
    meta: {
      requireAuth: false
    }
  },
  {
    path: '/contact',
    name: 'Contact',
    view: 'Contact',
    meta: {
      requireAuth: false
    }
  }
]
