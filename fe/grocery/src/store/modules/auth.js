// store/modules/auth.js

import { api } from 'src/boot/axios'
import { SessionStorage } from 'quasar'

const state = {
	isLoggedIn: SessionStorage.has('user') && SessionStorage.has('jwt'),
	user: SessionStorage.getItem('user') || null,
	jwt: SessionStorage.getItem('jwt') || null,
	test: '123',
  };
  
  const mutations = {
	SET_LOGIN_STATE(state, isLoggedIn) {
	  console.log(state)
	  state.isLoggedIn = isLoggedIn;
	  state.test = 123
	  console.log(state)
	},
	SET_USER(state, user) {
 	  SessionStorage.set('user', user) 
	  state.user = user;
	},
	SET_JWT(state, jwt) {
		SessionStorage.set('jwt', jwt) 
		state.jtw = jwt;
	  },
  };
  
  const actions = {
	async login({ commit }, credentials) {
	  // Simulating login with async timeout

	//   await new Promise(resolve => setTimeout(resolve, 1000));
	console.log('attempping login')
	const response = await api.post('auth/login', credentials )
	console.log(response)
	  
	  // Normally, you would call your API to perform login
	  // Upon successful login, update the state
	  commit('SET_LOGIN_STATE', JSON.parse(JSON.stringify(true)));
	  commit('SET_USER', { ...response.data.user }); // Example: set the user object
	  commit('SET_JWT', response.data.jwt); // Example: set the user object
	},
	async logout({ commit }) {
	  // Simulating logout with async timeout
	//   await new Promise(resolve => setTimeout(resolve, 1000));
  
	  // Clear user data and update login state
	  commit('SET_LOGIN_STATE', false);
	  commit('SET_USER', null);
	  commit('SET_JWT', null);
	}
  };
  
  const getters = {
	isLoggedIn: state => state.isLoggedIn,
	currentUser: state => state.user,
  };
  
  export default {
	namespaced: true,
	state,
	mutations,
	actions,
	getters,
  };