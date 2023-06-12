<template>
  <v-app id="inspire" style="padding:5px">
    <v-data-table
      :headers="dessertHeaders"
      :items="desserts"
      item-key="id"
      :search="search"
      class="elevation-1"
      :loading="loadingBo"
      loading-text="Loading... Please wait"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>상품 마스터 테이블</v-toolbar-title>
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
  </v-app>
</template>

<script>
import ProductJson from "../../dataset/mastColumn/product_Column_Json.json";
import axios from "axios";

export default {
  name: "ProductView",
  data: () => ({
    search: "",
    loadingBo: true,
    singleExpand: true,
    dessertHeaders: ProductJson,
    desserts: []
  }),
  mounted() {
    axios
      .get("/api/get/productdata")
      .then(response => {
        this.desserts = response.data.data;
        this.loadingBo = false;
      })
      .catch(ex => {
        alert(ex.message);
      });

    // const cookie = this.$cookies.get("accessToken");
    // axios({
    //   method: "GET",
    //   url: "/api/get/productdata",
    //   headers: {
    //     "X-BBODEK-TOKEN":  "Bearer " + cookie
    //   }
    // })
    //   .then(response => {
    //     if (response.status == "200") {
    //       this.desserts = response.data.data;
    //       this.loadingBo = false;
    //     }
    //   })
    //   .catch(ex => {
    //     alert(ex.message);
    //   });
  }
};
</script>

<style scoped></style>
