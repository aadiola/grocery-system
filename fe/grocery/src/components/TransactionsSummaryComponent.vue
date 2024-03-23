<template>
<div  class="q-my-md" style="max-width:400px">
	<q-card>
		<q-card-section class="bg-secondary text-white flex column">
			<div class="row vertical-middle">
				<span class="col-5 vertical-middle">Transaction ID: </span> <span class="col text-h6">{{ transaction.transactionId }}</span>
			</div>
			<div class="row">
				<span class="col-5">Date of Transaction: </span> 
				 <span class="text-h6">{{ formattedDate}}</span>
			</div>
			<div>
				
			</div>
			
      </q-card-section>
	  <q-separator inset />
	  <q-card-section>
	{{ productNames }}
    </q-card-section>
	  <q-separator inset />
    <q-card-section>
		<div class="row">
			<span class="col-5">Amount Tendered: </span> 
			<span class="text-h6">{{ transaction.amountTendered}}</span>
		</div>
		<div class="row">
			<span class="col-5">Discount: </span> 
			<span class="text-h6">{{ transaction.discount}}</span>
		</div>
		<div class="row">
			<span class="col-5">Total: </span> 
			<span class="text-h6">{{ transaction.total}}</span>
		</div>
    </q-card-section>
  </q-card>
</div>
</template>


<script>
import moment from 'moment';
import { mapGetters } from 'vuex';

export default {
	
	props: {
		transaction: {
			required: true
		}
	},



	computed: {
		...mapGetters('inventory', ['getProductById']),

		formattedDate () {
			return this.transaction.dateOfTransaction ? moment(this.transaction.dateOfTransaction).format('MM-DD-YYYY') : ''
		},

		productNames () {
			if(!this.transaction){
				return {}
			}
			console.log(this.transaction)
			console.log(this.transaction.productToQtySold)
			const productNames = {}
			this.transaction?.productToQtySold?.forEach(product => {
				const productInventory = this.getProductById(product.productId)
				const productName = productInventory?.productName || ''
				productNames[product.productId] = productName
			})
			return productNames
		}
	}


}

</script>