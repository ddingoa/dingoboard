<template>
  <v-form style="padding: 10px">
    <v-card>
      <v-card-title class="text-h5 font-weight-regular cyan darken-4 white--text">
        고객사별 입고 & 미 수거 현황
      </v-card-title>
      <v-card-text>
        <v-row>
          <v-col cols="5">
            <v-subheader class="pa-0">
              시작 시간
            </v-subheader>
            <v-menu
                ref="menu1"
                v-model="menu1"
                :close-on-content-click="false"
                transition="scale-transition"
                offset-y
                max-width="290px"
                min-width="auto"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-text-field
                    v-model="frontDate"
                    label="Start Date"
                    hint="YYYY-MM-DD format"
                    persistent-hint
                    prepend-icon="mdi-calendar"
                    v-bind="attrs"
                    v-on="on"
                ></v-text-field>
              </template>
              <v-date-picker
                  v-model="frontDate"
                  no-title
                  locale="ko-ko"
                  @input="menu1 = false"
              ></v-date-picker>
            </v-menu>
          </v-col>
          <v-col cols="1"></v-col>
          <v-col cols="5">
            <v-subheader class="pa-0">
              조건 시간
            </v-subheader>
            <v-menu
                ref="menu2"
                v-model="menu2"
                :close-on-content-click="false"
                transition="scale-transition"
                offset-y
                max-width="290px"
                min-width="auto"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-text-field
                    v-model="toDate"
                    label="End Date"
                    hint="YYYY-MM-DD format"
                    persistent-hint
                    prepend-icon="mdi-calendar"
                    v-bind="attrs"
                    v-on="on"
                ></v-text-field>
              </template>
              <v-date-picker
                  v-model="toDate"
                  no-title
                  locale="ko-ko"
                  @input="menu2 = false"
              ></v-date-picker>
            </v-menu>
          </v-col>
          <v-col cols="1"></v-col>
        </v-row>
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
            @click="formSet"
        >
        </v-autocomplete>
        <v-subheader class="pa-0">
          제품 구분
        </v-subheader>
        <v-autocomplete
            v-model="productModel"
            :items="productStates"
            item-text="text"
            item-value="value"
            :readonly="false"
            return-object
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

    <v-card v-if="checkValue">
      <v-card-title>
        현황 리스트
        <v-spacer></v-spacer>
        <v-text-field
            v-model="search"
            append-icon="mdi-magnify"
            label="Search"
            single-line
            hide-details
        ></v-text-field>
      </v-card-title>
      <v-data-table
          :headers="headers"
          :items="desserts"
          :search="search"
      >
      </v-data-table>
    </v-card>

    <v-card v-if="checkValue" style="margin-top: 15px">
      <h2>입고 현황 차트</h2>
      <OsNonEnterItemChartCard :chartDataValue="chartData" :hub-model="hubModel"></OsNonEnterItemChartCard>
    </v-card>

    <v-card v-if="checkValue" style="margin-top: 15px">
      <h2>불량 입고 현황 차트</h2>
      <OsNonItemChartCard :chartDataValue="chartData" :hub-model="hubModel"></OsNonItemChartCard>
    </v-card>

  </v-form>
</template>

<script>
import axios from "axios";
import nonenteritem from "../../dataset/order/non_enter_item_Column_Json.json";
import OsNonEnterItemChartCard from "@/components/orderComponents/OsNonEnterItemChartCard";
import store from "@/store";
import OsNonItemChartCard from "@/components/orderComponents/OsNonItemChartCard";

