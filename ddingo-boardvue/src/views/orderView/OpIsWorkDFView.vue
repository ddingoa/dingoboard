<template>
  <v-form style="padding: 10px">
    <v-card>
      <v-card-title class="text-h5 font-weight-regular blue white--text">
        허브 별 검수 실적 등록
      </v-card-title>
      <v-card-text>
        <v-row>
          <v-col cols="12">
            <v-subheader class="pa-0">
              <h3 style="color: black"> 허브 구분 </h3>
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
          <v-col cols="4">
            <v-subheader class="pa-0">
              <h3 style="color: black"> 라인 구분 </h3>
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
          <v-col cols="4">
            <v-subheader class="pa-0">
              <h3 style="color: black"> 설비 구분 </h3>
            </v-subheader>
            <v-autocomplete
                v-model="eqpModel"
                :items="eqpModelStates"
                item-text="text"
                item-value="value"
                :readonly="false"
                return-object
                @click="eqpCobGetData"
            >
            </v-autocomplete>
          </v-col>

          <v-col cols="4">
            <v-subheader class="pa-0">
              <h3 style="color: black"> 작업 구분 </h3>
            </v-subheader>
            <v-autocomplete
                v-model="workModel"
                :items="workModelStates"
                item-text="text"
                item-value="value"
                :readonly="false"
                return-object
                @click="workCobGetData"
                @change="itemGetData"
            >
            </v-autocomplete>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>

    <div v-if="checkValue" style="padding-top: 10px">
      <opls-work-d-f-row-card
          v-bind:itemlist="itemlist"
          @FromChild="FromChildData"
      ></opls-work-d-f-row-card>
    </div>
  </v-form>
</template>

<script>


import OplsWorkDFRowCard from "@/components/orderComponents/OplsWorkDFRowCard";
import axios from "axios";
import store from "@/store";

export default {
  name: "OpIsWorkDFView",
  components: {OplsWorkDFRowCard},
  data: () => ({
    hubModel: [],
    hubStates: [],
    lineModel: [],
    lineModelStates: [],
    eqpModel: [],
    eqpModelStates: [],
    workModel: [],
    workModelStates: [],
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
              workCode: this.workModel.vlaue,
              workName: this.workModel.text,
              itemCode: this.childData[i].id,
              itemName: this.childData[i].name,
              workItemCount: this.childData[i].value,
              regCd: store.state.userName,
              modCd: store.state.userName
            }
        )
      }
      axios
          .post("/api/set/opworkDFitemcount", sedndata)
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
      this.workModel = [];
      this.workModelStates = [];
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
    },
    workCobGetData() {
      this.workModel = [];
      this.workModelStates = [];

      axios
          .get("/api/get/opswork/" + this.lineModel.vlaue)
          .then(response => {
            if (response.status == 200) {
              for (var i in response.data.data) {
                this.workModelStates.push({
                  text: response.data.data[i].workName,
                  vlaue: response.data.data[i].workCode
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
  },
};
</script>

<style scoped></style>
