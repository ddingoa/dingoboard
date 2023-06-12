<template>
  <v-form style="padding: 10px">
    <v-card>
      <v-card-title class="text-h5 font-weight-regular blue-grey white--text">
        KIDS 주문
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

    <v-col cols="12" class="text-right">
      <v-row>
        <v-file-input
            v-model="inputfiles"
            placeholder="Upload your documents"
            label="File input"
            chips
            show-size
            multiple
            prepend-icon="mdi-paperclip"
        >
        </v-file-input>
        <v-btn color="primary"
               style="margin: 10px"
               large
               @click="uploadExcelFile"
        >
          엑셀 업로드
        </v-btn>
        <v-btn color="primary"
               style="margin: 10px"
               large
               @click="makeExcelFile5"
        >
          엑셀 다운로드
        </v-btn>
      </v-row>
    </v-col>
    <div style="padding-top: 15px">
      <h2>주문 품목 리스트</h2>
      <v-card>
        <v-card-title>
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
          <template v-slot:[`item.eaQty`]="props">
            <v-edit-dialog
                :return-value.sync="props.item.eaQty"
                large
                persistent
                @save="save(props.item)"
                @cancel="cancel(props.item)"
                @open="open(props.item)"
                @close="close"
            >
              {{ props.item.eaQty }}
              <template v-slot:input>
                <v-text-field
                    v-model="props.item.eaQty"
                    label="Edit"
                    type="number"
                    single-line
                    counter
                    autofocus
                ></v-text-field>
              </template>
            </v-edit-dialog>
          </template>


        </v-data-table>
      </v-card>

      <v-snackbar v-model="snack"
                  :timeout="3000"
                  :color="snackColor"
                  right>
        {{ snackText }}

        <template v-slot:action="{ attrs }">
          <v-btn v-bind="attrs" text @click="snack = false">
            Close
          </v-btn>
        </template>
      </v-snackbar>

      <v-btn block
             color="primary"
             style="padding: 15px"
             :loading="loading3"
             class="ma-2 white--text"
             @click="kidsOrderSave"
             large>
        주문 저장
      </v-btn>
    </div>

  </v-form>
</template>

<script>
import axios from "axios";
import kidsorder from "../../dataset/otherColumn/kidsOrderMast_Column_Json.json"
import store from "@/store";
import Xlsx from "xlsx";
import momnet from 'moment-timezone'

export default {
  name: "Order_T_KidsView",
  data: () => ({
    hubModel: [],
    hubStates: [],
    loader: null,
    loading3: false,
    search: '',
    headers: kidsorder,
    desserts: [],
    snack: false,
    snackColor: "",
    snackText: "",
    inputfiles: '',
    execlExport: [
      {
        "주문번호": "",
        "주문일자": "",
        "거래처코드": "",
        "사업부코드": "",
        "유상여부": "",
        "주문금액": "",
        "배송예정일": "",
        "사용예정일": "",
        "수거예정일": "",
        "주문일련번호": "",
        "배송지번호": "",
        "반코드": "",
        "품목코드": "",
        "상품그룹코드": "",
        "거래단위": "",
        "주문수량": 0,
        "박스입수": 0,
        "낱개수량": 0,
        "포장수량": 0,
        "배송수량": 0,
        "수거수량": 0,
        "비고": "",
        "등록자": "",
        "등록일시": "",
        "수정자": "",
        "수정일시": "",
        "마감여부": "",
        "주문입력구분": "",
      }],
  }),
  created() {
    this.initData();
  },
  watch: {
    dialog(val) {
      val || this.close();
    },
    dialogDelete(val) {
      val || this.closeDelete();
    },
    loader() {
      const l = this.loader;
      this[l] = !this[l];

      setTimeout(() => (this[l] = false), 3000);

      this.loader = null;
    }
  },
  methods: {
    dateFormat(date) {
      let month = date.getMonth() + 1;
      let day = date.getDate();
      let hour = date.getHours();
      let minute = date.getMinutes();
      let second = date.getSeconds();

      month = month >= 10 ? month : '0' + month;
      day = day >= 10 ? day : '0' + day;
      hour = hour >= 10 ? hour : '0' + hour;
      minute = minute >= 10 ? minute : '0' + minute;
      second = second >= 10 ? second : '0' + second;

      return date.getFullYear() + '-' + month + '-' + day;
    },
    kidsOrderSave() {
      this.loader = 'loading3'
      var data = [];
      var todate = new Date();
      for (var i = 0; i < this.desserts.length; i++) {
        if (this.desserts[i].eaQty > 0) {
          var datavalue = {
            orderNo: this.desserts[i].vendorCd + todate.getDay() + "" + i,
            orderDate: "",
            vendorCd: this.desserts[i].vendorCd,
            divisionCd: "B2",
            chargeYn: "Y",
            orderAmount: "0",
            deliveryDate: "",
            useDate: "",
            pickupDate: "",
            ordernoSeq: todate.getDay() + "-" + this.desserts[i].classCd + "" + i,
            classCd: this.desserts[i].classCd,
            memberId: this.desserts[i].classCd + "" + i,
            goodsCd: this.desserts[i].goodsCd,
            bizUnit: "EA",
            orderQty: this.desserts[i].eaQty,
            eaQty: this.desserts[i].eaQty,
            remark: "",
            regUserId: store.state.userName,
            modiUserId: store.state.userName,
            closeYn: "Y",
            keyinType: "뽀득 입력"

          }
          data.push(datavalue);
        }
      }
      axios
          .post("/api/set/kidsordermast", data)
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
    workOrder() {

    },
    fabGetItemList() {
      var value = this.hubModel.vlaue.toString();
      axios.get("/api/get/kidsordermast/" + value)
          .then(response => {
            this.desserts = response.data.data;
          }).catch(ex => {
        alert(ex.message);
      });

    },
    save(item) {
      this.snack = true;
      this.snackColor = "success";
      this.snackText = '입력을 완료 하셨습니다!'
      if (item.eaQty == "") {
        item.eaQty != 0 ? item.eaQty : 0;
      }
    },
    cancel(item) {
      this.snack = true;
      this.snackColor = "error";
      this.snackText = '입력을 취소 하셨습니다!!'
      if (item.eaQty == "") {
        item.eaQty = 0
      }
    },
    open(item) {
      this.snack = true;
      this.snackColor = "info";
      this.snackText = "수정 중입니다.";
      if (item.eaQty == 0) {
        item.eaQty = ""
      }
    },
    close() {
      console.log("Dialog closed");
    },
    makeExcelFile5() {
      const workBook = Xlsx.utils.book_new()
      const workSheet = Xlsx.utils.json_to_sheet(this.execlExport)
      Xlsx.utils.book_append_sheet(workBook, workSheet, 'orderShtee')
      Xlsx.writeFile(workBook, 'orderShteeExport.xlsx')
    },
    uploadExcelFile(){

    }
  }
}
</script>

<style scoped>

</style>