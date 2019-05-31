import {FE_URL} from '@/config'

/**
 * Google application client id
 */
export const GG_ID = '420188269949-ncukksnudmcnb2176eaeo245hinvgc9r.apps.googleusercontent.com';

/**
 * Google authentication path
 */
export const GG_BASE = 'https://accounts.google.com/o/oauth2/v2/auth';

/**
 * Google application scopes
 */
export const GG_SCOPES = [
    'https://www.googleapis.com/auth/userinfo.profile',
    'https://www.googleapis.com/auth/userinfo.email'
];

/**
 * Redirection url after a success authentication
 */
export const GG_REDIRECT = `${FE_URL}/google-auth`;

