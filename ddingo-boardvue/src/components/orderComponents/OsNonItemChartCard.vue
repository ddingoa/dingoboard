<template>
  <BarChartGenerator
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
import { Bar as BarChartGenerator } from 'vue-chartjs/legacy'

import {
  Chart as ChartJS,
  BarElement,
  Title,
  Tooltip,
  Legend,
  LinearScale,
  CategoryScale,
  PointElement
} from 'chart.js'
import axios from "axios";
import axiosAP from "axios";

ChartJS.register(
    BarElement,
    Title,
    Tooltip,
    Legend,
    LinearScale,
    CategoryScale,
    PointElement
)

export default {
  name: "OsNonItemChartCard",
  data: () => ({
    fabSettingData : [],
    chartData: {
      labels: [
      ],
      datasets: [
      ]
    },
    chartOptions: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          display: false,
          hidden : true,
        }
      }
    }
  }),

  created() {
    this.initData();
  },
  watch : {
    chartDataValue (){
      this.chartDataValue = this.chartDataValue
      this.initData();
    },
    hubModel () {
      this.hubModel = this.hubModel
      this.initData();
    }
  },
  methods :{
    initData() {
      axiosAP.get("/api/get/producthubcodeitem/" + this.hubModel.vlaue)
          .then(response => {
            this.fabSettingData = response.data.data;
            this.chartData.labels = [];
            this.chartData.datasets = [];

            var labelSetList =[];
            var chartData = [];

            for(var i=0; i<this.fabSettingData.length; i++){
              labelSetList.push(this.fabSettingData[i].goodsNm);
            }
            this.chartData.labels = labelSetList;
            var dataCheck = true;
            for(var j=0; j<this.chartDataValue.length; j++) {
              chartData =[];
              for (var i = 0; i < this.fabSettingData.length; i++) {
                if(this.fabSettingData[i].goodsNm == this.chartDataValue[j].itemName){
                  if(this.chartDataValue[j].dfCount > 0) {
                    chartData.push(this.chartDataValue[j].dfCount);
                    dataCheck = true;
                  }else {
                    dataCheck = false;
                  }
                }else {
                  chartData.push(0);
                }
              }
              if(dataCheck) {
                this.chartData.datasets.push({
                  label: this.chartDataValue[j].workStart + "_" + this.chartDataValue[j].goodsNm,
                  backgroundColor: '#' + Math.round(Math.random() * 0xffffff).toString(16),
                  data: chartData
                });
              }
            }


          })
          .catch(ex => {
            alert(ex.message);
          });

    },
  },
  components: {
    BarChartGenerator
  },
  props: {
    chartDataValue : [],
    hubModel : [],
    chartId: {
      type: String,
      default: 'bar-chart'
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
      default: () => [

      ],
    }
  },
}
</script>

<style scoped>

</style>