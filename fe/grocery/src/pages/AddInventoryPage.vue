<template>
	<div class="bg-primary q-pa-md">
		<div class="q-my-xl">
			<AddInventoryComponent />
		</div>
		
	</div>
	
</template>
<script>
import AddInventoryComponent from '../components/AddInventoryComponent.vue';

export default {

	components: {
		AddInventoryComponent
	},

}
</script>

<style>

.container{
	width: 100%;
	height: calc(100% - 20%);
}

</style>