import React, {Component} from 'react'
import Button from '@material-ui/core/Button'
import './options.css'

class Options extends Component {
    render(){
        return(
            <div id="options_wrapper">
                <div>
                    Options:
                </div>
                <div className="buttons">
                    <Button style={{margin: "10px"}} variant="contained" color="secondary" className="buttons">
                        Backup World (WPA)
                    </Button>
                    <Button style={{margin: "10px"}} variant="contained" color="secondary" className="buttons">
                        Download World (WPA)
                    </Button>
                    <Button style={{margin: "10px"}} variant="contained" color="secondary" className="buttons">
                        Update Server (WPA)
                    </Button>
                </div>
            </div>
        )
    }
}

export default Options