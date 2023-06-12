<template>
  <v-form  style="padding: 10px">
    <v-card>
      <v-card-title class="text-h5 font-weight-regular blue-grey white--text">
        허브 별 생산 작업
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
            @change="fabGetItemList"
        >
        </v-autocomplete>
      </v-card-text>
    </v-card>

    <v-data-table
        :headers="dessertHeaders"
        :items="desserts"
        :single-expand="singleExpand"
        :expanded.sync="expanded"
        item-key="itemCode"
        show-expand
        class="elevation-1"
    >
      <template v-slot:header="props">
        <thead>
        <tr>
          <th></th>
          <th></th>
          <th style="text-align: center; font-weight: bold"> 입고 </th>
          <th colspan="2" style="text-align: center; font-weight: bold"> 발주</th>
          <th colspan="2" style="text-align: center; font-weight: bold"> 생산</th>
          <th style="text-align: center; font-weight: bold"> 전일 잔여 생산</th>
          <th colspan="2" style="text-align: center; font-weight: bold"> 금일 잔여 생산</th>
        </tr>
        </thead>
      </template>
      <template v-slot:[`item.totalcountvalue`]="{ item }">
        <v-chip :color="getColor(item.totalcountvalue)" dark>
          {{ item.totalcountvalue }}
        </v-chip>
      </template>
      <template v-slot:expanded-item="{ headers, item }">
        <td :colspan="headers.length">
          <template>
            <v-card>
              <v-toolbar flat color="primary" dark>
                <v-toolbar-title>시간별 생산 시트</v-toolbar-title>
              </v-toolbar>
              <v-tabs vertical>
                <v-tab>
                  <v-icon left>
                    mdi-razor-single-edge
                  </v-icon>
                  세척
                </v-tab>
                <v-tab>
                  <v-icon left>
                    mdi-gesture-tap-button
                  </v-icon>
                  검수
                </v-tab>

                <v-tab-item>
                  <v-card flat>
                    Item :: {{ item.itemName }}
                    <os-sa-order-chart-card :chartDataValue="item"></os-sa-order-chart-card>
                  </v-card>
                </v-tab-item>
                <v-tab-item>
                  <v-card flat>
                    Item :: {{ item.itemName }}
                    <os-sa-order-grid-card-i-n :chartDFDataValue="item"></os-sa-order-grid-card-i-n>
                  </v-card>
                </v-tab-item>
              </v-tabs>
            </v-card>
          </template>
        </td>
      </template>
    </v-data-table>
  </v-form>
</template>

<script>


import OsSaOrderChartCard from "@/components/orderComponents/OsSaOrderChartCard";
import OsSaOrderGridCardIN from "@/components/orderComponents/OsSaOrderGridCardIN";
import OsSaOrderGridCard from "@/components/orderComponents/OsSaOrderGridCard";
import axios from "axios";
import axiosAP from "axios";

export default {
  name: "OpSaOrderView",
  components: {OsSaOrderChartCard, OsSaOrderGridCardIN, OsSaOrderGridCard},
  data: () => ({
    hubModel: [],
    hubStates: [],
    expanded: [],
    singleExpand: true,
    dessertHeaders: [
      {
        text: "제품",
        align: "start",
        sortable: false,
        value: "itemName"
      },
      {text: "입고 수량(ps)", value: "totalEnterCount",    "divider": true},
      {text: "발주 수량(Box)", value: "totalOrderCount"},
      {text: "발주 수량(ps)", value: "totalOrderPsCount",    "divider": true},
      {text: "세척 생산량(ps)", value: "totalWorkCount"},
      {text: "검수 생산량(ps)", value: "totalWorkDfCount",    "divider": true},
      {text: "전일 잔여 생산량(ps)", value: "yesterdayTotalCount",    "divider": true},
      {text: "남은 생산량(ps)", value: "nototalCount"},
      {text: "진척률(%)", value: "totalcountvalue"}
    ],
    desserts: []
  }),
  created() {
    this.initData();
  },
  mounted() {
    this.loading =  setInterval(this.fabGetItemList,5000)
  },
  destroyed() {
    clearInterval(this.loading)
  },
  methods: {
    initData() {
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
    getColor(totalcountvalue) {
      if (totalcountvalue < 30) return "red";
      else if (totalcountvalue < 80) return "orange";
      else return "green";
    },
    fabGetItemList() {
      if (this.hubModel.vlaue != null) {
        var value = this.hubModel.vlaue.toString();
        axiosAP.get("/api/get/workOrderEnterData/" + value)
            .then(response => {
              var totalWorkOrderData = response.data.totalWorkOrderData;
              var gridData = [];
              for (var i = 0; i < totalWorkOrderData.length; i++) {
                var workCount = totalWorkOrderData[i].totalWorkDfCount;
                var OrderCount = totalWorkOrderData[i].totalOrderPsCount + totalWorkOrderData[i].yesterdayTotalCount;

                var ratioWork = ((workCount/OrderCount) * 100).toFixed(2) == "NaN" ? "0.00" : ((workCount/OrderCount) * 100).toFixed(2)

                if (ratioWork == "Infinity") {
                  ratioWork = 100;
                }

                gridData.push({
                  "fabCode": this.hubModel.vlaue,
                  "itemName": totalWorkOrderData[i].goodsNm,
                  "itemCode": totalWorkOrderData[i].goodsCd,
                  "totalWorkCount": totalWorkOrderData[i].totalWorkCount,
                  "totalOrderCount": totalWorkOrderData[i].totalOrderCount,
                  "totalOrderPsCount": totalWorkOrderData[i].totalOrderPsCount,
                  "totalEnterCount": totalWorkOrderData[i].totalEnterCount,
                  "totalWorkDfCount": totalWorkOrderData[i].totalWorkDfCount,
                  "yesterdayTotalCount": totalWorkOrderData[i].yesterdayTotalCount >= 0 ?totalWorkOrderData[i].yesterdayTotalCount : "+" + Math.abs(totalWorkOrderData[i].yesterdayTotalCount),
                  "nototalCount": totalWorkOrderData[i].nototalCount,
                  "totalcountvalue": ratioWork
                })
              }
              this.desserts = gridData;
            })
            .catch(ex => {
              alert(ex.message);
            });
      }
    }
  }
};
</script>

<style scoped></style>
