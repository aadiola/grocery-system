import { store } from 'quasar/wrappers'
import { createStore } from 'vuex'
import auth from './modules/auth'; // Import the auth module
import inventory from './modules/inventory'; // Import the inventory module
import transactions from './modules/transactions'; // Import the transactions module

// import example from './module-example'

/*
 * If not building with SSR mode, you can
 * directly export the Store instantiation;
 *
 * The function below can be async too; either use
 * async/await or return a Promise which resolves
 * with the Store instance.
 */

export default store(function (/* { ssrContext } */) {
  const Store = createStore({
    modules: {
      auth,
      inventory,
      transactions
    },

    // enable strict mode (adds overhead!)
    // for dev mode and --debug builds only
    strict: process.env.DEBUGGING
  })

  return Store
})
