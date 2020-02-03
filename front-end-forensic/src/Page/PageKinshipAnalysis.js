import React from 'react';
import Box from '../Container/Box';
import FileUploadKinship from '../Container/UploadFileKinship';
import { Modal } from 'antd';
import Example from '../Container/Example';

const DescriptionBody = (
    <div>
        Upload file to calculate probability.
    </div>
);

class Body extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            visible: false,
        }
    }

    info() {
        Modal.info({
            title: 'Example For Kinship',
            content: (
                <Example/>
            ),
            onOk() { },
        });
    }

    render() {
        const ButtonComponent = (
            <div>
                <a onClick={this.info}>Click here for example file</a>
            </div>
        );
        const visible = this.state.visible;
        return (
            <div className="Body">
                <Box title="Description" body={DescriptionBody} btnComponent={ButtonComponent} link="/example" />
                <FileUploadKinship />
            </div>
        );
    }
}

export default Body