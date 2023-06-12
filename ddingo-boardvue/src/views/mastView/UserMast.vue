<template>
  <v-form>
    <v-data-table
        :headers="headers"
        :items="desserts"
        sort-by="calories"
        class="elevation-1"
        @click:row="selectRow"
    >
      <template v-slot:item.Open="{ item }">
        <v-col class="fill-height" height="500">
          <v-card class="text-center grey d-flex flex-column align-center justify-center" height="100%"
                  @click="imgViewOpen(item)">
            <div>
              <v-icon class="mr-2">
                mdi-image-multiple
              </v-icon>
            </div>
          </v-card>
        </v-col>
      </template>

      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>유저 마스터</v-toolbar-title>
          <v-divider class="mx-4" inset vertical></v-divider>
          <v-spacer></v-spacer>
          <v-dialog v-model="dialog" max-width="500px">
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                  color="primary"
                  dark
                  class="mb-2"
                  @click="fabGetCode()"
                  v-bind="attrs"
                  v-on="on"
              >
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
                          v-model="editedItem.email"
                          label="사용자 이메일"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="4">
                      <v-text-field
                          v-model="editedItem.userName"
                          label="사용자명"
                      ></v-text-field>
                    </v-col>

                    <v-col cols="12" sm="6" md="4">
                      <v-text-field
                          v-model="editedItem.password"
                          label="사용자 비밀번호"
                          readonly
                          placeholder="111111"
                      ></v-text-field>
                    </v-col>

                    <v-col cols="12" sm="6" md="4">
                      <v-text-field
                          v-model="editedItem.userCheckInit"
                          label="처음 사용자 여부"
                          placeholder="false"
                      ></v-text-field>
                    </v-col>
                  </v-row>

                  <v-row>
                    <v-col cols="12" sm="6" md="4">
                      <v-combobox
                          v-model="fabModel"
                          :items="fabModelItems"
                          label="공장명"
                          dense
                          item-text="text"
                          item-value="value"
                          :readonly="false"
                          return-object
                          @change="event => fabCobChange(event)"
                      ></v-combobox>
                    </v-col>

                    <v-col cols="12" sm="6" md="4">
                      <v-combobox
                          v-model="lineModel"
                          :items="lineModelStates"
                          label="라인명"
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
        </v-toolbar>
      </template>
      <template v-slot:[`item.actions`]="{ item }">
        <v-icon small class="mr-2" @click="editItem(item)">
          mdi-pencil
        </v-icon>
      </template>
      <template v-slot:no-data>
        <v-btn color="primary" @click="initialize">
          Reset
        </v-btn>
      </template>
    </v-data-table>


    <v-row justify="center">
      <v-dialog v-model="imgdialog" persistent max-width="600">
        <v-card>
          <v-card-title class="text-h5">
            QR 코드
          </v-card-title>
          <v-form  id="printArea">
            <v-row>
            <div v-for="i in 40" >

                <qrcode-value :qrUserData="qrUserData"></qrcode-value>

            </div>
            </v-row>
          </v-form>
          <v-card-actions>
            <v-spacer></v-spacer>
            <button type="button" onclick="printJS('printArea', 'html')">
              인쇄
            </button>
            <v-btn color="green darken-1" text @click="imgPrint">
              확인
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>

    <v-snackbar v-model="snack"
                :timeout="3000"
                right
                :color="snackColor">
      {{ snackText }}

      <template v-slot:action="{ attrs }">
        <v-btn v-bind="attrs" text @click="snack = false">
          Close
        </v-btn>
      </template>
    </v-snackbar>

  </v-form>
</template>

<script>
import userMast from "../../dataset/mastColumn/usermast_Column_Json.json";
import axios from "axios";
import QrcodeValue from "@/components/qrCodeComponents/QrcodeValue";
import printJS from 'print-js'

export default {
  name: "UserMast",
  components: {QrcodeValue},
  data: () => ({
    snack: false,
    snackColor: "",
    snackText: "",
    qrUserData: {},
    imageInfos: [],
    fabModel: [],
    fabModelItems: [],
    lineModel: [],
    lineModelStates: [],
    workModel: [],
    workModelStates: [],
    dialog: false,
    dialogDelete: false,
    headers: userMast,
    editModeCheck: false,
    desserts: [],
    imgdialog: false,
    editedIndex: -1,
    editedItem: {
      email: "",
      userName: "",
      password: "111111",
      fabCode: "",
      fabName: "",
      lineCode: "",
      lineName: "",
      userCheckInit: ""
    },
    defaultItem: {
      email: "",
      userName: "",
      password: "111111",
      fabCode: "",
      fabName: "",
      lineCode: "",
      lineName: "",
      userCheckInit: "false"
    }
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
    initialize() {
      axios
          .get("/user/get/memberlist")
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
      this.fabGetCode(true);
      this.fabCobChange({vlaue: this.editedItem.fabCode}, true);
      this.dialog = true;
    },
    close() {
      this.dialog = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },
    save() {
      this.setServerSave(this.editedItem);
      this.close();
    },
    selectRow(item) {
      this.qrUserData = item;
    },
    setServerSave(item) {
      if (this.editModeCheck) {
        var updata = {
          email: item.email,
          userName: item.userName,
          password: item.password,
          fabCode: item.fabCode,
          fabName: item.fabName,
          lineCode: item.lineCode,
          lineName: item.lineName,
          userCheckInit: item.userCheckInit
        };

        axios
            .post("/auth/signupupdate", updata)
            .then(() => {
              this.initialize();
            })
            .catch(ex => {
              alert(ex.message);
            });
      } else {
        var data = {
          email: item.email,
          userName: item.userName,
          password: item.password,
          fabCode: this.fabModel.vlaue,
          fabName: this.fabModel.text,
          lineCode: this.lineModel.vlaue,
          lineName: this.lineModel.text
        };

        axios
            .post("/auth/signup", data)
            .then(() => {
              this.snack = true;
              this.snackColor = "primary";
              this.snackText = "사용자가 추가 되었습니다.";
              this.initialize();
            })
            .catch(ex => {
              alert(ex.message);
            });
      }
    },
    fabGetCode(check) {
      this.fabModel = [];
      this.fabModelItems = [];
      this.lineModel = [];
      this.lineModelStates = [];
      this.workModel = [];
      this.workModelStates = [];

      axios
          .get("/api/get/fabmast")
          .then(response => {
            for (var i in response.data.data) {
              this.fabModelItems.push({
                text: response.data.data[i].fabName,
                vlaue: response.data.data[i].fabCode
              });
            }
            if (check) {
              this.fabModel = [this.editedItem.fabName];
            }
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
    fabCobChange(event, check) {
      axios
          .get("/api/get/linemast/" + event.vlaue)
          .then(response => {
            this.lineModelStates = [];
            this.lineModel = [];

            for (var i in response.data.data) {
              this.lineModelStates.push({
                text: response.data.data[i].lineName,
                vlaue: response.data.data[i].lineCode
              });
            }
            if (check) {
              this.lineModel = [this.editedItem.lineName];
            }
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
    imgViewOpen(item) {
      this.imgdialog = true;
      this.imageInfos = [];
    },
    imgPrint() {
      this.imgdialog = false
    },
  }
};
</script>

<style scoped></style>
