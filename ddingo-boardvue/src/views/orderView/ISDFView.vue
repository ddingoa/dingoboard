<template>
  <v-form style="padding: 10px">
    <v-card>
      <v-card-title class="text-h5 font-weight-regular blue-grey white--text">
        검수 불량 등록
      </v-card-title>
      <v-card-text>
        <v-row>
          <v-col cols="5">
            <v-subheader class="pa-0">
              시작 시간
            </v-subheader>
            <v-menu
              ref="menu1"
              v-model="menu1"
              :close-on-content-click="false"
              transition="scale-transition"
              offset-y
              max-width="290px"
              min-width="auto"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-text-field
                  v-model="frontDate"
                  label="Start Date"
                  hint="YYYY-MM-DD format"
                  persistent-hint
                  prepend-icon="mdi-calendar"
                  v-bind="attrs"
                  v-on="on"
                ></v-text-field>
              </template>
              <v-date-picker
                v-model="frontDate"
                no-title
                locale="ko-ko"
                @input="menu1 = false"
              ></v-date-picker>
            </v-menu>
          </v-col>
          <v-col cols="1"> </v-col>
          <v-col cols="5">
            <v-subheader class="pa-0">
              조건 시간
            </v-subheader>
            <v-menu
              ref="menu2"
              v-model="menu2"
              :close-on-content-click="false"
              transition="scale-transition"
              offset-y
              max-width="290px"
              min-width="auto"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-text-field
                  v-model="toDate"
                  label="End Date"
                  hint="YYYY-MM-DD format"
                  persistent-hint
                  prepend-icon="mdi-calendar"
                  v-bind="attrs"
                  v-on="on"
                ></v-text-field>
              </template>
              <v-date-picker
                v-model="toDate"
                no-title
                locale="ko-ko"
                @input="menu2 = false"
              ></v-date-picker>
            </v-menu>
          </v-col>
          <v-col cols="1"> </v-col>
        </v-row>
        <v-row>
          <v-col class="text-right">
            <v-btn
              :loading="loading3"
              :disabled="loading3"
              color="blue-grey"
              class="ma-2 white--text"
              @click="loader = 'loading3'"
            >
              조회
              <v-icon right dark>
                mdi-magnify
              </v-icon>
            </v-btn>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>

    <v-data-table
      :headers="headers"
      :items="desserts"
      sort-by="calories"
      class="elevation-1"
      style="padding-top: 10px"
    >
      <template v-slot:item.Open="{ item }" >
        <v-col class="fill-height" height="500">
          <v-card class="text-center grey d-flex flex-column align-center justify-center" height="100%" @click="imgViewOpen(item)">
            <div>
              <v-icon  class="mr-2">
                mdi-image-multiple
              </v-icon>
            </div>
          </v-card>
        </v-col>

      </template>

      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>불량 제품 등록</v-toolbar-title>
          <v-divider class="mx-4" inset vertical></v-divider>
          <v-spacer></v-spacer>
          <v-dialog v-model="dialog" max-width="700px">
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
                      <v-combobox
                        v-model="hubModel"
                        :items="hubStates"
                        label="허브명"
                        dense
                        item-text="text"
                        item-value="value"
                        :readonly="false"
                        @click="fabCobClick()"
                        @change="event => fabCobChange(event)"
                        return-object
                      ></v-combobox>
                    </v-col>

                    <v-col cols="12" sm="6" md="4">
                      <v-combobox
                        v-model="itemModel"
                        :items="itemStates"
                        label="제품명"
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
                        v-model="editedItem.dfCode"
                        label="불량코드"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="4">
                      <v-text-field
                        v-model="editedItem.dfName"
                        label="불량명"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="4">
                      <v-text-field
                        v-model="editedItem.dfCount"
                        label="불량 수량"
                      ></v-text-field>
                    </v-col>

                    <form
                      id="sendForm"
                      style="width: 100%;"
                      enctype="multipart/form-data"
                    >
                      <v-file-input
                        v-model="selectImgItems"
                        small-chips
                        multiple
                        ref="files"
                        id="files"
                        name="files"
                        label="File input w/ small chips"
                        accept="image/png, image/jpeg, image/bmp"
                      ></v-file-input>

                      <!--                      <input multiple="multiple" name="file" ref="file" accept="image/png, image/jpeg, image/bmp" type="file" class="custom-file-input">-->

                      <!--                    @change="imageClose"   <img class="popupImageItem"  style="width: 100px; height: 100px;" :src="uploadImageFile">-->
                    </form>
                  </v-row>

                  <v-row>
                    <v-col cols="12" sm="6" md="4">
                      <v-text-field
                        v-model="editedItem.actStatus"
                        label="처리 상태"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="4">
                      <v-text-field
                        v-model="editedItem.insManager"
                        label="검수자"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="4">
                      <v-menu
                        ref="menu"
                        v-model="insMenu"
                        :close-on-content-click="false"
                        :return-value.sync="insDate"
                        transition="scale-transition"
                        offset-y
                        min-width="auto"
                      >
                        <template v-slot:activator="{ on, attrs }">
                          <v-text-field
                            v-model="insDate"
                            label="검수 시간"
                            prepend-icon="mdi-calendar"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                          ></v-text-field>
                        </template>
                        <v-date-picker v-model="insDate" no-title scrollable>
                          <v-spacer></v-spacer>
                          <v-btn text color="primary" @click="insMenu = false">
                            Cancel
                          </v-btn>
                          <v-btn
                            text
                            color="primary"
                            @click="$refs.menu.save(insDate)"
                          >
                            OK
                          </v-btn>
                        </v-date-picker>
                      </v-menu>
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
                >Are you sure you want to delete this item?</v-card-title
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

    <v-row justify="center">
      <v-dialog v-model="imgdialog" persistent max-width="800">
        <v-card>
          <v-card-title class="text-h5">
            불량 이미지
          </v-card-title>
          <v-carousel>
            <v-carousel-item
              v-for="(item, i) in this.imageInfos"
              :key="i"
              :src="item.filePath"
              reverse-transition="fade-transition"
              transition="fade-transition"
            ></v-carousel-item>
          </v-carousel>

          <!--          <v-carousel>-->
          <!--            <template >-->
          <!--              <v-carousel-item v-for="(item, index) in this.imageInfos"-->
          <!--                               :key="index"-->
          <!--              >-->
          <!--                <v-row class="flex-nowrap" style="height:100%">-->
          <!--                  <template>-->
          <!--                    <v-col-->
          <!--                      class="d-flex child-flex"-->
          <!--                      cols="4"-->
          <!--                    >-->
          <!--                      <v-img-->
          <!--                        :src="item.filePath"-->
          <!--                        height="100%"-->
          <!--                        max-width="500"-->
          <!--                      >-->
          <!--                        <v-row class="fill-height"-->
          <!--                               align="center"-->
          <!--                               justify="center"-->
          <!--                        >-->
          <!--                          <div class="display-3">{{+index + 1}}</div>-->
          <!--                        </v-row>-->
          <!--                      </v-img>-->
          <!--                    </v-col>-->
          <!--                  </template>-->
          <!--                </v-row>-->
          <!--              </v-carousel-item>-->
          <!--            </template>-->
          <!--          </v-carousel>-->

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="green darken-1" text @click="imgdialog = false">
              확인
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>

    <div class="text-center">
      <v-dialog v-model="startloading" hide-overlay persistent width="300">
        <v-card color="primary" dark>
          <v-card-text>
            Please stand by
            <v-progress-linear
              indeterminate
              color="white"
              class="mb-0"
            ></v-progress-linear>
          </v-card-text>
        </v-card>
      </v-dialog>
    </div>
  </v-form>
