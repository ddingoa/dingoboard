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
import {Line as LineChartGenerator} from 'vue-chartjs/legacy'

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
  name: "OsSaOrderDfChartDashCard",
  props: {
    chartDFDataValue: [],
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
      default: 225
    },
    cssClasses: {
      default: '',
      type: String
    },
    styles: {
      type: Object,
      default: () => {
      }
    },
    plugins: {
      type: Array,
      default: () => []
    }
  },
  watch: {
    chartDFDataValue() {
      this.chartDFDataValue = this.chartDFDataValue
      this.initData();
    }
  },
  created() {
    this.initData();
  },
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
      datasets: []
    },
    chartOptions: {
      responsive: true,
      maintainAspectRatio: false
    }
  }),
  components: {
    LineChartGenerator
  },
  methods: {
    initData() {
      this.SetChartData(this.chartDFDataValue);
    },
    SetChartData(result) {
      var fomatChartData = [];
      var itemData = groupBy(result, "itemName");

      for (var key in itemData) {
        var chartColor = '#' + Math.round(Math.random() * 0xffffff).toString(16);
        var data = itemData[key];
        var chartDataValue = [];
        var goodsName = "";
        for (var i = 0; i < data.length; i++) {
          goodsName = data[i].itemName;
          for (var j = 0; j < this.chartData.labels.length; j++) {
            if (this.chartData.labels[j] == data[i].inputTime) {
              chartDataValue.push(data[i].workItemCount);
            } else {
              chartDataValue.push(0)
            }
          }
        }

        var chartData = {
          label: goodsName,
          backgroundColor: chartColor,
          borderColor: chartColor,
          data: chartDataValue,
          pointRadius: 4,
        }
        fomatChartData.push(chartData)
      }

      this.chartData.datasets = fomatChartData;

    }
  },
};

function groupBy(array, key) {
  const result = {}
  array.forEach(item => {
    if (!result[item[key]]) {
      result[item[key]] = []
    }
    result[item[key]].push(item)
  })
  return result
}
</script>

<style scoped></style>

