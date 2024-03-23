<template>
	<div class="inventory-form absolute-center">
	<div class='text-h5 text-left text-uppercase color-white q-my-md primary text-white'>Create an Inventory Product</div>
	<q-card>
	  <q-card-section >
		<q-form @submit="submitForm">
		  <template v-for="column in columns" :key="column.name" >
			<q-input
			  v-model="formData[column.field]"
			  :type="getColumnType(column)"
			  :align="column.align"
			  class="q-my-sm full-width"
			  filled stack-label
			  :label="column.label"
			  :rules="column.rules"
			  :lazy-rules="true"
			/>
		  </template>
		  <q-btn type="submit" label="Create Product" color="positive"  class="q-my-md"/>
		</q-form>
	  </q-card-section>
	  <q-dialog v-model="addedSuccess" :backdrop-filter="'blur(4px) saturate(150%)'" no-esc-dismiss no-backdrop-dismiss>
      <q-card>
        <q-card-section class="row items-center q-pb-none text-h6">
          Yey
        </q-card-section>

        <q-card-section>
			Successfully added product! Returning to inventory...
        </q-card-section>
      </q-card>
    </q-dialog>
	</q-card>
	</div>
	
  </template>
  
  <script>

import { mapActions } from 'vuex';

  export default {
	data() {
	  return {
		formData: {
		  productName: '',
		  productPrice: '',
		  availableQuantity: '',
		  expirationDate: '',
		  unit: '',
		  remarks: ''
		},
		columns: [
		  { name: 'productName', label: 'Product Name', field: 'productName', align: 'left' , rules: [val => !!val || 'Product Name is required']},
		  { name: 'productPrice', label: 'Product Price', field: 'productPrice', align: 'center', rules: [val => !!val || 'Product Price is required', val => val >= 0 || 'Product Price cannot be less than zero'] },
		  { name: 'availableQuantity', label: 'Available Quantity', field: 'availableQuantity', align: 'center', rules: [val => !!val || 'Quantity is required', val => val >= 0 || 'Quantity cannot be less than zero']},
		  { name: 'expirationDate', label: 'Expiration Date', field: 'expirationDate', align: 'center', rules: [val => !!val || !val]},
		  { name: 'unit', label: 'Unit', field: 'unit', rules: [val => !!val || !val] },
		  { name: 'remarks', label: 'Remarks', field: 'remarks', align: 'right', rules: [val => !!val || !val] },
		],
		addedSuccess: false
	  };
	},
	methods: {
	  ...mapActions('inventory', ['addProduct']),
		
	  submitForm() {
		console.log('Form submitted:', this.formData);
		try{
			this.addProduct(this.formData)
			this.addedSuccess = true
			setTimeout(() => {this.$router.push('/inventory')}, 3000)
			
		} catch (e) {
			console.log(e)
		}
		
	  },
	  getColumnType(column) {
		// Determine input type based on the field name
		if (column.field === 'expirationDate') {
		  return 'date';
		} else if (column.field === 'productPrice' || column.field === 'availableQuantity') {
		  return 'number';
		}
		return 'text';
	  }
	}
  };
  </script>

<style scoped>

.inventory-form{

	width: 50vw;
	max-wdith: 500px;
}


</style>