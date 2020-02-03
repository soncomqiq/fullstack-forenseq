import React from 'react';
import { Button, Modal, Table, Divider, Tag, notification, Upload, Icon, Input } from 'antd';
import axios from 'axios';
import { API_BASE_URL, ACCESS_TOKEN } from '../constants'
import WrappedEditForm from './PageEditPersonInfo';
import LoadingIndicator from '../common/LoadingIndicator';
import Highlighter from 'react-highlight-words';

class PageListPersons extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            personList: [],
            visible: {},
            loading: true,
        }
    }

    showModal = (e) => {
        let tmpObj = {}
        tmpObj[e] = true
        let visibleState = this.state.visible
        Object.assign(visibleState, tmpObj)
        this.setState({
            visible: visibleState,
        });
    }

    handleOk = (e, year) => {
        const auth = { 'headers': { 'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN) } }
        axios.delete(API_BASE_URL + "/resources/person/persons?sampleID=" + e + "&sampleYear=" + year, auth)
            .then((res) => {
                notification.success({
                    message: 'Forenseq App',
                    description: 'Sample ID: ' + e + " has been deleted!",
                });
                this.renderListPersons();
            })
            .catch((err) => {
                notification.error({
                    message: 'Forenseq App',
                    description: err || 'Something went wrong!'
                });
            })
        console.log(e);
        let tmpObj = {}
        tmpObj[e] = false
        let visibleState = this.state.visible
        Object.assign(visibleState, tmpObj)
        this.setState({
            visible: visibleState,
        });
    }

    handleCancel = (e) => {
        console.log(e);
        let tmpObj = {}
        tmpObj[e] = false
        let visibleState = this.state.visible
        Object.assign(visibleState, tmpObj)
        console.log(visibleState)
        this.setState({
            visible: visibleState,
        });
    }

    componentWillMount() {
        this.props.setIsLoading(true);
        localStorage.setItem('currentMenu','listperson')
        this.renderListPersons();
        this.props.setIsLoading(false);
    }

    renderListPersons = () => {
        const auth = { 'headers': { 'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN) } }
        axios.get(API_BASE_URL + "/resources/person/persons", auth).then((response) => {
            this.setState({
                personList: response.data
            })
            response.data.map((record) => {
                let tmpObj = {}
                tmpObj[record.id.sampleId] = false;
                Object.assign(this.state.visible, tmpObj)
            })
            this.setState({
                loading: false
            })
        });
    }

    ModalEdit = (record) => {
        const modal = Modal.info({
            title: 'Edit Form',
            content: (
                <div>
                    <WrappedEditForm data={record} renderListPersons={this.renderListPersons}
                        edit={true} destoryModel={() => {
                            modal.destroy()
                        }} />
                </div>
            ),
            onOk: () => { },
            onCancel: () => { },
            okText: 'Back',
            okType: 'danger',
            width: 1000,
            centered: true,
            keyboard: false,
        });
    }

    ModalAdd = (record) => {
        const modal = Modal.info({
            title: 'Add Form',
            content: (
                <div>
                    <WrappedEditForm renderListPersons={this.renderListPersons} submitBtn={"Add"}
                        edited={false} destoryModel={() => {
                            modal.destroy()
                        }} />
                </div>
            ),
            onOk: () => { },
            onCancel: () => { },
            okText: 'Back',
            okType: 'danger',
            width: 1000,
            centered: true,
            keyboard: false,
        });
    }

    getColumnSearchProps = (dataIndex, add) => ({
        filterDropdown: ({
            setSelectedKeys, selectedKeys, confirm, clearFilters,
        }) => (
                <div style={{ padding: 8 }}>
                    <Input
                        ref={node => { this.searchInput = node; }}
                        placeholder={`Search ${dataIndex}`}
                        value={selectedKeys[0]}
                        onChange={e => setSelectedKeys(e.target.value ? [e.target.value] : [])}
                        onPressEnter={() => this.handleSearch(selectedKeys, confirm)}
                        style={{ width: 188, marginBottom: 8, display: 'block' }}
                    />
                    <Button
                        type="primary"
                        onClick={() => this.handleSearch(selectedKeys, confirm)}
                        icon="search"
                        size="small"
                        style={{ width: 90, marginRight: 8 }}
                    >
                        Search
            </Button>
                    <Button
                        onClick={() => this.handleReset(clearFilters)}
                        size="small"
                        style={{ width: 90 }}
                    >
                        Reset
            </Button>
                </div>
            ),
        filterIcon: filtered => <Icon type="search" style={{ color: filtered ? '#1890ff' : undefined }} />,
        onFilter: (value, record) => (add) ? record[dataIndex][add].toString().toLowerCase().includes(value.toLowerCase()) :
            record[dataIndex].toString().toLowerCase().includes(value.toLowerCase()),
        onFilterDropdownVisibleChange: (visible) => {
            if (visible) {
                setTimeout(() => this.searchInput.select());
            }
        },
        render: (text) => (
            <Highlighter
                highlightStyle={{ backgroundColor: '#ffc069', padding: 0 }}
                searchWords={[this.state.searchText]}
                autoEscape
                textToHighlight={text.toString()}
            />
        ),
    })

    handleSearch = (selectedKeys, confirm) => {
        confirm();
        this.setState({ searchText: selectedKeys[0] });
    }

    handleReset = (clearFilters) => {
        clearFilters();
        this.setState({ searchText: '' });
    }

    render() {
        if (this.state.loading) {
            return <LoadingIndicator />
        }
        console.log(this.state.visible)
        const columns = [{
            title: 'Sample ID',
            dataIndex: 'id.sampleId',
            key: 'id.sampleId',
            align: 'center',
            ...this.getColumnSearchProps('id', 'sampleId'),
            sorter: (a, b) => a.id.sampleId > b.id.sampleId,
            sortDirections: ['descend', 'ascend'],
        }, {
            title: 'Sample Year',
            dataIndex: 'id.sampleYear',
            key: 'id.sampleYear',
            align: 'center',
            sorter: (a, b) => a.id.sampleYear > b.id.sampleYear,
            ...this.getColumnSearchProps('id','sampleYear'),
        }, {
            title: 'Age',
            dataIndex: 'age',
            key: 'age',
            align: 'center',
            sorter: (a, b) => a.age - b.age,
            ...this.getColumnSearchProps('age'),
        }, {
            title: 'Gender',
            dataIndex: 'gender',
            key: 'gender',
            align: 'center',
            sorter: (a, b) => a.gender > b.gender,
            ...this.getColumnSearchProps('gender'),
        }, {
            title: 'Province',
            dataIndex: 'province',
            key: 'province',
            align: 'center',
            sorter: (a, b) => a.province > b.province,
            ...this.getColumnSearchProps('province'),
        }, {
            title: 'Name',
            dataIndex: 'name',
            align: 'center',
            sorter: (a, b) => a.name > b.name,
            ...this.getColumnSearchProps('name'),
        }, {
            title: 'Surname',
            dataIndex: 'surname',
            align: 'center',
            sorter: (a, b) => a.surname > b.surname,
            ...this.getColumnSearchProps('surname'),
        }, {
            title: 'Action',
            key: 'action',
            align: 'center',
            render: (text, record) => (
                <span>
                    <a href={"/user/view/" + record.id.sampleYear + "/" + record.id.sampleId}>View</a>
                    <Divider type="vertical" />
                    <a onClick={() => this.ModalEdit(record)}>
                        Edit
                    </a>
                    <Divider type="vertical" />
                    <a onClick={() => this.showModal(record.id.sampleId)}>Delete</a>
                    <Modal
                        title="Modal"
                        visible={this.state.visible[record.id.sampleId]}
                        onOk={() => this.handleOk(record.id.sampleId, record.id.sampleYear)}
                        onCancel={() => this.handleCancel(record.id.sampleId)}
                        okText="Delete"
                        cancelText="Cancel"
                    >
                        <p>Are you sure to delete the whole data of Sample ID : {record.id.sampleId} / Sample Year : {record.id.sampleYear}</p>
                    </Modal>
                </span>
            ),
        }];
        const data = this.state.personList;
        return (
            <div>
                <div style={{ textAlign: "right" }}>
                    <Button type="primary" shape="round" icon="user-add" onClick={() => this.ModalAdd()}>Add new person</Button>
                </div>
                <br />
                <Table columns={columns} dataSource={data} />
            </div>
        )
    }
}

export default PageListPersons;