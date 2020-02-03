import {
    Form, Input, Button, AutoComplete, Radio, notification, Col, Row
} from 'antd';
import React from 'react'
import { ACCESS_TOKEN, API_BASE_URL } from '../constants';
import Axios from 'axios';

class RegistrationForm extends React.Component {
    state = {
        confirmDirty: false,
        autoCompleteResult: [],
    };

    handleSubmit = (e) => {
        e.preventDefault();
        const auth = { 'headers': { 'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN) } }
        this.props.form.validateFieldsAndScroll((err, values) => {
            if (!err) {
                console.log('Received values of form: ', values);
                let requestParam = {}
                let id = { sampleId: values.sampleId, sampleYear: values.sampleYear }
                requestParam["id"] = id;
                requestParam["pid"] = values.PID;
                requestParam["name"] = values.name;
                requestParam["surname"] = values.lastName;
                requestParam["gender"] = values.gender;
                requestParam["province"] = values.province;
                requestParam["region"] = values.region;
                requestParam["country"] = values.country;
                requestParam["race"] = values.race;
                requestParam["age"] = values.age;
                console.log(requestParam)
                Axios.post(API_BASE_URL + "/resources/person/persons", requestParam, auth)
                    .then((res) => {
                        this.props.destoryModel();
                        notification.success({
                            message: 'Forenseq App',
                            description: 'Sample ID :' + values.sampleId + " has been " + ((!this.props.edited) ? "edited" : "added") + "!",
                        });
                        this.props.renderListPersons();
                    })
                    .catch((err) => {
                        notification.error({
                            message: 'Forenseq App',
                            description: err || 'Something went wrong!'
                        });
                    })
            }
        });
    }

    render() {
        const { getFieldDecorator } = this.props.form;

        const formItemLayout = {
            labelCol: {
                xs: { span: 24 },
                sm: { span: 8 },
            },
            wrapperCol: {
                xs: { span: 24 },
                sm: { span: 16 },
            },
        };
        const tailFormItemLayout = {
            wrapperCol: {
                xs: {
                    span: 24,
                    offset: 0,
                },
                sm: {
                    span: 16,
                    offset: 8,
                },
            },
        };

        return (
            <Form layout="inline" {...formItemLayout} onSubmit={this.handleSubmit}>
                <Row gutter={2}>
                    <Col span={12}>
                        <Form.Item
                            label={(
                                <span>
                                    Sample ID&nbsp;
                        </span>
                            )}
                        >
                            {getFieldDecorator('sampleId', {
                                initialValue: (this.props.data) ? this.props.data.id.sampleId : "",
                                rules: [{ required: true, message: 'Please input your sample ID!' }],
                            })(
                                <Input />
                            )}
                        </Form.Item>
                        <Form.Item
                            label={(
                                <span>
                                    Sample Year
                        </span>
                            )}
                        >
                            {getFieldDecorator('sampleYear', {
                                initialValue: (this.props.data) ? this.props.data.id.sampleYear : "",
                                rules: [{ required: true, message: 'Please input your Sample Year!' }],
                            })(
                                <Input />
                            )}
                        </Form.Item>
                        <Form.Item
                            label={(
                                <span>
                                    Person ID&nbsp;
                        </span>
                            )}
                        >
                            {getFieldDecorator('PID', {
                                initialValue: (this.props.data) ? this.props.data.pid : "",
                                rules: [{ required: true, message: 'Please input your PID!' },
                                { len: 13, message: 'PID must be 13 digits!' },
                                ],
                            })(
                                <Input />
                            )}
                        </Form.Item>
                        <Form.Item
                            label={(
                                <span>
                                    First Name&nbsp;
                                </span>
                            )}
                        >
                            {getFieldDecorator('name', {
                                initialValue: (this.props.data) ? this.props.data.name : "",
                                rules: [{ required: true, message: 'Please input the name!' }],
                            })(
                                <Input />
                            )}
                        </Form.Item>
                        <Form.Item
                            label={(
                                <span>
                                    Last Name&nbsp;
                                </span>
                            )}
                        >
                            {getFieldDecorator('lastName', {
                                initialValue: (this.props.data) ? this.props.data.surname : "",
                                rules: [{ required: true, message: 'Please input the last name!' }],
                            })(
                                <Input />
                            )}
                        </Form.Item>
                        <Form.Item
                            label={(
                                <span>
                                    Gender&nbsp;
                        </span>
                            )}
                        >
                            {getFieldDecorator('gender', {
                                initialValue: (this.props.data) ? this.props.data.gender : "",
                                rules: [
                                    { required: true, message: 'Please select a gender' },
                                ],
                            })(
                                <Radio.Group>
                                    <Radio.Button value="male">Male</Radio.Button>
                                    <Radio.Button value="female">Famale</Radio.Button>
                                </Radio.Group>
                            )}
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item
                            label={(
                                <span>
                                    Province&nbsp;
                        </span>
                            )}
                        >
                            {getFieldDecorator('province', {
                                initialValue: (this.props.data) ? this.props.data.province : "",
                                rules: [{ required: true, message: 'Please input the province' }],
                            })(
                                <Input />
                            )}
                        </Form.Item>
                        <Form.Item
                            label={(
                                <span>
                                    Region&nbsp;
                        </span>
                            )}
                        >
                            {getFieldDecorator('region', {
                                initialValue: (this.props.data) ? this.props.data.region : "",
                                rules: [{ required: true, message: 'Please input the region' }],
                            })(
                                <Input />
                            )}
                        </Form.Item>
                        <Form.Item
                            label={(
                                <span>
                                    Country&nbsp;
                        </span>
                            )}
                        >
                            {getFieldDecorator('country', {
                                initialValue: (this.props.data) ? this.props.data.country : "",
                                rules: [{ required: true, message: 'Please input the country' }],
                            })(
                                <Input />
                            )}
                        </Form.Item>
                        <Form.Item
                            label={(
                                <span>
                                    Age&nbsp;
                        </span>
                            )}
                        >
                            {getFieldDecorator('age', {
                                initialValue: (this.props.data) ? this.props.data.age : "",
                                rules: [{ required: true, message: 'Please input an age!' },
                                { pattern: /^-?(0|[1-9][0-9]*)(\.[0-9]*)?$/, message: "age must be number." }],
                            })(
                                <Input />
                            )}
                        </Form.Item>
                        <Form.Item
                            label={(
                                <span>
                                    Race&nbsp;
                        </span>
                            )}
                        >
                            {getFieldDecorator('race', {
                                initialValue: (this.props.data) ? this.props.data.race : "",
                                rules: [{ required: true, message: 'Please input race' }],
                            })(
                                <Input />
                            )}
                        </Form.Item>
                        <Form.Item {...tailFormItemLayout}>
                            <Button type="primary" htmlType="submit">{this.props.submitBtn ? this.props.submitBtn : "Edit"}</Button>
                        </Form.Item>
                    </Col>
                </Row>
            </Form>
        );
    }
}

const WrappedEditForm = Form.create({ name: 'edit' })(RegistrationForm);
export default WrappedEditForm;