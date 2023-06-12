<template>
  <v-form style="padding: 10px">
  <v-data-table
    :headers="headers"
    :items="desserts"
    sort-by="calories"
    class="elevation-1"
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
        <v-toolbar-title>EQP MAST</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="800px">
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              color="primary"
              dark
              class="mb-2"
              @click="pupupOpwn()"
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
                      label="라인 명"
                      dense
                      item-text="text"
                      item-value="value"
                      :readonly="false"
                      return-object
                    ></v-combobox>
                  </v-col>

                  <v-col cols="12" sm="6" md="4">
                    <v-menu
                      ref="menu"
                      v-model="menu"
                      :close-on-content-click="false"
                      :return-value.sync="date"
                      transition="scale-transition"
                      offset-y
                      min-width="auto"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                          v-model="date"
                          label="운용 시작일"
                          prepend-icon="mdi-calendar"
                          readonly
                          v-bind="attrs"
                          v-on="on"
                        ></v-text-field>
                      </template>
                      <v-date-picker v-model="date" no-title scrollable>
                        <v-spacer></v-spacer>
                        <v-btn text color="primary" @click="menu = false">
                          Cancel
                        </v-btn>
                        <v-btn
                          text
                          color="primary"
                          @click="$refs.menu.save(date)"
                        >
                          OK
                        </v-btn>
                      </v-date-picker>
                    </v-menu>
                  </v-col>
                </v-row>

                <v-row>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field
                      v-model="editedItem.eqpName"
                      label="설비 명"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field
                      v-model="editedItem.aqCost"
                      label="취득 금액"
                    ></v-text-field>
                  </v-col>

                  <v-col cols="12" sm="6" md="4">
                    <v-text-field
                      v-model="editedItem.aqForm"
                      label="취득 형태"
                    ></v-text-field>
                  </v-col>

                  <v-col cols="12" sm="6" md="4">
                    <v-text-field
                      v-model="editedItem.regularPm"
                      label="정기 PM 구분"
                    ></v-text-field>
                  </v-col>

                  <v-col cols="12" sm="6" md="4">
                    <v-text-field
                      v-model="editedItem.dyCheck"
                      label="일일 점검"
                    ></v-text-field>
                  </v-col>

                  <v-col cols="12" sm="6" md="4">
                    <v-text-field
                      v-model="editedItem.wyCheck"
                      label="주간 점검"
                    ></v-text-field>
                  </v-col>

                  <v-col cols="12" sm="6" md="4">
                    <v-text-field
                      v-model="editedItem.myCheck"
                      label="월간 점검"
                    ></v-text-field>
                  </v-col>

                  <v-col cols="12" sm="6" md="4">
                    <v-text-field
                      v-model="editedItem.eqpManager"
                      label="설비 관리자"
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
              됩니다]</v-card-title
            >
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="closeDelete"
                >Cancel</v-btn
              >
              <v-btn color="blue darken-1" text @click="deleteItemConfirm"
                >OK</v-btn
              >
              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>
    <template v-slot:item.actions="{ item }">
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
import EqpMast from "../../dataset/mastColumn/eqpmast_Column_Json.json";
import axios from "../../service/axios";
import store from "@/store";
import axiosAP from "axios";
export default {
  name: "EqpMast",
  data: () => ({
    snack: false,
    snackColor: "",
    snackText: "",
    date: new Date(Date.now() - new Date().getTimezoneOffset() * 60000)
      .toISOString()
      .substr(0, 10),
    menu: false,
    modal: false,
    fabModel: [],
    fabModelItems: [],
    lineModel: [],
    lineModelStates: [],
    dialog: false,
    dialogDelete: false,
    headers: EqpMast,
    desserts: [],
    editedIndex: -1,
    editModeCheck: false,
    editedItem: {
      eqpCode: "",
      eqpName: "",
      lineCode: "",
      fabCode: "",
      fabName: "",
      opStart: "",
      aqCost: "",
      aqForm: "",
      regularPm: "",
      dyCheck: "",
      wyCheck: "",
      myCheck: "",
      eqpManager: "",
      regCd: "",
      modCd: ""
    },
    defaultItem: {
      eqpCode: "",
      eqpName: "",
      lineCode: "",
      fabCode: "",
      fabName: "",
      opStart: "",
      aqCost: "",
      aqForm: "",
      regularPm: "",
      dyCheck: "",
      wyCheck: "",
      myCheck: "",
      eqpManager: "",
      regCd: "",
      modCd: ""
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
      axiosAP
        .get("/api/get/eqpmast")
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
      this.fabModelEditClick();
      this.fabCobEditChange();
      this.date = this.editedItem.opStart;
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
        var data = {
          eqpCode: item.eqpCode,
          eqpName: item.eqpName,
          lineCode: item.lineCode,
          fabCode: item.fabCode,
          fabName: item.fabName,
          opStart: this.date,
          aqCost: item.aqCost,
          aqForm: item.aqForm,
          regularPm: item.regularPm,
          dyCheck: item.dyCheck,
          wyCheck: item.wyCheck,
          myCheck: item.myCheck,
          eqpManager: item.eqpManager,
          regCd: item.regCd,
          modCd: item.modCd
        };
        axios
          .post("/api/updateeqp/" + item.id, data)
          .then(response => {
            if (response.status == 200) {
              this.snack = true;
              this.snackColor = "primary";
              this.snackText = response.data.ResultValue;
              // alert(response.data.ResultValue);
              this.initialize();
            } else {
              this.snack = true;
              this.snackColor = "error";
              this.snackText = response.data.ResultValue;
              // alert(response.data.ResultValue);
            }
          })
          .catch(ex => {
            alert(ex.message);
          });
      } else {
        var newData = {
          eqpCode: item.eqpCode,
          eqpName: item.eqpName,
          lineCode: this.lineModel.vlaue,
          fabCode: this.fabModel.vlaue,
          fabName: this.fabModel.text,
          opStart: this.date,
          aqCost: item.aqCost,
          aqForm: item.aqForm,
          regularPm: item.regularPm,
          dyCheck: item.dyCheck,
          wyCheck: item.wyCheck,
          myCheck: item.myCheck,
          eqpManager: item.eqpManager,
          regCd: item.regCd,
          modCd: item.modCd
        };
        axios
          .post("/api/set/eqpmast", newData)
          .then(response => {
            if (response.status == 200) {
              this.snack = true;
              this.snackColor = "success";
              this.snackText = response.data.ResultValue;
              // alert(response.data.ResultValue);
              this.initialize();
            } else {
              this.snack = true;
              this.snackColor = "error";
              this.snackText = response.data.ResultValue;
              // alert(response.data.ResultValue);
            }
          })
          .catch(ex => {
            alert(ex.message);
          });
      }
    },
    delServerDel(item) {
      axios
        .post("/api/deleqp/", item)
        .then(response => {
          if (response.status == 200) {
            this.snack = true;
            this.snackColor = "success";
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
    pupupOpwn() {
      axiosAP
        .get("/api/get/fabmast")
        .then(response => {
          this.fabModel = [];
          this.fabModelItems = [];
          this.lineModelStates = [];
          this.lineModel = [];

          for (var i in response.data.data) {
            this.fabModelItems.push({
              text: response.data.data[i].fabName,
              vlaue: response.data.data[i].fabCode
            });
          }
        })
        .catch(ex => {
          alert(ex.message);
        });

      this.date = new Date(Date.now() - new Date().getTimezoneOffset() * 60000)
        .toISOString()
        .substr(0, 10);
    },
    fabCobChange(event) {
      axiosAP
        .get("/api/get/linemast/" + event.vlaue.toString())
        .then(response => {
          this.lineModelStates = [];
          this.lineModel = [];

          for (var i in response.data.data) {
            this.lineModelStates.push({
              text: response.data.data[i].lineName,
              vlaue: response.data.data[i].lineCode
            });
          }
        })
        .catch(ex => {
          alert(ex.message);
        });
    },
    fabModelEditClick() {
      axiosAP
        .get("/api/get/fabmast")
        .then(response => {
          this.fabModel = [];
          this.fabModelItems = [];

          for (var i in response.data.data) {
            this.fabModelItems.push({
              text: response.data.data[i].fabName,
              vlaue: response.data.data[i].fabCode
            });
          }
          this.fabModel = [this.editedItem.fabName];
        })
        .catch(ex => {
          alert(ex.message);
        });
    },
    fabCobEditChange() {
      axiosAP
        .get("/api/get/linemast/" + this.editedItem.fabCode)
        .then(response => {
          this.lineModelStates = [];
          this.lineModel = [];

          var lineName = "";

          for (var i in response.data.data) {
            if (this.editedItem.lineCode == response.data.data[i].lineCode) {
              lineName = response.data.data[i].lineName;
            }
            this.lineModelStates.push({
              text: response.data.data[i].lineName,
              vlaue: response.data.data[i].lineCode
            });
            this.lineModel = [lineName];
          }
        })
        .catch(ex => {
          alert(ex.message);
        });
    }
  }
};
</script>

<style scoped></style>
