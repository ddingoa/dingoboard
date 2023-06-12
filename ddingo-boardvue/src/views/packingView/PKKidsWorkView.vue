<template>
  <v-form style="padding: 10px">
    <v-card>
      <v-card-title class="text-h5 font-weight-regular blue-grey white--text">
        포장 계획
      </v-card-title>
      <v-card-text>
        <v-row>
          <v-col cols="6">
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
                @click="initData"
            >
            </v-autocomplete>
          </v-col>
          <v-col cols="6">
            <v-subheader class="pa-0">
              <h3 style="color: black"> 포장 구분 </h3>
            </v-subheader>
            <v-autocomplete
                v-model="daySep"
                :items="daySeptates"
                item-text="text"
                item-value="value"
                :readonly="false"
                return-object
                @change="getGridData"
            >
            </v-autocomplete>
          </v-col>
        </v-row>
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
                color="indigo darken-4"
            >
              <v-icon dark size="60">
                mdi-archive-settings
              </v-icon>
            </v-list-item-avatar>

            <v-list-item-content>
              <div class="text-overline mb-4">
                총 포장
              </div>
              <v-list-item-title class="text-h2 mb-1">
                {{ totalPkCount }}
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
                color="light-blue darken-2"
            >
              <v-icon dark size="60">
                mdi-archive-plus
              </v-icon>
            </v-list-item-avatar>

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
                color="deep-orange darken-1"
            >
              <v-icon dark size="60">
                mdi-archive-remove
              </v-icon>
            </v-list-item-avatar>

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
            :item-class="row_classes"
            class="elevation-1"
            :search="search"
            group-by="vendorNm"
            show-group-by
            fixed-header
        >
          <template v-slot:header="props">
            <thead>
            <tr>
              <th></th>
              <th></th>
              <th></th>
              <th colspan="5" style="text-align: center; font-weight: bold"> 키즈 제품</th>
              <th style="text-align: center; font-weight: bold"> 선생님 식기</th>
              <th></th>
              <th></th>
            </tr>
            </thead>
          </template>

          <template v-slot:top>
            <v-toolbar flat>
              <v-toolbar-title>포장 현황 검색</v-toolbar-title>
              <v-spacer></v-spacer>
              <v-text-field
                  v-model="search"
                  append-icon="mdi-magnify"
                  label="Search"
                  single-line
                  hide-details
              ></v-text-field>
            </v-toolbar>
          </template>

          <template v-slot:[`item.actions`]="{ item }">
            <v-btn
                :loading="loading3"
                :disabled="item.disable"
                color="blue-grey"
                class="ma-2 white--text"
                @click="btnClickAdd(item)"
            >
              포장 완료
              <v-icon right dark>
                mdi-cube
              </v-icon>
            </v-btn>
          </template>

          <template v-slot:item.pkworkValue="{ item }">
            <v-chip
                :color="getColor(item.pkworkValue)"
                dark
            >
              {{ item.pkworkValue }}
            </v-chip>
          </template>

          <template v-slot:item.rrkis04="{ item }">
            <v-chip
                :color="getSetColor()"
                dark
            >
              {{ item.rrkis04 }}
            </v-chip>
          </template>

        </v-data-table>
      </v-col>
    </v-col>

    <v-dialog v-model="dialogPking" max-width="500px">
      <v-card>
        <v-card-title class="text-h5"
        >포장 완료 처리를 하시겠습니까?!
        </v-card-title>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="closepk">Cancel</v-btn>
          <v-btn color="blue darken-1" text @click="pkdialogsave">OK</v-btn>
          <v-spacer></v-spacer>
        </v-card-actions>
      </v-card>
    </v-dialog>

  </v-form>
</template>

<script>
import pkorderColumn from "@/dataset/pkorder/pkorder_Column_Json.json";
import axios from "axios";
import store from "@/store";

