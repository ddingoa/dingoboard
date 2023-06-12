<template>
  <v-form class="container">
    <v-card>
      <v-card-title class="text-h5 font-weight-regular blue white--text">
        비밀번호를 변경해 주세요.
      </v-card-title>
      <v-card-text>
        <div style="padding: 10px">
          <v-text-field
            v-model="passwordValue"
            :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
            :rules="[rules.passwordRules]"
            :type="show1 ? 'text' : 'password'"
            name="input-10-1"
            label="Password"
            counter
            @click:append="show1 = !show1"
          ></v-text-field>
        </div>
        <div style="padding: 10px">
          <v-text-field
              v-model="confirmPassword"
            :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
              :rules="[rules.confirmPasswordRules, rules.passwordRules,rules.confirmPasswordRulesCheck]"
            :type="show2 ? 'text' : 'password'"
            name="input-10-2"
            label="Check Password"
            @click:append="show2 = !show2"
          ></v-text-field>
        </div>
        <div style="padding: 10px">
          <v-btn block color="primary" @click="passwordRules()">
            비밀번호 변경
          </v-btn>
        </div>
      </v-card-text>
    </v-card>
  </v-form>
</template>

<script>
import store from "../store";
import axios from "../service/axios";

export default {
  name: "Defined",
  data: () => ({
    show1: false,
    show2: false,
    show3: false,
    show4: false,
    passwordValue: '',
    confirmPassword: '',
    rules: {
      passwordRules: v => !!v || "Password is required",
      confirmPasswordRules: v => !!v || "Password is required",
      confirmPasswordRulesCheck : v => v === this.passwordValue || '패스워드가 일치하지 않습니다.'
    }
  }),
  methods: {
    passwordRules() {
        var data = {
          password: this.passwordValue,
          email: store.state.email
        };

        axios
            .post("/auth/password", data)
            .then(() => {
              this.$router.push("/login");
            })
            .catch(ex => {
              alert(ex.message);
            });
    },
  }
};
</script>

<style scoped></style>
