<template>
  <v-form style="padding: 10px">
    <v-card>
      <v-card-title class="text-h5 font-weight-regular blue-grey white--text">
        허브 별 포장 작업
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
          <th colspan="2" style="text-align: center; font-weight: bold"> 발주</th>
          <th colspan="2" style="text-align: center; font-weight: bold"> 포장</th>
          <th colspan="2" style="text-align: center; font-weight: bold"> 잔여 포장</th>
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
                <v-toolbar-title>시간별 포장 시트(Box)</v-toolbar-title>
              </v-toolbar>
              <pk-work-order-chart-card  :chartDFDataValue="item">
              </pk-work-order-chart-card>
            </v-card>
          </template>
        </td>
      </template>
    </v-data-table>
  </v-form>
</template>

<script>
import axios from "axios";
import PkWorkOrderChartCard from "@/components/pkWorkComponents/PkWorkOrderChartCard";

export default {
  name: "PkOrderViewN",
  components: {PkWorkOrderChartCard},
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
      {text: "총 발주 수량(Box)", value: "totalOrderCount"},
      {text: "발주 수량(Ps)", value: "totalOrderPsCount",    "divider": true},
      {text: "포장 수량(Box)", value: "totalPkWorkCount"},
      {text: "포장 수량(ps)", value: "totalPkWorkPsCount",    "divider": true},
      {text: "남은 포장 수량(Box)", value: "nototalCount"},
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
        axios.get("/api/get/pkWorkOrderData/" + value)
            .then(response => {
              var totalWorkOrderData = response.data.totalWorkOrderData;
              var gridData = [];
              for (var i = 0; i < totalWorkOrderData.length; i++) {

                var ratioWork = (totalWorkOrderData[i].totalPkWorkCount / totalWorkOrderData[i].totalOrderCount * 100).toFixed(2) == "NaN" ? "0.00" : (totalWorkOrderData[i].totalPkWorkCount / totalWorkOrderData[i].totalOrderCount * 100).toFixed(2)

                if (ratioWork == "Infinity") {
                  ratioWork = 100;
                }

                gridData.push({
                  "fabCode": this.hubModel.vlaue,
                  "itemName": totalWorkOrderData[i].goodsNm,
                  "itemCode": totalWorkOrderData[i].goodsCd,
                  "totalOrderCount": totalWorkOrderData[i].totalOrderCount,
                  "totalOrderPsCount": totalWorkOrderData[i].totalOrderPsCount,
                  "totalPkWorkCount": totalWorkOrderData[i].totalPkWorkCount,
                  "totalPkWorkPsCount": totalWorkOrderData[i].totalPkWorkPsCount,
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
}
</script>

<style scoped>

</style>