export default {
  name: "PKKidsWorkView",
  data: () => ({
    daySep: [],
    daySeptates: [{text: "당일", value: "A"}, {text: "차일", value: "B"}],
    hubModel: [],
    hubStates: [],
    viewCheck: false,
    totalPkCount: 0,
    workPkCount: 0,
    nonPkCount: 0,
    loader: null,
    loading3: false,
    dialogPking: false,
    toDate: "",
    search: "",
    headers: pkorderColumn,
    desserts: [],
  }),
  created() {
    this.initData()
  },
  watch: {
    loader() {
      const l = this.loader;
      this[l] = !this[l];

      setTimeout(() => (this[l] = false), 3000);

      this.loader = null;
    },
    dialogPking(val) {
      val || this.dialogClose();
    }
  },
  methods: {
    row_classes(item) {
      this.editedIndex = this.desserts.indexOf(item);
      if(this.editedIndex%2 == 0){
        return "cyan lighten-2";
      }
    },
    dialogClose() {
      this.dialogPking = false;
    },
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
    getColor(pkworkValue) {
      if (pkworkValue === "포장 완료") return 'green'
      else return 'red'
    },
    getSetColor() {
      return 'red'
    },
    getGridData() {
      var data = {
        fabCode: this.hubModel.vlaue,
        daySep: this.daySep.value
      }


      axios
          .post("/api/get/pkkidsdata", data)
          .then(response => {
            this.desserts = [];
            var getGridData = response.data.data;
            var itemData = groupBy(getGridData, "orderNo");

            for (i of Object.keys(itemData)) {
              var value = groupBy(itemData[i], "classCd")
              for (let j of Object.keys(value)) {
                var item01 = 0;
                var item02 = 0;
                var item03 = 0;
                var item04 = 0;
                var item05 = 0;
                var item06 = 0;
                var orderNo = "";
                var pkworkValue = "";
                var pkoCode = "";
                var fabCode = "";
                var fabName = "";
                var orderDate = "";
                var deliveryDate = "";
                var useDate = "";
                var pickupDate = "";
                var vendorCd = "";
                var address2 = "";
                var vendorNm = "";
                var classCd = "";
                var classNumber = "";
                var className = "";
                var bizUnit = "";
                var goodsCd = "";
                var teacherQty = "";
                var deliveryCode = "";
                var daySeq = "";
                var pkingCheck = false;

                for (var z = 0; z < value[j].length; z++) {

                  orderNo = value[j][z].orderNo;
                  pkingCheck = value[j][z].pkingCheck;
                  pkworkValue = value[j][z].pkworkValue;
                  pkoCode = value[j][z].pkoCode;
                  fabCode = "";
                  fabName = "";
                  orderDate = value[j][z].orderDate;
                  deliveryDate = value[j][z].deliveryDate;
                  useDate = value[j][z].useDate;
                  pickupDate = value[j][z].pickupDate;
                  vendorCd = value[j][z].vendorCd;
                  address2 = value[j][z].address1;
                  vendorNm = value[j][z].vendorNm;
                  classCd = value[j][z].classCd;
                  classNumber = value[j][z].classNumber;
                  className = value[j][z].className;
                  bizUnit = value[j][z].bizUnit;
                  goodsCd = value[j][z].goodsCd;
                  teacherQty = value[j][z].teacherQty;
                  deliveryCode = value[j][z].deliveryCode;
                  daySeq = value[j][z].daySeq;

                  if (value[j][z].goodsCd == "RR-KIS-01") {
                    item01 = value[j][z].eaQty;
                  }

                  if (value[j][z].goodsCd == "RR-KIS-02") {
                    item02 = value[j][z].eaQty;
                  }
                  if (value[j][z].goodsCd == "RR-KIS-03") {
                    item03 = value[j][z].eaQty;
                  }

                  if (value[j][z].goodsCd == "RR-KIS-04") {
                    item04 = value[j][z].eaQty;
                  }

                  if (value[j][z].goodsCd == "RR-KIS-05") {
                    item05 = value[j][z].eaQty;
                  }

                  if (value[j][z].goodsCd == "RR-KIS-06") {
                    item06 = value[j][z].eaQty;
                  }
                }

                var dataSet = {
                  orderNo: orderNo,
                  pkingCheck: pkingCheck,
                  pkworkValue: pkworkValue,
                  pkoCode: "",
                  fabCode: "",
                  fabName: "",
                  orderDate: orderDate,
                  deliveryDate: deliveryDate,
                  useDate: useDate,
                  pickupDate: pickupDate,
                  vendorCd: vendorCd,
                  address2: address2,
                  vendorNm: vendorNm,
                  classCd: classCd,
                  classNumber: classNumber,
                  className: className,
                  bizUnit: bizUnit,
                  goodsCd: goodsCd,
                  rrkis01: item01,
                  rrkis02: item02,
                  rrkis03: item03,
                  rrkis04: item04,
                  rrkis05: item05,
                  rrkis06: item06,
                  teacherQty: teacherQty,
                  deliveryCode: deliveryCode,
                  daySeq: daySeq,
                }

                this.desserts.push(dataSet);
              }
            }

            var pkingCount = 0;
            for (var i = 0; i < this.desserts.length; i++) {
              if (this.desserts[i].pkingCheck) {
                this.desserts[i].pkworkValue = "포장 완료"
                this.desserts[i].disable = true;
                pkingCount++;
              } else {
                this.desserts[i].pkworkValue = "미포장"
              }
            }


            this.totalPkCount = this.desserts.length;
            this.workPkCount = pkingCount;
            this.nonPkCount = this.desserts.length - pkingCount;
            this.viewCheck = true;
            var dt = new Date(response.data.toDate);
            var arrDay = ['일', '월', '화', '수', '목', '금', '토'];
            this.toDate = dt.getFullYear() + '년 ' + (dt.getMonth() + 1) + '월' + dt.getDate() + '일 (' + arrDay[dt.getDay()] + '요일)';

          })
          .catch(ex => {
            alert(ex.message);
          });

    },
    btnClickAdd(item) {
      this.loader = "loading3";
      this.editedIndex = this.desserts.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogPking = true;
    },
    closepk() {
      this.dialogPking = false;
    },
    pkdialogsave() {
      this.desserts[this.editedIndex].disable = true;
      this.dialogPking = false;

      var columnList = [{"rrkis01": "RR-KIDS-01"}, {"rrkis02": "RR-KIDS-02"}, {"rrkis03": "RR-KIDS-03"}, {"rrkis04": "RR-KIDS-04"}, {"rrkis05": "RR-KIDS-05"}, {"rrkis06": "RR-KIDS-06"}, {"rrkis07": "RR-KIDS-07"}
        , {"rrkis08": "RR-KIDS-08"}, {"rrkis09": "RR-KIDS-09"}];

      var sendDataList = [];
      for (var key in columnList) {
        var sendData = {
          pkoCode: "",
          orderNo: this.desserts[this.editedIndex].orderNo,
          fabCode: this.hubModel.vlaue,
          fabName: this.hubModel.text,
          workStart: "",
          inputTime: "",
          orderDate: this.desserts[this.editedIndex].orderDate,
          deliveryDate: this.desserts[this.editedIndex].deliveryDate,
          useDate: this.desserts[this.editedIndex].useDate,
          deliveryCode: this.desserts[this.editedIndex].deliveryCode,
          pickupDate: this.desserts[this.editedIndex].pickupDate,
          address1: this.desserts[this.editedIndex].address2,
          bizUnit: "EA",
          vendorCd: this.desserts[this.editedIndex].vendorCd,
          vendorNm: this.desserts[this.editedIndex].vendorNm,
          classCd: this.desserts[this.editedIndex].classCd,
          className: this.desserts[this.editedIndex].className,
          daySeq: this.desserts[this.editedIndex].daySeq,
          goodsCd: Object.values(columnList[key])[0],
          goodsNm: "",
          itemPsCount: this.desserts[this.editedIndex][Object.keys(columnList[key])],
          holidaysApply: "",
          pkingCheck: true,
          active: true,
          regCd: store.state.userName,
          modCd: store.state.userName
        }
        sendDataList.push(sendData)
      }
      axios
          .post("/api/set/pkorderkidsdata", sendDataList)
          .then(response => {
            if (response.status == 200) {
              alert(response.data.ResultValue);
              this.getGridData();
            } else {
              alert(response.data.ResultValue);
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

<style scoped>
.orange {
  background-color: tomato;
}

</style>