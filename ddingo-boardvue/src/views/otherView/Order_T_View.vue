<template>
  <v-form style="padding: 10px">
    <v-card>
      <v-card-title class="text-h5 font-weight-regular blue-grey white--text">
        주문 입력
      </v-card-title>
      <v-card-text>
        <v-subheader class="pa-0">
          사업부 구분
        </v-subheader>
        <v-autocomplete
            v-model="productModel"
            :items="productStates"
            item-text="text"
            item-value="value"
            :readonly="false"
            @click="initData"
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
            return-object
            @change="event => accounCobChange(event)"
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
      <div>
        <v-data-table :headers="headers" :items="desserts">
          <template v-slot:[`item.orderQty`]="props">
            <v-edit-dialog
                :return-value.sync="props.item.orderQty"
                large
                persistent
                @save="save(props.item)"
                @cancel="cancel(props.item)"
                @open="open(props.item)"
                @close="close"
            >
              {{ props.item.orderQty }}
              <template v-slot:input>
                <v-text-field
                    v-model="props.item.orderQty"
                    label="Edit"
                    type="number"
                    single-line
                    counter
                    autofocus
                ></v-text-field>
              </template>
            </v-edit-dialog>
          </template>

          <template v-slot:[`item.boxInQty`]="props">
            <v-edit-dialog
                :return-value.sync="props.item.boxInQty"
                large
                persistent
                @save="save(props.item)"
                @cancel="cancel(props.item)"
                @open="open(props.item)"
                @close="close"
            >
              {{ props.item.boxInQty }}
              <template v-slot:input>
                <v-text-field
                    v-model="props.item.boxInQty"
                    label="Edit"
                    type="number"
                    single-line
                    counter
                    autofocus
                ></v-text-field>
              </template>
            </v-edit-dialog>
          </template>

          <template v-slot:[`item.holidaysApply`]="props">
            <v-edit-dialog
                :return-value.sync="props.item.holidaysApply"
                large
                persistent
                @save="save(props.item)"
                @cancel="cancel(props.item)"
                @open="open(props.item)"
                @close="close"
            >
              {{ props.item.holidaysApply }}
              <template v-slot:input>
                <v-text-field
                    v-model="props.item.holidaysApply"
                    label="Edit"
                    type="text"
                    single-line
                    counter
                    autofocus
                ></v-text-field>
              </template>
            </v-edit-dialog>
          </template>


          <template v-slot:[`item.chargeYn`]="props">
            <v-edit-dialog
                :return-value.sync="props.item.chargeYn"
                large
                persistent
                @save="save(props.item)"
                @cancel="cancel(props.item)"
                @open="open(props.item)"
                @close="close"
            >
              {{ props.item.chargeYn }}
              <template v-slot:input>
                <v-text-field
                    v-model="props.item.chargeYn"
                    label="Edit"
                    type="text"
                    single-line
                    counter
                    autofocus
                ></v-text-field>
              </template>
            </v-edit-dialog>
          </template>


          <template v-slot:[`item.deliveryNo`]="props">
            <v-edit-dialog
                :return-value.sync="props.item.deliveryNo"
                large
                persistent
                @save="save(props.item)"
                @cancel="cancel(props.item)"
                @open="open(props.item)"
                @close="close"
            >
              {{ props.item.deliveryNo }}
              <template v-slot:input>
                <v-text-field
                    v-model="props.item.deliveryNo"
                    label="Edit"
                    type="text"
                    single-line
                    counter
                    autofocus
                ></v-text-field>
              </template>
            </v-edit-dialog>
          </template>


        </v-data-table>
      </div>

      <v-btn color="info" block @click="saveGridData()">주문 등록 하기
      </v-btn>

    </div>

  </v-form>
</template>

<script>
import axios from "axios";
import orderTColumn from "@/dataset/mastColumn/order_T_Column_Json.json";
import store from "@/store";
import Xlsx from 'xlsx';
import momnet from 'moment-timezone';

