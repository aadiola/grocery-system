<template>
<div class="q-pa-md flex q-gutter-md">
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
	<div class="column">
		<TransactionsSummaryComponent
			v-if="Object.keys(selectedRow).length != 0"
			:transaction="selectedRow"
			@deleted="onDelete"
			@edit="onEdit"
			
		/>
		<q-table
		title="Transactions"
		:rows="getTransactionsWithTotal"
		:columns="columns"
		:visible-columns="visibleColumns"
		:pagination="pagination"
		row-key="transactionId"
		@row-click="onRowClick"
		class=""
		style="width: 45vw;"
	>
			<template v-slot:body-cell="props">
				<q-td :props="props" :class="{ 'clicked': props.row.transactionId === selectedRow.transactionId }">
				{{ props.value }}
				</q-td>
			</template>
		
		</q-table>
	</div>
	
	<div class="column" :style="'width: 40vw;'">
		<TransactionsAddEditComponent
		:transactionId="editRowTransactionId"
		:key="addEditComponentKey"
		@edit="onEdit"
			@added="onAdded"
			@edited="onAdded"
		/>
		
	</div>
	
	</div>

</template>



<script>
import moment from 'moment';
import { mapGetters, mapActions } from 'vuex';

import TransactionsSummaryComponent from 'src/components/TransactionsSummaryComponent.vue'
import TransactionsAddEditComponent from 'src/components/TransactionsAddEditComponent.vue'

const columns = [
	{ name: 'transactionId', label: 'ID', field: 'transactionId', align:'left', style: 'width: 5%', headerStyle: 'width: 5%', sortable: true },
	{ name: 'dateOfTransaction', label: 'Date of Transaction', field: 'dateOfTransaction', align: 'left', style: 'width: 25%; padding-right: 3em', headerStyle: 'width: 25%', sortable: true, format: (val, row) => val ? moment(val).format('MM-DD-YYYY') : '', align: 'center' },
	{ name: 'amountTendered', label: 'Amount Tendered', field: 'amountTendered', align: 'center', style: 'width: 10%', headerStyle: 'width: 10%' },
	{ name: 'discount', label: 'Discount', field: 'discount', align: 'center',style: 'padding-right: 1em'},
	{ name: 'total', label: 'Total', field: 'total', align: 'center',style: 'padding-right: 1em', headerClass: 'bold-header',  classes: 'text-bold'},
]

const visibleColumns = ['transactionId', 'dateOfTransaction', 'amountTendered', 'discount', 'total']

export default {

	components: {
		TransactionsSummaryComponent,
		TransactionsAddEditComponent
	},

	data () {
		return {
			columns,
			visibleColumns,
			defaultSortColumn:'productId',
			deleteDialogVisible: false,
			deleteItem: null,
			errorDialogVisible: false,
			pagination: {
				rowsPerPage: 10,
				sortBy: 'transactionId',
				descending: true,
			},
			selectedRow: {},
			editRowTransactionId: null,
			addEditComponentKey: 0
		}
	},

	computed: {
		...mapGetters('transactions', ['getAllTransactions']),

		getTransactionsWithTotal () {
		return this.getAllTransactions.map(transaction => {
			const total = transaction.productToQtySold.reduce(
				(acc, cur) => acc + ((cur.pricePerUnit * cur.quantity) - transaction.discount),
				0
			)
			return {...transaction, total: total}
		})
		}
	},

	methods: {

		...mapActions('inventory', ['removeProduct']),
		...mapActions('transactions', ['setTransactions']),

		deleteRow(row) {
			this.deleteDialogVisible = true
			this.deleteItem = row.productId
		},

		async confirmDelete() {
			const res = await this.removeProduct(this.deleteItem)
			this.deleteItem = null
			this.deleteDialogVisible = false
			if(res !== 1) {
				this.errorDialogVisible = true
			}
			
		},

		onRowClick(event, row, index){
			console.log(row)
			this.selectedRow = row
		},
		async onDelete() {
			await this.setTransactions()
			this.setupLatestTransaction()
		},

		onEdit () {
			this.editRowTransactionId = this.selectedRow.transactionId
			this.addEditComponentKey += 1 
		},

		onAdded () {
			this.setupLatestTransaction()
		},

		async setupLatestTransaction () {
			await this.setTransactions()
			this.addEditComponentKey += 1 
			this.editRowTransactionId = null
			this.selectedRow = this.getTransactionsWithTotal[this.getTransactionsWithTotal.length-1] || {}
		}
	},

	async created () {
		await this.setTransactions()
		this.setupLatestTransaction()
		
	}
}

</script>

<style scoped>

.q-td.clicked {
background-color: rgb(228,228,228); /* Change background color when row is clicked */
}
</style>