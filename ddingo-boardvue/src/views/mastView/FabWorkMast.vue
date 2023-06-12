<template>
  <v-form style="padding: 10px">
    <v-card>
      <v-card-title class="text-h5 font-weight-regular indigo darken-1 white--text">
        허브별 생산량 세팅
      </v-card-title>
      <v-card-text>
        <v-subheader class="pa-0">
          허브 구분
        </v-subheader>
        <v-autocomplete
            v-model="bubModel"
            :items="buStates"
            item-text="text"
            item-value="value"
            :readonly="false"
            return-object
            @change="hubStringChange"
        >
        </v-autocomplete>
      </v-card-text>
    </v-card>

    <v-form v-if="ThisValueCheck">
      <v-row align="center" style="padding : 20px">
        <v-col cols="12" style="background-color: aquamarine">
           <h2> {{ bubModel.text }}</h2>
        </v-col>
      </v-row>

      <div style="padding-top: 15px">
      <h5 class="display-1" align="left"> <v-icon
          large
          color="green darken-2"
      >
        mdi-domain
      </v-icon>제품별 세팅</h5>
          <div>
            <v-data-table :headers="headers" :items="skuData">
              <template v-slot:[`item.fubItemPsCount`]="props">
                <v-edit-dialog
                    :return-value.sync="props.item.fubItemPsCount"
                    large
                    persistent
                    @open="open"
                    @close="close"
                    @save="save(props.item)"
                    @cancel="cancel"
                >
                  {{ props.item.fubItemPsCount }}
                  <template v-slot:input>
                    <v-text-field
                        v-model="props.item.fubItemPsCount"
                        :rules="[max25chars]"
                        label="Edit"
                        type="number"
                        single-line
                        counter
                        autofocus
                    ></v-text-field>
                  </template>
                </v-edit-dialog>
              </template>
            </v-data-table>
          </div>
          <div style="background-color: #42b983">
            시간대 별 생산량
            <v-data-table ref="worktimeGrid"  :headers="workTimeheaders" :items="workTimedata">
            </v-data-table>
          </div>

        <v-btn
            :loading="loading3"
            :disabled="loading3"
            color="info"
                     block @click="saveGridData()">적용 하기</v-btn>
      </div>

      <v-snackbar v-model="snack"
                  :timeout="3000"
                  right
                  :color="snackColor"
      >
        {{ snackText }}

        <template v-slot:action="{ attrs }">
          <v-btn v-bind="attrs" text @click="snack = false">
            Close
          </v-btn>
        </template>
      </v-snackbar>

    </v-form>
  </v-form>
</template>

<script>
import productitem from "../../dataset/mastColumn/hubproductSku_Column_Json.json"
import axiosAP from "axios";
import hubworkTime from "../../dataset/mastColumn/productItemHubTimeWork_Column_Json.json";

