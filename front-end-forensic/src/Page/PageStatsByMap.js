import React, { Component } from "react"
import {
  ComposableMap,
  ZoomableGroup,
  Geographies,
  Geography,
  Markers,
  Marker,
} from "react-simple-maps"
import { scaleLinear } from "d3-scale"
import { VictoryPie } from "victory"
import request from "axios"
import Axios from "axios"
import { Radio, Icon, Col, Row, Typography } from 'antd'
import { API_BASE_URL, ACCESS_TOKEN } from '../constants';

const { Text } = Typography;

const wrapperStyles = {
  width: "100%",
  maxWidth: 980,
  margin: "0 auto",
}

const cityScale = scaleLinear()
  .domain([0, 6]) //[0,37843000]
  .range([1, 25])

class BasicMap extends Component {
  constructor() {
    super()
    this.state = {
      alleles: [],
      chromosome: 'Autosom',
      locus: 'Amelogenin',
      autosom_locus: [],
      y_locus: [],
      x_locus: [],
      colorFlag: {},
    }
    this.fetchAlleles = this.fetchAlleles.bind(this)
  }

  componentWillMount(){
    localStorage.setItem('currentMenu', 'map')
  }

  componentDidMount() {
    this.fetchAlleles(this.state.locus)
    Axios.get(API_BASE_URL + "/resources/getlocuslist").then(Response => {
      this.setState({
        autosom_locus: Response.data["autosomLocus"],
        y_locus: Response.data["yLocus"],
        x_locus: Response.data["xLocus"]
      })
    })
  }

  fetchAlleles(locus) {
    const auth = { 'headers': { 'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN) } }
    request
      .get(API_BASE_URL + "/resources/statisticmap/" + locus, auth)
      .then(res => {
        this.setState({
          alleles: res.data,
        })
        let dict = {}
        res.data.forEach((allele) => {
          dict[allele.allele] = this.rgb2hex(allele.color)
        })
        this.setState({
          colorFlag: dict
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
      <Radio.Group onChange={(e) => { this.setState({ locus: e.target.value }); this.fetchAlleles(e.target.value) }} value={this.state.locus}>
        {component}
      </Radio.Group>
    );
  }

  rgb2hex(rgb) {
    rgb = rgb.match(/^rgba?[\s+]?\([\s+]?(\d+)[\s+]?,[\s+]?(\d+)[\s+]?,[\s+]?(\d+)[\s+]?/i);
    return (rgb && rgb.length === 4) ? "#" +
      ("0" + parseInt(rgb[1], 10).toString(16)).slice(-2) +
      ("0" + parseInt(rgb[2], 10).toString(16)).slice(-2) +
      ("0" + parseInt(rgb[3], 10).toString(16)).slice(-2) : '';
  }

  render() {
    const HeartSvg = () => (
      <svg width="1em" height="1em" fill="currentColor" viewBox="0 0 1024 1024">
        <path d="M923 283.6c-13.4-31.1-32.6-58.9-56.9-82.8-24.3-23.8-52.5-42.4-84-55.5-32.5-13.5-66.9-20.3-102.4-20.3-49.3 0-97.4 13.5-139.2 39-10 6.1-19.5 12.8-28.5 20.1-9-7.3-18.5-14-28.5-20.1-41.8-25.5-89.9-39-139.2-39-35.5 0-69.9 6.8-102.4 20.3-31.4 13-59.7 31.7-84 55.5-24.4 23.9-43.5 51.7-56.9 82.8-13.9 32.3-21 66.6-21 101.9 0 33.3 6.8 68 20.3 103.3 11.3 29.5 27.5 60.1 48.2 91 32.8 48.9 77.9 99.9 133.9 151.6 92.8 85.7 184.7 144.9 188.6 147.3l23.7 15.2c10.5 6.7 24 6.7 34.5 0l23.7-15.2c3.9-2.5 95.7-61.6 188.6-147.3 56-51.7 101.1-102.7 133.9-151.6 20.7-30.9 37-61.5 48.2-91 13.5-35.3 20.3-70 20.3-103.3 0.1-35.3-7-69.6-20.9-101.9z" />
      </svg>
    );
    const HeartIcon = props => <Icon component={HeartSvg} {...props} />;
    const chromosome = this.state.chromosome;
    const locusList = this.renderLocusList();
    console.log(this.state)
    return (
      <div style={wrapperStyles}>
        <Row>
          <Col span={6} pull={0}>
            <Radio.Group value={chromosome} onChange={(e) => this.setState({ chromosome: e.target.value })}>
              <Radio.Button value="Autosom">Autosom</Radio.Button>
              <Radio.Button value="Y_STRs">Y_STRs</Radio.Button>
              <Radio.Button value="X_STRs">X_STRs</Radio.Button>
            </Radio.Group>
            <br /><br />
            {locusList}
          </Col>
          <Col span={14} pull={-4}>
            <ComposableMap
              projectionConfig={{ scale: 3400 }}
              width={700}
              height={900}
              style={{
                width: "100%",
                height: "auto",
              }}
            >
              <ZoomableGroup center={[100, 12.8]}>
                <Geographies geography="/gadm36_THA_1.json">
                  {(geographies, projection) =>
                    geographies.map((geography, i) =>
                      <Geography
                        key={i}
                        geography={geography}
                        projection={projection}
                        style={{
                          default: {
                            fill: "#ECEFF1",
                            stroke: "#607D8B",
                            strokeWidth: 0.75,
                            outline: "none",
                          },
                          hover: {
                            fill: "#FFFFFF",
                            stroke: "#607D8B",
                            strokeWidth: 0.75,
                            outline: "none",
                          },
                          pressed: {
                            fill: "#000000",
                            stroke: "#607D8B",
                            strokeWidth: 0.75,
                            outline: "none",
                          },
                        }}
                      />
                    )}
                </Geographies>
                <Markers>
                  {
                    this.state.alleles.map((allele, i) => (
                      <Marker key={i} marker={{ coordinates: [allele.x, allele.y] }}>
                        <circle
                          cx={0}
                          cy={0}
                          r={cityScale(allele.count)}
                          fill={allele.color}
                          stroke="#607D8B"
                          strokeWidth="2"
                        />
                      </Marker>
                    ))
                  }
                </Markers>
              </ZoomableGroup>
            </ComposableMap>
          </Col>
          <Col >
            <div style={{ textAlign: "left" }}>
              <br />
              <br />
              <br />
              <Text strong>Allele Frequency</Text>
              <br />
              <br />
              {
                Object.keys(this.state.colorFlag).map((key, index) => (
                  <div style={{ textAlign: "left" }}><Text >&nbsp;<HeartIcon style={{ color: this.state.colorFlag[key] }} />&nbsp;&nbsp;<Text style={{ color: 'black' }}>{key}</Text></Text></div>
                ))
              }
            </div>
          </Col>
        </Row>
      </div>
    )
  }
}

export default BasicMap