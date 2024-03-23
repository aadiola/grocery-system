<template>
	<div class="inventory-form absolute-center">
	<div class='text-h5 text-left text-uppercase color-white q-my-md primary text-white'>Edit an Inventory Product</div>
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
			  :disable="column.disabled"
			/>
		  </template>
		  <q-btn type="submit" label="Update Product" color="positive"  class="q-my-md"/>
		  <q-btn to="/inventory" label="Cancel Edit" color="warning"  class="q-ma-md"/>
		</q-form>
	  </q-card-section>
	  <q-dialog v-model="editedSuccess" :backdrop-filter="'blur(4px) saturate(150%)'" no-esc-dismiss no-backdrop-dismiss>
      <q-card>
        <q-card-section class="row items-center q-pb-none text-h6">
          Yey
        </q-card-section>

        <q-card-section>
			Successfully updated product! Returning to inventory...
        </q-card-section>
      </q-card>
    </q-dialog>
	</q-card>
	</div>
	
	
  </template>
  
  <script>

import { mapActions, mapGetters } from 'vuex';

  export default {

	data() {
	  return {
		formData: {
		  productId: null,
		  productName: '',
		  productPrice: '',
		  availableQuantity: '',
		  expirationDate: '',
		  unit: '',
		  remarks: ''
		},
		columns: [
		{ name: 'productId', label: 'Product ID', field: 'productId', align: 'left' , disabled: true, rules: [val => !!val || !val] },
		  { name: 'productName', label: 'Product Name', field: 'productName', align: 'left' , rules: [val => !!val || 'Product Name is required']},
		  { name: 'productPrice', label: 'Product Price', field: 'productPrice', align: 'center', rules: [val => !!val || 'Product Price is required', val => val >= 0 || 'Product Price cannot be less than zero'] },
		  { name: 'availableQuantity', label: 'Available Quantity', field: 'availableQuantity', align: 'center', rules: [val => !!val || 'Quantity is required', val => val >= 0 || 'Quantity cannot be less than zero']},
		  { name: 'expirationDate', label: 'Expiration Date', field: 'expirationDate', align: 'center', rules: [val => !!val || !val]},
		  { name: 'unit', label: 'Unit', field: 'unit', rules: [val => !!val || !val] },
		  { name: 'remarks', label: 'Remarks', field: 'remarks', align: 'right', rules: [val => !!val || !val] },
		],
		editedSuccess: false
	  };
	},

	computed: {
		...mapGetters('inventory', ['getProductById'])
	},


	methods: {
	  ...mapActions('inventory', ['updateProduct']),
		
	  submitForm() {
		console.log('Form submitted:', this.formData);
		try{
			this.updateProduct(this.formData)
			this.editedSuccess = true
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
	},

	mounted () {

		console.log(this.$route.query)
		const prodId = this.$route.query.productId
		console.log(prodId)
		if(!prodId) {
			this.$router.push('/inventory');
		}
		const product = this.getProductById(prodId)
		if (!product) {
			this.$router.push('/inventory');
		}
		console.log(product)
		this.formData = { ...product }
	  }
  };
  </script>

<style scoped>

.inventory-form{

	width: 50vw;
}


</style>