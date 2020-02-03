import React from 'react'
import { Redirect } from 'react-router-dom'

class Box extends React.Component {

    constructor(props) {
        super(props)

        this.state = {
            popup: false
        }

        this.handleClick = this.handleClick.bind(this)
    }

    handleClick() {
        this.setState(function (prevState) {
            return {
                popup: !prevState.popup
            }
        })
    }

    render() {
        if (this.state.popup === true) {
            return <Redirect to={this.props.link} />
        }

        return (
            <div className="Box">
                <h2 className="Box-Title">{this.props.title}</h2>
                <div className="Box-A">
                    <h3>{this.props.body}</h3>
                </div>
                {(this.props.btn) ?
                    <input type="Button" className="Box-Btn" value={this.props.btn} onClick={this.handleClick} /> : null}
                {this.props.btnComponent}
            </div>
        );
    }
}

export default Box