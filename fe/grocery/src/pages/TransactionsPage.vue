<template>
	<div class="bg-primary q-pa-md">
		<div class="q-my-xl">
			<TransactionsComponent />
		</div>
		
	</div>
	
</template>
<script>
import TransactionsComponent from '../components/TransactionsComponent.vue';
import { mapActions } from 'vuex';


export default {

	components: {
		TransactionsComponent
	},

	methods: {
		...mapActions('transactions', ['setTransactions'])
	},

	async created () {
		// console.log(store)
		await this.setTransactions();
		// next()
	},
}
</script>

<style>

.container{
	width: 100%;
	height: calc(100% - 20%);
}

</style>