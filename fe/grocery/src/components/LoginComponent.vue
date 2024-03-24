<template>
  <q-card class="my-card login-card ">
      <q-card-section class="bg-white text-primary dark text-center ">
        <div class="text-h6">MC Alba Grocery Store</div>
      </q-card-section>
      <q-separator />
      <q-card-section>
      <q-form
      @submit="onSubmit"
      class="q-gutter-md"
    >
      <q-input
        filled
        v-model="username"
        label="Username"
        hint="Ask Kim"
        lazy-rules
        :error="error"
        @focus="this.error=false"
        :rules="[ val => val && val.length !== '' || 'Please enter your username']"
      />

      <q-input
        filled
        type="password"
        v-model="password"
        label="Password"
        lazy-rules
        :rules="[
          val => val !== null && val !== '' || 'Please enter your password',
        ]"
        :error="error"
        @focus="this.error=false"
        error-message="Invalid Username or Password"
      />
      <q-card-section>
      <div class="absolute-center">
        <q-btn label="Login" type="submit" color="secondary"/>
      </div>
    </q-card-section>
    </q-form>
      
  </q-card-section>
    </q-card>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {

  data () {

    return{
      username: "",
      password: "",
      error: false,
    }
  },

  computed: {
    ...mapGetters('auth', ['isLoggedIn']),
  },

  methods: {
    onSubmit () {
      const {username, password } = this 
      this.handleLogin({username, password})
    },

    ...mapActions('auth', ['login', 'logout']),
    async handleLogin(credentials) {
      try {
        // Call your API or authentication service to perform login
        await this.login(credentials);
        // Redirect or perform any action upon successful login
        if(this.isLoggedIn) {

          this.$router.push('/inventory');
        }
        // this.$router.push('/inventory');
      } catch (error) {
        this.error = true
        console.error('Login failed:', error);
        // Handle login error (display error message, etc.)
      }
    },
    
  }

}

</script>

<style>

.login-card {
  min-height: 35vh;
  min-width: 35vh;
}

</style>

