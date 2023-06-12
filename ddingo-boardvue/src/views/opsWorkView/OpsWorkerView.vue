<template>
  <form style="padding: 10px">
    <v-card>
      <v-card-title class="text-h5 font-weight-regular indigo darken-1 white--text">
        작업자 등록
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
          @click="fabCobClick()"
          @change="event => fabCobChange(event)"
        >
        </v-autocomplete>

        <v-subheader class="pa-0">
          라인 구분
        </v-subheader>
        <v-autocomplete
          v-model="lineModel"
          :items="lineModelStates"
          item-text="text"
          item-value="value"
          :readonly="false"
          return-object
          @change="event => lineCobChange()"
        >
        </v-autocomplete>

        <v-subheader class="pa-0">
          작업 구분
        </v-subheader>
        <v-autocomplete
          v-model="workModel"
          :items="workModelStates"
          item-text="text"
          item-value="value"
          :readonly="false"
          return-object
          @change="event => workCobChange()"
        >
        </v-autocomplete>
      </v-card-text>
    </v-card>
    <v-data-table
      :headers="headers"
      :items="desserts"
      :single-expand="singleExpand"
      :expanded.sync="expanded"
      item-key="id"
      @item-expanded="expandedEvent($event)"
      show-expand
      class="elevation-1"
    >
      <template v-slot:expanded-item="{ headers, item }">
        <td :colspan="headers.length">
          <p class="blue lighten-2 white--text" style="font-size: large">
            {{ item.opName }}
          </p>
          <form class="container">
            <v-simple-table>
              <template v-slot:default>
                <thead>
                  <tr>
                    <th
                      class="text-left"
                      v-for="(item, index) in workTimeheaders"
                      :key="item.text"
                    >
                      <div v-show="index > 1">{{ item.text }}</div>
                    </th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td
                      v-for="(item, index) in workerTime[selectIndex]"
                      :key="item.key"
                    >
                      <v-checkbox
                        v-show="index != 'id' && index != 'opCode'"
                        v-model="workTimeeditedItem[index] = item"
                        style="margin:0px;padding:0px"
                        hide-details
                      />
                    </td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>

            <div style="float:right; margin-top: 20px;margin-bottom: 10px">
              <v-btn
                class="mr-4 pa-1"
                @click="saveGridData(workerTime[selectIndex])"
                >Save</v-btn
              >
            </div>
          </form>
        </td>
      </template>

      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>작업별 작업자 등록</v-toolbar-title>
          <v-divider class="mx-4" inset vertical></v-divider>
          <v-spacer></v-spacer>
          <v-dialog v-model="dialog" max-width="500px">
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                color="primary"
                dark
                class="mb-2"
                v-bind="attrs"
                v-on="on"
                :disabled="btnNewCheck == false"
              >
                작업자 등록
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
                          v-model="workerModel"
                          :items="workerStates"
                          label="작업자명"
                          dense
                          item-text="text"
                          item-value="value"
                          :readonly="false"
                          @click="getworkerdata"
                          return-object
                          :disabled="editModeCheck"
                      ></v-combobox>
                    </v-col>

                    <v-col cols="12" sm="6" md="4">
                      <v-combobox
                        v-model="hubModel"
                        :items="hubStates"
                        label="허브명"
                        dense
                        item-text="text"
                        item-value="value"
                        :readonly="false"
                        return-object
                        @click="fabCobGetData"
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
                        @click="lineCobGetData"
                      ></v-combobox>
                    </v-col>

                    <v-col cols="12" sm="6" md="4">
                      <v-combobox
                        v-model="workModel"
                        :items="workModelStates"
                        label="작업명"
                        dense
                        item-text="text"
                        item-value="value"
                        :readonly="false"
                        return-object
                        @click="workCobGetData"
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
  </form>
</template>

<script>
import opsWorkerJson from "../../dataset/opsWork/opWorker_Column_Json.json";
import axios from "axios";
import store from "../../store";
import workTime from "../../dataset/opsWork/opsWorkerTime_Column_Json.json";

