<template>
	<div class="relative q-my-md" style="width:45vw; height: 75vw;">
		<q-card style="height: 100%;">
		<q-card-section class="bg-secondary text-white flex column">
			<div class="row vertical-middle">
				<span class="text-h5 vertical-middle">{{transactionTitle}}</span>
			</div>
      </q-card-section>
	  <q-card-section>
	  <q-form @submit="handleSubmit" >
		<q-input
			  v-model="transactionForm.dateOfTransaction"
			  :type="'date'"
			  :align="'left'"
			  class=" full-width q-my-sm"
			  filled stack-label
			  :label="'Date of Transaction'"
			  :rules="[val => !!val]"
			  :lazy-rules="true"
			/>
		<span class="text-h6">Products</span>
		<div class="overflow-auto" style="max-height: 30vw;">


		<div
		  v-for="(product, index) in transactionForm.productToQtySold"
		  :key="index"
		  class="flex"
		>
		  <q-input
			v-model="product.productId"
			:label="'ID'"
			:id="'product_' + index"
			type="text"
			outlined
			style="width: 8%"
			disable
			stack-label
			dense
		  />
		  <q-select
		  	outlined
			dense 
			v-model="product.productName"
			use-input
			hide-selected
			fill-input
			class="q-ml-xs"
			input-debounce="0"
			:options="productOptions"
			option-label="productName"
			option-value="productId"
			@filter="filterProducts"
			style="width: 30%"
			@update:model-value="val => onProductSelect(index, val)"
			
		  >
		  <template v-slot:no-option>
          <q-item>
            <q-item-section class="text-grey">
              No results
            </q-item-section>
          </q-item>
		</template>
		</q-select>
		  <q-input
			v-model="product.pricePerUnit"
			label="Price"
			type="number"
			outlined
			style="width: 15%"
			stack-label
			class="q-ml-xs"
			disable
			dense
		  />
		  <q-input
			v-model="product.quantity"
			label="Quantity"
			type="number"
			outlined
			style="width: 15%"
			stack-label
			class="q-ml-xs"
			dense
			input-class="no-spinner-input"
			no-spinner
			:rules="[val => hasStock(product, val) || 'Greater than available stock', val => val > 0 || 'Quantity cannot be less than 1']"
		  />
		  <q-input
			:model-value="productTotal(product)"
			label="Total"
			type="number"
			outlined
			style="width: 15%"
			readonly
			stack-label
			class="q-ml-xs"
			dense
			/>
		  <q-btn @click="removeProduct(index)" icon="delete" :size="'xs'" class="q-ml-xs" outline color="negative" style="max-height: 40px;"/>
	  </div>
	</div>
		<q-btn @click="addProduct" label="Add Product" color="primary" class="q-my-sm"/>
		<div class="row q-my-md">
			<span class="col-2"> Amount Tendered: </span>
			<q-input
			v-model="transactionForm.amountTendered"
			label="Amount Tendered"
			type="number"
			outlined
			style="width: 150px"
			dense
			:rules="[val => !!val || 'Amount Tendered is required', val => val >= 0 || 'Amount Tendered cannot be less than zero']"
		  />

		</div>
		
		  <div class="row q-my-md">
			<span class="col-2"> Discount: </span>
		  <q-input
			v-model="transactionForm.discount"
			label="Discount"
			type="number"
			outlined
			style="width: 150px"
			dense
			:rules="[val => val >= 0 || 'Discount cannot be less than zero']"
		  />
		  </div>
		  
		  <div class="row q-my-md" >
			<span class="text-h6 col-2"> Total: </span>
			<div class="text-h3 col">{{ transactionTotal }}</div>
		  </div>
		<q-btn type="submit" :label="submitText" color="positive" />
	  </q-form>
	</q-card-section>
	</q-card>
	</div>
  </template>


<script>
import moment from 'moment';
import { mapGetters, mapActions } from 'vuex';

export default {
	
	props: {
		transactionId : {
			default: null,
		},

	},


	data () {
		return {
			mode: 0, // mode 0: add, mode 1: edit
			transactionForm: {
				dateOfTransaction: moment().format('YYYY-MM-DD'),
				productToQtySold: [{}],
				discount: 0,
				amountTendered: null

			},
			options: [
				'Google', 'Facebook', 'Twitter', 'Apple', 'Oracle'
			],
			productOptions: []
		}
	},

	computed: {
		...mapGetters('inventory', ['getAllProducts', 'getProductById']),
		...mapGetters('transactions', ['getTransactionById', 'getAllTransactions']),

		transactionTotal() {
			return this.transactionForm.productToQtySold.reduce((acc, val) => acc + (val.pricePerUnit * val.quantity), 0) - this.transactionForm.discount || ''
			// return 15
		},

		submitText () {
			return this.mode == 1 ? "Update Transaction" : "Create Transaction"
		},
		transactionTitle () {
			return this.mode == 1 ? `Update Transaction #${this.transactionId}` : 'Add New Transaction'
		}
	},

	watch: {

		transactionForm () {
			const productToQtySold = this.transactionForm.productToQtySold.map(productSale => productSale.productTotal = productTotal(productSale))
		}
	},

	methods: {
		...mapActions('transactions', ['addTransaction', 'updateTransaction']),

		addProduct() {
		this.transactionForm.productToQtySold.push({ productId: '', productName: '', pricePerUnit: '', quantity: ''});
		},
		removeProduct(index) {
			if(this.transactionForm.productToQtySold.length == 1) {
				return
			}
		this.transactionForm.productToQtySold.splice(index, 1);
		},
		productTotal(product) {
		if(!product.quantity){
			return null
		}
		return product.pricePerUnit * parseInt(product.quantity);
		},
		
		async handleSubmit() {
		// Handle form submission logic here
		// console.log('Form submitted:', this.products);
		if(this.mode ==0) {
				try{
				await this.addTransaction(this.transactionForm)
				this.$emit('added')
			} catch (e) {
				console.log(e)
			}
		} else {
			try{
				console.log({...this.transactionForm, transactionId: this.transactionId})
				await this.updateTransaction({...this.transactionForm, transactionId: this.transactionId})
				this.$emit('edited')
		} catch (e) {
			console.log(e)
		}
		}
		
		
		},
		filterProducts(search, update, abort) {
			update(() => {
				const needle = search.toLowerCase()
				this.productOptions = this.getAllProducts.filter(v => v.productName.toLowerCase().indexOf(needle) > -1)
			})
		},
		onProductSelect (index, val){
			this.transactionForm.productToQtySold[index].productName = val.productName
			this.transactionForm.productToQtySold[index].productId = val.productId
			this.transactionForm.productToQtySold[index].pricePerUnit = val.productPrice
			return val.productId

		},

		hasStock(product, val) {
			const productInventory = this.getAllProducts.find(prod => prod.productId == product.productId) 
			if(!productInventory) {
				return false
			}
			return productInventory.availableQuantity >= val
		}
  	},

	mounted () {
		if(this.transactionId != null) {
			this.mode = 1
			const transaction = this.getTransactionById(this.transactionId)
			if(!transaction) {
				this.mode = 0
				return
			}
			this.transactionForm.amountTendered = transaction.amountTendered
			this.transactionForm.dateOfTransaction = transaction.dateOfTransaction
			this.transactionForm.discount = transaction.discount
			this.transactionForm.productToQtySold = []
			transaction.productToQtySold.forEach(element => {
				this.transactionForm.productToQtySold.push(
					{...element, productName: this.getProductById(element.productId)?.productName}
				)
			});
			console.log(this.transactionForm)
		}
	}


}




</script>

<style scoped>
</style>