import React, { Component } from 'react';
import { Button, Radio, Icon, Input, Col, Row } from 'antd';
import Axios from 'axios';
import { API_BASE_URL } from '../constants';
import LocusStatisticInfo from '../Container/LocusStatsInfo';

export default class PageStatsByGraph extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      chromosome: 'Autosom',
      locus: 'Amelogenin',
      autosom_locus: [],
      y_locus: [],
      x_locus: [],
      alleleCount: [],
      dataSummary: []
    }
  }

  handleChartData = async (e) => {
    let chromosome = this.state.chromosome;
    let locus = e.target.value;
    const Response = await Axios.get(API_BASE_URL + "/resources/getstatsgraphinfo/" + chromosome + "/" + locus)
    this.setState({
      alleleCount: Response.data
    })
    const Response1 = await Axios.get(API_BASE_URL + "/resources/hetero")
    this.setState({
      dataSummary: Response1.data
    })
  }

  componentWillMount() {
    localStorage.setItem('currentMenu', 'graph')
    let tmp = { target: { value: this.state.locus } }
    this.handleChartData(tmp);
    Axios.get(API_BASE_URL + "/resources/getlocuslist").then(Response => {
      this.setState({
        autosom_locus: Response.data["autosomLocus"],
        y_locus: Response.data["yLocus"],
        x_locus: Response.data["xLocus"]
      })
    })
  }

  renderLocusList(props) {
    const radioStyle = {
      display: 'block',
      height: '30px',
      lineHeight: '30px',
    };
    let component = null;
    switch (this.state.chromosome) {
      case 'Autosom':
        component = this.state.autosom_locus.map(locus =>
          <Row type="flex" justify="start" align="top">
            <Radio style={radioStyle} value={locus}>{locus}</Radio>
          </Row>
        )
        break;
      case 'Y_STRs':
        component = this.state.y_locus.map(locus =>
          <Row type="flex" justify="start" align="top">
            <Radio style={radioStyle} value={locus}>{locus}</Radio>
          </Row>
        )
        break;
      case 'X_STRs':
        component = this.state.x_locus.map(locus =>
          <Row type="flex" justify="start" align="top">
            <Radio style={radioStyle} value={locus}>{locus}</Radio>
          </Row>
        )
        break;
      default:
        component = null;
    }
    return (
      <Radio.Group onChange={(e) => { this.setState({ locus: e.target.value }); this.handleChartData(e) }} value={this.state.locus}>
        {component}
      </Radio.Group>
    );
  }

  render() {
    const chromosome = this.state.chromosome;
    const locusList = this.renderLocusList();
    return (
      <Row>
        <Col span={6}>
          <Radio.Group value={chromosome} onChange={(e) => this.setState({ chromosome: e.target.value })}>
            <Radio.Button value="Autosom">Autosom</Radio.Button>
            <Radio.Button value="Y_STRs">Y_STRs</Radio.Button>
            <Radio.Button value="X_STRs">X_STRs</Radio.Button>
          </Radio.Group>
          <br /><br />
          {locusList}
        </Col>
        <Col span={12} push={3}>
          <LocusStatisticInfo
            locus={this.state.locus}
            alleleCount={this.state.alleleCount}
            heteroSummary={this.state.dataSummary}
          />
        </Col>
      </Row>
    );
  }
}