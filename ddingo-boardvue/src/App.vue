<template>
  <div id="app">
    <navigation></navigation>
  </div>
</template>

<script>
import navigation from './components/menulist/navigation.vue';
import vuetify from './plugins/vuetify'
import createPersistedState from "vuex-persistedstate";

export default {
  vuetify: vuetify,
  components: {navigation},
  name: "App",
  plugins: [createPersistedState()],

};
</script>

<style>

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}

</style>
