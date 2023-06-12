<template>
  <v-form>
    <v-card>
      <v-card-title class="text-h5 font-weight-regular blue-grey white--text">
        {{ hubName}}
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
import productitem from "../../dataset/mastColumn/productItemHub_Column_Json.json"
import axiosAP from "axios";
import store from "@/store";

export default {
  name: "fabItemGrid",
  data () {
    return {
      dialogDelete: false,
      headers: productitem,
      desserts: [
      ],
      hubCode : this.hubCodedata,
      hubName : this.hubNameData,
      editedItem: {
        id: "",
        fabCode: "",
        fabName: "",
        itemCode: "",
        itemName: "",
        business: "",
        itemType: "",
        texture: "",
        weight: "",
        packingQuantity: "",
        plasticWrap: "",
        regCd: "",
        modCd: ""
      },
    }
  },
  props: {
    hubCodedata : String,
    hubNameData : String
  },
  created() {
    this.initialize();
  },
  watch : {
    hubCodedata (){
      this.hubCode = this.hubCodedata
      this.hubName = this.hubNameData
      this.getHubItemList();
    }
  },
  methods: {
    initialize() {
      // if(this.hubCode != null) {
      //   axiosAP.get("/api/get/producthubitem/" + this.hubCode)
      //       .then(response => {
      //         this.desserts = response.data.data
      //       })
      //       .catch(ex => {
      //         alert(ex.message);
      //       });
      // }
    },
    getHubItemList () {
      axiosAP.get("/api/get/producthubcodeitem/" + this.hubCode.toString())
          .then(response => {
            this.desserts = response.data.data
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
    deleteItem(item){
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

      axiosAP.delete("/api/del/producthubitem/" + this.editedItem.id.toString())
          .then(response => {
            if (response.status == 200) {
              this.desserts.splice(this.editedIndex, 1);
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
