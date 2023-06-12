<template>
  <v-form id="app">
    <h2>QR CODE</h2>
    <div>
      <StreamBarcodeReader
        @decode="(a, b, c) => onDecode(a, b, c)"
        @loaded="() => onLoaded()"
      ></StreamBarcodeReader>
      Input Value: {{ text || "Nothing" }}
    </div>
  </v-form>
</template>

<script>
import { StreamBarcodeReader } from "vue-barcode-reader";

export default {
  name: "QrcodeReader",
  data: () => ({
    text: "",
    id: null
  }),
  components: { StreamBarcodeReader },
  methods: {
    onDecode(a, b, c) {
      this.text = a;

      this.$emit("qrcodereader", a);

      if (this.id) clearTimeout(this.id);
      this.id = setTimeout(() => {
        if (this.text === a) {
          this.text = "";
        }
      }, 5000);
    },
    onLoaded() {
      console.log("load");
    },
    async onInit(promise) {
      try {
        await promise;
      } catch (error) {
        if (error.name === "NotAllowedError") {
          this.alert = "alert-danger";
          this.status = "ERROR: you need to grant camera access permisson";
        } else if (error.name === "NotFoundError") {
          this.alert = "alert-danger";
          this.status = "ERROR: no camera on this device";
        } else if (error.name === "NotSupportedError") {
          this.alert = "alert-danger";
          this.status = "ERROR: secure context required (HTTPS, localhost)";
        } else if (error.name === "NotReadableError") {
          this.alert = "alert-danger";
          this.status = "ERROR: is the camera already in use?";
        } else if (error.name === "OverconstrainedError") {
          this.alert = "alert-danger";
          this.status = "ERROR: installed cameras are not suitable";
        } else if (error.name === "StreamApiNotSupportedError") {
          this.alert = "alert-danger";
          this.status = "ERROR: Stream API is not supported in this browser";
        }
      }
    }
  }
};
</script>

<style scoped>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
