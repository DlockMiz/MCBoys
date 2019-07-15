import React, { Component } from 'react'
import axios from 'axios'
import SockJsClient from 'react-stomp';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Switch from '@material-ui/core/Switch';
import './home.css'


class Home extends Component {
    constructor(props) {
        super(props)
     }

    state = {
        status: false,
        websocket_url: "http://67.205.128.141:8080",
        log_output: "No Current Logs"
    }

    componentDidMount() {
        this.determineStatusColor()
        this.determineWebSocketUrl()
    }
    test = () =>{
        var that = this
        axios.get("/test")
    }

    test1 = () =>{
        var that = this
        axios.get("/plz").then(function(response){
            console.log(response.data)
        })
    }

    determineWebSocketUrl = () =>{
        var current_url;
        var local_url = "http://localhost:8080"
        var live_url = "http://67.205.128.141:8080"

        if(window.location.hostname == 'localhost'){
            current_url = local_url
        } else {
            current_url = live_url
        }
        this.setState({websocket_url: current_url})
    }

    serverSwitch = () => {
        this.setState({ status: !this.state.status }, function () {
             this.determineStatusColor()
             this.state.status ? this.turnServerOn() : this.turnServerOff() 
        })
    }

    turnServerOn = () => {
        var that = this
        axios.get("/run")
    }

    turnServerOff = () => {
        console.log("off")
    }

    determineStatusColor = () => {
        var text = document.getElementById("status")
        this.state.status ? text.style.color = "green" : text.style.color = "red"
    }
    
    render() {
        return (
            <div>
                <SockJsClient url={this.state.websocket_url+"/ws"} topics={['/mc_server']}
                onMessage={(msg) => { this.setState({log_output: this.state.log_output += msg + '\n'}) }}
                ref={ (client) => { this.clientRef = client }} />
                <AppBar position="static">
                    <Toolbar>
                        <h1>The Boys</h1>
                    </Toolbar>
                </AppBar>
                <div className="main_wrapper">
                    <button onClick={this.test}>
                        YEET
                    </button>
                    <button onClick={this.test1}>
                        Skeet
                    </button>
                    <div className="column">Server Status: <div id="status">{this.state.status ? "Online" : "Offline"}</div>
                        <Switch
                            checked={this.state.status}
                            value="checkedB"
                            onChange={this.serverSwitch}
                            color="primary"
                        />
                        <div id="log_window">
                            Log Output
                            <div id="output">
                                {this.state.log_output}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default Home