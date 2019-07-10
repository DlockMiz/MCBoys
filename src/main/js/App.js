import React, {Component} from 'react'
import axios from 'axios'

class App extends Component {

  runCommand = () => {
    axios.get("/run").then(function(response){
      console.log(response.data)
    })
  }

  render() {
    return(
      <div>
        <button onClick={this.runCommand}>Press to YEET</button>
      </div>
    )
  }

}

export default App;
