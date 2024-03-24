<template>
<div  class="q-my-md relative" style="width:45vw;">
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
		<div v-if="Object.keys(transaction).length!=0" style="max-height: 25vw; overflow: auto">
			<q-table
			title="Products in the Transaction"
			:rows="products"
			:columns="columns"
			:visible-columns="visibleColumns"
			:pagination="{rowsPerPage: 0}"
			:hide-pagination="true"
			flat
			dense
		>


		</q-table>
		</div>
		
    </q-card-section>
	  <q-separator inset />
    <q-card-section>
		<div class="row">
			<span class="col-3">Amount Tendered: </span> 
			<span class="text-h6 text-right col">{{ transaction.amountTendered}}</span>
		</div>
		<div class="row">
			<span class="col-3">Discount: </span> 
			<span class="text-h6 text-right col">{{ transaction.discount}}</span>
		</div>
		<div class="row">
			<span class="col-3">Total: </span> 
			<span class="text-h6 text-right col">{{ transaction.total}}</span>
		</div>
    </q-card-section>
	<q-card-section class="q-gutter-md">
		<q-btn @click="editTransaction()" color="secondary" label="Edit" dense style="width: 5vw;" />
          <q-btn @click="deleteTransaction()" color="negative" label="Delete" dense style="width: 5vw;" />
		</q-card-section>
  </q-card>
  <q-dialog v-model="deleteDialogVisible">
      <q-card>
        <q-card-section class="text-h6">Confirm Deletion</q-card-section>
        <q-card-section>
          Are you sure you want to delete this item?
        </q-card-section>
        <q-card-actions align="right">
          <q-btn label="Cancel" color="secondary" @click="deleteDialogVisible = false" />
          <q-btn label="Delete" color="negative" @click="confirmDelete" />
        </q-card-actions>
      </q-card>
	</q-dialog>
	  <q-dialog v-model="errorDialogVisible">
      <q-card>
        <q-card-section class="text-h6">Ooopss, sorryyyy</q-card-section>
        <q-card-section>
          An error was encountered, please try again...
        </q-card-section>
        <q-card-actions align="right">
          <q-btn label="Okay" color="secondary" @click="errorDialogVisible = false" />
        </q-card-actions>
      </q-card>
    </q-dialog>
</div>
</template>


<script>
import moment from 'moment';
import { mapGetters, mapActions } from 'vuex';


const columns = [
	{ name: 'productId', label: 'ID', field: 'productId', align:'left', style: 'width: 5%', headerStyle: 'width: 5%'},
	{ name: 'productName', label: 'Product Name', field: 'productName', align: 'left', style: 'width: 25%;', headerStyle: 'width: 25%', align: 'center' },
	{ name: 'pricePerUnit', label: 'Price Per Unit', field: 'pricePerUnit', align: 'center', },
	{ name: 'quantity', label: 'Quantity', field: 'quantity', align: 'center'},
	{ name: 'productTotal', label: 'Product Total', field: 'productTotal', align: 'right' },
]

const visibleColumns = ['productId', 'productName', 'pricePerUnit', 'quantity', 'productTotal']

export default {
	
	props: {
		transaction: {
			default: {}
		}
	},

	data () {
		return {
			columns: columns,
			visibleColumns: visibleColumns,
			deleteDialogVisible: false,
			errorDialogVisible: false,
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
			const productNames = {}
			this.transaction?.productToQtySold?.forEach(product => {
				const productInventory = this.getProductById(product.productId)
				console.log(productInventory)
				const productName = productInventory?.productName || ''
				productNames[product.productId] = productName
			})
			console.log(productNames)
			return productNames
		},

		products () {
			if(!this.transaction){
				return {}
			}
			const productNames = {}
			this.transaction?.productToQtySold?.forEach(product => {
				const productInventory = this.getProductById(product.productId)
				const productName = productInventory?.productName || ''
				productNames[product.productId] = productName
			})
			const productComplete = this.transaction?.productToQtySold?.map( product => {
				return {
					...product, productName: productNames[product.productId], productTotal: (product.quantity * product.pricePerUnit)
				}
				
			})
			return productComplete
		}
	},

	methods: {
		...mapActions('transactions', ['removeTransaction']),

		deleteTransaction() {
			this.deleteDialogVisible = true
		},

		async confirmDelete() {
			const res = await this.removeTransaction(this.transaction.transactionId)
			this.deleteDialogVisible = false
			if(res !== 1) {
				this.errorDialogVisible = true
			}
			this.$emit('deleted')
		},

		editTransaction() {
			this.$emit('edit')
		}
	}


}

</script>