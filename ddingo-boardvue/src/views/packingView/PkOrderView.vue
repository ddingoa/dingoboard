<template>
  <v-form style="padding: 10px">
    <v-card>
      <v-card-title class="text-h5 font-weight-regular blue-grey white--text">
        포장 계획
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
            @click="initData"
            @change="getGridData"
        >
        </v-autocomplete>
      </v-card-text>
    </v-card>

    <v-col cols="12" v-if="viewCheck">
      <v-row style="padding: 10px; background-color: antiquewhite">
        <v-card
            class="mx-auto"
            max-width="344"
            style="margin: 10px"
            outlined
        >
          <v-list-item three-line>
            <v-list-item-avatar
                tile
                size="80"
                color="grey"
            ></v-list-item-avatar>

            <v-list-item-content>
              <div class="text-overline mb-4">
                총 포장
              </div>
              <v-list-item-title class="text-h2 mb-1">
                {{totalPkCount}}
              </v-list-item-title>
              <v-list-item-subtitle>{{ toDate }}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </v-card>

        <v-card
            class="mx-auto"
            max-width="344"
            style="margin: 10px"
            outlined
        >
          <v-list-item three-line>
            <v-list-item-avatar
                tile
                size="80"
                color="grey"
            ></v-list-item-avatar>

            <v-list-item-content>
              <div class="text-overline mb-4">
                포장 완료
              </div>
              <v-list-item-title class="text-h2 mb-1">
                {{ workPkCount }}
              </v-list-item-title>
              <v-list-item-subtitle>{{ toDate }}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </v-card>

        <v-card
            class="mx-auto"
            max-width="344"
            style="margin: 10px"
            outlined
        >
          <v-list-item three-line>
            <v-list-item-avatar
                tile
                size="80"
                color="grey"
            ></v-list-item-avatar>

            <v-list-item-content>
              <div class="text-overline mb-4">
                잔여 포장
              </div>
              <v-list-item-title class="text-h2 mb-1">
                {{ nonPkCount }}
              </v-list-item-title>
              <v-list-item-subtitle>{{ toDate }}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </v-card>
      </v-row>

      <v-col cols="12" style="padding-top: 15px">
        <v-data-table
            :headers="headers"
            :items="desserts"
            :items-per-page="10"
            class="elevation-1"
        >
          <template v-slot:item.pkworkValue="{ item }">
            <v-chip
                :color="getColor(item.pkworkValue)"
                dark
            >
              {{ item.pkworkValue }}
            </v-chip>
          </template>
        </v-data-table>
      </v-col>

      <v-col cols="12" style="padding-top: 15px; background-color: blue">
        Chart
      </v-col>

    </v-col>

  </v-form>
</template>

<script>
import axios from "axios";
import pkorderColumn from "../../dataset/pkorder/pkorder_Column_Json.json"

export default {
  name: "PkOrderView",
  data: () => ({
    hubModel: [],
    hubStates: [],
    viewCheck : false,
    totalPkCount : 0,
    workPkCount : 0,
    nonPkCount : 0,
    toDate : "",
    headers: pkorderColumn,
    desserts: [],
  }),
  created() {
    this.initData()
  },
  methods: {
    initData() {
      this.hubModel = [];
      this.hubStates = [];
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
    getColor (pkworkValue) {
      if (pkworkValue === "포장 완료") return 'green'
      else return 'red'
    },
    getGridData() {
      axios
          .get("/api/get/pkorderdata/" + this.hubModel.vlaue)
          .then(response => {
            this.desserts = [];
            var getGridData = response.data.data;
            this.desserts = getGridData;
            var pkingCount = 0;
            for (var i = 0; i < getGridData.length; i++) {
              if (getGridData[i].pkingCheck) {
                getGridData[i].pkworkValue = "포장 완료"
                pkingCount++;
              }else {
                getGridData[i].pkworkValue = "미포장"
              }
            }


            this.totalPkCount = getGridData.length;
            this.workPkCount = pkingCount;
            this.nonPkCount = getGridData.length - pkingCount;
            this.viewCheck = true;

            var dt = new Date(response.data.toDate);
            var arrDay = ['일','월','화','수','목','금','토'];
            this.toDate = dt.getFullYear()+'년 '+ (dt.getMonth()+1)+'월'+dt.getDate()+'일 ('+arrDay[dt.getDay()]+'요일)';

          })
          .catch(ex => {
            alert(ex.message);
          });

    },
  }
};
</script>

<style scoped></style>