</template>

<script>
import dfcolumn from "../../dataset/idDf/isDf_Column_Json.json";
import axios from "axios";
import store from "../../store";

export default {
  name: "ISDFView",
  data: () => ({
    imgdialog: false,
    startloading: false,
    frontDate: new Date(Date.now() - new Date().getTimezoneOffset() * 60000)
      .toISOString()
      .substr(0, 10),
    toDate: new Date(Date.now() - new Date().getTimezoneOffset() * 60000)
      .toISOString()
      .substr(0, 10),
    menu1: false,
    menu2: false,
    insDate: new Date(Date.now() - new Date().getTimezoneOffset() * 60000)
      .toISOString()
      .substr(0, 10),
    insMenu: false,
    expanded: [],
    singleExpand: true,
    dialog: false,
    dialogDelete: false,
    editModeCheck: false,
    headers: dfcolumn,
    desserts: [],
    uploadImageFile: [],
    hubModel: [],
    hubStates: [],
    itemModel: [],
    itemStates: [],
    selectImgItems: [],
    imageInfos: [],
    imgItems: [],
    editedIndex: -1,
    editedItem: {
      dfCode: "",
      fabCode: "",
      fabName: "",
      itemCode: "",
      itemName: "",
      dfName: "",
      dfCount: "",
      dfImg: "",
      actStatus: "",
      insManager: "",
      insTime: "",
      regCd: "",
      modCd: ""
    },
    defaultItem: {
      dfCode: "",
      fabCode: "",
      fabName: "",
      itemCode: "",
      itemName: "",
      dfName: "",
      dfCount: "",
      dfImg: "",
      actStatus: "",
      insManager: "",
      insTime: "",
      regCd: "",
      modCd: ""
    },
    loader: null,
    loading3: false
  }),
  computed: {
    formTitle() {
      this.editModeCheck = this.editedIndex === -1 ? false : true;
      return this.editedIndex === -1 ? "신규 등록" : "수정 아이템";
    },
    columns() {
      if (this.$vuetify.breakpoint.xl) {
        return 4;
      }

      if (this.$vuetify.breakpoint.lg) {
        return 3;
      }

      if (this.$vuetify.breakpoint.md) {
        return 2;
      }

      return 1;
    }
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

  created() {
    this.initialize();
  },
  methods: {
    initialize() {
      axios
        .get("/api/get/dfitem")
        .then(response => {
          this.desserts = response.data.meisdfList;

          if (this.startloading == true) {
            this.startloading = false;
          }
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
      this.startloading = true;
      if (this.editModeCheck) {
        var updata = {
          opCode: item.opCode,
          opName: item.opName,
          fabCode: item.fabCode,
          lineCode: item.lineCode,
          fabName: item.fabName,
          lineName: item.lineName,
          workCode: item.workCode,
          workName: item.workName,
          regCd: item.regCd,
          modCd: item.modCd
        };

        // axios
        //   .post("/api/upopswork/" + item.id, updata)
        //   .then(response => {
        //     if (response.status == 200) {
        //       alert(response.data.ResultValue);
        //       this.initialize();
        //     } else {
        //       alert(response.data.ResultValue);
        //     }
        //   })
        //   .catch(ex => {
        //     alert(ex.message);
        //   });
      } else {
        var data = {
          dfCode: item.dfCode,
          fabCode: this.hubModel.vlaue,
          fabName: this.hubModel.text,
          itemCode: this.itemModel.vlaue,
          itemName: this.itemModel.text,
          dfName: item.dfName,
          dfCount: item.dfCount,
          actStatus: item.actStatus,
          insManager: item.insManager,
          insTime: this.insDate
        };

        axios
          .post("/api/set/dfitem", data)
          .then(response => {
            if (response.status == 200) {
              var form = document.getElementById("sendForm");
              let formData = new FormData(form);

              for (var i = 0; i < this.selectImgItems.length; i++) {
                let file = this.selectImgItems[i];
                formData.append("files[" + i + "]", file);
              }

              axios
                .post("/api/set/upload/" + item.dfCode, formData, {
                  headers: {
                    "Content-Type": "multipart/form-data"
                  }
                })
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
              alert(response.data.ResultValue);
            }
          })
          .catch(ex => {
            alert(ex.message);
          });
      }
    },
    fabCobClick() {
      axios
        .get("/api/get/fabmast")
        .then(response => {
          this.hubStates = [];
          this.hubModel = [];

          for (var i in response.data.data) {
            this.hubStates.push({
              text: response.data.data[i].fabName,
              vlaue: response.data.data[i].fabCode
            });
          }

          this.desserts = [];
        })
        .catch(ex => {
          alert(ex.message);
        });
    },
    fabCobChange(event) {
      axios
        .get("/api/get/productdata")
        .then(response => {
          this.itemStates = [];
          this.itemModel = [];

          for (var i in response.data.data) {
            this.itemStates.push({
              text: response.data.data[i].goodsNm,
              vlaue: response.data.data[i].goodsCd
            });
          }
        })
        .catch(ex => {
          alert(ex.message);
        });
    },
    imgViewOpen(item) {
      this.editedIndex = this.desserts.indexOf(item);
      this.editedItem = Object.assign({}, item);

      axios
        .post("/api/get/dfitemimg/" + this.editedItem.dfCode.toString())
        .then(response => {
          this.imgdialog = true;
          this.imageInfos = [];
          this.imgItems = response.data.fileDtoList;
          for (var i = 0; i < this.imgItems.length; i++) {
            this.imageInfos.push({
              filePath: "data:image/png;base64," + this.imgItems[i]
            });
          }

          // var imgsrc = "data:image/png;base64," + this.imgItems;
          // document.getElementById('image').src = imgsrc;
        })
        .catch(ex => {
          alert(ex.message);
        });
    }
  }
};
</script>

<style scoped></style>
