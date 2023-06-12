<template>
  <v-form  style="padding: 10px">
    <v-col cols="12">
    <table border="1" style="width: 1.85in; height: 1.059in">
      <th>
        지엔어린이집
      </th>
      <tr>
        <td>지엔반</td>
      </tr>
      <tr>
        <td align="center" >
          <qr-code class="qrWrap" :size=size tag="svg" :error-level=correctionLevel :text=value></qr-code>
          <!--            <barcode :value="value" :tag="tag"  :width="1" :height="60" :options="options"></barcode>-->
        </td>
      </tr>
    </table>
    </v-col>
  </v-form>
</template>

<script>
import VueBarcode from 'vue-barcode';

export default {
  name: "QrcodeValue",
  props: {
    qrUserData : Object
  },
  data: () => ({
    qrvalue: "",
    correctionLevel : "H",
    size: 35,
    value: 'QT0123423',
    tag: 'svg',
    options: {
      lineColor: '#ff7069',
      fontSize: 11,
      font: 'Courier',
      width: 3,
      height: 60,
      marginBottom: 60,
      format: 'MSI',
      background: '#ccffff'
    },
  }),
  components: {
    'barcode': VueBarcode
  },
  watch : {
    qrUserData (){
      var StringData = {
        'fabCode': this.qrUserData.fabCode,
        'lineCode': this.qrUserData.lineCode,
        'email': this.qrUserData.email,
        'userName': this.qrUserData.userName
      }
      this.qrvalue = JSON.stringify(StringData);
    }
  },
}
</script>

<style scoped>
.qrWrap {
  background-color: white;
}
</style>