export default {
  name: "FabWorkMast",
  data () {
    return {
      fabSettingData : [],
      ThisValueCheck : false,
      loader: null,
      loading3: false,
      bubModel: [],
      buStates: [],
      headers: productitem,
      skuData: [],
      snack: false,
      snackColor: "",
      snackText: "",
      max25chars: "",
      editedEQPIndex: -1,
      editedEQPItem: {
        itemCode: "",
        itemName: "",
        itemType: "",
        texture: "",
        packingQuantity: "",
        fubItemCount: "",
        fubItemPsCount: "",
        regCd: "",
        modCd: ""
      },
      selectIndex:  -1,
      workerTime: [],
      workTimeheaders: hubworkTime,
      workTimedata : [],
      workTimecopydata : [],
      workTimeeditedItem: {
        itemCode: "",
        itemName: "",
        workTime20: "",
        workTime21: "",
        workTime22: "",
        workTime23: "",
        workTime00: "",
        workTime02: "",
        workTime03: "",
        workTime04: "",
        workTime05: "",
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
        regCd: "",
        modCd: ""
      },
      sendData : {
        fab_code: "",
        fab_name: "",
        item_code: "",
        item_name: "",
        item_type: "",
        plastic_wrap: "",
        packing_quantity: "",
        texture: "",
        weight: "",
        active: "",
        work_time00: "",
        work_time01: "",
        work_time02: "",
        work_time03: "",
        work_time04: "",
        work_time05: "",
        work_time06: "",
        work_time07: "",
        work_time08: "",
        work_time09: "",
        work_time10: "",
        work_time11: "",
        work_time12: "",
        work_time13: "",
        work_time14: "",
        work_time15: "",
        work_time16: "",
        work_time17: "",
        work_time18: "",
        work_time19: "",
        work_time20: "",
        work_time21: "",
        work_time22: "",
        work_time23: "",
        reg_cd: "",
        mod_cd: "",
      },
    }
  },
  created() {
    this.initView()
  },
  watch: {
    loader() {
      const l = this.loader;
      this[l] = !this[l];

      setTimeout(() => (this[l] = false), 3000);

      this.loader = null;
    }
  },
  methods :{
    initView(){
      this.ThisValueCheck = false;

      axiosAP
          .get("/api/get/fabmast")
          .then(response => {
            this.bubModel = [];
            this.buStates = [];

            for (var i in response.data.data) {
              this.buStates.push({
                text: response.data.data[i].fabName,
                value: response.data.data[i].fabCode
              });
            }
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
    save (item) {
      this.snack = true
      this.snackColor = 'success'
      this.snackText = '입력을 완료 하셨습니다!'
      this.editedEQPIndex = this.skuData.indexOf(item);
      this.editedEQPItem = Object.assign({}, item);
      this.skuData[this.editedEQPIndex].fubItemCount= this.editedEQPItem.fubItemPsCount * this.editedEQPItem.packingQuantity;
      this.setUpdateTimeGrid();
      this.$forceUpdate()
    },
    cancel () {
      this.snack = true
      this.snackColor = 'error'
      this.snackText = '입력을 취소 하셨습니다!!'
    },
    open() {
      this.snack = true;
      this.snackColor = "info";
      this.snackText = "수정 중입니다.";
    },
    close () {
      console.log('Dialog closed')
    },
    setUpdateTimeGrid(){
      var skuitemcount = 19;
      for(var i=0; i< this.workTimeheaders.length; i++){
        if(i > 1){
          this.workTimedata[this.editedEQPIndex][this.workTimeheaders[i].value] = Math.ceil(this.editedEQPItem.fubItemPsCount / skuitemcount);
        }
      }
      this.$refs.worktimeGrid.$forceUpdate()
    },
    hubStringChange() {
      this.ThisValueCheck = true;
      this.getHubSettingDataList(this.bubModel.value);

    },
    getHubSettingDataList (item) {
      axiosAP.get("/api/get/fabsetting/" + item.toString())
          .then(response => {
            this.fabSettingData = response.data.data;
            this.getHubItemList(item);
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
    getHubItemList (item) {
      axiosAP.get("/api/get/producthubcodeitem/" + item.toString())
          .then(response => {
            var resultData = response.data.data;
            //fabSetting에서 아이템 삭제
            if(this.fabSettingData.length >= resultData.length) {

              this.skuData = this.fabSettingData;
              this.workTimedata = this.fabSettingData;

            }else if(resultData.length > this.fabSettingData.length){
              //fabSetting에서 아이템 추가
              this.setValueData(resultData);
            }

          })
          .catch(ex => {
            alert(ex.message);
          });
    },
    saveGridData(){
      this.loader = 'loading3';
      var itemData = this.skuData;
      var itemTiemData = this.workTimedata;

     this.sendData = itemData;
     this.sendData = itemTiemData;

      axiosAP.post("/api/set/fabsetting",this.sendData)
          .then(response => {
            if (response.status == 200) {
              this.snack = true;
              this.snackColor = "primary";
              this.snackText = response.data.ResultValue;
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
    setValueData(data) {
      this.skuData = data
      this.workTimedata =data

      for (var i = 0; i < this.skuData.length; i++) {
        for (var j = 0; j < this.fabSettingData.length; j++) {
          if (this.skuData[i].goodsCd == this.fabSettingData[j].goodsCd){
              this.skuData[i].fubItemCount = this.fabSettingData[j].fubItemCount
              this.skuData[i].fubItemPsCount= this.fabSettingData[j].fubItemPsCount
          }
        }
      }

      for (var i = 0; i < this.workTimedata.length; i++) {
        for (var j = 0; j < this.workTimeheaders.length; j++) {
          if (j > 1) {
            for(var z=0; z<this.fabSettingData.length; z++){
              if(this.workTimedata[i].goodsCd == this.fabSettingData[z].goodsCd){
                this.workTimedata[i][this.workTimeheaders[j].value] =this.fabSettingData[z][this.workTimeheaders[j].value]
              }
            }
          }
        }
      }

      // this.workTimedata = this.fabSettingData;

    }
  }
}
</script>

<style scoped>

</style>
