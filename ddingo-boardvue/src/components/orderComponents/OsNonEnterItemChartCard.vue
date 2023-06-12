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
  name: "OsNonEnterItemChartCard",
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
        },
        datalabels: {
          color: 'white',
          display: function(context) {
            return context.dataset.data;
          },
          font: {
            weight: 'bold'
          },
          formatter: Math.round
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
       this.chartDataSet();
    },
    chartDataSet() {
      this.chartData.labels = [];
      this.chartData.datasets = [];
      var labelSetList =[];
      var chartData = [];

      for(var i=0; i<this.chartDataValue.length; i++){
        if(i ==0 ) {
          labelSetList.push(this.chartDataValue[i].workStart);
        }else if(this.chartDataValue[i].workStart != this.chartDataValue[i-1].workStart){
          labelSetList.push(this.chartDataValue[i].workStart);
        }
      }
      this.chartData.labels = labelSetList;

      var dataCheck = true;
      for(var j=0; j<this.chartDataValue.length; j++) {
        chartData =[];
        for (var i = 0; i < labelSetList.length; i++) {
          if(labelSetList[i] == this.chartDataValue[j].workStart){
            if(this.chartDataValue[j].enterItem > 0) {
              chartData.push(this.chartDataValue[j].enterItem);
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
            label: this.chartDataValue[j].workStart + "_" + this.chartDataValue[j].itemName,
            backgroundColor: '#' + Math.round(Math.random() * 0xffffff).toString(16),
            data: chartData,
            datalabels: {
              anchor: 'end',
              align: 'start',
            }
          });
        }
      }

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
      default: () => []
    }
  },
}
</script>

<style scoped>

</style>