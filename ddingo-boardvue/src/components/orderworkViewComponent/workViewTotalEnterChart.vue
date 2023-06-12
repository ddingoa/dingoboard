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

import {CategoryScale, Chart as ChartJS, Legend, LinearScale, LineElement, PointElement, Title, Tooltip} from 'chart.js'

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
  name: "workViewTotalEnterChart",
  data: () => ({
    chartData: {
      labels: [],
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
  props: {
    chartDataValue: [],
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
      default: () => {
      }
    },
    plugins: {
      type: Array,
      default: () => []
    }
  },
  created() {
    this.initData();
  },
  watch: {
    chartDataValue() {
      this.chartDataValue = this.chartDataValue
      this.initData();
    }
  },
  methods: {
    initData() {
      this.SetChartData(this.chartDataValue);
    },
    SetChartData(result) {
      this.chartData.labels = Object.keys(groupBy(result, "workStart"));
      var fomatChartData = [];
      var itemData = groupBy(result, "goodsCd");

      for (var key in itemData) {
        var chartColor = '#' + Math.round(Math.random() * 0xffffff).toString(16);
        var data = itemData[key];
        var chartDataValue = [];
        var goodsName = "";
        for (var i = 0; i < data.length; i++) {
          goodsName = data[i].goodsNm;
          chartDataValue.push(data[i].totalEnterCount);
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
  }
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

<style scoped>

</style>
