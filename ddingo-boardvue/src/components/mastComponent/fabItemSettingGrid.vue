<template>
  <v-form>
    <v-card>
      <v-card-title class="text-h5 font-weight-regular blue-grey white--text">
        {{ hubNameData}}
      </v-card-title>
    </v-card>
    <v-col cols="12">
      <v-col cols="12" class="text-right">
        <v-btn color="primary"
          large
               @click="saveCheckItem"
        >
          저장
        </v-btn>
      </v-col>

      <v-col cols="12">
        <v-data-table
            v-model="selected"
            :headers="headers"
            :items="itemData"
            :search="search"
            :single-select="singleSelect"
            item-key="goodsCd"
            show-select
            class="elevation-1"
        >
          <template v-slot:top>
            <v-toolbar flat>
              <v-toolbar-title>제품 리스트</v-toolbar-title>
              <v-spacer></v-spacer>
              <v-text-field
                  v-model="search"
                  append-icon="mdi-magnify"
                  label="Search"
                  single-line
                  hide-details
              ></v-text-field>
            </v-toolbar>
          </template>
      </v-data-table>
      </v-col>
    </v-col>
  </v-form>
</template>

<script>
import axiosAP from "axios";
import store from "@/store";
import productitem from "../../dataset/mastColumn/productItemHub_Column_Json.json"

export default {
  name: "fabItemSettingGrid",
  data () {
    return {
      search: "",
      singleSelect: false,
      selected: [],
      headers: productitem,
      itemData: [],
    }
  },
  props: {
    hubCodedata : String,
    hubNameData : String
  },
  created() {
    this.initialize();
  },
  watch : {
    hubCodedata (){
      this.getHubItemList();
    },
    hubNameData (){
      this.getHubItemList();
    }
  },
  methods: {
    initialize() {
      this.getHubItemList();
    },
    getHubItemList () {
      this.selected = [];
      axiosAP.get("/api/get/hubcodeitemlist/"+ this.hubCodedata.toString())
          .then(response => {
            this.itemData = response.data.data
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
    saveCheckItem(){
      var setData = []

      for(var i=0; i< this.selected.length; i++)
      {
        var data  = {
          goodsCd: this.selected[i].goodsCd,
          divisionCd: this.selected[i].divisionCd,
          goodsType: this.selected[i].goodsType,
          goodsNm: this.selected[i].goodsNm,
          material: this.selected[i].material,
          weight: this.selected[i].weight,
          packingQuantity: this.selected[i].packingQuantity,
          plasticWrap: this.selected[i].plasticWrap,
          fabCode: this.hubCodedata,
          fabName: this.hubNameData,
          regCd: store.state.userName,
          modCd: store.state.userName
        }
        setData.push(data);
      };

      axiosAP.post("/api/set/producthubitem",setData)
          .then(response => {
            this.itemData = response.data.data
          })
          .catch(ex => {
            alert(ex.message);
          });
    }
  }
}
</script>

<style scoped>

</style>