export default {
  name: "Order_T_View",
  data: () => ({
    productModel: [],
    productStates: [{text: "KIDS", value: "K0"},
      {text: "BIZ", value: "B0"},
      {text: "ECO", value: "E0"}],
    accountModel: [],
    accountStates: [],
    headers: orderTColumn,
    desserts: [],
    inputfiles: '',
    inputFileResult: [],
    excelSendResult: [],
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
  methods: {
    initData() {
      this.productModel = [];
      this.productStates = [{text: "KIDS", value: "K0"}, {text: "BIZ", value: "B0"}, {text: "ECO", value: "E0"}];
      this.accountModel = [];
      this.accountStates = [];
      this.desserts = [];

    },
    productCobChange(event) {
      axios
          .get("/api/get/accountCobList/" + event)
          .then(response => {
            this.accountStates = response.data.accountStates;
            this.itmeTypes = response.data.itmeTypes;
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
    accounCobChange(event) {
      var data = {
        "code": this.productModel,
        "account": this.accountModel.value
      }

      axios
          .post("/api/get/ordertmast", data)
          .then(response => {
            this.desserts = [];
            var gridData = [];
            var resultdate = response.data.data;
            var nowData = new Date();
            var delivery = new Date();
            var use = new Date();
            var pickupDate = new Date();
            delivery.setDate(delivery.getDate() + 1);
            use.setDate(use.getDate() + 1);
            pickupDate.setDate(pickupDate.getDate() + 2);
            for (var i = 0; i < resultdate.length; i++) {
              if (this.accountModel.value == resultdate[i].accountCode) {
                gridData.push({
                  "id": resultdate[i].id,
                  "workStart": resultdate[i].workStart,
                  "orderKeyCode": resultdate[i].orderKeyCode,
                  "divisionCd": resultdate[i].divisionCd,
                  "orderDate": this.dateFormat(nowData),
                  "deliveryDate": this.dateFormat(delivery),
                  "useDate": this.dateFormat(use),
                  "pickupDate": this.dateFormat(pickupDate),
                  "vendorCd": this.accountModel.value,
                  "vendorNm": this.accountModel.text,
                  "goodsCd": resultdate[i].goodsCd,
                  "goodsNm": resultdate[i].goodsNm,
                  "orderItemCount": resultdate[i].orderItemCount,
                  "orderQty": 0,
                  "eaQty": 0,
                  "boxInQty": 1,
                  "holidaysApply": "",
                  "insManger": resultdate[i].insManger == null ? store.state.userName : resultdate[i].insManger
                })
              } else {
                gridData.push({
                  "workStart": "",
                  "orderKeyCode": resultdate[i].orderKeyCode,
                  "divisionCd": resultdate[i].divisionCd,
                  "orderDate": this.dateFormat(nowData),
                  "deliveryDate": this.dateFormat(delivery),
                  "useDate": this.dateFormat(use),
                  "pickupDate": this.dateFormat(pickupDate),
                  "vendorCd": this.accountModel.value,
                  "vendorNm": this.accountModel.text,
                  "goodsCd": resultdate[i].goodsCd,
                  "goodsNm": resultdate[i].goodsNm,
                  "orderItemCount": 0,
                  "orderQty": 0,
                  "eaQty": 0,
                  "boxInQty": 1,
                  "holidaysApply": "",
                  "insManger": store.state.userName
                })
              }
            }

            this.desserts = gridData;
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
    save(item) {
      this.snack = true;
      this.snackColor = "success";
      this.snackText = '입력을 완료 하셨습니다!'
      item.eaQty = item.orderQty * item.boxInQty
      if (item.orderQty == "") {
        item.orderQty != 0 ? item.orderQty : 0;
      }
    },
    cancel(item) {
      this.snack = true;
      this.snackColor = "error";
      this.snackText = '입력을 취소 하셨습니다!!'
      if (item.orderQty == "") {
        item.orderQty = 0
      }

    },
    open(item) {
      this.snack = true;
      this.snackColor = "info";
      this.snackText = "수정 중입니다.";
      if (item.orderQty == 0) {
        item.orderQty = ""
      }
    },
    close() {
      console.log("Dialog closed");
    },
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
    saveGridData() {
      if (this.desserts.length > 0) {
        var sendData = [];
        for (var i = 0; i < this.desserts.length; i++) {

          var resultObject = {};

          var todate = new Date();

          resultObject = {
            "orderNo": this.desserts[i].vendorCd + new Date().getDate() + new Date().getHours() + i,
            "orderDate": this.desserts[i].orderDate,
            "vendorCd": this.desserts[i].vendorCd,
            "divisionCd": this.desserts[i].divisionCd,
            "chargeYn": this.desserts[i].chargeYn,
            "orderAmount": 0,
            "deliveryDate": this.desserts[i].deliveryDate,
            "useDate": this.desserts[i].useDate,
            "pickupDate": this.desserts[i].pickupDate,
            "ordernoSeq": new Date().getDate() + "" + new Date().getHours() + "" + new Date().getSeconds() + "" + i,
            "deliveryNo": this.desserts[i].deliveryNo,
            "classCd": "00",
            "goodsCd": this.desserts[i].goodsCd,
            "goodsGroupCd": "00",
            "bizUnit": "BOX",
            "orderQty": this.desserts[i].orderQty,
            "boxInQty": this.desserts[i].boxInQty,
            "eaQty": this.desserts[i].eaQty,
            "packQty": this.desserts[i].packQty,
            "deliveryQty": this.desserts[i].deliveryQty,
            "pickupQty": this.desserts[i].pickupQty,
            "remark": this.desserts[i].remark,
            "regUserId": store.state.userName,
            "regDate": this.dateFormat(todate),
            "modiUserId": store.state.userName,
            "modiDate": this.dateFormat(todate),
            "closeYn": "Y",
            "keyinType": "뽀득입력",
          }
          sendData.push(resultObject);
        }

        axios
            .post("/api/set/bizorderset", sendData)
            .then(response => {
              alert(response.data.ResultValue);
              this.initData();
            })
            .catch(ex => {
              alert(ex.message);
            });
      }
    },
    makeExcelFile5() {
      const workBook = Xlsx.utils.book_new()
      const workSheet = Xlsx.utils.json_to_sheet(this.execlExport)
      Xlsx.utils.book_append_sheet(workBook, workSheet, 'orderShtee')
      Xlsx.writeFile(workBook, 'orderShteeExport.xlsx')
    },
    uploadExcelFile(event) {
      this.inputFileResult = [];
      this.excelSendResult = [];

      const file = this.inputfiles[0];
      const fileName = file.name;

      // declare FileReader, temp result
      const reader = new FileReader();
      let tmpResult = {};

      // if you use "this", don't use "function(e) {...}"
      reader.onload = (e) => {
        let data = e.target.result;
        data = new Uint8Array(data);
        // get excel file
        let excelFile = Xlsx.read(data, {type: "array"});

        // get prased object
        excelFile.SheetNames.forEach(function (sheetName) {
          const roa = Xlsx.utils.sheet_to_json(
              excelFile.Sheets[sheetName],
              {header: 1}
          );
          if (roa.length) tmpResult[sheetName] = roa;
        });
        this.inputFileResult = tmpResult.Sheet1;
        var testDat = this.inputFileResult.splice(1, 1);

        var ColumnSet = this.inputFileResult.splice(0, 1);
        for (var j = 0; j < this.inputFileResult.length; j++) {
          var resultObject = {};
          resultObject = {
            "orderNo": this.inputFileResult[j][0],
            "orderDate": this.inputFileResult[j][1],
            "vendorCd": this.inputFileResult[j][2],
            "divisionCd": this.inputFileResult[j][3],
            "chargeYn": this.inputFileResult[j][4],
            "orderAmount": this.inputFileResult[j][5],
            "deliveryDate": this.inputFileResult[j][6],
            "useDate": this.inputFileResult[j][7],
            "pickupDate": this.inputFileResult[j][8],
            "ordernoSeq": this.inputFileResult[j][9],
            "deliveryNo": this.inputFileResult[j][10],
            "classCd": this.inputFileResult[j][11],
            "goodsCd": this.inputFileResult[j][12],
            "goodsGroupCd": this.inputFileResult[j][13],
            "bizUnit": this.inputFileResult[j][14],
            "orderQty": this.inputFileResult[j][15],
            "boxInQty": this.inputFileResult[j][16],
            "eaQty": this.inputFileResult[j][17],
            "packQty": this.inputFileResult[j][18],
            "deliveryQty": this.inputFileResult[j][19],
            "pickupQty": this.inputFileResult[j][20],
            "remark": this.inputFileResult[j][21],
            "regUserId": this.inputFileResult[j][22],
            "regDate": this.inputFileResult[j][23],
            "modiUserId": this.inputFileResult[j][24],
            "modiDate": this.inputFileResult[j][25],
            "closeYn": this.inputFileResult[j][26],
            "keyinType": this.inputFileResult[j][27],
          }
          this.excelSendResult.push(resultObject)
        }

        axios
            .post("/api/set/bizorderset", this.excelSendResult)
            .then(response => {
              alert(response.data.ResultValue);
              this.initData();
            })
            .catch(ex => {
              alert(ex.message);
            });
      };


      reader.readAsArrayBuffer(file);
    },
  }
}
</script>

<style scoped>

</style>