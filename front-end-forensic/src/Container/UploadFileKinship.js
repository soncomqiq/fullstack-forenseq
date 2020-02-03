import React from 'react'
import { API_BASE_URL, ACCESS_TOKEN } from '../constants';
import axios from 'axios';
import { Button, Icon, Table, Divider, Tag } from 'antd';
import '../css/Box.css'

class FileUploadKinship extends React.Component {
    constructor(props) {
        super(props)

        this.state = {
            selectedFile: null, loaded: '', message: null, uploading: false, dataKinship: [], piList: []
        }
    }

    handleUpload = async () => {
        this.setState({
            uploading: true
        })
        this.setState({ loaded: '..', message: 'Uploading file' });

        var formData = new FormData();
        formData.append('file', this.state.selectedFile, this.state.selectedFile.name);

        await fetch(API_BASE_URL + "/file/uploadFile", {
            headers: { 'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN) },
            method: 'POST',
            body: formData
        }).then((res) => { this.setState({ loaded: (<div><Icon type="loading" />&nbsp;Please wait...</div>), message: 'Analysing' }) })
        const auth = { 'headers': { 'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN) } }
        await axios.get(API_BASE_URL + "/analysis/getkinship").then((res) => {
            let piList = []
            const keys = Object.keys(res.data.piList)
            console.log(keys)
            keys.map((key) => {
                piList.push({ locus: key, PI: res.data.piList[key] })
            })
            this.setState({
                piList: piList,
                dataKinship: res.data,
                loaded: (<div>Completed</div>),
                message: 'Success'
            })
            console.log(res.data)
        })
        this.setState({
            uploading: false
        })
    }

    handleselectedFile = event => {
        this.setState({
            selectedFile: event.target.files[0],
            loaded: 'Ready to upload',
            message: 'File is selected'
        })
    }

    render() {
        const dataKinship = this.state.dataKinship;
        const size = Object.keys(dataKinship).length;
        const uploading = this.state.uploading;
        const fileList = this.state.selectedFile;
        const piList = this.state.piList;
        const columns = [{
            title: 'Locus Name',
            dataIndex: 'locus',
            align: 'center'
        }, {
            title: 'PI',
            dataIndex: 'PI',
            align: 'center',
            render: (text, record) => (
                parseFloat(record.PI, 10).toFixed(4)
            ),
        },];
        return (
            <div className="Box">
                <input type="file" name="" id="" onChange={this.handleselectedFile} />
                <br />
                <Button
                    type="primary"
                    onClick={this.handleUpload}
                    disabled={fileList === null}
                    loading={uploading}
                    style={{ marginTop: 16 }}
                >
                    {uploading ? 'Uploading' : 'Start Upload'}
                </Button>
                <div className="Box-Space" />
                <br />
                {(fileList === null) ?
                    null : (<div style={{ padding: "5px", border: "1px solid powderblue" }}>
                        <div> {this.state.loaded} </div>
                        <div> {this.state.message} </div>
                    </div>)
                }
                {(size > 0) ?
                    <div>
                        <hr
                            style={{
                                border: 0,
                                clear: "both",
                                display: "block",
                                width: "96%",
                                backgroundColor: "#333333",
                                height: "1px"
                            }}
                        />
                        Parent:&nbsp;<strong>{dataKinship.parent}</strong><br />
                        Child:&nbsp;<strong>{dataKinship.child}</strong><br />
                        <br />
                        <Table columns={columns} dataSource={piList} />
                        <br />
                        Prior Prob. :&nbsp;<strong>{parseFloat(dataKinship.priorProb, 10).toFixed(4)}</strong>&nbsp;&nbsp;&nbsp;
                        Post. Prob. :&nbsp;<strong>{parseFloat(dataKinship.postProb, 10).toFixed(4)}</strong>
                        <br />
                        <br />
                        {dataKinship.report}
                    </div> : null}
            </div >
        )
    }
}

export default FileUploadKinship