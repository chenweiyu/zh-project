import Vue from 'vue'
import Router from 'vue-router'

import Home from '@/views/Home'

import Householder from '@/views/household/Householder'
import HouseholderDetail from '@/views/household/HouseholderDetail'
import Household from '@/views/household/Household'

import Electric from '@/views/community/Electric'
import Water from '@/views/community/Water'
import Community from '@/views/community/Community'
import CommunityDetail from '@/views/community/CommunityDetail'

import User from '@/views/sys/User'
import Role from '@/views/sys/Role'
import Dict from '@/views/sys/Dict'

import LoginIndex from '@/views/login/LoginIndex'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '',
      component: LoginIndex,
      name: 'LoginIndex'
    },
    {
      path: '/login',
      component: LoginIndex,
      name: 'LoginIndex'
    },
    {
      path: '/home',
      component: Home,
      redirect: '/householder',
      children: [
        {
          path: '/householder',
          component: Householder
        },
        {
          path: '/householderDetail',
          component: HouseholderDetail
        },
        {
          path: '/household',
          component: Household
        },
        {
          path: '/electric',
          component: Electric
        },
        {
          path: '/water',
          component: Water
        },
        {
          path: '/community',
          component: Community
        },
        {
          path: '/communityDetail',
          component: CommunityDetail
        },
        {
          path: "/user",
          component: User
        },
        {
          path: "/role",
          component: Role
        },
        {
          path: "/dict",
          component: Dict
        }
      ]
    }
  ]
})
