import React, { Component } from 'react'
import axios from 'axios'
import {
  Upload, Button, Icon, message,
} from 'antd';
import LogoUpload from '../images/LogoUpload.png'
import { API_BASE_URL, ACCESS_TOKEN } from '../constants';
import Axios from 'axios';

const Dragger = Upload.Dragger;

const props = {
  name: 'file',
  multiple: true,
  action: API_BASE_URL + '/file/uploadFile',
};

export default class UploadFileExcel extends Component {
  constructor(props) {
    super(props)

    this.state = {
      selectedFile: null, loaded: '', message: null, uploading: false
    }
  }

  onChange = async (info) => {
    this.setState({
      uploading: true
    })
    const status = info.file.status;
    const data = new FormData()
    data.append('file', info.file.originFileObj, info.file.name)
    await axios.post(props.action, data)
      .then((Response) => {
        this.setState({ loaded: '1/7', message: 'Bring data to Person table' })
      })
      .catch((error) => console.log(error))
    const auth = { 'headers': { 'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN) } }
    await axios.get(API_BASE_URL + "/resources/person/uploadpersonfile", auth).then((res) => this.setState({ loaded: '2/7', message: 'Bring data to CE_Data table' }))
    await axios.get(API_BASE_URL + "/resources/strlocusinfo/uploadcedatafile", auth).then((res) => this.setState({ loaded: '3/7', message: 'Bring data to Forenseq table' }))
    await axios.get(API_BASE_URL + "/resources/forenseq/uploadforenseqfile", auth).then((res) => this.setState({ loaded: '4/7', message: 'Bring data to ForenseqX table' }))
    await axios.get(API_BASE_URL + "/resources/forenseqx/uploadforenseqxfile", auth).then((res) => this.setState({ loaded: '5/7', message: 'Bring data to ForenseqY table' }))
    await axios.get(API_BASE_URL + "/resources/forenseqy/uploadforenseqyfile", auth).then((res) => this.setState({ loaded: '6/7', message: 'Bring data to iSNPs table' }))
    await axios.get(API_BASE_URL + "/resources/isnps/uploadisnpsfile", auth).then((res) => this.setState({ loaded: '7/7', message: 'Successed' }))
    if (status !== 'uploading') {
      console.log(info.file, info.fileList);
    }
    if (status === 'done') {
      message.success(`${info.file.name} file uploaded successfully.`);
      this.setState({
        uploading: false
      })
    } else if (status === 'error') {
      message.error(`${info.file.name} file upload failed.`);
      this.setState({
        uploading: false
      })
    }
  }

  render() {
    const uploading = this.state.uploading;
    return (
      <div className="App">
        <div><img src={LogoUpload} /></div>
        <br />
        {(uploading) ?
          (<div style={{ padding: "5px", border: "1px solid powderblue" }}>
            <div> {this.state.loaded} </div>
            <div> {this.state.message} </div>
          </div>) : null
        }
        <Dragger {...props} onChange={this.onChange}>
          <p className="ant-upload-drag-icon">
            <Icon type="inbox" />
          </p>
          <p className="ant-upload-text">Click or drag file to this area to upload</p>
          <p className="ant-upload-hint">Support for a single or bulk upload. Strictly prohibit from uploading company data or other band files</p>
        </Dragger>
      </div >
    )
  }
}
