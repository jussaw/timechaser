import React, { useState } from "react";
import Chart from "react-apexcharts";

export default function Sandbox() {
  const [options, setOptions] = useState({
    plotOptions: {
      pie: {
        donut: {
          size: "65%",
          background: "transparent",
          labels: {
            show: true,
            name: {
              show: true,
              fontSize: "22px",
              fontFamily: "Helvetica, Arial, sans-serif",
              fontWeight: 600,
              color: undefined,
              offsetY: -10,
              formatter: function (val) {
                return val;
              },
            },
            value: {
              show: true,
              fontSize: "16px",
              fontFamily: "Helvetica, Arial, sans-serif",
              fontWeight: 400,
              color: undefined,
              offsetY: 20,
              formatter: function (val) {
                return val;
              },
            },
            total: {
              show: true,
              showAlways: false,
              label: "Worked \nHours",
              fontSize: "16px",
              fontFamily: "Helvetica, Arial, sans-serif",
              fontWeight: 600,
              color: "#373d3f",
              formatter: function (w) {
                return w.globals.seriesTotals.reduce((a, b) => {
                  return a + b;
                }, 0);
              },
            },
          },
        },
      },
    },
    labels: ["Apple", "Mango", "Orange", "Watermelon"],
    colors: ["#E9D5FF", "#968586", "#EF4444", "#3B82F6", "#FB923C", "#9BA1B3"],

    legend: {
      show: true,
      showForSingleSeries: false,
      showForNullSeries: true,
      showForZeroSeries: true,
      position: "bottom",
      horizontalAlign: "center",
      floating: false,
      fontSize: "14px",
      fontFamily: "Helvetica, Arial",
      fontWeight: 400,
      formatter: undefined,
      inverseOrder: false,
      width: undefined,
      height: undefined,
      tooltipHoverFormatter: undefined,
      customLegendItems: [],
      offsetX: 0,
      offsetY: 0,
      labels: {
        colors: undefined,
        useSeriesColors: false,
      },
      markers: {
        size: 6,
        shape: undefined, // circle, square, line, plus, cross
        strokeWidth: 2,
        fillColors: [
          "#E9D5FF",
          "#968586",
          "#EF4444",
          "#3B82F6",
          "#FB923C",
          "#9BA1B3",
        ],
        radius: 2,
        customHTML: undefined,
        onClick: undefined,
        offsetX: 0,
        offsetY: 0,
      },
      itemMargin: {
        horizontal: 5,
        vertical: 0,
      },
      onItemClick: {
        toggleDataSeries: true,
      },
      onItemHover: {
        highlightDataSeries: true,
      },
    },
    markers: {
      size: [4, 10],
      colors: [
        "#E9D5FF",
        "#968586",
        "#EF4444",
        "#3B82F6",
        "#FB923C",
        "#9BA1B3",
      ],
      strokeColors: "#fff",
      strokeWidth: 2,
      strokeOpacity: 0.9,
      strokeDashArray: 0,
      fillOpacity: 1,
      discrete: [],
      shape: "circle",
      radius: 2,
      offsetX: 0,
      offsetY: 0,
      onClick: undefined,
      onDblClick: undefined,
      showNullDataPoints: true,
      hover: {
        size: undefined,
        sizeOffset: 3,
      },
    },
    tooltip: {
      enabled: true,
      fillSeriesColor: false,
    },
  });

  const [series, setSeries] = useState([44, 55, 41, 17, 15, 40]);

  //TODO: get project/program names and put into labels

  //TODO: get time submissions and put into series

  return (
    <div className="dashboard-component flex h-full w-full flex-grow items-center justify-center">
      <div className="donut">
        <Chart options={options} series={series} type="donut" width="380" />
      </div>
    </div>
  );
}

// class Donut extends Component {
//   constructor(props) {
//     super(props);

//     this.state = {
//       options: {
//         labels: ["Apple", "Mango", "Orange", "Watermelon"],
//         fill: {
//           colors: [``, "#E91E63", "#9C27B0"],
//         },
//       },
//       series: [44, 55, 41, 17, 15],
//     };
//   }
// }
