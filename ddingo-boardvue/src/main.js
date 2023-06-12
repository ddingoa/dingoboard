import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import VueCookies from "vue-cookies";
import VueQRCodeComponent from 'vue-qrcode-component'
import VueBarcodeScanner from 'vue-barcode-scanner'

let options = {
  sound: true, // default is false
  soundSrc: '/static/sound.wav', // default is blank
  sensitivity: 300, // default is 100
}

Vue.use(VueBarcodeScanner, options)
Vue.component('qr-code', VueQRCodeComponent)

Vue.config.productionTip = false
//쿠키를 사용한다.
Vue.use(VueCookies);

//쿠키의 만료일은 7일이다. (글로벌 세팅)
Vue.$cookies.config("7d");

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