export default {
  name: "NonEnterItemView",
  components: {OsNonItemChartCard, OsNonEnterItemChartCard},
  data: () => ({
    frontDate: new Date(Date.now() - new Date().getTimezoneOffset() * 60000)
        .toISOString()
        .substr(0, 10),
    toDate: new Date(Date.now() - new Date().getTimezoneOffset() * 60000)
        .toISOString()
        .substr(0, 10),
    menu1: false,
    menu2: false,
    insDate: new Date(Date.now() - new Date().getTimezoneOffset() * 60000)
        .toISOString()
        .substr(0, 10),
    insMenu: false,
    hubModel: [],
    hubStates: [],
    accountModel: [],
    accountStates: [],
    productModel: [],
    productStates: [ { text : "KIDS", value : "K0" },{ text : "BIZ", value : "B0" },{ text : "ECO", value : "E0" }],
    search: "",
    headers: [],
    desserts: [],
    checkValue: false,
    setColumn: ["미수거", "입고", "입고불량"],
    defColumn: {},
    setColumnValue: [],
    chartData: []
  }),
  created() {
    this.initialize();
  },
  methods: {
    formSet() {
      this.accountModel = [];
      this.accountStates = [];
      this.productModel = [];
      this.productStates = [ { text : "KIDS", value : "K0" },{ text : "BIZ", value : "B0" },{ text : "ECO", value : "E0" }];
      this.search = "";
      this.desserts = [];
      this.checkValue = false;
      this.setColumn = ["미수거", "입고", "입고불량"];
      this.defColumn = {};
      this.setColumnValue = [];
      this.chartData = [];
    },
    initialize() {
      this.fabSetData();
    },
    allDataList() {
      axios
          .get("/api/get/nonenteritem")
          .then(response => {
            this.desserts = response.data.accountStates;
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
    groups() {
      return groupBy(this.list, 'groupName')
    },
    productCobChange(event) {
      if (event.toString() == "ALL") {
        // this.allDataList();
      } else {
        axios
            .get("/api/get/accountCobList/" + this.productModel.value)
            .then(response => {
              this.accountStates = response.data.accountStates;
              this.itmeTypes = response.data.itmeTypes;
            })
            .catch(ex => {
              alert(ex.message);
            });
      }
    },
    accounCobChange(event) {
      var data = {
        bucode: this.productModel.value,
        fabcode: this.hubModel.vlaue,
        accountcode: event.toString(),
        startdata: this.frontDate,
        enddata: this.toDate
      }
      axios
          .post("/api/get/nonenteritem", data)
          .then(response => {
            var resultData = response.data.accountStates;
            this.chartData =  response.data.accountStates;
            this.desserts = resultData;
            resultData = groupBy(resultData, "workStart")
            this.setColumnValue = [];
            for (var key in resultData) {
              for (var j = 0; j < this.setColumn.length; j++) {
                var Column = {}
                // this.$set(Column,  key+j, key + "_" + this.setColumn[j])
                if(j === this.setColumn.length -1){
                  this.setColumnValue.push({
                        "text": key + "_" + this.setColumn[j],
                        "value": key + "_" + this.setColumn[j],
                        "width": "200px",
                        "divider": true
                      }
                  )
                }else {
                  this.setColumnValue.push({
                        "text": key + "_" + this.setColumn[j],
                        "value": key + "_" + this.setColumn[j],
                        "width": "200px"
                      }
                  )
                }

              }
            }

            var dfheaders = [{
              "text": "거래처 코드",
              "value": "vendorCd",
              "width": "150px",
              "align": " d-none"
            },
              {
                "text": "거래처명",
                "value": "vendorNm",
                "width": "200px"
              },
              {
                "text": "사업부",
                "value": "divisionCd",
                "width": "200px",
                "align": " d-none"
              },
              {
                "text": "품목",
                "value": "itemName",
                "width": "200px",
                "divider": true
              }]
            const arr4 = dfheaders.concat(this.setColumnValue);
            this.headers = arr4;
            this.desserts = [];
            var resultitemData = groupBy(response.data.accountStates, "itemCode")

            for (var key in resultData) {
              for (var i = 0; i < resultData[key].length; i++) {
                this.defColumn = {
                  vendorCd: resultData[key][i].vendorCd,
                  vendorNm: resultData[key][i].vendorNm,
                  divisionCd: resultData[key][i].divisionCd,
                  itemName: resultData[key][i].itemName
                }
                for (var j = 0; j < this.setColumn.length; j++) {
                  var editValue = {};
                  if (this.setColumn[j] == "미수거") {
                    this.$set(editValue, key + "_" + this.setColumn[j], resultData[key][i].nonitemcount)
                    this.defColumn = Object.assign(
                        {},
                        this.defColumn,
                        editValue
                    )
                  } else if (this.setColumn[j] == "입고") {
                    this.$set(editValue, key + "_" + this.setColumn[j], resultData[key][i].enterItem)
                    this.defColumn = Object.assign(
                        {},
                        this.defColumn,
                        editValue
                    )
                  } else if (this.setColumn[j] == "입고불량") {
                    this.$set(editValue, key + "_" + this.setColumn[j], resultData[key][i].dfCount)
                    this.defColumn = Object.assign(
                        {},
                        this.defColumn,
                        editValue
                    )
                  }
                }

                var checkValue = true;
                for (var z = 0; z < this.desserts.length; z++) {
                  if (this.desserts[z].itemName == this.defColumn.itemName) {
                    this.desserts[z] = Object.assign(
                        {},
                        this.desserts[z],
                        this.defColumn
                    )
                    checkValue = false;
                  }
                }
                if (checkValue) {
                  this.desserts.push(this.defColumn)
                }
              }
            }
            this.checkValue = true;
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
    fabSetData() {
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
  }
};

function groupBy(array, key) {
  const result = {}
  array.forEach(item => {
    if (!result[item[key]]) {
      result[item[key]] = []
    }
    result[item[key]].push(item)
  })
  return result
}
</script>

<style scoped></style>