export default {
  name: "OpsWorkerView",

  data: () => ({
    btnNewCheck: false,
    selectIndex:  -1,
    search: "",
    expanded: [],
    singleExpand: true,
    hubModel: [],
    hubStates: [],
    lineModel: [],
    lineModelStates: [],
    workModel: [],
    workModelStates: [],
    workerModel: [],
    workerStates: [],
    dialog: false,
    dialogDelete: false,
    headers: opsWorkerJson,
    editModeCheck: false,
    desserts: [],
    workerTime: [],
    editedIndex: -1,
    editedItem: {
      opCode: "",
      opName: "",
      fabCode: "",
      lineCode: "",
      workCode: "",
      workName: "",
      fabName: "",
      lineName: "",
      regCd: "",
      modCd: ""
    },
    defaultItem: {
      opCode: "",
      opName: "",
      fabCode: "",
      lineCode: "",
      fabName: "",
      lineName: "",
      workCode: "",
      workName: "",
      regCd: "",
      modCd: ""
    },
    workTimeheaders: workTime,
    workTimeeditedItem: {
      opCode: "",
      workTime20: "",
      workTime21: "",
      workTime22: "",
      workTime23: "",
      workTime00: "",
      workTime02: "",
      workTime03: "",
      workTime04: "",
      workTime05: "",
      workTime06: "",
      workTime07: "",
      workTime08: "",
      workTime09: "",
      workTime10: "",
      workTime11: "",
      workTime12: "",
      workTime13: "",
      workTime14: "",
      workTime15: "",
      workTime16: "",
      workTime17: "",
      workTime18: "",
      workTime19: "",
      regCd: "",
      modCd: ""
    }
  }),

  computed: {
    formTitle() {
      this.editModeCheck = this.editedIndex === -1 ? false : true;
      return this.editedIndex === -1 ? "작업자 등록" : "작업자 등록 수정";
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
    expandedEvent(data){
      this.selectIndex = this.desserts.indexOf(data.item);
    },
    initialize() {
      axios
        .get("/api/get/opsworker")
        .then(response => {
          this.desserts = response.data.workerData;
          this.workerTime = response.data.workerTimeData;
        })
        .catch(ex => {
          alert(ex.message);
        });
    },
    editItem(item) {
      this.editedIndex = this.desserts.indexOf(item);
      this.editedItem = Object.assign({}, item);

      this.hubModel = [this.editedItem.fabName];
      this.lineModel = [this.editedItem.lineName];
      this.workModel = [this.editedItem.workName];
      this.workerModel =  [this.editedItem.opName];

      this.dialog = true;
    },
    deleteItem(item) {
      this.editedIndex = this.desserts.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogDelete = true;
    },
    deleteItemConfirm() {
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
        var updata = {
          id : item.id,
          opCode: this.workerModel.vlaue == null ? item.opCode : this.workerModel.vlaue,
          opName: this.workerModel.text== null ? item.opName : this.workerModel.text,
          fabCode: this.hubModel.vlaue== null ? item.fabCode : this.hubModel.vlaue,
          lineCode: this.lineModel.vlaue== null ? item.lineCode : this.lineModel.vlaue,
          fabName: this.hubModel.text== null ? item.fabName : this.hubModel.text,
          lineName: this.lineModel.text== null ? item.lineName : this.lineModel.text,
          workCode: this.workModel.vlaue== null ? item.workCode : this.workModel.vlaue,
          workName: this.workModel.text== null ? item.workName : this.workModel.text,
          regCd: item.regCd,
          modCd: item.modCd
        };

        axios
          .post("/api/upopsworker/" + item.id, updata)
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
        var data = {
          opCode: this.workerModel.vlaue,
          opName: this.workerModel.text,
          fabCode: this.hubModel.vlaue,
          lineCode: this.lineModel.vlaue,
          fabName: this.hubModel.text,
          lineName: this.lineModel.text,
          workCode: this.workModel.vlaue,
          workName: this.workModel.text,
          regCd: item.regCd,
          modCd: item.modCd
        };

        axios
          .post("/api/set/opsworker", data)
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
      // axios
      //   .delete("/api/deleline/" + item.LINE_CODE)
      //   .then()
      //   .catch(ex => {
      //     alert(ex.message);
      //   });
    },
    fabCobClick() {
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

          this.btnNewCheck = false;
          this.desserts = [];
        })
        .catch(ex => {
          alert(ex.message);
        });
    },
    fabCobChange(event) {
      axios
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
    lineCobChange() {
      var data = {
        fabCode: this.hubModel.vlaue,
        lineCode: this.lineModel.vlaue
      };
      axios
        .post("/api/get/fablinework", data)
        .then(response => {
          if (response.status == 200) {
            for (var i in response.data.data) {
              this.workModelStates.push({
                text: response.data.data[i].workName,
                vlaue: response.data.data[i].workCode
              });
            }
          } else {
            alert(response.data.ResultValue);
          }
        })
        .catch(ex => {
          alert(ex.message);
        });
    },
    fabModelEditClick() {
      axios
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
    getworkerdata(){
      axios
          .get("/user/get/memberlist")
          .then(response => {
            if (response.status == 200) {
              for (var i in response.data.data) {
                this.workerStates.push({
                  text: response.data.data[i].userName,
                  vlaue: response.data.data[i].userName
                });
              }
            } else {
              alert(response.data.ResultValue);
            }
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
    fabCobEditChange() {
      axios
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
    },
    workCobChange() {
      var data = {
        fabCode: this.hubModel.vlaue,
        lineCode: this.lineModel.vlaue,
        workCode: this.workModel.vlaue
      };
      axios
        .post("/api/get/fablineworker", data)
        .then(response => {
          if (response.status == 200) {
            this.desserts = response.data.workerData;
            this.workerTime = response.data.workerTimeData;

            this.btnNewCheck = true;
            alert(response.data.ResultValue);
          } else {
            alert(response.data.ResultValue);
          }
        })
        .catch(ex => {
          alert(ex.message);
        });
    },
    saveGridData(workData) {
      // this.editedItem = Object.assign({}, this.selectExcludes);
      this.workTimeeditedItem.id = workData.id;
      this.workTimeeditedItem.opCode = workData.opCode;
      this.workTimeeditedItem.regCd = store.state.userName;
      this.workTimeeditedItem.modCd = store.state.userName;

      axios
        .post("/api/set/opsworkertime", this.workTimeeditedItem)
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
    fabCobGetData() {
      axios
          .get("/api/get/fabmast")
          .then(response => {
            this.fabModelItems = [];
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
    },
    lineCobGetData(){
      axios
          .get("/api/get/linemast")
          .then(response => {
            this.lineModelStates = [];
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
    workCobGetData(){
      axios
          .get("/api/get/opswork")
          .then(response => {
            this.workModelStates = [];
            for (var i in response.data.data) {
              this.workModelStates.push({
                text: response.data.data[i].workName,
                vlaue: response.data.data[i].workCode
              });
            }
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
  }
};
</script>

<style scoped></style>
