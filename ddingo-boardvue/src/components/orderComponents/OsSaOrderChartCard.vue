<template>
  <LineChartGenerator
    :chart-options="chartOptions"
    :chart-data="chartData"
    :chart-id="chartId"
    :dataset-id-key="datasetIdKey"
    :plugins="plugins"
    :css-classes="cssClasses"
    :styles="styles"
    :width="width"
    :height="height"
  />
</template>

<script>
import { Line as LineChartGenerator } from 'vue-chartjs/legacy'

import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  LineElement,
  LinearScale,
  CategoryScale,
  PointElement
} from 'chart.js'
import axios from "axios";

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  LineElement,
  LinearScale,
  CategoryScale,
  PointElement
)

export default {
  name: "OsSaOrderChartCard",
  data: () => ({
    chartData: {
      labels: [
        '00',
        '01',
        '02',
        '03',
        '04',
        '05',
        '06',
        '07',
        '08',
        '09',
        '10',
        '11',
        '12',
        '13',
        '14',
        '15',
        '16',
        '17',
        '18',
        '19',
        '20',
        '21',
        '22',
        '23'
      ],
      datasets: [
      ]
    },
    chartOptions: {
      responsive: true,
      maintainAspectRatio: false
    }
  }),
  components: {
    LineChartGenerator
  },
  props: {
    chartDataValue : [],
    chartId: {
      type: String,
      default: 'line-chart'
    },
    datasetIdKey: {
      type: String,
      default: 'label'
    },
    width: {
      type: Number,
      default: 400
    },
    height: {
      type: Number,
      default: 400
    },
    cssClasses: {
      default: '',
      type: String
    },
    styles: {
      type: Object,
      default: () => {}
    },
    plugins: {
      type: Array,
      default: () => []
    }
  },
  created() {
    this.initData();
  },
  watch : {
    chartDataValue (){
      this.chartDataValue = this.chartDataValue
      this.initData();
    }
  },
  methods :{
    initData() {
      var data = {
        fabCode : this.chartDataValue.fabCode,
        itemCode : this.chartDataValue.itemCode
      };
      var result = [];
      var specResult = [];
      axios
          .post("/api/get/opworkitemlist", data)
          .then(response => {
            result = response.data.chartData;
            specResult = response.data.chartSpecData;
            this.SetChartData(result,specResult);
          })
          .catch(ex => {
            alert(ex.message);
          });
    },
    SetChartData(result,specResult) {
      var labelsSize = this.chartData.labels.length;
      var fomatChartData = [];
      for(var i=0; i<result.length; i++){
        var chartColor =  '#' + Math.round(Math.random() * 0xffffff).toString(16);

        var frontChartData = [];
        frontChartData = chartdata;
        var chartdata = [];

        for(var j=0; j< labelsSize; j++) {
          if(this.chartData.labels[j] == result[i].inputTime){
            chartdata.push(result[i].itemCount)
          }else {
            chartdata.push(0)
          }
        }

        var data = {};
        if(i ==0) {
          data = {
            label: result[i].eqpName,
            backgroundColor: chartColor,
            borderColor: chartColor,
            data: chartdata,
            pointRadius: 4,
          }
          fomatChartData.push(data)
        }else {
          if(result[i].eqpCode != result[i-1].eqpCode) {
            data = {
              label: result[i].eqpName,
              backgroundColor: chartColor,
              borderColor: chartColor,
              data: chartdata,
              pointRadius: 4,
            }
            fomatChartData.push(data);
          }else {
            for(var z=0; z<chartdata.length; z++){
              if(frontChartData[z] != 0){
                chartdata[z] = frontChartData[z]
              }
            }
            var checkindex = true;
            for(var index=0; index<fomatChartData.length; index++){
              if(fomatChartData[index].label == result[i].eqpName){
                fomatChartData[index].data = chartdata
                checkindex = false;
              }
            }

            if(checkindex) {
              data = {
                label: result[i].eqpName,
                backgroundColor: chartColor,
                borderColor: chartColor,
                data: chartdata,
                pointRadius: 4,
              }
              fomatChartData.push(data)
            }
          }
        }
      }

      if(specResult != null) {
        var SetUSL = {
          label: "최소 생산량",
          backgroundColor: '#e70d0d',
          data: {"00": specResult.workTime00, "23": specResult.workTime00},
          pointRadius: 4,
          borderColor: 'rgb(234,26,26)',
        }
        fomatChartData.push(SetUSL)
      }

      this.chartData.datasets = fomatChartData;

    }
  }
};
</script>

<style scoped>

</style>
