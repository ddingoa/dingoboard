<template>
  <v-form>
    <v-tabs>
      <v-tab v-for="(item, index) in hubStates" :key="index"><h3>{{ item.text }}</h3></v-tab>
      <v-tab-item v-for="(item, index) in hubStates" :key="index">
        <hub-dash-board-component :fabCodeValue="item"></hub-dash-board-component>
      </v-tab-item>
    </v-tabs>
  </v-form>
</template>

<script>

import axios from "axios";
import HubDashBoardComponent from "@/components/menulist/hubDashBoardComponent";

export default {
  name: "index",
  components: {HubDashBoardComponent},
  data: () => ({
    hubStates : [],
    hubModel : [],
  }),
  mounted() {
    this.initFabData();
  },
  methods: {
    initFabData() {
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
          })
          .catch(ex => {
            alert(ex.message);
          });
    }
  }

};
</script>

<style scoped></style>
