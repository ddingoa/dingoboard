<template>
  <v-form style="padding: 10px">
    <v-card>
      <v-card-title class="text-h5 font-weight-regular blue-grey white--text">
        허브 별 생산 실적 등록
      </v-card-title>
      <v-card-text>
        <v-row>
          <v-col cols="12">
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
                @click="fabSetting"
            >
            </v-autocomplete>
          </v-col>
          <v-col cols="6">
            <v-subheader class="pa-0">
              라인 구분
            </v-subheader>
            <v-autocomplete
                v-model="lineModel"
                :items="lineModelStates"
                item-text="text"
                item-value="value"
                :readonly="false"
                return-object
                @click="lineCobGetData"
            >
            </v-autocomplete>
          </v-col>
          <v-col cols="6">
            <v-subheader class="pa-0">
              설비 구분
            </v-subheader>
            <v-autocomplete
                v-model="eqpModel"
                :items="eqpModelStates"
                item-text="text"
                item-value="value"
                :readonly="false"
                return-object
                @click="eqpCobGetData"
                @change="itemGetData"
            >
            </v-autocomplete>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>

    <div v-if="checkValue" style="padding-top: 10px">

      <v-tabs>
        <v-tab>EA</v-tab>
        <v-tab>KG</v-tab>
        <v-tab-item>
          <op-sa-order-work-row-card
              v-bind:itemlist="itemlist"
              @FromChild="FromChildData"
          ></op-sa-order-work-row-card>
        </v-tab-item>
        <v-tab-item>
          <OsSaOrderWorkRowKGCard
              v-bind:itemlist="itemlist"
              @FromChild="FromChildData">
          </OsSaOrderWorkRowKGCard>
        </v-tab-item>
      </v-tabs>
    </div>
  </v-form>
</template>

<script>

import OpSaOrderWorkRowCard from "@/components/orderComponents/OpSaOrderWorkRowCard";
import axios from "axios";
import store from "@/store";
import OsSaOrderWorkRowKGCard from "@/components/orderComponents/OsSaOrderWorkRowKGCard";

export default {
  name: "OpSaOrderWorkView",
  components: {OsSaOrderWorkRowKGCard, OpSaOrderWorkRowCard},
  data: () => ({
    hubModel: [],
    hubStates: [],
    lineModel: [],
    lineModelStates: [],
    eqpModel: [],
    eqpModelStates: [],
    itemlist: [],
    checkValue: false,
    childData: [],
  }),
  created() {
    this.initMethod();
  },
  methods: {
    FromChildData(data) {
      this.childData = data
      var sedndata = [];
      for (var i = 0; i < this.childData.length; i++) {
        sedndata.push(
            {
              fabCode: this.hubModel.vlaue,
              fabName: this.hubModel.text,
              lineCode: this.lineModel.vlaue,
              lineName: this.lineModel.text,
              eqpCode: this.eqpModel.vlaue,
              eqpName: this.eqpModel.text,
              itemCode: this.childData[i].id,
              itemName: this.childData[i].name,
              workItemCount: this.childData[i].value,
              regCd: store.state.userName,
              modCd: store.state.userName
            }
        )
      }
      axios
          .post("/api/set/opworkitemcount", sedndata)
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
    initMethod() {
      axios
          .get("/api/get/fabmast")
          .then(response => {
            this.hubStates = [];
            this.hubModel = [];

            for (var i in response.data.data) {
              this.hubStates.push({
                text: response.data.data[i].fabName,
                vlaue: response.data.data[i].fabCode
              });
            }

            this.btnNewCheck = false;
            this.desserts = [];
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
    fabSetting() {
      this.hubModel = [];
      this.hubStates = [];
      this.lineModel = [];
      this.lineModelStates = [];
      this.eqpModel = [];
      this.eqpModelStates = [];
      this.itemlist = [];
      this.checkValue = false;
      this.initMethod();
    },
    lineCobGetData(event) {
      axios
          .get("/api/get/linemast/" + this.hubModel.vlaue)
          .then(response => {
            this.lineModelStates = [];
            this.lineModel = [];

            for (var i in response.data.data) {
              this.lineModelStates.push({
                text: response.data.data[i].lineName,
                vlaue: response.data.data[i].lineCode
              });
            }
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
    eqpCobGetData(event) {
      this.eqpModelStates = [];
      this.eqpModel = [];
      axios
          .get("/api/get/eqpmastline/" + this.lineModel.vlaue)
          .then(response => {
            if (response.status == 200) {
              for (var i in response.data.data) {
                this.eqpModelStates.push({
                  text: response.data.data[i].eqpName,
                  vlaue: response.data.data[i].eqpCode
                });
              }
            } else {
              alert(response.data.ResultValue);
            }
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
    itemGetData() {
      axios
          .get("/api/get/producthubcodeitem/" + this.hubModel.vlaue)
          .then(response => {
            if (response.status == 200) {
              this.itemlist = response.data.data;
              this.checkValue = true;
            } else {
              alert(response.data.ResultValue);
            }
          })
          .catch(ex => {
            alert(ex.message);
          });
    }
  },
};
</script>

<style scoped></style>
