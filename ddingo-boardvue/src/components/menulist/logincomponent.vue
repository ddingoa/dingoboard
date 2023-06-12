<template>
  <form style="padding-top: 150px">

    <v-card
        class="mx-auto"
        max-width="600"
    >
<!--      <v-img-->
<!--          :src="require('../../assets/bbcoview.gif')"-->
<!--          height="200px"-->
<!--      >-->
<!--      </v-img>-->
<!--      <v-card-title>Login</v-card-title>-->
      <v-card-text>
        <v-form>
          <v-text-field
              v-model="logindata.email"
              label="아이디"
              id="email"
              name="email"
              prepend-icon="mdi-account"
              type="text"
          ></v-text-field>

          <v-text-field
              v-model="logindata.password"
              id="password"
              label="비밀 번호"
              name="password"
              prepend-icon="mdi-lock"
              type="password"
              @keyup.enter="fetchContacts(logindata)"
          ></v-text-field>
        </v-form>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="primary"
               block
               v-on:click="fetchContacts(logindata)"
        >Login
        </v-btn>
      </v-card-actions>
    </v-card>

  </form>
</template>


<script>
import axios from "axios";
import store from "../../store";
import VueCookies from "vue-cookies";

export default {
  name: "logincomponent",
  data() {
    return {
      logindata: {
        email: "",
        password: ""
      }
    };
  },
  props: {
    iconflag: Boolean
  },
  created() {
    store.state.iconflagSet = false;
    this.$cookies.remove("accessToken");
    this.$cookies.remove("refreshToken");
  },
  methods: {
    fetchContacts: function (logindata) {
      // let data = {
      //   email: (logindata.email = "alsdnpk@bbodek.com"),
      //   password: (logindata.password = "123qwe")
      // };

      let data = {
        email: logindata.email,
        password: logindata.password
      };
      try {
        axios({
          method: "POST",
          url: "/auth/login",
          data: data
        })
            .then(response => {
              if (response.status == "200") {
                if (response.data.userCheckInit) {
                  this.loginStatus(response);
                } else {
                  VueCookies.set("accessToken", response.data.tokenDto.accessToken);
                  VueCookies.set("refreshToken", response.data.tokenDto.refreshToken);
                  store.commit("emildata", {email: logindata.email});
                  this.$router.replace("/define");
                }
              }
            })
            .catch(ex => {
              alert(ex.message);
            });
      } catch (err) {
        return err;
      }
    },
    loginStatus(response) {
      // VueCookies.set("accessToken", response.data.tokenDto.accessToken);
      VueCookies.set("refreshToken", response.data.tokenDto.refreshToken);

      axios.defaults.headers.common["bbdekToken"] = "BBodek " + response.data.tokenDto.accessToken;

      // axios.defaults.headers.common["refreshToken"] = "Bearer " +
      //     response.data.tokenDto.refreshToken;

      store.commit("drawerSetData", {drawer: true});
      store.commit("iconflagSetData", {iconflag: true});
      store.commit("userdata", {userName: response.data.userName});
      store.commit("fabCodeData", {fabCode: response.data.fabCode});
      store.commit("userSetData", {userSet: response.data.authority});
      store.commit("fabNameData", {fabName: response.data.fabName});
      store.commit("lineCodeData", {lineCode: response.data.lineCode});
      store.commit("lineNameData", {lineName: response.data.lineName});
      store.commit("workCodeData", {workCode: response.data.workCode});
      store.commit("workNameData", {workName: response.data.workName});

      this.$router.replace("/bb/index")
    },
  }
};
</script>
<style scoped></style>
