import Vue from 'vue'
import Vuex from 'vuex'
//vuex用来存储整个vue项目可以共享的变量
//状态
Vue.use(Vuex)

const token_key ='token'
const user_key = 'user'
let s=window.sessionStorage

const store= new Vuex.Store({
  state: {
    token:s.getItem(token_key),
    user:s.getItem(user_key),
    currentUserId: null
  },
  mutations: {
    setToken(state,value){
      state.token = value
      s.setItem(token_key, value)
    },
    setUser(state,value){
      state.user = value
      s.setItem(user_key, value)
    }
  },

})
export default store
