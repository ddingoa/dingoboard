<template>
  <v-form>
    <v-col cols="12">
      <v-row>
        <v-col cols="12" style="background-color: antiquewhite">
          <v-col cols="12">
            <v-row style="padding: 10px;">
              <v-card
                  class="mx-auto"
                  max-width="450"
                  outlined
                  style="margin: 5px"
              >
                <v-list-item three-line>
                  <v-list-item-avatar
                      tile
                      size="80"
                      color="blue darken-1"
                  >
                    <v-icon dark size="60">
                      mdi-cog-clockwise
                    </v-icon>
                  </v-list-item-avatar>

                  <v-list-item-content>
                    <div class="text-overline mb-1">
                      세척 생산량(EA)
                    </div>
                    <v-list-item-title class="text-h4 mb-1">
                      {{ workCount }}
                    </v-list-item-title>
                    <v-list-item-subtitle>{{ toDate }}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>
              </v-card>

              <v-card
                  class="mx-auto"
                  max-width="450"
                  outlined
                  style="margin: 5px"
              >
                <v-list-item three-line>
                  <v-list-item-avatar
                      tile
                      size="80"
                      color="indigo darken-1"
                  >
                    <v-icon dark size="60">
                      mdi-hexagon-multiple
                    </v-icon>
                  </v-list-item-avatar>

                  <v-list-item-content>
                    <div class="text-overline mb-1">
                      검수량(EA)
                    </div>
                    <v-list-item-title class="text-h4 mb-1">
                      {{ workDfCount }}
                    </v-list-item-title>
                    <v-list-item-subtitle>{{ toDate }}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>
              </v-card>

              <v-card
                  class="mx-auto"
                  max-width="450"
                  outlined
                  style="margin: 5px"
              >
                <v-list-item three-line>
                  <v-list-item-avatar
                      tile
                      size="80"
                      color="green darken-2"
                  >
                    <v-icon dark size="60">
                      mdi-archive-arrow-down-outline
                    </v-icon>
                  </v-list-item-avatar>

                  <v-list-item-content>
                    <div class="text-overline mb-1">
                      포장량(BOX)
                    </div>
                    <v-list-item-title class="text-h4 mb-1">
                      {{ pkBoxCount }}
                    </v-list-item-title>
                    <v-list-item-subtitle>{{ toDate }}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>
              </v-card>
            </v-row>
          </v-col>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="9">
          <v-row>
            <v-col cols="12" style="margin: 10px">
              <h3 style="background-color: bisque">세척 생산량</h3>
              <div v-if="chartWorkData.length > 0">
              <OsSaOrderChartDashCard :chartDataValue="chartWorkData"></OsSaOrderChartDashCard>
              </div>
              <div v-else>
                <defined-chart-componets></defined-chart-componets>
              </div>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="12" style="margin: 10px">
              <h3 style="background-color: bisque">검수 생산량</h3>
              <div v-if="chartWorkDfData.length > 0">
                <os-sa-order-df-chart-dash-card :chartDFDataValue="chartWorkDfData"></os-sa-order-df-chart-dash-card>
              </div>
              <div v-else>
                <defined-chart-componets></defined-chart-componets>
              </div>
            </v-col>
          </v-row>
        </v-col>

        <v-col cols="3">
          <v-card>
            <v-card-title class="text-h5 font-weight-regular blue-grey white--text">
              포장량
            </v-card-title>
            <v-simple-table fixed-header height="480px">
              <template v-slot:default>
                <thead>
                <tr>
                  <th class="text-center">
                    포장
                  </th>
                  <th class="text-center">
                    포장 수량(BOX)
                  </th>
                </tr>
                </thead>
                <tbody>
                <tr
                    v-for="item in desserts"
                    :key="item.itemName"
                >
                  <td>{{ item.itemName }}</td>
                  <td>{{ item.itemCount }}</td>
                </tr>
                </tbody>
              </template>
            </v-simple-table>
          </v-card>


        </v-col>

      </v-row>
    </v-col>
  </v-form>
</template>

<script>
import OsSaOrderChartDashCard from "@/components/dashboardComponents/OsSaOrderChartDashCard";
import OsSaOrderDfChartDashCard from "@/components/dashboardComponents/OsSaOrderDfChartDashCard";
import axios from "axios";
import DefinedChartComponets from "@/components/definedChart/definedChartComponets";

export default {
  name: "hubDashBoardComponent",
  components: {DefinedChartComponets, OsSaOrderDfChartDashCard, OsSaOrderChartDashCard},
  props: {
    fabCodeValue: Object,
  },
  watch: {
    fabCodeValue() {
      this.fabCodeValue = this.fabCodeValue
      this.initView();
    }
  },
  data: () => ({
    workCount: 0,
    workDfCount: 0,
    pkBoxCount: 0,
    chartWorkData: [],
    chartWorkDfData: [],
    desserts: [],
    toDate: "",
  }),
  created() {
    this.initView();
  },
  mounted() {
    this.loading = setInterval(this.initView, 5000)
  },
  destroyed() {
    clearInterval(this.loading)
  },
  methods: {
    initView() {
      axios
          .get("/api/get/dashboard/" + this.fabCodeValue.vlaue)
          .then(response => {
            if (response.status == 200) {
              this.workCount = response.data.dayWorkData[0].dayWorkCount == null ? 0 : response.data.dayWorkData[0].dayWorkCount;
              this.workDfCount = response.data.dayWorkData[0].dayDfWorkCount == null ? 0 : response.data.dayWorkData[0].dayDfWorkCount;
              this.pkBoxCount = response.data.dayWorkPkData[0].dayPkCount == null ? 0 : response.data.dayWorkPkData[0].dayPkCount;

              this.chartWorkData = response.data.dayWorkTimeData;
              this.chartWorkDfData = response.data.dayWorkTimeDfData;

              var pkCount = response.data.pkCountList;
              var pkKidsCount = response.data.pkKidsCountList;

              this.desserts = pkCount;

              if (pkKidsCount.length != null) {
                for (var i = 0; i < pkKidsCount.length; i++) {
                  this.desserts.push({
                    itemName: pkKidsCount[i].vendorNm,
                    itemCount: pkKidsCount[i].className,
                  })
                }

                this.pkBoxCount += pkKidsCount.length;
              }

              var dt = new Date(response.data.toDate);
              var arrDay = ['일', '월', '화', '수', '목', '금', '토'];
              this.toDate = dt.getFullYear() + '년 ' + (dt.getMonth() + 1) + '월' + dt.getDate() + '일 (' + arrDay[dt.getDay()] + '요일)';

            } else {
              alert(response.data.ResultValue);
            }
          })
          .catch(ex => {
            alert(ex.message);
          });

    }
  }
}
</script>

<style scoped>

</style>