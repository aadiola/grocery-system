const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('layouts/LoginLayout.vue'),
    children: [
      { path: '', name: 'home', component: () => import('pages/LoginPage.vue') }
    ]
  },

  {
    path: '/inventory',
    name: 'Inventory',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', name: 'inventory', component: () => import('pages/InventoryPage.vue')},
      { path: 'add', name: 'addInventory', component: () => import('pages/AddInventoryPage.vue')},
      { path: 'edit', name: 'editInventory', component: () => import('pages/EditInventoryPage.vue')}
    ],
    meta: {requiresAuth: true}


  },

  {
    path: '/transactions',
    name: 'Transactions',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', name: 'transactions', component: () => import('pages/TransactionsPage.vue')}
    ],
    meta: {requiresAuth: true}


  },

  {
    path: '/loginfailed',
    name: 'LoginFailed',
    component: () => import ('layouts/LoginLayout.vue'),
    children: [
      { path: '', name: 'loginfailed', component: () => import('pages/LoginFailed.vue')}
    ]
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue')
  }
]

export default routes
