import React, { useState } from "react";
import Chart from "react-apexcharts";

export default function HourAllocationChart() {
  const [options, setOptions] = useState({
    labels: ["Apple", "Mango", "Orange", "Watermelon"],
    colors: ["#87BC45", "#EF4444", "#BD7EBE", "#3B82F6", "#FB923C", "#F5D800"],
    legend: {
      show: true,
      fontWeight: 600,
    },
    plotOptions: {
      pie: {
        donut: {
          size: "65%",
          background: "transparent",
          labels: {
            show: true,
            value: {
              show: true,
              fontWeight: 500,
              formatter: function (val) {
                return val;
              },
            },
            total: {
              show: true,
              label: "Worked Hours",
              fontWeight: 700,
            },
          },
        },
      },
    },
  });
  const [series, setSeries] = useState([44, 55, 41, 17, 15, 40, 23]);

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
