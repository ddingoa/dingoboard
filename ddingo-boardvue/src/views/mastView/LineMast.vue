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
          <v-toolbar-title>LINE MAST</v-toolbar-title>
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
                      <v-combobox
                          v-model="fabModel"
                          :items="fabModelItems"
                          label="공장명"
                          dense
                          item-text="text"
                          item-value="value"
                          :readonly="false"
                          return-object
                      ></v-combobox>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="12" sm="6" md="4">
                      <v-text-field
                          v-model="editedItem.lineName"
                          label="라인 명"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="4">
                      <v-text-field
                          v-model="editedItem.rePerson"
                          label="필수 인원"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="4">
                      <v-text-field
                          v-model="editedItem.lineManager"
                          label="라인 관리자"
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
              >정말 삭제 하시겠습니까? [데이터베이스에서 완전 삭제 됩니다]
              </v-card-title>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="closeDelete"
                >Cancel
                </v-btn>
                <v-btn color="blue darken-1" text @click="deleteItemConfirm"
                >OK
                </v-btn>
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
import Linemast from "../../dataset/mastColumn/linemast_Column_Json.json";
import axios from "axios";
import store from "../../store";

export default {
  name: "LineMast",
  data: () => ({
    snack: false,
    snackColor: "",
    snackText: "",
    fabModel: [],
    fabModelItems: [],
    dialog: false,
    dialogDelete: false,
    headers: Linemast,
    editModeCheck: false,
    desserts: [],
    editedIndex: -1,
    editedItem: {
      id: "",
      lineCode: "",
      lineName: "",
      btDepartment: "",
      fabCode: "",
      fabName: "",
      rePerson: "",
      lineManager: "",
      regCd: "",
      modCd: ""
    },
    defaultItem: {
      id: "",
      lineCode: "",
      lineName: "",
      btDepartment: "",
      fabCode: "",
      fabName: "",
      rePerson: "",
      lineManager: "",
      regCd: "",
      modCd: ""
    }
  }),

  computed: {
    formTitle() {
      this.editModeCheck = this.editedIndex === -1 ? false : true;
      return this.editedIndex === -1 ? "신규 등록" : "수정 아이템";
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
          .get("/api/get/linemast")
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
          btDepartment: item.btDepartment,
          lineCode: item.lineCode,
          lineName: item.lineName,
          fabCode: item.fabCode,
          fabName: item.fabName,
          rePerson: item.rePerson,
          lineManager: item.lineManager,
          regCd: item.regCd,
          modCd: item.modCd
        };
        axios
            .post("/api/updateline/" + this.editedItem.id, updata)
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
      } else {
        var data = {
          btDepartment: item.btDepartment,
          lineCode: item.lineCode,
          lineName: item.lineName,
          fabCode: this.fabModel.vlaue,
          fabName: this.fabModel.text,
          rePerson: item.rePerson,
          lineManager: item.lineManager,
          regCd: item.regCd,
          modCd: item.modCd
        };
        axios
            .post("/api/set/linemast", data)
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
      }
    },
    delServerDel(item) {
      axios
          .post("/api/del/line", item)
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

    fabGetCode() {
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
    }
  }
};
</script>

<style scoped></style>
