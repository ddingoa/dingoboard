<template>
  <v-form style="padding: 10px">

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

    <v-data-table
        :headers="headers"
        :items="desserts"
        sort-by="calories"
        class="elevation-1"
        @click:row="selectRow"
    >
      <template v-slot:[`item.createdDate`]="{ item }">
        <span>{{ new Date(item.createdDate).toLocaleString() }}</span>
      </template>
      <template v-slot:[`item.modifiedDate`]="{ item }">
      <span v-if="item.modifiedDate != null">{{
          new Date(item.modifiedDate).toLocaleString()
        }}</span>
      </template>

      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>KIDS 배송 마스터</v-toolbar-title>
          <v-divider class="mx-4" inset vertical></v-divider>
          <v-spacer></v-spacer>
          <v-dialog v-model="dialog" max-width="500px">
            <template v-slot:activator="{ on, attrs }">
              <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on">
                신규 등록
              </v-btn>
            </template>
            <v-card>
              <v-card-title>
                <span class="text-h5">{{ formTitle }}</span>
              </v-card-title>
              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col cols="12" sm="6" md="4">
                      <v-text-field
                          v-model="editedItem.deliveryCode"
                          label="배송순서"
                          :disabled="editModeCheck"
                      ></v-text-field>
                    </v-col>

                    <v-col cols="12" sm="6" md="4">
                      <v-text-field
                          v-model="editedItem.deliveryMd"
                          label="배송 기사"
                      ></v-text-field>
                    </v-col>
                    </v-row>
                    <v-row>
                    <v-col cols="12" sm="6" md="4">
                      <v-combobox
                          v-model="pkModel"
                          :items="pkModelStates"
                          label="포장 차수"
                          dense
                          item-text="text"
                          item-value="value"
                          :readonly="false"
                          return-object
                      ></v-combobox>
                    </v-col>
                  </v-row>
                </v-container>
              </v-card-text>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="close">
                  Cancel
                </v-btn>
                <v-btn color="blue darken-1" text @click="save">
                  Save
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
          <v-dialog v-model="dialogDelete" max-width="500px">
            <v-card>
              <v-card-title class="text-h5"
              >정말 삭제 하시겠습니까? [데이터베이스에서 완전 삭제
                됩니다]
              </v-card-title
              >
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="closeDelete"
                >Cancel
                </v-btn
                >
                <v-btn color="blue darken-1" text @click="deleteItemConfirm"
                >OK
                </v-btn
                >
                <v-spacer></v-spacer>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-toolbar>
      </template>
      <template v-slot:[`item.actions`]="{ item }">
        <v-icon small class="mr-2" @click="editItem(item)">
          mdi-pencil
        </v-icon>
        <v-icon small @click="deleteItem(item)">
          mdi-delete
        </v-icon>
      </template>
      <template v-slot:no-data>
        <v-btn color="primary" @click="initialize">
          Reset
        </v-btn>
      </template>
    </v-data-table>

    <v-form class="container" v-if="rowSelectCheck">
      <v-tabs
      >
        <v-tab>
          배송 순서별 거래처
        </v-tab>
        <v-tab-item>
          <kids-delivery-grid :Codedata="Codedata,Codename"></kids-delivery-grid>
        </v-tab-item>
        <v-tab>
          배송 순서별 거래처 세팅
        </v-tab>
        <v-tab-item>
          <kids-delivery-setting-grid :Codedata="Codedata"></kids-delivery-setting-grid>
        </v-tab-item>
      </v-tabs>
    </v-form>
  </v-form>
</template>

<script>
import kidsother from "@/dataset/otherColumn/kidsDelivery_Column_Json.json";
import axiosAP from "axios";
import store from "@/store";
import KidsDeliverySettingGrid from "@/components/otherComponents/kidsDeliverySettingGrid";
import KidsDeliveryGrid from "@/components/otherComponents/kidsDeliveryGrid";
import Xlsx from "xlsx";
import axios from "axios";

