<template>
  <v-form style="padding: 10px">
    <v-card>
      <v-card-title class="text-h5 font-weight-regular blue-grey white--text">
        허브 별 포장 실적 등록
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
            @click="fabSetting"
            @change="fabSettingItem"
        >
        </v-autocomplete>
      </v-card-text>
    </v-card>

    <div v-if="checkValue" style="padding-top: 10px">
      <pk-work-row-card
          v-bind:itemlist="itemlist"
          @FromChild="FromChildData">
      </pk-work-row-card>
    </div>
  </v-form>
</template>

<script>
import axios from "axios";
import PkWorkRowCard from "@/components/pkWorkComponents/PkWorkRowCard";
import store from "@/store";

export default {
  name: "PkWorkVidwN",
  components: {PkWorkRowCard},
  data: () => ({
    hubModel: [],
    hubStates: [],
    checkValue: false,
    childData: [],
    itemlist: [
    ],
  }),
  created() {
    this.initMethod();
  },
  methods: {
    FromChildData(data) {
      this.childData = data
      var sedndata = [];
      for(var i=0; i<this.childData.length; i++){
        sedndata.push(
            {
              pkoCode: "",
              workStart: "",
              inputTime: "",
              fabCode : this.hubModel.vlaue,
              fabName : this.hubModel.text,
              itemCode : this.childData[i].id,
              itemName : this.childData[i].name,
              itemCount : this.childData[i].value,
              pkItemCount : this.childData[i].valueCount,
              holidaysApply: "",
              pkingCheck: true,
              active: true,
              regCd : store.state.userName,
              modCd : store.state.userName
            }
        )
      }
      axios
          .post("/api/set/pkorderdatalist", sedndata)
          .then(response => {
            if (response.status == 200) {
              alert(response.data.ResultValue);
            } else {
              alert(response.data.ResultValue);
            }
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
    initMethod() {
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

            this.btnNewCheck = false;
            this.desserts = [];
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
    fabSetting() {
      this.hubModel = [];
      this.hubStates = [];
      this.itemlist = [];
      this.checkValue = false;
      this.initMethod();
    },
    fabSettingItem() {
      axios
          .get("/api/get/producthubcodeitem/" + this.hubModel.vlaue)
          .then(response => {
            if (response.status == 200) {
              this.itemlist = response.data.data;
              this.checkValue = true;
            } else {
              alert(response.data.ResultValue);
            }
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
  }
}
</script>

<style scoped>

</style>