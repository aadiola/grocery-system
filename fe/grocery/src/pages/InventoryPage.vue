<template>
	<div class="bg-primary q-pa-md">
		<div class="q-my-xl">
			<InventoryComponent />
		</div>
		
	</div>
	
</template>
<script>
import InventoryComponent from '../components/InventoryComponent.vue';
import { mapActions } from 'vuex';


export default {

	components: {
		InventoryComponent
	},

	methods: {
		...mapActions('inventory', ['setProducts'])
	},

	async created () {
		// console.log(store)
		await this.setProducts();
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