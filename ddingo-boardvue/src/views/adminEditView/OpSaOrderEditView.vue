<template>
  <v-form style="padding: 10px">
    <v-card>
      <v-card-title class="text-h5 font-weight-regular blue-grey white--text">
        당일 작업 실적 수정
      </v-card-title>
      <v-card-text>
<!--        <v-row>-->
<!--          <v-col cols="5">-->
<!--            <v-subheader class="pa-0">-->
<!--              시작 시간-->
<!--            </v-subheader>-->
<!--            <v-menu-->
<!--                ref="menu1"-->
<!--                v-model="menu1"-->
<!--                :close-on-content-click="false"-->
<!--                transition="scale-transition"-->
<!--                offset-y-->
<!--                max-width="290px"-->
<!--                min-width="auto"-->
<!--            >-->
<!--              <template v-slot:activator="{ on, attrs }">-->
<!--                <v-text-field-->
<!--                    v-model="frontDate"-->
<!--                    label="Start Date"-->
<!--                    hint="YYYY-MM-DD format"-->
<!--                    persistent-hint-->
<!--                    prepend-icon="mdi-calendar"-->
<!--                    v-bind="attrs"-->
<!--                    v-on="on"-->
<!--                ></v-text-field>-->
<!--              </template>-->
<!--              <v-date-picker-->
<!--                  v-model="frontDate"-->
<!--                  no-title-->
<!--                  locale="ko-ko"-->
<!--                  @input="menu1 = false"-->
<!--              ></v-date-picker>-->
<!--            </v-menu>-->
<!--          </v-col>-->
<!--          <v-col cols="1"> </v-col>-->
<!--          <v-col cols="5">-->
<!--            <v-subheader class="pa-0">-->
<!--              조건 시간-->
<!--            </v-subheader>-->
<!--            <v-menu-->
<!--                ref="menu2"-->
<!--                v-model="menu2"-->
<!--                :close-on-content-click="false"-->
<!--                transition="scale-transition"-->
<!--                offset-y-->
<!--                max-width="290px"-->
<!--                min-width="auto"-->
<!--            >-->
<!--              <template v-slot:activator="{ on, attrs }">-->
<!--                <v-text-field-->
<!--                    v-model="toDate"-->
<!--                    label="End Date"-->
<!--                    hint="YYYY-MM-DD format"-->
<!--                    persistent-hint-->
<!--                    prepend-icon="mdi-calendar"-->
<!--                    v-bind="attrs"-->
<!--                    v-on="on"-->
<!--                ></v-text-field>-->
<!--              </template>-->
<!--              <v-date-picker-->
<!--                  v-model="toDate"-->
<!--                  no-title-->
<!--                  locale="ko-ko"-->
<!--                  @input="menu2 = false"-->
<!--              ></v-date-picker>-->
<!--            </v-menu>-->
<!--          </v-col>-->
<!--          <v-col cols="1"> </v-col>-->
<!--        </v-row>-->
        <v-col cols="1"> </v-col>
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
            @click="fabGetCobData"
            @change="itemGetCobData"
        >
        </v-autocomplete>

        <v-subheader class="pa-0">
          아이템 구분
        </v-subheader>
        <v-autocomplete
            v-model="itemModel"
            :items="itemStates"
            item-text="text"
            item-value="value"
            :readonly="false"
            return-object
            @change="gridGetData"
        >
        </v-autocomplete>
      </v-card-text>
    </v-card>

    <div style="padding-top: 60px">
      <h2>당일 작업 실적 리스트</h2>
      <div>
        <v-data-table :headers="headers" :items="desserts">
          <template v-slot:[`item.workItemCount`]="props">
            <v-edit-dialog
                :return-value.sync="props.item.workItemCount"
                @save="save(props.item)"
                @cancel="cancel"
                @open="open"
                @close="close"
            >
              {{ props.item.workItemCount }}
              <template v-slot:input>
                <v-text-field
                    v-model="props.item.workItemCount"
                    :rules="[max25chars]"
                    label="Edit"
                    single-line
                    counter
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
import axios from "axios";
import axiosAP from "axios";
import adminEidtOrder from "@/dataset/adminEdit/OpSaOrderEdit_Column_Json.json";
import store from "@/store";
import momnet from "moment-timezone";

export default {
  name: "OpSaOrderEditView",
  data: () => ({
    frontDate: momnet(new Date(Date.now() - new Date().getTimezoneOffset() * 60000)).tz('Asia/Seoul')
        .toISOString()
        .substr(0, 10),
    toDate: momnet(new Date(Date.now() - new Date().getTimezoneOffset() * 60000)).tz('Asia/Seoul')
        .toISOString()
        .substr(0, 10),
    menu1: false,
    menu2: false,
    insDate:momnet(new Date(Date.now() - new Date().getTimezoneOffset() * 60000)).tz('Asia/Seoul')
        .toISOString()
        .substr(0, 10),
    hubModel : [],
    hubStates : [],
    itemModel : [],
    itemStates : [],
    checkValue : false,
    itmeTypes: [],
    snack: false,
    snackColor: "",
    snackText: "",
    max25chars: v => v.length <= 25 || "Input too long!",
    pagination: {},
    headers: adminEidtOrder,
    desserts: [],
    editItem : [],
  }),
  methods : {
    fabGetCobData() {
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
    itemGetCobData(){
      axiosAP.get("/api/get/producthubcodeitem/" + this.hubModel.vlaue)
          .then(response => {
            this.itemStates = [];
            this.itemModel = {};

            for (var i in response.data.data) {
              this.itemStates.push({
                text: response.data.data[i].goodsNm,
                vlaue: response.data.data[i].goodsCd
              });
            }
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
    gridGetData() {
      this.checkValue = true;
      var data = {
        fabCode : this.hubModel.vlaue,
        itemCode : this.itemModel.vlaue
      }

      axiosAP.post("/api/get/opworkitemcount", data)
          .then(response => {
            this.desserts = response.data.data;

          })
          .catch(ex => {
            alert(ex.message);
          });

    },
    save(item) {
      if(this.editItem.length == 0){
        this.editItem.push(item)
      }else {
        for (var i = 0; i < this.editItem.length; i++) {
          if (this.editItem[i].id != item.id) {
            this.editItem.push(item)
          } else {
            this.editItem[i] = item;
          }
        }
      }

      this.snack = true;
      this.snackColor = "success";
      this.snackText = "Data saved";
    },
    cancel() {
      this.snack = true;
      this.snackColor = "error";
      this.snackText = "Canceled";
    },
    open() {
      this.snack = true;
      this.snackColor = "info";
      this.snackText = "Dialog opened";
    },
    close() {

    },
    saveGridData () {
      var sedndata = [];
      var gridData = this.editItem;
      for(var i=0; i<gridData.length; i++){
        sedndata.push(
            {
              id :  gridData[i].id,
              opCode : gridData[i].opCode,
              fabCode : gridData[i].fabCode,
              fabName :  gridData[i].fabName,
              lineCode :  gridData[i].lineCode,
              lineName : gridData[i].lineName,
              eqpCode :  gridData[i].eqpCode,
              eqpName :  gridData[i].eqpName,
              itemCode :  gridData[i].itemCode,
              itemName :  gridData[i].itemName,
              inputTime : gridData[i].inputTime,
              workItemCount :  gridData[i].workItemCount,
              regCd : store.state.userName,
              modCd : store.state.userName
            }
        )
      }
      axios
          .post("/admin/up/opworkitemcount", sedndata)
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
  }
}
</script>

<style scoped>

</style>
