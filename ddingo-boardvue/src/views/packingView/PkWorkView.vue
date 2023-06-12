<template>
  <v-form style="padding: 10px">
    <v-card>
      <v-card-title class="text-h5 font-weight-regular blue-grey white--text">
        허브 별 포장 실적 등록
      </v-card-title>
      <v-card-text>
        <v-subheader class="pa-0">
          허브 구분
        </v-subheader>
        <v-autocomplete
            v-model="hubModel"
            :items="hubStates"
            item-text="text"
            item-value="value"
            :readonly="false"
            return-object
            @change="orderData"
            @click="initData"
        >
        </v-autocomplete>
      </v-card-text>
    </v-card>


    <h2>포장 리스트</h2>
    <v-data-table :headers="headers" :items="desserts"
                  class="overflow-x-auto"
    >
      <template v-slot:[`item.actions`]="{ item }">
        <v-btn
            :loading="loading3"
            :disabled="item.disable"
            color="blue-grey"
            class="ma-2 white--text"
            @click="btnClickAdd(item)"
        >
          포장 완료
          <v-icon right dark>
            mdi-cube
          </v-icon>
        </v-btn>
      </template>
    </v-data-table>

    <v-dialog v-model="dialogPking" max-width="500px">
      <v-card>
        <v-card-title class="text-h5"
        >포장 완료 처리를 하시겠습니까?!
        </v-card-title>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="closepk">Cancel</v-btn>
          <v-btn color="blue darken-1" text @click="pkdialogsave">OK</v-btn>
          <v-spacer></v-spacer>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-form>
</template>

<script>
import pkworkcolumn from "../../dataset/pkorder/pkwork_Colun_Json.json"
import axios from "axios";
import store from "@/store";

export default {
  name: "PkWorkView",
  data: () => ({
    loader: null,
    loading3: false,
    dialogPking: false,
    hubModel: [],
    hubStates: [],
    headers: pkworkcolumn,
    desserts: [],
  }),
  watch: {
    loader() {
      const l = this.loader;
      this[l] = !this[l];

      setTimeout(() => (this[l] = false), 3000);

      this.loader = null;
    },
    dialogPking(val) {
      val || this.dialogClose();
    }
  },
  created() {
    this.initData()
  },
  methods: {
    dialogClose() {
      this.dialogPking = false;
    },
    initData() {
      this.hubModel = [];
      this.hubStates = [];
      this.desserts = [];
      axios
          .get("/api/get/fabmast")
          .then(response => {
            this.hubStates = [];
            this.hubModel = {};

            for (var i in response.data.data) {
              this.hubStates.push({
                text: response.data.data[i].fabName,
                vlaue: response.data.data[i].fabCode
              });
            }
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
    btnClickAdd(item) {
      this.loader = "loading3";
      this.editedIndex = this.desserts.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogPking = true;
    },
    closepk() {
      this.dialogPking = false;
    },
    pkdialogsave() {
      this.desserts[this.editedIndex].disable = true;
      this.dialogPking = false;

      var sendData = {
        pkoCode: this.desserts[this.editedIndex].orderNo,
        fabCode: this.desserts[this.editedIndex].fabCode,
        fabName: this.desserts[this.editedIndex].fabName,
        workStart: "",
        btDepartment: this.desserts[this.editedIndex].divisionCd,
        accountCode: this.desserts[this.editedIndex].vendorCd,
        accountName: this.desserts[this.editedIndex].vendorNm,
        itmeCode: this.desserts[this.editedIndex].goodsCd,
        itemName: this.desserts[this.editedIndex].goodsNm,
        itemCount: this.desserts[this.editedIndex].eaQty,
        pkItemCount: this.desserts[this.editedIndex].orderQty,
        holidaysApply: "",
        pkingCheck: true,
        active: true,
        regCd: store.state.userName,
        modCd: store.state.userName
      }

      axios
          .post("/api/set/pkorderdata", sendData)
          .then(response => {
            if (response.status == 200) {
              alert(response.data.ResultValue);
            } else {
              alert(response.data.ResultValue);
            }
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
    orderData() {
      axios
          .get("/api/get/pkorderdata/" + this.hubModel.vlaue)
          .then(response => {
            this.desserts = [];
            var getGridData = response.data.data;
            this.desserts = getGridData;
            for (var i = 0; i < getGridData.length; i++) {
              if (getGridData[i].pkingCheck) {
                this.desserts[i].disable = true;
              }
            }
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
  }
};
</script>

<style scoped>
.x-scroll-disabled {
  overflow-x: hidden;
}
</style>
