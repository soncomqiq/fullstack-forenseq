import React, { Component } from 'react';
import {
  Form, Row, Col, Input, Button, Icon, Radio, Statistic
} from 'antd';
import Axios from 'axios';
import { API_BASE_URL, ACCESS_TOKEN } from '../constants';
import { Typography } from 'antd';

const { Text } = Typography;

class PageSearchByManual extends React.Component {
  state = {
    expand: false,
    chromosome: "Autosome",
    currentKit: "AmpFLSTR_Identifiler_Plus",
    autosomKit: [],
    yKit: [],
    xKit: [],
    currentLocusList: [],
    totalMatchSample: 0,
    listMatchSample: [],
    totalSample: 0,
    isAuthenticated: this.props.isAuthenticated,
    isClicked: false
  };

  componentWillMount() {
    Axios.get(API_BASE_URL + "/resources/person/numberofperson").then((Response) => {
      console.log(Response.data)
      this.setState({
        totalSample: Response.data
      })
    })
    Axios.get(API_BASE_URL + "/resources/getallautosomalkit").then((Response) => {
      this.setState({
        autosomKit: Response.data
      })
    })
    Axios.get(API_BASE_URL + "/resources/getallykit").then((Response) => {
      this.setState({
        yKit: Response.data
      })
    })
    Axios.get(API_BASE_URL + "/resources/getallxkit").then((Response) => {
      this.setState({
        xKit: Response.data
      })
    })
    this.getFieldsAPI();
  }

  getFieldsAPI() {
    let currentChromosome = "";
    switch (this.state.chromosome) {
      case "Autosome":
        currentChromosome = "/resources/getlocusautosomalkit/";
        break;
      case "Y_STRs":
        currentChromosome = "/resources/getlocusykit/";
        break;
      case "X_STRs":
        currentChromosome = "/resources/getlocusxkit/";
        break;
      default:
        currentChromosome = "";
    }
    let result = [];
    Axios.get(API_BASE_URL + currentChromosome + this.state.currentKit).then((Response) => {
      Response.data.map((locus) => result.push(locus));
      this.setState({
        currentLocusList: result
      })
    })
  }

  // To generate mock Form.Item
  getFields() {
    console.log(this.props.example)
    const { getFieldDecorator } = this.props.form;
    const children = [];
    const locusList = this.state.currentLocusList;
    for (let i = 0; i < locusList.length; i++) {
      children.push(
        <Col span={2} key={locusList[i]} style={{ display: 'block' }}>
          <Form.Item label={`${locusList[i]}`} colon={false}>
            {getFieldDecorator(`${locusList[i]}`, {
              rules: [{
                required: false,
                message: 'Input something!',
              }],
              initialValue: this.props.example[locusList[i]]
            })(
              <Input placeholder={"M,N"} />
            )}
          </Form.Item>
        </Col>
      );
    }
    return children;
  }

  handleSearch = (e) => {
    e.preventDefault();
    this.setState({
      isClicked: true
    })
    this.props.form.validateFields((err, values) => {
      console.log('Received values of form: ', values);
      let data = [];
      console.log(Object.keys(values))
      var locus = Object.keys(values)
      for (var i = 0; i < locus.length; i++) {
        console.log(values[locus[i]])
        if (typeof values[locus[i]] !== "undefined") {
          var multi = values[locus[i]].split(',')
          multi.map(allele =>
            data.push({
              locus: `${locus[i]}`,
              allele: `${allele}`
            })
          )
        }
      }
      if (this.props.isAuthenticated) {
        const auth = { 'headers': { 'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN) } }
        Axios.post(API_BASE_URL + '/resources/findpersonbylocus', data, auth).then((Response) => {
          console.log(Response.data)
          this.setState({
            totalMatchSample: Response.data.length,
            listMatchSample: Response.data
          })
        });
      } else {
        Axios.post(API_BASE_URL + '/resources/findNumberOfPersonByLocus', data).then((Response) => {
          console.log(Response.data)
          this.setState({
            totalMatchSample: Response.data,
          })
        });
      }
    });
  }

  handleReset = () => {
    this.props.form.resetFields();
    this.props.setExampleEmpty();
  }

  renderKitList = () => {
    let component = null;
    switch (this.state.chromosome) {
      case "Autosome":
        component = this.state.autosomKit.map((kit) =>
          <Radio.Button value={kit}>{kit}</Radio.Button>
        )
        break;
      case "Y_STRs":
        component = this.state.yKit.map((kit) =>
          <Radio.Button value={kit}>{kit}</Radio.Button>
        )
        break;
      case "X_STRs":
        component = this.state.xKit.map((kit) =>
          <Radio.Button value={kit}>{kit}</Radio.Button>
        )
        break;
      default:
        component = null;
    }
    return (
      <Radio.Group value={this.state.currentKit} onChange={(e) => this.setState({ currentKit: e.target.value }, () => this.getFieldsAPI())}>
        {component}
      </Radio.Group>
    );
  }

  render() {
    console.log(this.state.listMatchSample)
    const isAuthenticated = this.state.isAuthenticated
    const isClicked = this.state.isClicked
    const chromosome = this.state.chromosome;
    const currentKit = this.state.currentKit;
    const renderKitList = this.renderKitList();
    const getFields = this.getFields();
    return (
      <div>
        <Row>
          <p>Current chromosome is : <strong>{chromosome}</strong></p>
          <Radio.Group value={chromosome} onChange={(e) => this.setState({ chromosome: e.target.value })}>
            <Radio.Button value="Autosome">Autosome</Radio.Button>
            <Radio.Button value="Y_STRs">Y_STRs</Radio.Button>
            <Radio.Button value="X_STRs">X_STRs</Radio.Button>
          </Radio.Group>
          <br />
          <br />
          <p>Current kit is : <strong>{currentKit}</strong></p>
          {renderKitList}
        </Row>
        <br />
        <br />
        <Form
          className="ant-advanced-search-form"
          style={{
            padding: '24px',
            background: '#fbfbfb',
            border: '1px solid #d9d9d9',
            borderRadius: '6px',
          }}
          onSubmit={this.handleSearch}
        >
          <Row gutter={24}>{getFields}</Row>
          <Row>
            <Col span={24} style={{ textAlign: 'left' }}>
              <br />
              <Button type="primary" htmlType="submit">Search</Button>
              <Button style={{ marginLeft: 8 }} onClick={this.handleReset}>Clear</Button>
            </Col>
          </Row>
        </Form>
        <br /><br />
        <div>
          <Statistic title="Matched Sample" value={this.state.totalMatchSample} suffix={"/ " + this.state.totalSample} />
        </div>
        {isAuthenticated && isClicked ? <div>
          {this.state.listMatchSample.map((data) => {
            return (
              <div>
                <Text type="warning">Sample Year: {data[0]} Sample ID:{data[1]}</Text>
              </div>
            )
          })}
        </div> : null}
      </div >
    );
  }
}

export default Form.create()(PageSearchByManual);