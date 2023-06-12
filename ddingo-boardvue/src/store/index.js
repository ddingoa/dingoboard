import Vue from 'vue'
import Vuex from 'vuex'
import navigation from "../components/menulist/navigation.vue";
import { login } from "../module/login";
import createPersistedState from "vuex-persistedstate";
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    email: "",
    userName: "",
    iconflagSet: "",
    drawer: false,
    fabCode: "",
    fabName: "",
    lineCode: "",
    lineName: "",
    workCode: "",
    workName: "",
    userSet : false,
  },
  mutations: {
    emildata(state, payload) {
      state.email = payload.email;
    },
    userdata(state, payload) {
      state.userName = payload.userName;
    },
    iconflagSetData(state, payload) {
      state.iconflagSet = payload.iconflag;
    },
    drawerSetData(state, payload) {
      state.drawer = payload.drawer;
    },
    fabCodeData(state, payload) {
      state.fabCode = payload.fabCode;
    },
    fabNameData(state, payload) {
      state.fabName = payload.fabName;
    },
    lineCodeData(state, payload) {
      state.lineCode = payload.lineCode;
    },
    lineNameData(state, payload) {
      state.lineName = payload.lineName;
    },
    workCodeData(state, payload) {
      state.workCode = payload.workCode;
    },
    workNameData(state, payload) {
      state.workName = payload.workName;
    },
    userSetData(state, payload) {
      state.userSet = payload.userSet;
    }
  },
  actions: {
  },
  modules: {
    navigation,
    login
  },
  plugins: [createPersistedState()],
})
