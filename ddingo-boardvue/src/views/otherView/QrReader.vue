<template>
  <form style="padding: 10px">
    <v-card>
      <v-toolbar
          flat
          color="primary"
          dark
      >
        <v-toolbar-title>BBODEK QR Reader</v-toolbar-title>
      </v-toolbar>
      <v-tabs vertical>
        <v-tab>
          <v-icon left>
            mdi-account
          </v-icon>
          QR Reader
        </v-tab>
        <v-tab>
          <v-icon left>
            mdi-lock
          </v-icon>
          QR MAST
        </v-tab>

        <v-tab-item>
          <v-col cols="12" style="height: 700px">
            <v-row>
              <v-col cols="4">
                <v-btn block depressed
                       color="primary"
                       :loading="loading4"
                       :disabled="loading4"
                       @click="reload">Reload
                  <template v-slot:loader>
                    <span class="custom-loader">
                      <v-icon light>mdi-cached</v-icon>
                    </span>
                  </template>
                </v-btn>
                <v-card>
                  <qrcode-reader @qrcodereader="qrcodereaderChange" v-if="!destroyed"></qrcode-reader>
                </v-card>
              </v-col>
              <v-col cols="8" style="background-color: #42b983">
                <v-data-table
                    :headers="viewheaders"
                    :items="desserts"
                    :items-per-page="10"
                    class="elevation-1"
                ></v-data-table>
              </v-col>
            </v-row>
          </v-col>
        </v-tab-item>

        <v-tab-item>
          <v-col cols="12" style="background-color:blue;height: 700px ">
            <v-text-field
                v-model="search"
                append-icon="mdi-magnify"
                label="Search"
                single-line
                hide-details
                background-color="blue-grey lighten-5"
            ></v-text-field>

            <v-data-table
                :headers="headers"
                :items="desserts"
                :items-per-page="10"
                :search="search"
                class="elevation-1"
            >
              <template v-slot:top>
                <v-toolbar flat>
                  <v-toolbar-title>Label Mast</v-toolbar-title>
                  <v-divider class="mx-4" inset vertical></v-divider>
                  <v-spacer></v-spacer>
                  <v-dialog v-model="Eidtdialog" max-width="500px">
                    <v-card>
                      <v-card-title>
                        <span class="text-h5">{{ formTitle }}</span>
                      </v-card-title>
                      <v-card-text>
                        <v-container>
                          <v-row>
                            <v-col cols="12" sm="6" md="4">
                              <v-text-field
                                  v-model="editedItem.labeName"
                                  label="이름"
                              ></v-text-field>
                            </v-col>
                            <v-col cols="12" sm="6" md="4">
                              <v-text-field
                                  v-model="editedItem.labePW"
                                  label="번호"
                              ></v-text-field>
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
                        <v-btn color="blue darken-1" text @click="deleteItemConfirm"
                        >Cancel
                        </v-btn
                        >
                        <v-btn color="blue darken-1" text @click="valueDelete"
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

            </v-data-table>
          </v-col>
        </v-tab-item>

      </v-tabs>
    </v-card>

    <v-dialog
        v-model="fromdialog"
        persistent
        max-width="450px"
    >
      <v-card>
        <v-card-title>
          <span class="text-h5">Label Profile</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-col
                cols="12"
            >
              <v-text-field
                  label="Label Number"
                  hint="BBODEK Label Number"
                  v-model="this.rederItemValue"
                  persistent-hint
                  required
                  readonly
              ></v-text-field>
            </v-col>


            <v-col
                cols="12"
            >
              <v-text-field
                  label="Label name*"
                  required
                  v-model="labelName"
              ></v-text-field>
            </v-col>

            <v-col
                cols="12"
            >
              <v-text-field
                  label="Label Number PW*"
                  hint="Set Password"
                  persistent-hint
                  required
                  v-model="labelpw"
              ></v-text-field>
            </v-col>

            <v-col
                cols="12"
            >
              <v-textarea
                  solo
                  name="input-7-4"
                  label="Note"
                  v-model="labelnote"
              ></v-textarea>
            </v-col>

          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
              color="blue darken-1"
              text
              @click="closePopUp"
          >
            취소
          </v-btn>
          <v-btn
              color="blue darken-1"
              text
              @click="checkPopUp"
          >
            저장
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>


    <v-dialog
        v-model="checkDialog"
        width="500"
    >
      <v-card>
        <v-card-title class="text-h5 grey lighten-2">
          반납 처리
        </v-card-title>

        <v-card-text>
          중복 코드 입니다.
          반납 처리 하시겠습니까?!
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
              color="error"
              text
              @click="checkDialog = false"
          >
            취소
          </v-btn>
          <v-btn
              color="primary"
              text
              @click="checkCodeItem"
          >
            반납
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>


    <v-snackbar v-model="snack"
                :timeout="3000"
                right
                top
                :color="snackColor">
      {{ snackText }}

      <template v-slot:action="{ attrs }">
        <v-btn v-bind="attrs" text @click="snack = false">
          Close
        </v-btn>
      </template>
    </v-snackbar>
  </form>
