import { Upload, Icon, message, Statistic, Row, Col } from 'antd';
import React from 'react';
import { API_BASE_URL } from '../constants';
import Axios from 'axios';

const Dragger = Upload.Dragger;

const props = {
  name: 'file',
  multiple: true,
  action: API_BASE_URL + '/file/search/excelfile',
};

export default class PageSearchByExcel extends React.Component {

  constructor(props) {
    super(props)
    this.state = {
      totalSample: 0,
      totalMatchSample: 0,
      isUploaded: false,
    }
  }

  componentWillMount() {
    Axios.get(API_BASE_URL + "/resources/person/numberofperson").then((Response) => {
      this.setState({
        totalSample: Response.data
      })
    })
  }

  onChange = (info) => {
    const status = info.file.status;
    const data = new FormData()
    data.append('file', info.file.originFileObj, info.file.name)
    Axios.post(props.action, data)
      .then((Response) => {
        this.setState({
          totalMatchSample: Response.data.length,
          isUploaded: true,
        })
      })
      .catch((error) => console.log(error))
    if (status !== 'uploading') {
      console.log(info.file, info.fileList);
    }
    if (status === 'done') {
      message.success(`${info.file.name} file uploaded successfully.`);
    } else if (status === 'error') {
      message.error(`${info.file.name} file upload failed.`);
    }
  }

  render() {
    // console.log(this.state.totalMatchSample)
    return (
      <div>
        <Dragger {...props} onChange={this.onChange}>
          <p className="ant-upload-drag-icon">
            <Icon type="inbox" />
          </p>
          <p className="ant-upload-text">Click or drag file to this area to upload</p>
          <p className="ant-upload-hint">Support for a single or bulk upload. Strictly prohibit from uploading company data or other band files</p>
        </Dragger>
        <br />
        <br />
        <div>
          {(this.state.isUploaded) ? <Statistic title="This data is" value={(this.state.totalMatchSample > 0 ? 'found' : 'not found') + ' in database'} /> : null}
        </div>
      </div>
    )
  }
}