import { api } from 'src/boot/axios'

const state = {
	transactions: [], // Initial state with an empty array of transactions
  };
  
  const mutations = {
	SET_TRANSACTIONS(state, transactions) {
	  state.transactions = transactions;
	},
	ADD_TRANSACTIONS(state, transaction) {
	  state.transactions.push(transaction);
	},
	REMOVE_TRANSACTIONS(state, transactionId) {
	  state.transactions = state.transactions.filter(transaction => transaction.id !== transactionId);
	},
	UPDATE_TRANSACTIONS(state, updatedTransaction) {
	  const index = state.transactions.findIndex(transaction => transaction.id === updatedTransaction.id);
	  if (index !== -1) {
		state.transactions.splice(index, 1, updatedTransaction);
	  }
	},
  };
  
  const actions = {
	async setTransactions({ commit }, transactions) {
      try{
        const resp = await api.get('/sales/')
		console.log(resp)
        const transactions = resp.data
        commit('SET_TRANSACTIONS', transactions);
      } catch (e) {
        console.log(e);
      }
	  
	},
	async addTransaction({ commit, dispatch }, transaction) {
      try{
        const resp = await api.post('/sales/', {...transaction})
      } catch (e) {
        console.log(e)
      }
	  
	},
	async removeTransaction({ commit, dispatch }, transactionId) {
      try {
        const resp = await api.delete(`/sales/${transactionId}`)
        dispatch('setTransactions')
        return 1
      } catch (e) {
        console.log(e)
        return 0
      } 
	},
	async updateTransaction({ commit }, updatedTransaction) {
      try{
        const resp = await api.put('/sales/', {...updatedTransaction})
      } catch (e) {
        console.log(e)
      }
	}
  };
  
  const getters = {
	getTransactionById: state => id => state.transactions.find(transaction => transaction.transactionId == id),
	getAllTransactions: state => state.transactions,
  };
  
  export default {
	namespaced: true,
	state,
	mutations,
	actions,
	getters,
  };