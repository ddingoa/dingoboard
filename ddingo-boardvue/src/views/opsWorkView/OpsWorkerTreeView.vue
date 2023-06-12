<template>
  <form style="padding: 10px">
    <v-card>
      <v-card-title class="text-h5 font-weight-regular blue-grey white--text">
        작업 인력 배치 현황
      </v-card-title>
      <v-card-text>
        <v-row>
          <v-col cols="6">
            <v-treeview :items="items"></v-treeview>
          </v-col>

          <v-col cols="6">
            <v-treeview :items="items"></v-treeview>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
  </form>
</template>

<script>
import axios from "axios";

export default {
  name: "OpsWorkerTreeView",
  data: () => ({
    items: [
      {
        id: 1,
        name: "Applications :",
        children: [
          { id: 2, name: "Calendar : app" },
          { id: 3, name: "Chrome : app" },
          { id: 4, name: "Webstorm : app" }
        ]
      },
      {
        id: 5,
        name: "Documents :",
        children: [
          {
            id: 6,
            name: "vuetify :",
            children: [
              {
                id: 7,
                name: "src :",
                children: [
                  { id: 8, name: "index : ts" },
                  { id: 9, name: "bootstrap : ts" }
                ]
              }
            ]
          },
          {
            id: 10,
            name: "material2 :",
            children: [
              {
                id: 11,
                name: "src :",
                children: [
                  { id: 12, name: "v-btn : ts" },
                  { id: 13, name: "v-card : ts" },
                  { id: 14, name: "v-window : ts" }
                ]
              }
            ]
          }
        ]
      },
      {
        id: 15,
        name: "Downloads :",
        children: [
          { id: 16, name: "October : pdf" },
          { id: 17, name: "November : pdf" },
          { id: 18, name: "Tutorial : html" }
        ]
      },
      {
        id: 19,
        name: "Videos :",
        children: [
          {
            id: 20,
            name: "Tutorials :",
            children: [
              { id: 21, name: "Basic layouts : mp4" },
              { id: 22, name: "Advanced techniques : mp4" },
              { id: 23, name: "All about app : dir" }
            ]
          },
          { id: 24, name: "Intro : mov" },
          { id: 25, name: "Conference introduction : avi" }
        ]
      }
    ],
    treeData : [],
  }),
  created() {
    this.init();
  },
  methods : {
    init(){
      axios
          .get("/api/get/opsworkertree")
          .then(response => {
            if (response.status == 200) {
              this.treeData = response.data.data;
              alert(response.data.ResultValue);
            } else {
              alert(response.data.ResultValue);
            }
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
    SetTreeData(){








    },
  }
};
</script>

<style scoped></style>