</template>

<script>
import printJS from 'print-js'
import QrcodeReader from "@/components/qrCodeComponents/QrcodeReader";
import QrcodeValue from "@/components/qrCodeComponents/QrcodeValue";
import store from "@/store";
import axiosAP from "axios";

export default {
  name: "QrReader",
  components: {QrcodeReader},
  data: () => ({
    Eidtdialog: false,
    dialogDelete: false,
    snack: false,
    snackColor: "",
    snackText: "",
    destroyed: false,
    labelnote: "",
    labelpw: "",
    labelName: "",
    dialog: true,
    hubModel: [],
    hubStates: [],
    productModel: [],
    productStates: [],
    accountModel: [],
    accountStates: [],
    rederValue: [],
    checkDialog: false,
    fromdialog: false,
    search: '',
    loader: null,
    loading4: false,
    qrUserData: {'id': '0001'},
    editedItem: {
      id: "",
      qrCode: "",
      createdDate: "",
      labeName: "",
      labePW: "",
      labeNote: "",
      returnTime: "",
      outTime: ""
    },
    headers: [
      {text: "Actions", value: "actions", sortable: "false", width: "150px"},
      {
        text: '번호',
        align: 'start',
        sortable: false,
        value: 'id',
      },
      {text: '코드 번호', value: 'qrCode'},
      {text: '출하 시간', value: 'outTime'},
      {text: '반납 시간', value: 'returnTime'},
      {text: '이름', value: 'labeName'},
      {text: 'PW', value: 'labePW'},
      {text: '비고', value: 'labeNote'},
    ],
    viewheaders: [
      {
        text: '번호',
        align: 'start',
        sortable: false,
        value: 'id',
      },
      {text: '코드 번호', value: 'qrCode'},
      {text: '출하 시간', value: 'outTime'},
      {text: '반납 시간', value: 'returnTime'},
    ],
    desserts: [],
    rederItemValue: ""
  }),
  computed: {
    formTitle() {
      this.editModeCheck = this.editedIndex === -1 ? false : true;
      return this.editedIndex === -1 ? "New Item" : "Edit Item";
    }
  },

  watch: {
    loader() {
      const l = this.loader
      this[l] = !this[l]
      setTimeout(() => (this[l] = false), 3000)
      this.loader = null
    },
    dialog(val) {
      val || this.close();
    },
  },
  created() {
    this.initialize();
  },
  methods: {
    async onInit(promise) {
      this.loading = true
      try {
        await promise
      } catch (error) {
        console.error(error)
      } finally {
        this.loading = false
      }
    },
    async reload() {
      this.loader = 'loading4'
      this.destroyed = true
      await this.$nextTick()
      this.destroyed = false
    },
    checkCodeItem() {
      axiosAP
          .post("/api/label/updatacode/" + this.rederItemValue)
          .then(response => {
            if (response.status == 200) {
              this.snack = true;
              this.snackColor = "primary";
              this.snackText = response.data.ResultValue;
              this.initialize();
            } else {
              this.snack = true;
              this.snackColor = "error";
              this.snackText = response.data.ResultValue;
            }
          })
          .catch(ex => {
            alert(ex.message);
          });
      this.checkDialog = false
    },
    editItem(item) {
      this.editedIndex = this.desserts.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.Eidtdialog = true;
    },
    deleteItem(item) {
      this.editedIndex = this.desserts.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogDelete = true;
    },
    valueDelete() {
      axiosAP
          .post("/api/dellabel/" + this.editedItem.id)
          .then(response => {
            if (response.status == 200) {
              this.snack = true;
              this.snackColor = "primary";
              this.snackText = response.data.ResultValue;
              this.initialize();
            } else {
              this.snack = true;
              this.snackColor = "error";
              this.snackText = response.data.ResultValue;
            }
          })
          .catch(ex => {
            alert(ex.message);
          });

    },
    close() {
      this.Eidtdialog = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },
    save() {

      var item = {
        id: this.editedItem.id,
        qrCode: this.editedItem.qrCode,
        labeName: this.editedItem.labeName,
        labePW: this.editedItem.labePW,
        labeNote: this.editedItem.labeNote
      }

      axiosAP
          .post("/api/set/qrdata", item)
          .then(response => {
            if (response.status == 200) {
              this.snack = true;
              this.snackColor = "primary";
              this.snackText = response.data.ResultValue;
              this.initialize();
            } else {
              this.snack = true;
              this.snackColor = "error";
              this.snackText = response.data.ResultValue;
            }
          })
          .catch(ex => {
            alert(ex.message);
          });
      this.Eidtdialog = false;
    },
    deleteItemConfirm() {
      this.dialogDelete = false;
    },
    initialize() {
      axiosAP.get("/api/get/qrdata")
          .then(response => {
            this.desserts = response.data.data;
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
    checkPopUp() {
      var item = {
        qrCode: this.rederItemValue,
        labeName: this.labelName,
        labePW: this.labelpw,
        labeNote: this.labelnote
      }

      axiosAP
          .post("/api/set/qrdata", item)
          .then(response => {
            if (response.status == 200) {
              this.snack = true;
              this.snackColor = "primary";
              this.snackText = response.data.ResultValue;
              this.initialize();
            } else {
              this.snack = true;
              this.snackColor = "error";
              this.snackText = response.data.ResultValue;
            }
          })
          .catch(ex => {
            alert(ex.message);
          });


      this.fromdialog = false;
    },
    closePopUp() {
      this.fromdialog = false;
    },
    qrcodereaderChange(reader) {
      var readerValue = reader;
      var readerValueCheck = reader.substr(0, 6);
      if (readerValueCheck == "BBODEK") {
        this.labelpw = "";
        this.labelName = "";
        this.labelnote = "";
        var checkUnit = true;
        if (this.fromdialog == false) {
          this.rederItemValue = readerValue;
          var audio = new Audio('http://soundbible.com/mp3/Air Plane Ding-SoundBible.com-496729130.mp3');
          audio.play();
          if (this.desserts.length > 0) {
            for (var i = 0; i < this.desserts.length; i++) {
              if (this.desserts[i]["qrCode"] == this.rederItemValue) {
                checkUnit = false;
              }
            }
            if (checkUnit) {
              this.fromdialog = true;
            } else {
              this.checkDialog = true;
              // this.snack = true;
              // this.snackColor = "error";
              // this.snackText = "중복 코드 입니다.";
            }
          } else {
            this.fromdialog = true;
          }

        }
      }
    }

  }
};

function timestamp() {
  var today = new Date();
  today.setHours(today.getHours() + 9);
  return today.toISOString().replace('T', ' ').substring(0, 19);
}
</script>

<style scoped></style>
