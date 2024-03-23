<template>
	<q-page class="flex flex-center">
	    <q-dialog v-model="alert" :backdrop-filter="backdropFilter" persistent>
      	<q-card>
        <q-card-section>
          <div class="text-h6">Notice</div>
        </q-card-section>

        <q-card-section class="q-pt-none">
          Please login to access this page. Redirect to login page in {{ timer }} seconds.
        </q-card-section>
	  </q-card>
    </q-dialog>
	</q-page>
  </template>


<script>

export default {

	data () {
		return {
			timer: 5,
			alert: true,
			backdropFilter: 'brightness(150%)'
		}
	},


	mounted () {

		const interval = setInterval( () => this.timer-=1, 1000);
		

		setTimeout( ()=> {
			clearInterval(interval)
			this.$router.push('/')
		},5000)
	}


}


</script>

<style scoped>
  .q-dialog__backdrop
  {
	background: transparent;
    backdrop-filter: blur(7px);
  }


.transparent-dialog .q-dialog__backdrop {
  background-color: transparent !important;
}

.transparent-dialog .q-dialog__inner {
  background-color: transparent !important;
  box-shadow: none !important;
}
</style>
