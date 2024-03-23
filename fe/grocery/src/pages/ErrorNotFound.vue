<template>
  <div class="fullscreen bg-blue text-white text-center q-pa-md flex flex-center">
    <div>
      <div style="font-size: 30vh">
        404
      </div>

      <div class="text-h2" style="opacity:.4">
        Oops. Nothing here.... Sending you homeeee!
      </div>

      <div>
        <q-circular-progress
        :value="timer"
        :min="0"
        :max="5"
        size="50px"
        color="orange"
        class="q-ma-md"
        reverse
        show-value
    />
      </div>

      <q-btn
        class="q-mt-xl"
        color="white"
        text-color="blue"
        unelevated
        :to="nextUrl"
        label="Go Home"
        no-caps
      />
    </div>
  </div>
</template>

<script>
import { defineComponent } from 'vue';
import { mapState } from 'vuex';

export default defineComponent({
  name: 'ErrorNotFound',

  data () {
    return {
      timer: 5
    }
  },

  computed: {
    ...mapState('auth', ['isLoggedIn']),

    nextUrl() {
      if(this.isLoggedIn){
        return '/inventory'
      }

      return '/'
    } 
  },

  mounted () {

    const interval = setInterval( () => this.timer-=1, 1000);


    setTimeout( ()=> {
      this.$router.push(this.nextUrl)
        clearInterval(interval)
    },6000)
  }

});
</script>
