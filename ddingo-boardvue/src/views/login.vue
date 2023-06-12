<template>
  <logincomponent v-on:iconflagSet="iconflagMes"></logincomponent>
</template>

<script>

import store from "../store";
import Logincomponent from "@/components/menulist/logincomponent";

export default {
  name: "Login",
  components: {Logincomponent},
  data: () => ({
    iconflag: false
  }),

  created() {
    store.commit("drawerSetData", { drawer: false });
  },
  methods: {
    iconflagMes: function(param) {
      this.$emit("iconflagSet", param);
    }
  }
};
</script>
