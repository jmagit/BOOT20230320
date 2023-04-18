import React, { Component } from 'react'

function Pantalla(props) {
    return <output>{props.valor}</output>
}
export class Contador extends Component {
  constructor(props) {
    super(props)
    this.state = {
        contador: +(this.props.init ?? 0)
    }
    this.delta = +(this.props.delta ?? 1)
    this.baja = () => {
        this.changeContador(-this.delta)
    }
    // this.sube = this.sube.bind(this)
  }

  changeContador(value) {
    this.setState(prev => ({contador: prev.contador + value}))
  }

  sube() {
    this.changeContador(this.delta)
  }

  render() {
    return (
      <div>
        <Pantalla valor={this.state.contador} />
        <div>
            <input type='button' value='-' onClick={this.baja} />
            <input type='button' value='+' onClick={this.sube.bind(this)} />
            <input type='button' value='Init' onClick={() => this.setState({contador: 0})} />
        </div>
      </div>
    )
  }
}
