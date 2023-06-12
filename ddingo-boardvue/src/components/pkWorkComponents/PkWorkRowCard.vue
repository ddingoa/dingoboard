<template>
  <v-container>
    <v-row>
      <v-col cols="12" v-for="(item, idx) in itemlist" :key="idx">
        <v-row>
          <v-subheader :id="'text_' + item.goodsCd" style="width: 20%">{{
              item.goodsNm
            }}
          </v-subheader>
          <v-text-field
              v-model="selectExcludes[item.goodsCd]"
              :id="'text_' + item.goodsCd"
              placeholder="0"
              prefix="Box"
              type="number"
              @click="TextField($event)"
              onKeyup="this.value=this.value.replace(/[^0-9]/g,'');"
          ></v-text-field>
          <v-text-field
              style="margin-left: 10px"
              v-model="selectExcludesPs[item.goodsCd]"
              :id="'text_ps' + item.goodsCd"
              placeholder="0"
              prefix="Box in Qty"
              type="number"
              @click="TextField($event)"
              onKeyup="this.value=this.value.replace(/[^0-9]/g,'');"
          ></v-text-field>
          <v-btn
              color="primary"
              large
              style="margin: 5px"
              :id="item.goodsCd"
              @click="click10add($event)"
          >
            +10
          </v-btn>
          <v-btn
              color="primary"
              large
              style="margin: 5px"
              :id="item.goodsCd"
              @click="click1add($event)"
          >
            +1
          </v-btn>
          <v-btn
              color="error"
              large
              style="margin: 5px"
              :id="item.goodsCd"
              @click="clickn1add($event)"
          >
            -1
          </v-btn>
          <v-btn
              color="error"
              large
              style="margin: 5px"
              :id="item.goodsCd"
              @click="clickn10add($event)"
          >
            -10
          </v-btn>
        </v-row>
      </v-col>
    </v-row>

    <v-btn block color="primary" large @click="workOrder()">
      실적 저장
    </v-btn>
  </v-container>
</template>

<script>
export default {
  name: "PkWorkRowCard",
  props:  ["itemlist"],
  data: () => ({
    selectExcludes: [],
    selectExcludesPs: [],
  }),
  created() {
    for (var i in this.selectExcludes) {
      this.$set(this.selectExcludes, i, 0);
    }
  },
  methods: {
    click10add(event) {
      const targetId = event.currentTarget.id;
      // alert(targetId); // returns 'foo'
      //  var addValue = parseInt(document.getElementById("text_"+targetId).value);
      //  addValue += 10
      var addVlaue = parseInt(this.selectExcludes[targetId]);
      if (isNaN(addVlaue)) {
        addVlaue = 0;
      }
      addVlaue += 10;
      this.$set(this.selectExcludes, targetId, addVlaue);
    },
    click1add(event) {
      const targetId = event.currentTarget.id;
      // alert(targetId); // returns 'foo'
      // var addValue = parseInt(document.getElementById("text_"+targetId).value);
      // addValue += 1
      // document.getElementById("text_"+targetId).value  = addValue;

      var addVlaue = parseInt(this.selectExcludes[targetId]);
      if (isNaN(addVlaue)) {
        addVlaue = 0;
      }
      addVlaue += 1;
      this.$set(this.selectExcludes, targetId, addVlaue);
    },
    clickn1add(event) {
      const targetId = event.currentTarget.id;
      // alert(targetId); // returns 'foo'
      // var addValue = parseInt(document.getElementById("text_"+targetId).value);
      // addValue -= 1
      // if(addValue >= 0) {
      //   document.getElementById("text_"+targetId).value = addValue;
      // }

      var addVlaue = parseInt(this.selectExcludes[targetId]);
      if (isNaN(addVlaue)) {
        addVlaue = 0;
      }
      addVlaue -= 1;
      if (addVlaue >= 0) {
        this.$set(this.selectExcludes, targetId, addVlaue);
      }
    },
    clickn10add(event) {
      const targetId = event.currentTarget.id;
      // alert(targetId); // returns 'foo'
      // var addValue = parseInt(document.getElementById("text_"+targetId).value);
      // addValue -= 10
      // if(addValue >= 0) {
      //   document.getElementById("text_"+targetId).value = addValue;
      // }

      var addVlaue = parseInt(this.selectExcludes[targetId]);
      if (isNaN(addVlaue)) {
        addVlaue = 0;
      }
      addVlaue -= 10;
      if (addVlaue >= 0) {
        this.$set(this.selectExcludes, targetId, addVlaue);
      }
    },
    TextField() {
      // const targetId = event.currentTarget.id;
      // document.getElementById("text_"+targetId).value = document.getElementById("text_"+targetId).value;
    },
    workOrder() {
      var dataList = [];
      var datapsList = [];
      for (var i in this.selectExcludes) {
        if(this.selectExcludes[i] != 0) {
          var data = {
            id: i,
            value: this.selectExcludes[i],
            valueCount : this.selectExcludesPs[i],
            name: document.getElementById("text_" + i).outerText
          };
          dataList.push(data);
        }
        this.selectExcludes[i] = ""
        this.selectExcludesPs[i] = ""
      }

      this.$emit('FromChild', dataList)
    }
  }
}
</script>

<style scoped>

</style>