<template>
  <v-form style="padding: 10px">
    <v-card>
      <v-card-title class="text-h5 font-weight-regular blue-grey white--text">
        재고 현황
      </v-card-title>
      <v-card-text>
        <v-subheader class="pa-0">
          사업부 구분
        </v-subheader>
        <v-autocomplete
            v-model="productModel"
            :items="productStates"
            item-text="text"
            item-value="value"
            return-object
            :readonly="false"
            @change="productCobChange"
        >
        </v-autocomplete>
      </v-card-text>
    </v-card>


    <div style="padding-top: 60px">
      <h2>재고 제품 리스트</h2>
      <div>
        <v-data-table :headers="headers" :items="desserts"
                      item-key="itemCode"
                      :search="search"
                      class="elevation-1"
        >
          <template v-slot:header="props">
            <thead>
            <tr>
              <th></th>
              <th></th>
              <th></th>
              <th colspan="3" style="text-align: center; font-weight: bold"> 제공 재고</th>
              <th colspan="2" style="text-align: center; font-weight: bold"> 안전 재고</th>
              <th colspan="3" style="text-align: center; font-weight: bold"> 창고 재고</th>
            </tr>
            </thead>
          </template>

          <template v-slot:top>
            <v-toolbar flat>
              <v-toolbar-title>상품 마스터</v-toolbar-title>
              <v-spacer></v-spacer>
              <v-text-field
                  v-model="search"
                  append-icon="mdi-magnify"
                  label="Search"
                  single-line
                  hide-details
              ></v-text-field>
            </v-toolbar>
          </template>

          <template v-slot:[`item.itemPsCount`]="props">
            <v-edit-dialog
                :return-value.sync="props.item.itemPsCount"
                large
                persistent
                @save="save"
                @cancel="cancel"
                @open="open"
                @close="close"
            >
              {{ props.item.itemPsCount }}
              <template v-slot:input>
                <v-text-field
                    v-model="props.item.itemPsCount"
                    :rules="[max25chars]"
                    label="Edit"
                    type="number"
                    single-line
                    counter
                    autofocus
                ></v-text-field>
              </template>
            </v-edit-dialog>
          </template>

          <template v-slot:[`item.itemDfPsCount`]="props">
            <v-edit-dialog
                :return-value.sync="props.item.itemDfPsCount"
                large
                persistent
                @save="save"
                @cancel="cancel"
                @open="open"
                @close="close"
            >
              {{ props.item.itemDfPsCount }}
              <template v-slot:input>
                <v-text-field
                    v-model="props.item.itemDfPsCount"
                    :rules="[max25chars]"
                    label="Edit"
                    type="number"
                    single-line
                    counter
                    autofocus
                ></v-text-field>
              </template>
            </v-edit-dialog>
          </template>

        </v-data-table>

        <v-snackbar v-model="snack"
                    :timeout="3000"
                    :color="snackColor">
          {{ snackText }}

          <template v-slot:action="{ attrs }">
            <v-btn v-bind="attrs" text @click="snack = false">
              Close
            </v-btn>
          </template>
        </v-snackbar>
      </div>

      <div style="float:right; margin-top: 20px;margin-bottom: 10px">
        <v-btn class="mr-4 pa-1" @click="saveGridData()">Save</v-btn>
      </div>
    </div>
  </v-form>
</template>

<script>
import inbentoryColumn from "@/dataset/mastColumn/itemInventory_Column_Json.json";
import axios from "axios";
import store from "../../store";

export default {
  name: "ItemInventoryView",
  data: () => ({
    search: "",
    productModel: [],
    productStates: [{text: "KIDS", value: "K0"},
      {text: "BIZ", value: "B0"},
      {text: "ECO", value: "E0"}],
    itmeTypes: [],
    snack: false,
    snackColor: "",
    snackText: "",
    max25chars: v => v.length <= 25 || "Input too long!",
    pagination: {},
    headers: inbentoryColumn,
    desserts: [],
    status: "",
  }),
  mounted() {
    this.loading = setInterval(this.productCobChange, 5000)
  },
  destroyed() {
    clearInterval(this.loading)
  },
  methods: {
    productCobChange() {
      if (this.productModel != null) {
        axios
            .get("/api/get/iteminventory/" + this.productModel.value)
            .then(response => {
              this.desserts = [];
              var resultData = response.data.data;
              for (var i = 0; i < resultData.length; i++) {
                this.desserts.push({
                  "itemCode": resultData[i].goodsCd,
                  "itemName": resultData[i].goodsNm,
                  "business": resultData[i].divisionCd,
                  "wrokTotalItemPsCount": resultData[i].wrokTotalItemPsCount,
                  "pakingTotalItemPsCount": resultData[i].pakingTotalItemPsCount,
                  "orderTotalItemPsCount": resultData[i].orderTotalItemPsCount,
                  "saftItemCoun": resultData[i].saftItemCoun,
                  "saftItemPsCount": resultData[i].saftItemPsCount,
                  "itemCount": resultData[i].itemCount,
                  "itemPsCount": resultData[i].itemPsCount,
                  "itemDfPsCount": resultData[i].itemDfPsCount
                })
              }
            })
            .catch(ex => {
              alert(ex.message);
            });
      }
    },
    saveGridData() {
      var saveData = [];

      for (var i = 0; i < this.desserts.length; i++) {
        saveData.push(
            {
              id: this.desserts[i].id,
              business: this.desserts[i].business,
              itemCode: this.desserts[i].itemCode,
              itemName: this.desserts[i].itemName,
              itemCount: this.desserts[i].itemCount,
              itemPsCount: this.desserts[i].itemPsCount,
              itemDfPsCount: this.desserts[i].itemDfPsCount,
              note: this.desserts[i].note,
              active: this.desserts[i].active,
              regCd: store.state.userName,
              modCd: store.state.userName,
            }
        )
      }

      axios
          .post("/api/set/iteminventory", saveData)
          .then(response => {
            if (response.status == 200) {
              alert(response.data.ResultValue);
            } else {
              alert(response.data.ResultValue);
            }
            this.loading = setInterval(this.productCobChange, 5000)
          })
          .catch(ex => {
            alert(ex.message);
          });

    },
    save() {
      this.snack = true;
      this.snackColor = "success";
      this.snackText = "임시 저장 성공";
    },
    cancel() {
      this.snack = true;
      this.snackColor = "error";
      this.snackText = "취소 하였습니다.";
      this.loading = setInterval(this.productCobChange, 5000)
    },
    open() {
      this.snack = true;
      this.snackColor = "info";
      this.snackText = "입력 모드 입니다.";
      clearInterval(this.loading)
    },
    close() {
      console.log("Dialog closed");
    },
  }
}
</script>

<style scoped>

</style>
