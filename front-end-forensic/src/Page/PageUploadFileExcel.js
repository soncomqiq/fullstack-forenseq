import React, { Component } from 'react';
import UploadFileExcel from '../Container/UploadFileExcel'

export default class PageUploadFileExcel extends Component {
    componentWillMount(){
        this.props.setIsLoading(true);
        localStorage.setItem('currentMenu','add')
        this.props.setIsLoading(false);
    }
    render() {
        return (
            <UploadFileExcel />
        )
    }
}