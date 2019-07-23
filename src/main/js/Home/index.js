import React, { Component } from 'react'
import axios from 'axios'
import SockJsClient from 'react-stomp';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import LinearProgress from '@material-ui/core/LinearProgress';
import Switch from '@material-ui/core/Switch';
import {config} from '../Config/environment'
import Options from '../Options'
import './home.css'


class Home extends Component {
    constructor(props) {
        super(props)
     }

    state = {
        status: false,
        websocket_url: config.url.WEBSOCKET_URL,
        log_output: " "
    }

    componentDidMount() {
        this.getServerStatus()
    }

    getServerStatus = () =>{
        var that = this
        axios.get("/server_status").then(function(response){
            var newStatus = response.data == "ON" ? true : false
            that.setState({status: newStatus})
        })
    }
    serverSwitch = () => {
        this.setState({ status: !this.state.status }, function () {
             this.state.status ? axios.get("start_server") : axios.get("/stop_server")
        })
    }
    
    render() {
        return (
            <div>
                <SockJsClient url={this.state.websocket_url+"/logs"} topics={['/logs']}
                onMessage={(msg) => {this.setState({log_output: this.state.logoutput += msg + '\n'})}}
                ref={ (client) => { this.clientRef = client }} />

                <SockJsClient url={this.state.websocket_url+"/server_status"} topics={['/server_status']}
                onMessage={(msg) => {this.setState({status: msg})}}
                ref={ (client) => { this.clientRef = client }} />
                
                <AppBar position="static">
                    <Toolbar>
                        <h1>The Boys</h1>
                    </Toolbar>
                </AppBar>
                <div className="main_wrapper">
                    {/* <button onClick={this.delete}>
                        DELETE
                    </button>
                    <button onClick={this.yeet}>
                        YEET
                    </button> */}
                    <div className="column" style={{borderRight: '5px solid #3f51b5'}}>Server Status: <div style={this.state.status ? {color:'green'} : {color: 'red'}}>{this.state.status ? "Online" : "Offline"}</div>
                    {/* <br /> */}
                    {/* <LinearProgress color="secondary" variant="determinate" value={75} /> */}
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
                    <div className="column">
                        <Options></Options>
                    </div>
                </div>
            </div>
        )
    }
}

export default Home