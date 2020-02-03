import React from 'react'
import Box from './Box';

const ExamplePic = (
    <div>
        put pictures here
    </div>
)

class Example extends React.Component {
    render() {
        return (
            <div className="Body">
                <Box body={ExamplePic} />
            </div>
        );
    }
}

export default Example