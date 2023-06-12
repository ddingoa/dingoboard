<template>
  <form style="padding: 10px">
    <v-card>
      <v-card-title class="text-h5 font-weight-regular indigo darken-1 white--text">
        작업 등록
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
          @click="fabCobClick()"
          @change="event => fabCobChange(event)"
          return-object
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
          @change="event => lineCobChange()"
          return-object
        >
        </v-autocomplete>
      </v-card-text>
    </v-card>

    <v-data-table
      :headers="headers"
      :items="desserts"
      sort-by="calories"
      class="elevation-1"
      style="padding-top: 10px"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>라인별 작업 등록</v-toolbar-title>
          <v-divider class="mx-4" inset vertical></v-divider>
          <v-spacer></v-spacer>
          <v-dialog v-model="dialog" max-width="500px">
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                color="primary"
                dark
                class="mb-2"
                @click="eqpModelClick()"
                v-bind="attrs"
                v-on="on"
                :disabled="btnNewCheck == false"
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
                        v-model="editedItem.workName"
                        label="작업 명"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="4">
                      <v-text-field
                        v-model="editedItem.lineManager"
                        label="작업 관리자"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="4">
                      <v-text-field
                          v-model="editedItem.rePerson"
                          label="작업 필수 인원"
                      ></v-text-field>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="12" sm="6" md="4">
                      <v-combobox
                        v-model="eqpModel"
                        :items="eqpModelItems"
                        label="설비명"
                        dense
                        item-text="text"
                        item-value="value"
                        :readonly="false"
                        @click="getEQPData"
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
import opsWorkJson from "../../dataset/opsWork/opsWork_Column_Json.json";
import axios from "axios";
import store from "@/store";

export default {
  name: "OpsWorkView",
  data: () => ({
    btnNewCheck: false,
    hubModel: {},
    hubStates: [],
    lineModel: {},
    lineModelStates: [],
    eqpModel: [],
    eqpModelItems: [],
    dialog: false,
    dialogDelete: false,
    headers: opsWorkJson,
    editModeCheck: false,
    desserts: [],
    editedIndex: -1,
    editedItem: {
      workCode: "",
      workName: "",
      eqpCode: "",
      eqpName: "",
      fabCode: "",
      fabName: "",
      lineCode: "",
      lineManager: "",
      rePerson: "",
      regCd: "",
      modCd: ""
    },
    defaultItem: {
      workCode: "",
      workName: "",
      eqpCode: "",
      eqpName: "",
      fabCode: "",
      fabName: "",
      lineCode: "",
      lineManager: "",
      rePerson: "",
      regCd: "",
      modCd: ""
    }
  }),

  computed: {
    formTitle() {
      this.editModeCheck = this.editedIndex === -1 ? false : true;
      return this.editedIndex === -1 ? "신규 등록" : "작업 수정";
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
    // this.initialize();
  },
  methods: {
    initialize() {
      if (this.hubModel.vlaue != null && this.lineModel.vlaue != null) {
        this.lineCobChange();
      } else {
        axios
          .get("/api/get/opswork")
          .then(response => {
            this.desserts = response.data.data;
          })
          .catch(ex => {
            alert(ex.message);
          });
      }
    },
    editItem(item) {
      this.editedIndex = this.desserts.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.eqpModelEditClick();
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
        var updata = {
          workCode: item.workCode,
          workName: item.workName,
          eqpCode: this.eqpModel.vlaue == null ? item.eqpCode : this.eqpModel.vlaue,
          eqpName: this.eqpModel.text == null ? item.eqpName : this.eqpModel.text,
          fabCode: item.fabCode,
          fabName: item.fabName,
          lineCode: item.lineCode,
          lineManager: item.lineManager,
          rePerson: item.rePerson,
          regCd: item.regCd,
          modCd: item.modCd
        };

        axios
          .post("/api/upopswork/" + item.id, updata)
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
          workCode: item.workCode,
          workName: item.workName,
          eqpCode: this.eqpModel.vlaue,
          eqpName: this.eqpModel.text,
          fabCode: this.hubModel.vlaue,
          fabName: this.hubModel.text,
          lineCode: this.lineModel.vlaue,
          lineManager: item.lineManager,
          rePerson: item.rePerson,
          regCd: item.regCd,
          modCd: item.modCd
        };

        axios
          .post("/api/set/opswork", data)
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
    eqpModelEditClick(vlaue) {
      axios
        .get("/api/get/eqpmastline/" + vlaue)
        .then(response => {
          this.eqpModel = [];
          this.eqpModelItems = [];
          for (var i in response.data.data) {
            this.eqpModelItems.push({
              text: response.data.data[i].eqpName,
              vlaue: response.data.data[i].eqpCode
            });
          }
          this.eqpModel = [this.editedItem.eqpName];
        })
        .catch(ex => {
          alert(ex.message);
        });
    },
    eqpModelClick() {
      axios
        .get("/api/get/eqpmastline/" + this.lineModel.vlaue.toString())
        .then(response => {
          this.eqpModel = [];
          this.eqpModelItems = [];
          for (var i in response.data.data) {
            this.eqpModelItems.push({
              text: response.data.data[i].eqpName,
              vlaue: response.data.data[i].eqpCode
            });
          }
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
          this.lineModel = {};

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
    lineCobChange() {
      var data = {
        fabCode: this.hubModel.vlaue,
        lineCode: this.lineModel.vlaue
      };
      axios
        .post("/api/get/fablinework", data)
        .then(response => {
          if (response.status == 200) {
            this.desserts = response.data.data;
            this.btnNewCheck = true;
          } else {
            alert(response.data.ResultValue);
          }
        })
        .catch(ex => {
          alert(ex.message);
        });
    },
    delServerDel(item) {
      axios
        .post("/api/del/opswork", item)
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
    getEQPData() {
      axios
          .get("/api/get/eqpmast")
          .then(response => {
            this.eqpModelItems = [];
            for (var i in response.data.data) {
              this.eqpModelItems.push({
                text: response.data.data[i].eqpName,
                vlaue: response.data.data[i].eqpCode
              });
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
