import React, {Component} from 'react'
import Button from '@material-ui/core/Button'
import axios from 'axios'
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import './options.css'

class Options extends Component {
    state = {
        worlds: []
    }

    componentDidMount(){
        this.collectBackupWorlds()
    }

    createBackupWorld = () =>{
        var that = this
        axios.get("/create_backup_world").then(function(response){
            that.collectBackupWorlds()
        })
    }

    collectBackupWorlds = () =>{
        var that = this
        axios.get("/get_backup_worlds").then(function(response){
            that.setState({worlds: response.data.list})
        })
    }

    render(){
        return(
            <div>
                <div>
                    Options:
                </div>
                <div className="buttons">
                    <Button onClick={this.createBackupWorld} style={{margin: "10px"}} variant="contained" color="secondary">
                        Backup World (WPA)
                    </Button>
                    <Button style={{margin: "10px"}} variant="contained" color="secondary">
                        Download World (WPA)
                    </Button>
                    <Button style={{margin: "10px"}} variant="contained" color="secondary">
                        Update Server (WPA)
                    </Button>
                </div>
                <div id="world_window">
                    Backed Up Worlds
                    <div id="worlds">
                        <Table>
                        <TableHead>
                            <TableRow>
                            <TableCell>Worlds</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {this.state.worlds.map(world => (
                            <TableRow key={world.name}>
                                <TableCell component="th">{world}</TableCell>
                            </TableRow>
                            ))}
                        </TableBody>
                        </Table>
                    </div>
                </div>
            </div>
        )
    }
}

export default Options