export default {
  name: "KidsDeliveryView",
  components: {KidsDeliveryGrid, KidsDeliverySettingGrid},
  data: () => ({
    rowSelectCheck: false,
    pkModel:[],
    pkModelStates : [{text: "당일", value : "A"},{text: "차일", value : "B"}],
    accountModel: [],
    accountStates: [],
    Codedata: "null",
    Codename: "null",
    dialog: false,
    dialogDelete: false,
    headers: kidsother,
    editModeCheck: false,
    desserts: [],
    editedIndex: -1,
    editedItem: {
      id: "",
      deliveryCode: "",
      deliveryMd: "",
      dayCode: "",
      daySeq: "",
      regCd: "",
      modCd: ""
    },
    defaultItem: {
      id: "",
      deliveryCode: "",
      deliveryMd: "",
      dayCode: "",
      daySeq: "",
      regCd: "",
      modCd: ""
    },
    inputfiles: '',
    inputFileResult: [],
    excelSendResult: [],
    execlExport: [
      {
        "배송번호": "",
        "배송기사": "",
        "행정주소": "",
        "거래처명": "",
        "차수": ""
      }],
  }),

  computed: {
    formTitle() {
      this.editModeCheck = this.editedIndex === -1 ? false : true;
      return this.editedIndex === -1 ? "New Item" : "Edit Item";
    }
  },

  watch: {
    dialog(val) {
      val || this.close();
    },
    dialogDelete(val) {
      val || this.closeDelete();
    }
  },

  created() {
    this.initialize();
  },

  methods: {
    makeExcelFile5() {
      const workBook = Xlsx.utils.book_new()
      const workSheet = Xlsx.utils.json_to_sheet(this.execlExport)
      Xlsx.utils.book_append_sheet(workBook, workSheet, 'KidsDelivery')
      Xlsx.writeFile(workBook, 'KidsDeliveryExport.xlsx')
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
        this.inputFileResult = tmpResult.KidsDelivery;
        var ColumnSet = this.inputFileResult.splice(0, 1);
        for (var j = 0; j < this.inputFileResult.length; j++) {
          var resultObject = {};
          resultObject = {
            "deliveryCode": this.inputFileResult[j][0],
            "deliveryMd": this.inputFileResult[j][1],
            "address1": this.inputFileResult[j][2],
            "vendorNm": this.inputFileResult[j][3],
            "regCd": store.state.userName,
            "modCd": store.state.userName,
          }
          this.excelSendResult.push(resultObject)
        }
        axios
            .post("/api/set/kidswmsupload", this.excelSendResult)
            .then(response => {
              alert(response.data.ResultValue);
            })
            .catch(ex => {
              alert(ex.message);
            });
      };

      reader.readAsArrayBuffer(file);
    },
    initialize() {
      axiosAP.get("/api/get/kidsWmsModel")
          .then(response => {
            this.desserts = response.data.data;
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
    editItem(item) {
      this.editedIndex = this.desserts.indexOf(item);
      this.editedItem = Object.assign({}, item);

      this.pkModel = [this.editedItem.daySeq];

      this.dialog = true;
    },
    deleteItem(item) {
      this.editedIndex = this.desserts.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogDelete = true;
    },
    deleteItemConfirm() {
      this.editedItem.regCd = store.state.userName;
      this.editedItem.modCd = store.state.userName;
      this.delServerDel(this.editedItem);
      this.desserts.splice(this.editedIndex, 1);
      this.closeDelete();
    },
    close() {
      this.dialog = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },
    closeDelete() {
      this.dialogDelete = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },
    save() {
      this.editedItem.regCd = store.state.userName;
      this.editedItem.modCd = store.state.userName;
      this.setServerSave(this.editedItem);
      this.close();
    },

    setServerSave(item) {
      if (this.editModeCheck) {
        item.daySeq = this.pkModel.text;
        item.dayCode = this.pkModel.value;

        axiosAP
            .post("/api/up/kidsWmsModel/" + this.editedItem.id, item)
            .then(response => {
              if (response.status == 200) {
                alert(response.data.ResultValue);
                this.initialize();
              } else {
                alert(response.data.ResultValue);
              }
            })
            .catch(ex => {
              alert(ex.message);
            });
      } else {
        item.daySeq = this.pkModel.text;
        item.dayCode = this.pkModel.value;

        axiosAP
            .post("/api/set/kidsWmsModel", item)
            .then(response => {
              if (response.status == 200) {
                alert(response.data.ResultValue);
                this.initialize();
              } else {
                alert(response.data.ResultValue);
              }
            })
            .catch(ex => {
              alert(ex.message);
            });
      }
    },
    delServerDel(item) {
      axiosAP
          .post("/api/del/kidsWmsModel", item)
          .then(response => {
            if (response.status == 200) {
              alert(response.data.ResultValue);
              this.initialize();
            } else {
              alert(response.data.ResultValue);
            }
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
    selectRow(event, {item}) {
      this.rowSelectCheck = true;
      this.Codedata = item.deliveryCode == null ? "None" : item.deliveryCode;
      this.Codename = item.deliveryMd == null ? "None" : item.deliveryMd;
      // this.$refs.childComponent.initialize();
    }

  }
};
</script>

<style scoped>

</style>