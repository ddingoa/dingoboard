<template>
  <form style="padding: 10px">
    <v-card>
      <v-card-title class="text-h5 font-weight-regular cyan darken-4 white--text">
        입고 거래처명
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
          @click="initialize()"
        >
        </v-autocomplete>

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
          @change="event => productCobChange(event)"
        >
        </v-autocomplete>

        <v-subheader class="pa-0">
          작업중인 거래처를 선택해 주세요.
        </v-subheader>
        <v-autocomplete
          v-model="accountModel"
          :items="accountStates"
          item-text="text"
          item-value="value"
          :readonly="false"
          @change="event => accounCobChange(event)"
        >
        </v-autocomplete>
      </v-card-text>
    </v-card>

    <div style="padding-top: 60px">
      <h2>입고 품목 리스트</h2>
      <div>
        <v-data-table :headers="headers" :items="desserts">
          <template v-slot:[`item.enterItem`]="props">
            <v-edit-dialog
              :return-value.sync="props.item.enterItem"
              large
              persistent
              @save="save(props.item)"
              @cancel="cancel(props.item)"
              @open="open(props.item)"
              @close="close"
            >
              {{ props.item.enterItem }}
              <template v-slot:input>
                <v-text-field
                  v-model="props.item.enterItem"
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

          <template v-slot:[`item.dfCount`]="props">
            <v-edit-dialog
              :return-value.sync="props.item.dfCount"
              large
              persistent
              @save="save(props.item)"
              @cancel="cancel(props.item)"
              @open="open(props.item)"
              @close="close"
            >
              {{ props.item.dfCount }}
              <template v-slot:input>
                <v-text-field
                  v-model="props.item.dfCount"
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
                    :color="snackColor"
                    right>
          {{ snackText }}

          <template v-slot:action="{ attrs }">
            <v-btn v-bind="attrs" text @click="snack = false">
              Close
            </v-btn>
          </template>
        </v-snackbar>
      </div>

      <v-btn       color="info"
                   block @click="saveGridData()">입고등록 하기</v-btn>
    </div>
  </form>
</template>

<script>
import enterColumn from "../../dataset/order/enter_item_Column_Json.json";
import axios from "axios";
import store from "../../store";

export default {
  name: "EnterItemView",
  data: () => ({
    hubModel: [],
    hubStates: [],
    productModel: [],
    productStates: [ { text : "KIDS", value : "K0" },{ text : "BIZ", value : "B0" },{ text : "ECO", value : "E0" }],
    accountModel: [],
    accountStates: [],
    itmeTypes: [],
    snack: false,
    snackColor: "",
    snackText: "",
    max25chars: v => v.length <= 25 || "Input too long!",
    pagination: {},
    headers: enterColumn,
    desserts: []
  }),
  created() {
    this.initialize();
  },
  methods: {
    initialize() {
      this.accountModel =[];
      this.accountStates = [];
      this.desserts = [];
      this.productModel = [];
      this.productStates = [ { text : "KIDS", value : "K0" },{ text : "BIZ", value : "B0" },{ text : "ECO", value : "E0" }];
      this.itmeTypes = null;
      this.hubStates = [];
      this.hubModel=[];
      this.fabSetData();
    },
    save(item) {
      this.snack = true;
      this.snackColor = "success";
      this.snackText = '입력을 완료 하셨습니다!'
      if(item.enterItem == "" || item.dfCount == "") {
        item.enterItem != 0 ? item.enterItem : 0;
        item.dfCount != 0 ? item.dfCount : 0;
      }
    },
    cancel(item) {
      this.snack = true;
      this.snackColor = "error";
      this.snackText = '입력을 취소 하셨습니다!!'
      if(item.enterItem == "") {
        item.enterItem = 0
      }

      if(item.dfCount == "") {
        item.dfCount =  0;
      }
    },
    open(item) {
      this.snack = true;
      this.snackColor = "info";
      this.snackText = "수정 중입니다.";

      if(item.enterItem == 0) {
        item.enterItem = ""
      }

      if(item.dfCount == 0) {
        item.dfCount =  "";
      }

    },
    close() {
      console.log("Dialog closed");
    },
    accounCobChange(event) {
      var data = {
        hubcode : this.hubModel.vlaue,
        accountCode: event,
        username: store.state.userName
      };

      axios
        .post("/api/get/accountdatas", data)
        .then(response => {
          this.desserts = response.data.data;
        })
        .catch(ex => {
          alert(ex.message);
        });
    },
    fabSetData(){
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
    productCobChange(event) {
      axios
        .get("/api/get/accountCobList/" + this.productModel.value)
        .then(response => {
          this.accountStates = response.data.accountStates;
          this.itmeTypes = response.data.itmeTypes;
        })
        .catch(ex => {
          alert(ex.message);
        });
    },
    saveGridData() {
      var dataValueCheck = false;
      var sendData =[];
      for(var i=0; i<this.desserts.length; i++){
        if(this.desserts[i].enterItem != 0 || this.desserts[i].dfCount != 0 ){
          dataValueCheck = true
          sendData.push(this.desserts[i]);
        }
      }

      if(dataValueCheck) {
        var meenterdtoList = {
          meenteritemList: sendData,
          username: store.state.userName,
          enterCobDto: {
            text: this.hubModel.text,
            value: this.hubModel.vlaue
          }
        };
        axios
            .post("/api/set/entersavedata", meenterdtoList)
            .then(response => {
              alert(response.data.ResultValue);
              this.initialize();
            })
            .catch(ex => {
              alert(ex.message);
            });
      }else {
        alert("데이터를 확인 해주세요.")
      }
    }
  }
};
</script>

<style scoped></style>
