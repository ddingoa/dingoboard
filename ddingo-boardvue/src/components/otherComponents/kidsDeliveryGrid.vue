<template>
  <v-form>
    <v-card>
      <v-card-title class="text-h5 font-weight-regular blue-grey white--text">
      </v-card-title>
    </v-card>

    <v-data-table
        :headers="headers"
        :items="desserts"
        :items-per-page="5"
        class="elevation-1"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-dialog v-model="dialogDelete" max-width="500px">
            <v-card>
              <v-card-title class="text-h5"
              >정말 삭제 하시겠습니까? [데이터베이스에서 완전 삭제 됩니다]
              </v-card-title>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="closeDelete"
                >Cancel
                </v-btn>
                <v-btn color="blue darken-1" text @click="deleteItemConfirm"
                >OK
                </v-btn>
                <v-spacer></v-spacer>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-toolbar>
      </template>
      <template v-slot:[`item.actions`]="{ item }">
        <v-icon small @click="deleteItem(item)">
          mdi-delete
        </v-icon>
      </template>

    </v-data-table>
  </v-form>
</template>

<script>
import kidsGrid from "@/dataset/otherColumn/kidsDeliberyGrid_Column_Json.json";
import axiosAP from "axios";
import store from "@/store";

export default {
  name: "kidsDeliveryGrid",
  data() {
    return {
      dialogDelete: false,
      headers: kidsGrid,
      desserts: [],
      Codedata: this.Codedata,
      Codename: this.Codename,
      editedItem: {
        id: "",
        deliveryCode: "",
        deliveryMd: "",
        address1: "",
        vendorCd: "",
        vendorNm: "",
        regCd: "",
        modCd: ""
      },
    }
  },
  props: {
    Codedata: String,
    Codename : String
  },
  created() {
    this.initialize();
  },
  watch: {
    Codedata() {
      this.Codedata = this.Codedata
      this.getHubItemList();
    },
    Codename() {
      this.Codename = this.Codename
    }
  },
  methods: {
    initialize() {

    },
    getHubItemList() {
      axiosAP.get("/api/get/kidsWmsSubModel/" + this.Codedata.toString())
          .then(response => {
            var resultData = response.data.data;
            this.desserts = [];
            for (var i in resultData) {
              this.desserts.push({
                id: response.data.data[i].id,
                deliveryCode: this.Codedata,
                deliveryMd: this.Codename,
                address1: response.data.data[i].address1,
                vendorCd: response.data.data[i].vendorCd,
                vendorNm: response.data.data[i].vendorNm,
                createdDate: response.data.data[i].createdDate,
                regCd: response.data.data[i].regCd,
              })

            }
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
    deleteItem(item) {
      this.editedIndex = this.desserts.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogDelete = true;
    },
    closeDelete() {
      this.dialogDelete = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },
    deleteItemConfirm() {
      this.editedItem.regCd = store.state.userName;
      this.editedItem.modCd = store.state.userName;

      axiosAP.post("/api/del/kidsWmsSubModel",this.editedItem)
          .then(response => {
            if (response.status == 200) {
              this.getHubItemList();
              this.closeDelete();
              alert(response.data.ResultValue);
            } else {
              alert(response.data.ResultValue);
            }
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
  },
}
</script>

<style scoped>

</style>