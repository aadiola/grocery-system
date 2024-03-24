<template>
	<div class="bg-primary q-pa-md">
		<div>
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
		...mapActions('transactions', ['setTransactions']),
		...mapActions('inventory', ['setProducts'])
	},

	async created () {
		// console.log(store)
		await this.setTransactions();
		await this.setProducts()
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