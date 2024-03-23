import { api } from 'src/boot/axios'

const state = {
	products: [], // Initial state with an empty array of products
  };
  
  const mutations = {
	SET_PRODUCTS(state, products) {
	  state.products = products;
	},
	ADD_PRODUCT(state, product) {
	  state.products.push(product);
	},
	REMOVE_PRODUCT(state, productId) {
	  state.products = state.products.filter(product => product.id !== productId);
	},
	UPDATE_PRODUCT(state, updatedProduct) {
	  const index = state.products.findIndex(product => product.id === updatedProduct.id);
	  if (index !== -1) {
		state.products.splice(index, 1, updatedProduct);
	  }
	},
  };
  
  const actions = {
	async setProducts({ commit }, products) {
      try{
        const resp = await api.get('/inventory/')
        const products = resp.data
        commit('SET_PRODUCTS', products);
      } catch (e) {
        console.log(e);
      }
	  
	},
	async addProduct({ commit, dispatch }, product) {
      try{
        const resp = await api.post('/inventory/', {...product})
      } catch (e) {
        console.log(e)
      }
	  
	},
	async removeProduct({ commit, dispatch }, productId) {
      try {
        const resp = await api.delete(`/inventory/${productId}`)
        dispatch('setProducts')
        return 1
      } catch (e) {
        console.log(e)
        return 0
      } 
	},
	async updateProduct({ commit }, updatedProduct) {
      try{
        const resp = await api.put('/inventory/', {...updatedProduct})
      } catch (e) {
        console.log(e)
      }
	}
  };
  
  const getters = {
	getProductById: state => id => state.products.find(product => product.productId == id),
    getAvailableProducts: state => state.product.find(product => product.archived == false && product.availableQuantity > 0),
	getAllProducts: state => state.products,
  };
  
  export default {
	namespaced: true,
	state,
	mutations,
	actions,
	getters,
  };