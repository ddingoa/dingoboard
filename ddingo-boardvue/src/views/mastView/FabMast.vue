<template>
  <v-form style="padding: 10px">
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
          <v-toolbar-title>FAB MAST</v-toolbar-title>
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
                          v-model="editedItem.fabName"
                          label="공장 명"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="4">
                      <v-text-field
                          v-model="editedItem.fabManager"
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
          허브별 아이템
        </v-tab>
        <v-tab-item>
          <fab-item-grid :hubCodedata="hubCodedata, hubNameData"></fab-item-grid>
        </v-tab-item>
        <v-tab>
          허브별 아이템 세팅
        </v-tab>
        <v-tab-item>
          <fab-item-setting-grid :hubCodedata="hubCodedata, hubNameData"></fab-item-setting-grid>
        </v-tab-item>
      </v-tabs>
    </v-form>

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
import fabmast from "../../dataset/mastColumn/fabmast_Column_Json.json";

import axiosAP from "axios";
import store from "../../store";
import FabItemGrid from "@/components/mastComponent/fabItemGrid";
import FabItemSettingGrid from "@/components/mastComponent/fabItemSettingGrid";

export default {
  name: "FabMast",
  components: {FabItemSettingGrid, FabItemGrid},
  data: () => ({
    snack: false,
    snackColor: "",
    snackText: "",
    rowSelectCheck: false,
    hubCodedata: "null",
    hubNameData: "null",
    dialog: false,
    dialogDelete: false,
    headers: fabmast,
    editModeCheck: false,
    desserts: [],
    editedIndex: -1,
    editedItem: {
      id: "",
      fabCode: "",
      fabName: "",
      fabManager: "",
      regCd: "",
      modCd: ""
    },
    defaultItem: {
      id: "",
      fabCode: "",
      fabName: "",
      fabManager: "",
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
      axiosAP.get("/api/get/fabmast")
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
        axiosAP
            .post("/api/updatefab/" + this.editedItem.id, item)
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
        axiosAP
            .post("/api/set/fabmast", item)
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
      axiosAP
          .post("/api/del/fabmast", item)
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
    selectRow(event, {item}) {
      this.rowSelectCheck = true;
      this.hubCodedata = item.fabCode == null ? "None" : item.fabCode;
      this.hubNameData = item.fabName == null ? "None" : item.fabName;
      // this.$refs.childComponent.initialize();
    }

  }
};
</script>

<style scoped></style>
