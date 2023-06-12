<template>
  <v-app id="inspire" style="padding:5px">
    <v-data-table
      :headers="dessertHeaders"
      :items="desserts"
      :single-expand="singleExpand"
      :expanded.sync="expanded"
      item-key="vendorCd"
      show-expand
      :search="search"
      class="elevation-1"
      :loading="loadingBo"
      loading-text="Loading... Please wait"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>거래처 마스터 테이블</v-toolbar-title>
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
      <template v-slot:expanded-item="{ headers, item }">
        <td :colspan="headers.length">
          <p class="blue lighten-2 white--text" style="font-size: large">
            {{ item.vendorNm }}
          </p>
          <AccountCard v-bind:childVaule="item"></AccountCard>
        </td>
      </template>
    </v-data-table>
  </v-app>
</template>

<script>
import AccountCard from "../../components/mastComponent/accountCard.vue";
import AccountJson from "../../dataset/mastColumn/account_Column_Json.json";
import axios from "axios";

export default {
  name: "accountView",
  components: {
    AccountCard
  },
  data: () => ({
    search: "",
    loadingBo: true,
    expanded: [],
    singleExpand: true,
    dessertHeaders: AccountJson,
    desserts: []
  }),
  mounted() {
    axios
      .get("/api/get/accountdata")
      .then(response => {
        this.desserts = response.data.data;
        this.loadingBo = false;
      })
      .catch(ex => {
        alert(ex.message);
      });
  }
};
</script>

<style scoped></style>
