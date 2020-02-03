import React from 'react'
import Axios from 'axios';
import { API_BASE_URL, ACCESS_TOKEN } from '../constants';
import { Table, Radio, Row, Col, Button, Input, Icon } from 'antd';
import Highlighter from 'react-highlight-words';
import LoadingIndicator from '../common/LoadingIndicator';

class PageViewSingleID extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            CEData: [],
            summarizeForenseq: [],
            CEDataX: [],
            summarizeForenseqX: [],
            CEDataY: [],
            summarizeForenseqY: [],
            CEDataI: [],
            summarizeForenseqI: [],
            forenseq: [],
            forenseqY: [],
            forenseqX: [],
            iSNPs: [],
            renderType: 'A',
            loading: true,
        }
    }

    componentWillMount() {
        this.props.setIsLoading(true);
        localStorage.setItem('currentMenu','listperson')
        this.props.setIsLoading(false);
        this.renderAllTable()
    }

    getColumnSearchProps = (dataIndex, add) => ({
        filterDropdown: ({ setSelectedKeys, selectedKeys, confirm, clearFilters }) => (
            <div style={{ padding: 8 }}>
                <Input
                    ref={node => {
                        this.searchInput = node;
                    }}
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
                <Button onClick={() => this.handleReset(clearFilters)} size="small" style={{ width: 90 }}>
                    Reset
            </Button>
            </div>
        ),
        filterIcon: filtered => (
            <Icon type="search" style={{ color: filtered ? '#1890ff' : undefined }} />
        ),
        onFilter: (value, record) => (add) ? record[dataIndex][add].toString().toLowerCase().includes(value.toLowerCase()) :
            record[dataIndex].toString().toLowerCase().includes(value.toLowerCase()),
        onFilterDropdownVisibleChange: visible => {
            if (visible) {
                setTimeout(() => this.searchInput.select());
            }
        },
        render: text => (
            <Highlighter
                highlightStyle={{ backgroundColor: '#ffc069', padding: 0 }}
                searchWords={[this.state.searchText]}
                autoEscape
                textToHighlight={text.toString()}
            />
        ),
    });

    renderAllTable = () => {
        const auth = { 'headers': { 'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN) } }
        Axios.get(API_BASE_URL + "/resources/strlocusinfo/getByID?sampleYear=" + this.props.match.params.yid
            + "&sampleID=" + this.props.match.params.id, auth)
            .then((res) => {
                let summarizeForenseq = [];
                let CEData = [];
                let summarizeForenseqX = [];
                let CEDataX = [];
                let summarizeForenseqY = [];
                let CEDataY = [];
                let summarizeForenseqI = [];
                let CEDataI = [];
                res.data.map((record) => {
                    switch (record.id.from) {
                        case 'CE_Data':
                            switch (record.type) {
                                case 'Autosomal':
                                    CEData.push(record)
                                    break;
                                case 'X':
                                    CEDataX.push(record)
                                    break;
                                case 'Y':
                                    CEDataY.push(record)
                                    break;
                                case 'iSNPs':
                                    CEDataI.push(record)
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 'Forenseq':
                            switch (record.type) {
                                case 'Autosomal':
                                    summarizeForenseq.push(record)
                                    break;
                                case 'X':
                                    summarizeForenseqX.push(record)
                                    break;
                                case 'Y':
                                    summarizeForenseqY.push(record)
                                    break;
                                case 'iSNPs':
                                    summarizeForenseqI.push(record)
                                    break;
                                default:
                                    break;
                            }
                            break;
                        default:
                            break;
                    }
                    return null;
                })
                // console.log(CEData);
                this.setState({
                    CEData: CEData,
                    CEDataX: CEDataX,
                    CEDataY: CEDataY,
                    CEDataI: CEDataI,
                    summarizeForenseq: summarizeForenseq,
                    summarizeForenseqX: summarizeForenseqX,
                    summarizeForenseqY: summarizeForenseqY,
                    summarizeForenseqI: summarizeForenseqI,
                })
            })
        Axios.get(API_BASE_URL + "/resources/forenseq/getByID?sampleYear=" + this.props.match.params.yid
            + "&sampleID=" + this.props.match.params.id, auth)
            .then((res) => {
                this.setState({
                    forenseq: res.data
                })
            })
        Axios.get(API_BASE_URL + "/resources/forenseqy/getByID?sampleYear=" + this.props.match.params.yid
            + "&sampleID=" + this.props.match.params.id, auth)
            .then((res) => {
                this.setState({
                    forenseqY: res.data
                })
            })
        Axios.get(API_BASE_URL + "/resources/forenseqx/getByID?sampleYear=" + this.props.match.params.yid
            + "&sampleID=" + this.props.match.params.id, auth)
            .then((res) => {
                this.setState({
                    forenseqX: res.data
                })
            })
        Axios.get(API_BASE_URL + "/resources/isnps/getByID?sampleYear=" + this.props.match.params.yid
            + "&sampleID=" + this.props.match.params.id, auth)
            .then((res) => {
                this.setState({
                    iSNPs: res.data
                })
                this.setState({
                    loading: false
                })
            })
    }

    handleSearch = (selectedKeys, confirm) => {
        confirm();
        this.setState({ searchText: selectedKeys[0] });
    }

    handleReset = (clearFilters) => {
        clearFilters();
        this.setState({ searchText: '' });
    }

    maskedAllele(sequence, seqAlign) {
        let AlphaColor = {
        }
        let AlphaColorSwitch = {}
        const colours = ['#5BF13E', '#4cd631', '#FFB23E', '#e0a041', '#388BEE', '#4c8ddb', '#ED493B', '#d6554a', '#8d28cc', '#bb5ff4']
        if (seqAlign === "No Repeated Data") {
            //Do nothing
            return (<span style={{ backgroundColor: "green" }}>{sequence}</span>);
        } else {
            let alleles = seqAlign.split(' ');
            let pattern = []
            AlphaColor = {}
            AlphaColorSwitch = {}
            console.log(AlphaColor)
            console.log(AlphaColorSwitch)
            alleles.map(allele => {
                if (/\d/.test(allele)) {
                    let tmp = allele.split(')');
                    let tmp1 = tmp[0].split('(');
                    let tmp2 = Object.assign({ pattern: tmp1[1], number: tmp[1] })
                    // console.log(tmp2)
                    pattern.push(tmp2)
                } else if (allele === "") {

                } else {
                    let tmp2 = Object.assign({ pattern: allele, number: 1 })
                    pattern.push(tmp2)
                }
                return null;
            })
            console.log(pattern)
            let k = 0;
            let final = []
            for (var i = 0; i < pattern.length; i++) {
                for (var j = 0; j < pattern[i].number; j++) {
                    if (AlphaColor[pattern[i].pattern] == null) {
                        AlphaColor[pattern[i].pattern] = colours[k++];
                        AlphaColorSwitch[pattern[i].pattern] = colours[k++];
                    }
                    final.push(pattern[i].pattern)
                }
            }
            if (final.length === 0) {
                return <span style={{ backgroundColor: "#5BF13E" }}>{sequence}</span>
            }
            return final.map((letter, i) => (
                <span style={{ backgroundColor: (i % 2 === 0) ? AlphaColorSwitch[letter] : AlphaColor[letter] }}>{letter}</span>
            ))
        }
    }

    renderTable() {
        const AlphaColor = {
            A: '#5BF13E',
            T: '#7eb7fc',
            C: '#FFB23E',
            G: '#ff9991'
        }
        const columnsCE = [{
            title: 'Locus',
            dataIndex: 'id.locus',
            key: 'id.locus',
            align: 'center',
            width: 150,
            ...this.getColumnSearchProps('id', 'locus'),
            sorter: (a, b) => a.id.locus > b.id.locus,
            sortDirections: ['descend', 'ascend'],
        }, {
            title: 'Genotype',
            dataIndex: 'id.genotype',
            key: 'id.genotype',
            align: 'center',
            width: 150,
            sorter: (a, b) => a.id.genotype > b.id.genotype,
            sortDirections: ['descend', 'ascend'],
        }, {
            title: 'QC Indicatior',
            dataIndex: 'qcindicate',
            key: 'qcindicate',
            align: 'center',
            width: 150,
            sorter: (a, b) => a.qcindicate > b.qcindicate,
            sortDirections: ['descend', 'ascend'],
        },];
        const columnsISNPS = [{
            title: 'Locus',
            dataIndex: 'id.locus',
            key: 'id.locus',
            align: 'center',
            width: 150,
            ...this.getColumnSearchProps('id', 'locus'),
            sorter: (a, b) => a.id.locus > b.id.locus,
            sortDirections: ['descend', 'ascend'],
        }, {
            title: 'Genotype',
            dataIndex: 'id.genotype',
            key: 'id.genotype',
            align: 'center',
            width: 150,
            sorter: (a, b) => a.id.genotype > b.id.genotype,
            sortDirections: ['descend', 'ascend'],
            render: (text, record) => (
                record.id.genotype.split('').map((letter) => (
                    <span style={{ backgroundColor: AlphaColor[letter] }}>{letter}</span>
                ))
            )
        }, {
            title: 'QC Indicatior',
            dataIndex: 'qcindicate',
            key: 'qcindicate',
            align: 'center',
            width: 150,
            sorter: (a, b) => a.qcindicate > b.qcindicate,
            sortDirections: ['descend', 'ascend'],
        },];
        const columns = [{
            title: 'Locus',
            dataIndex: 'forenseqIdentity.locus',
            key: 'forenseqIdentity.locus',
            align: 'center',
            width: 150,
            sorter: (a, b) => a.forenseqIdentity.locus > b.forenseqIdentity.locus,
            sortDirections: ['descend', 'ascend'],
            ...this.getColumnSearchProps('forenseqIdentity', 'locus'),
        }, {
            title: 'Allele',
            dataIndex: 'forenseqIdentity.allele',
            key: 'forenseqIdentity.allele',
            align: 'center',
            width: 100,
            sorter: (a, b) => a.forenseqIdentity.allele > b.forenseqIdentity.allele,
            sortDirections: ['descend', 'ascend'],
        }, {
            title: 'Read Count',
            dataIndex: 'forenseqIdentity.read_count',
            key: 'forenseqIdentity.read_count',
            align: 'center',
            width: 150,
            sorter: (a, b) => a.forenseqIdentity.read_count > b.forenseqIdentity.read_count,
            sortDirections: ['descend', 'ascend'],
        }, {
            title: 'Type',
            dataIndex: 'type',
            key: 'type',
            align: 'center',
            width: 100,
            sorter: (a, b) => a.type > b.type,
            sortDirections: ['descend', 'ascend'],
        }, {
            title: 'Genotype',
            dataIndex: 'genotype',
            key: 'genotype',
            align: 'center',
            width: 100,
            sorter: (a, b) => a.genotype > b.genotype,
            sortDirections: ['descend', 'ascend'],
        }, {
            title: 'Sequence',
            dataIndex: 'sequence',
            key: 'sequence',
            align: 'left',
            width: 500,
            sorter: (a, b) => a.sequence > b.sequence,
            sortDirections: ['descend', 'ascend'],
            render: (text, record) => (
                <div style={{ whiteSpace: "pre-wrap", wordBreak: "break-all" }}>
                    {this.maskedAllele(record.sequence, record.alignment)}
                </div>
            )
        }];
        const columnsI = [{
            title: 'Locus',
            dataIndex: 'forenseqIdentity.locus',
            key: 'forenseqIdentity.locus',
            align: 'center',
            width: 150,
            sorter: (a, b) => a.forenseqIdentity.locus > b.forenseqIdentity.locus,
            sortDirections: ['descend', 'ascend'],
            ...this.getColumnSearchProps('forenseqIdentity', 'locus'),
        }, {
            title: 'Allele',
            dataIndex: 'forenseqIdentity.allele',
            key: 'forenseqIdentity.allele',
            width: 150,
            align: 'center',
            sorter: (a, b) => a.forenseqIdentity.allele > b.forenseqIdentity.allele,
            sortDirections: ['descend', 'ascend'],
            render: (text, record) => (
                record.forenseqIdentity.allele.split('').map((letter) => (
                    <span style={{ backgroundColor: AlphaColor[letter] }}>{letter}</span>
                ))
            )
        }, {
            title: 'Read Count',
            dataIndex: 'read_count',
            key: 'read_count',
            align: 'center',
            width: 150,
            sorter: (a, b) => a.read_count - b.read_count,
            sortDirections: ['descend', 'ascend'],
        }, {
            title: 'Type',
            dataIndex: 'type',
            key: 'type',
            align: 'center',
            width: 150,
            sorter: (a, b) => a.type > b.type,
            sortDirections: ['descend', 'ascend'],
        }, {
            title: 'Genotype',
            dataIndex: 'genotype',
            key: 'genotype',
            align: 'center',
            width: 150,
            sorter: (a, b) => a.genotype > b.genotype,
            sortDirections: ['descend', 'ascend'],
            render: (text, record) => (
                record.genotype.split('').map((letter) => (
                    <span style={{ backgroundColor: AlphaColor[letter] }}>{letter}</span>
                ))
            )
        }];
        switch (this.state.renderType) {
            case 'A':
                return (
                    <div>
                        <Row justify="center" type="flex">
                            <Col span={(this.state.CEData.length > 0) ? 10 : null}>
                                {/*CE TABLE*/}
                                <br />
                                <h1>Forenseq Table</h1>
                                <br />
                                <Table bordered scroll={{ y: 440 }} pagination={false} dataSource={this.state.summarizeForenseq} columns={columnsCE} size="small" />
                            </Col>
                            {(this.state.CEData.length > 0) ? (<Col span={2}></Col>) : null}
                            {(this.state.CEData.length > 0) ? (
                                <Col span={10}>
                                    {/*CE TABLE*/}
                                    <br />
                                    <h1>CE Data Table</h1>
                                    <br />
                                    <Table bordered scroll={{ y: 440 }} pagination={false} dataSource={this.state.CEData} columns={columnsCE} size="small" />
                                </Col>
                            ) : null}
                        </Row>
                        {/*CE FORENSEQ TABLE*/}
                        <br />
                        <h1>Forenseq Table</h1>
                        <br />
                        <Table bordered dataSource={this.state.forenseq} columns={columns} />
                    </div>
                )

            case 'X':
                return (
                    <div>
                        <Row justify="center" type="flex">
                            <Col span={(this.state.CEDataX.length > 0) ? 10 : 10}>
                                {/*CE TABLE*/}
                                <br />
                                <h1>Forenseq Table</h1>
                                <br />
                                <Table bordered scroll={{ y: 440 }} pagination={false} dataSource={this.state.summarizeForenseqX} columns={columnsCE} size="small" />
                            </Col>
                            {(this.state.CEDataX.length > 0) ? (<Col span={2}></Col>) : null}
                            {(this.state.CEDataX.length > 0) ? (
                                <Col span={10}>
                                    {/*CE TABLE*/}
                                    <br />
                                    <h1>CE Data Table</h1>
                                    <br />
                                    <Table bordered scroll={{ y: 440 }} pagination={false} dataSource={this.state.CEDataX} columns={columnsCE} size="small" />
                                </Col>
                            ) : null}
                        </Row>
                        {/*CE FORENSEQX TABLE*/}
                        <br />
                        <h1>ForenseqX Table</h1>
                        <br />
                        <Table bordered dataSource={this.state.forenseqX} columns={columns} />
                    </div>
                )

            case 'Y':
                return (
                    <div>
                        <Row justify="center" type="flex">
                            <Col span={(this.state.CEDataY.length > 0) ? 10 : null}>
                                {/*CE TABLE*/}
                                <br />
                                <h1>Forenseq Table</h1>
                                <br />
                                <Table bordered scroll={{ y: 440 }} pagination={false} dataSource={this.state.summarizeForenseqY} columns={columnsCE} size="small" />
                            </Col>
                            {(this.state.CEDataY.length > 0) ? (<Col span={2}></Col>) : null}
                            {(this.state.CEDataY.length > 0) ? (
                                <Col span={10}>
                                    {/*CE TABLE*/}
                                    <br />
                                    <h1>CE Data Table</h1>
                                    <br />
                                    <Table bordered scroll={{ y: 440 }} pagination={false} dataSource={this.state.CEDataY} columns={columnsCE} size="small" />
                                </Col>
                            ) : null}
                        </Row>
                        {/*CE FORENSEQY TABLE*/}
                        <br />
                        <h1>ForenseqY Table</h1>
                        <br />
                        <Table bordered dataSource={this.state.forenseqY} columns={columns} />
                    </div>
                )

            case 'I':
                return (
                    <div>
                        <Row justify="center" type="flex">
                            <Col span={(this.state.CEDataI.length > 0) ? 10 : null}>
                                {/*CE TABLE*/}
                                <br />
                                <h1>Forenseq Table</h1>
                                <br />
                                <Table bordered scroll={{ y: 440 }} pagination={false} dataSource={this.state.summarizeForenseqI} columns={columnsISNPS} size="small" />
                            </Col>
                            {(this.state.CEDataI.length > 0) ? (<Col span={2}></Col>) : null}
                            {(this.state.CEDataI.length > 0) ? (
                                <Col span={10}>
                                    {/*CE TABLE*/}
                                    <br />
                                    <h1>CE Data Table</h1>
                                    <br />
                                    <Table bordered scroll={{ y: 440 }} pagination={false} dataSource={this.state.CEDataI} columns={columnsCE} size="small" />
                                </Col>
                            ) : null}
                        </Row>
                        {/*CE FORENSEQY TABLE*/}
                        <br />
                        <h1>iSNPs Table</h1>
                        <br />
                        <Table bordered dataSource={this.state.iSNPs} columns={columnsI} />
                    </div>
                )

            default:
                return <div>default</div>
        }
    }

    render() {
        console.log(this.state.loading)
        if (this.state.loading) {
            return <LoadingIndicator />
        }
        return (
            <div>
                <h>Sample ID : {this.props.match.params.id}</h>
                <br />
                <br />
                <Radio.Group
                    onChange={e => this.setState({ renderType: `${e.target.value}` })}
                    defaultValue="A"
                >
                    <Radio.Button value="A">Autosomal</Radio.Button>
                    <Radio.Button value="X">X</Radio.Button>
                    <Radio.Button value="Y">Y</Radio.Button>
                    <Radio.Button value="I">iSNPs</Radio.Button>
                </Radio.Group>
                {this.renderTable()}
            </div>
        )
    }
}

export default PageViewSingleID;