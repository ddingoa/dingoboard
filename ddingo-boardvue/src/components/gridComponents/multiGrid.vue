<template>
  <v-app id="medicalRecordHome">
    <v-data-table :headers="surgeryInformationHeaders" :items="surgeryInformationDesserts" hide-default-footer class="transparent elevation-0 my-4" hide-default-header disable-pagination disable-sort :items-per-page="5">
      <template #header="{ }">
        <thead class="v-data-table-header">
        <tr>
          <th v-for="(h,i) in surgeryInformationHeaders" :key="i" class="text-center parent-header td-border-style" :rowspan="h.children?1:2" :colspan="h.children?h.children.length:1">
            {{ h.text }}
          </th>
        </tr>
        <tr>
          <th v-for="(h1,i1) in getSubHeader(surgeryInformationHeaders)" :key="i1" class="text-center child-header td-border-style">
            {{ h1.text }}
          </th>
        </tr>
        </thead>
      </template>
    </v-data-table>
  </v-app>
</template>

<script>
export default {
  name: "multiGrid",
  methods: {
    getMainHeader(headers) {
      return headers.filter((i) => i.children);
    },
    getSubHeader(headers) {
      let result = [];
      headers
          .filter((i) => i.children)
          .forEach((v) => {
            result = result.concat(v.children);
          });
      return result;
    },
  },
  data() {
    return {
      surgeryInformationHeaders: [
        {
          text: "code",
          align: "start",
          sortable: false,
          value: "code"
        },
        { text: "time", value: "time" },
        { text: "level", value: "level" },
        { text: "name", value: "name" },
        {
          text: "2022-01-01",
          value: "author",
          divider: true,
          children: [{ text: "식기", value: "a1"  }, { text: "수저", value: "a2"  }, { text: "밥그릇", value: "a3"  }]
        }
      ],
      surgeryInformationDesserts: [{
        "code" : "1",
        "time" : "A",
        "level" : "B",
        "name" : "C",
        "a1" : "E",
        "a2" : "F",
        "a3" : "G"
      }]
    };
  }
};

</script>

<style scoped>

</style>