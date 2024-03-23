<template>
  <q-layout view="lHh Lpr lFf" class="bg-primary">
    <q-header elevated>
      <q-toolbar class="custom-toolbar">
        <q-toolbar-title>
          Grocery System
        </q-toolbar-title>
        <div class="q-mr-md">
          Hello, <b>{{ username }}</b>!
        </div>
        <q-btn color="negative" icon="logout" label="Log Out" @click="handleLogout()"/>
      </q-toolbar>
    </q-header>

    <q-page-container>
      <q-tabs class="tabs" inline-label>
        <q-route-tab
          to="/inventory"
          label="Inventory"
        />
        <q-route-tab
          to="/transactions"
          label="Transactions"
        />
      </q-tabs>
        <router-view />
    </q-page-container>
  </q-layout>
</template>

<script>

import { mapGetters, mapActions } from 'vuex'


export default {
  name: 'MainLayout',


  computed: {
    ...mapGetters('auth', ['currentUser']),

    username (){
      return this.currentUser?.username
    }
  },

  methods: {
    ...mapActions('auth', ['logout']),

    async handleLogout() {
      try {
        // Call your API or authentication service to perform logout
        await this.logout();
        // Redirect or perform any action upon successful logout
        this.$router.push({ name: 'home' });
      } catch (error) {
        console.error('Logout failed:', error);
        // Handle logout error (display error message, etc.)
      }
    },
  }
}
</script>
<style scoped>
.custom-toolbar {
  background-color: white; /* Example color */
  color: #750C0C; /* Text color */
}

.tabs {
  background: #4ea2da;
  color: #F2C037;
}


</style>