<template>
 <div class="q-pa-md flex column">
	<q-btn color="positive" label="Add Product" class="q-my-md row q-ml-auto" @click="this.$router.push({name: 'addInventory'})"/>
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
    <q-table
      title="Inventory"
      :rows="getAllProducts"
      :columns="columns"
	  :visible-columns="visibleColumns"
	  :pagination="pagination"
      row-key="productId"
    >
	<template v-slot:body-cell-actions="props">
        <q-td :props="props" class="q-gutter-sm">
			<q-btn @click="editRow(props.row)" color="secondary" label="Edit" dense />
          <q-btn @click="deleteRow(props.row)" color="negative" label="Delete" dense />
        </q-td>
      </template>
 </q-table>
  </div>

</template>



<script>
import moment from 'moment';
import { mapGetters, mapActions } from 'vuex';

const columns = [
	{ name: 'productId', label: 'ID', field: 'productId', align:'left', style: 'width: 5%', headerStyle: 'width: 5%', sortable: true },
	{ name: 'productName', label: 'Product Name', field: 'productName', align: 'left', style: 'width: 25%', headerStyle: 'width: 25%', sortable: true  },
	{ name: 'productPrice', label: 'Product Price', field: 'productPrice', align: 'center', style: 'width: 10%', headerStyle: 'width: 10%', sortable: true  },
	{ name: 'availableQuantity', label: 'Available Quantity', field: 'availableQuantity', align: 'center', sortable: true  },
	{ name: 'expirationDate', label: 'Expiration Date', field: 'expirationDate', format: (val, row) => val ? moment(val).format('MM-DD-YYYY') : '', align: 'center' , sortable: true  },
	{ name: 'unit', label: 'Unit', field: 'unit' },
	{ name: 'remarks', label: 'Remarks', field: 'remarks', align: 'right'  },
	{ name: 'archived', label: 'Archived', field: 'archived' },
	{ name: 'actions', label: '', field: 'actions' },
]

const visibleColumns = ['productId', 'productName', 'productPrice', 'availableQuantity', 'expirationDate', 'unit','remarks', 'actions']

export default {

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
				sortBy: 'productId',
				descending: false,
			}
		}
	},

	computed: {
		...mapGetters('inventory', ['getAllProducts'])
	},

	methods: {

		...mapActions('inventory', ['removeProduct']),

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

		editRow(row){
			this.$router.push({name: 'editInventory', query: {productId: row.productId}})
		}
	}
}

</script>

<style scoped>

.inventory-table tr > *:nth-child(1) {
  color: red;
  width: 6rem;
}
</style>