<template>
	<div class="bg-primary q-pa-md">
		<div class="q-my-xl">
			<EditInventoryComponent />
		</div>
		
	</div>
	
</template>
<script>
import EditInventoryComponent from '../components/EditInventoryComponent.vue';


export default {

	components: {
		EditInventoryComponent
	},


}
</script>

<style>

.container{
	width: 100%;
	height: calc(100% - 20%);
}

</style>