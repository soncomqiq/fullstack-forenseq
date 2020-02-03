import React, { Component } from 'react'
import { Menu, Icon } from 'antd';
import LoadingIndicator from '../common/LoadingIndicator';

const SubMenu = Menu.SubMenu;
const MenuItemGroup = Menu.ItemGroup;

export default class AdminNavbar extends Component {
    constructor(props) {
        super(props)

        this.state = {
            isLoginActive: false,
        }
    }

    handleMenuClick = ({ key }) => {
        console.log(key)
        if (key === "logout") {
            this.props.onLogout();
        } else {
            { localStorage.setItem('currentMenu', key) }
        }
    }

    render() {
        if (this.props.isLoading) {
            return <LoadingIndicator />;
        }
        const colorTheme = '';
        console.log(localStorage.getItem('currentMenu'))
        return (
            <Menu
                onClick={this.handleMenuClick}
                selectedKeys={[localStorage.getItem('currentMenu')]}
                mode="horizontal"
                theme="light"
                style={{ lineHeight: '64px', backgroundColor: colorTheme }}
            >
                <Menu.Item key="home">
                    <a href="/" ><Icon type="home" />Home</a>
                </Menu.Item>
                <Menu.Item key="search">
                    <a href="/search" ><Icon type="search" />Search</a>
                </Menu.Item>
                <SubMenu key="stats" title={<span><Icon type="radar-chart" />Statistics</span>}>
                    <MenuItemGroup title="Group By Locus" style={{ backgroundColor: colorTheme }}>
                        <Menu.Item key="graph"><a href="/stats/graph" ><Icon type="bar-chart" />Graph</a></Menu.Item>
                        <Menu.Item key="map"><a href="/stats/map" ><Icon type="google" />Map</a></Menu.Item>
                    </MenuItemGroup>
                </SubMenu>
                <SubMenu key="analysis" title={<span><Icon type="line-chart" />Analysis</span>}>
                    <MenuItemGroup title="Analysis" style={{ backgroundColor: colorTheme }}>
                        <Menu.Item key="kinship"><a href="/analysis/kinship" ><Icon type="team" />Kinchip Analysis</a></Menu.Item>
                    </MenuItemGroup>
                </SubMenu>
                <SubMenu key="menu" style={{ float: 'right' }} title={<span><Icon type="setting" />Lab User Menu</span>}>
                    <MenuItemGroup title="Function" style={{ backgroundColor: colorTheme }}>
                        <Menu.Item key="add"><a href="/adddata" ><Icon type="file-add" />Add data</a></Menu.Item>
                        <Menu.Item key="addperson"><a href="/uploadpersons" ><Icon type="usergroup-add" />Add Persons</a></Menu.Item>
                        <Menu.Item key="cedata"><a href="/uploadcedata" ><Icon type="file-text" />Add CE Data</a></Menu.Item>
                        <Menu.Item key='listperson'><a href="/listpersons" ><Icon type="team" />Person List</a></Menu.Item>
                        <Menu.Item key="isnp"><a href="/isnpstat" ><Icon type="box-plot" />iSNPs</a></Menu.Item>
                        <Menu.Item key="alignment"><a href="/seqalign" ><Icon type="barcode" />Seq Alignment</a></Menu.Item>
                        <Menu.Item key="logout"><Icon type="logout" />Logout</Menu.Item>
                    </MenuItemGroup>
                </SubMenu>
            </Menu>
        )
    }
}
