import React, { Component } from 'react';
import PageSearchByExcel from './PageSearchByExcel';
import PageSearchByManual from './PageSearchByManual';
import PageSearchByText from './PageSearchByText';
import { Radio, Icon } from 'antd'

export default class PageSearch extends Component {
    constructor(props) {
        super(props);
        this.state = {
            searchType: "Text",
            example: '',
        }
    }

    componentWillMount() {
        localStorage.setItem('currentMenu', 'search')
    }

    renderSearch() {
        const isAuthenticated = this.props.isAuthenticated
        switch (this.state.searchType) {
            case 'Excel':
                return (
                    <div>
                        <PageSearchByExcel isAuthenticated={isAuthenticated} />
                    </div>
                )

            case 'Text':
                return (
                    <div>
                        <PageSearchByText isAuthenticated={isAuthenticated} example={this.state.example} setExampleEmpty={this.setExampleEmpty} />
                    </div>
                )

            case 'Manual':
                return (
                    <div>
                        <PageSearchByManual isAuthenticated={isAuthenticated} example={this.state.example} setExampleEmpty={this.setExampleEmpty} />
                    </div>
                )

            default:
                return <div>default</div>
        }
    }

    setExampleEmpty = () => {
        this.setState({
            example: ''
        })
    }

    handleExample = () => {
        switch (this.state.searchType) {
            case 'Excel':

                break

            case 'Text':
                this.setState({
                    example: 'D12S391:19,25\nTPOX:8\nD13S317:8'
                })
                break

            case 'Manual':
                this.setState({
                    example: {
                        D12S391: '19,25',
                        TPOX: '8',
                        D13S317: '8',
                    }
                })
                break

            default:
                break
        }
    }

    render() {
        console.log(this.state.example)
        return (<div>
            <br />
            <div className="container">
                <p>
                    <strong>
                        We provide multiple methods to compare your sample data with our
                        database&nbsp;
                        <Icon type="info-circle" onClick={() => { this.handleExample() }} />
                    </strong>
                </p>
                <br />
                <Radio.Group
                    onChange={e => { this.setState({ searchType: `${e.target.value}` }); this.setExampleEmpty() }}
                    defaultValue="Text"
                >
                    <Radio.Button value="Excel" >Excel</Radio.Button>
                    <Radio.Button value="Text" >Text</Radio.Button>
                    <Radio.Button value="Manual" >Manual</Radio.Button>
                </Radio.Group>
            </div>
            <br />
            {this.renderSearch()}
        </div>)
    }
}