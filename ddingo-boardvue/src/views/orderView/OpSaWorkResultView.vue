<template>
  <v-form style="padding: 10px">
    <v-card>
      <v-card-title class="text-h5 font-weight-regular blue-grey white--text">
        생산 작업 조회
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

        <v-row>
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
            >
            </v-autocomplete>
          </v-card-text>
        </v-row>

        <v-row>
          <v-col class="text-right">
            <v-btn
                :loading="loading3"
                :disabled="loading3"
                color="blue-grey"
                class="ma-2 white--text"
                @click="getViewData"
            >
              조회
              <v-icon right dark>
                mdi-magnify
              </v-icon>
            </v-btn>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>

    <v-col cols="12">
      <v-card>
        <v-tabs background-color="primary"
                dark
        >
          <v-tab>
            <h4>생산 작업 종합</h4>
          </v-tab>
          <v-tab>
            <h4>제품별 입고 차트</h4>
          </v-tab>
          <v-tab>
            <h4>제품별 발주 차트</h4>
          </v-tab>
          <v-tab>
            <h4>제품별 생산 차트</h4>
          </v-tab>
          <v-tab>
            <h4>제품별 검수 차트</h4>
          </v-tab>
          <v-tab-item>
            <v-data-table
                :headers="headers"
                :items="desserts"
                :items-per-page="10"
                class="elevation-1"
                group-by="workStart"
            ></v-data-table>
          </v-tab-item>
          <v-tab-item>
            <work-view-total-enter-chart :chartDataValue="desserts"></work-view-total-enter-chart>
          </v-tab-item>
          <v-tab-item>
            <work-view-total-order-chart :chartDataValue="desserts"></work-view-total-order-chart>
          </v-tab-item>
          <v-tab-item>
            <work-view-total-work-chart :chartDataValue="desserts"></work-view-total-work-chart>
          </v-tab-item>
          <v-tab-item>
            <work-view-total-work-df-chart :chartDataValue="desserts"></work-view-total-work-df-chart>
          </v-tab-item>
        </v-tabs>
      </v-card>
    </v-col>
  </v-form>
</template>

<script>
import axios from "axios";
import WorkViewTotalEnterChart from "@/components/orderworkViewComponent/workViewTotalEnterChart";
import WorkViewTotalOrderChart from "@/components/orderworkViewComponent/workViewTotalOrderChart";
import WorkViewTotalWorkChart from "@/components/orderworkViewComponent/workViewTotalWorkChart";
import WorkViewTotalWorkDfChart from "@/components/orderworkViewComponent/workViewTotalWorkDfChart";
import momnet from "moment-timezone";

export default {
  name: "OpSaWorkResultView",
  components: {WorkViewTotalWorkDfChart, WorkViewTotalWorkChart, WorkViewTotalOrderChart, WorkViewTotalEnterChart},
  data: () => ({
    frontDate:  momnet(new Date(Date.now() - new Date().getTimezoneOffset() * 60000)).tz('Asia/Seoul')
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
    loader: null,
    loading3: false,
    hubModel: [],
    hubStates: [],
    headers: [
      {
        text: '작업일',
        align: 'start',
        sortable: false,
        value: 'workStart',
      },
      {text: '제품 코드', value: 'goodsCd', align: ' d-none'},
      {text: '제품 명', value: 'goodsNm', divider: true},
      {text: '입수 수량(EA)', value: 'totalEnterCount', divider: true},
      {text: '발주 수량(EA)', value: 'totalOrderPsCount', divider: true},
      {text: '세척 수량(EA)', value: 'totalWorkCount', divider: true},
      {text: '검수 수량(EA)', value: 'totalWorkDfCount'},
    ],
    desserts: [],
  }),
  watch: {
    dialog(val) {
      val || this.close();
    },
    loader() {
      const l = this.loader;
      this[l] = !this[l];

      setTimeout(() => (this[l] = false), 3000);

      this.loader = null;
    }
  },
  created() {
    this.initMethod();
  },
  methods: {
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
    getViewData() {
      this.loader = 'loading3';

      var data = {
        fabCode: this.hubModel.vlaue,
        startDate: this.frontDate,
        endDate: this.toDate
      };

      axios
          .post("/api/get/dayorderworkview", data)
          .then(response => {
            this.desserts = response.data.data;
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