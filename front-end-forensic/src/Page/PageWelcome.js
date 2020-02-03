import React from 'react'
import Title from 'antd/lib/typography/Title';
import Text from 'antd/lib/typography/Text';
import LogoUpload from '../images/LogoUpload.png'

class PageWelcome extends React.Component {
    componentWillMount(){
        localStorage.setItem('currentMenu', 'home')
    }

    render() {
        return (
            <div>
                <Title level={2}>Welcome to FGxBIO</Title>
                <br />
                <img src={LogoUpload} />
                <br />
                <br />
                <Title level={2}><Text>The Database for Short Tandem Repeat (STR) Sequence</Text></Title>
            </div>
        )
    }
}

export default PageWelcome;