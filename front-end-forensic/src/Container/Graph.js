import React, { Component } from 'react';
import Chart from 'react-apexcharts';

export default class GraphStat extends Component {
    constructor(props){
        super(props)
    }

    render() {
        return (
          <div class="row">
              <Chart options={this.props.options} series={this.props.series} type="bar" width={500} height={320} />
          </div>
        );
      }
}