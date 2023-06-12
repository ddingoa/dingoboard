<template>
  <v-form style="padding: 10px">
    <v-card>
      <v-card-title class="text-h5 font-weight-regular blue-grey white--text">
        배송 박스 태그 출력
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
        >
        </v-autocomplete>

        <v-subheader class="pa-0">
          제품 구분
        </v-subheader>
        <v-autocomplete
          v-model="productModel"
          :items="productStates"
          item-text="text"
          item-value="value"
          :readonly="false"
          @change="event => productCobChange(event, index)"
        >
        </v-autocomplete>

        <v-subheader class="pa-0">
          검수조 구분
        </v-subheader>
        <v-autocomplete
          v-model="accountModel"
          :items="accountStates"
          item-text="text"
          item-value="value"
          :readonly="false"
          @change="event => accounCobChange(event, index)"
        >
        </v-autocomplete>

        <v-subheader class="pa-0">
          작업 구분
        </v-subheader>
        <v-autocomplete
          v-model="accountModel"
          :items="accountStates"
          item-text="text"
          item-value="value"
          :readonly="false"
          @change="event => accounCobChange(event, index)"
        >
        </v-autocomplete>

        <v-subheader class="pa-0">
          작업중인 거래처를 선택해 주세요.
        </v-subheader>
        <v-autocomplete
          v-model="accountModel"
          :items="accountStates"
          item-text="text"
          item-value="value"
          :readonly="false"
          @change="event => accounCobChange(event, index)"
        >
        </v-autocomplete>
      </v-card-text>
    </v-card>

    <div style="padding-top: 60px">
      <h2>포장 리스트</h2>
      <div>
        <v-data-table :headers="headers" :items="desserts">
          <template v-slot:[`item.actions`]="{ item }">
            <v-btn
              :loading="loading3"
              :disabled="item.disable"
              color="blue-grey"
              class="ma-2 white--text"
              @click="btnClickAdd(item)"
            >
              포장 완료
              <v-icon right dark>
                mdi-cube
              </v-icon>
            </v-btn>
          </template>
        </v-data-table>
      </div>
    </div>
    <v-dialog v-model="dialogPking" max-width="500px">
      <v-card>
        <v-card-title class="text-h5"
        >포장 완료 처리를 하시겠습니까?!
        </v-card-title>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="closepk">Cancel</v-btn>
          <v-btn color="blue darken-1" text @click="pkdialogsave">OK</v-btn>
          <v-spacer></v-spacer>
        </v-card-actions>
      </v-card>
    </v-dialog>

  </v-form>
</template>

<script>
import boxtagcolumn from '../../dataset/pkorder/pktagbox_Column_Json.json'

export default {
  name: "PkTagBoxView",
  data: () => ({
    loader: null,
    loading3: false,
    dialogPking: false,
    hubModel: {},
    hubStates: [
      {
        text: "1허브",
        vlaue: "hub1"
      },
      {
        text: "2허브",
        vlaue: "hub2"
      },
      {
        text: "3허브",
        vlaue: "hub3"
      }
    ],
    productModel: {},
    productStates: ["KIDS", "BIZ", "ECO"],
    accountModel: {},
    accountStates: [],
    headers: boxtagcolumn,
    desserts: [],
  }),
  watch: {
    loader() {
      const l = this.loader;
      this[l] = !this[l];

      setTimeout(() => (this[l] = false), 3000);

      this.loader = null;
    },
    dialogPking(val) {
      val || this.close();
    }
  },
  methods: {
    btnClickAdd(item) {
      this.loader = "loading3";
      this.editedIndex = this.desserts.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogPking = true;
    },
    closepk() {
      this.dialogPking = false;
    },
    pkdialogsave() {
      this.desserts[this.editedIndex].disable = true;
      this.dialogPking = false;
    }
  }
};
</script>

<style scoped></style>
