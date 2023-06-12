<template>
  <v-form>
    <v-card>
      <v-card-title class="text-h5 font-weight-regular blue-grey white--text">
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
            item-key="vendorCd"
            show-select
            class="elevation-1"
        >
          <template v-slot:top>
            <v-toolbar flat>
              <v-toolbar-title>거래처 리스트</v-toolbar-title>
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
import deliberySetting from "@/dataset/otherColumn/kidsDeliverySetting_Column_Json.json";
import axiosAP from "axios";
import store from "@/store";

export default {
  name: "kidsDeliverySettingGrid",
  data() {
    return {
      search: "",
      singleSelect: false,
      selected: [],
      headers: deliberySetting,
      itemData: [],
    }
  },
  props: {
    Codedata: String
  },
  created() {
    this.initialize();
  },
  watch: {
    Codedata() {
      this.getHubItemList();
    }
  },
  methods: {
    initialize() {
      this.getHubItemList();
    },
    getHubItemList() {
      this.selected = [];
      axiosAP.get("/api/get/kidsWmsModellist/" + this.Codedata.toString())
          .then(response => {
            this.itemData = response.data.data
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
    saveCheckItem() {
      var setData = []

      for (var i = 0; i < this.selected.length; i++) {
        var data = {
          deliveryCode: this.Codedata,
          address1: this.selected[i].address1,
          vendorCd: this.selected[i].vendorCd,
          vendorNm: this.selected[i].vendorNm,
          regCd: store.state.userName,
          modCd: store.state.userName
        }
        setData.push(data);
      };

      axiosAP.post("/api/set/kidsWmsSubModel", setData)
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
    }
  }
}
</script>

<style scoped>

</style>