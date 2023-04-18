import React, { Component } from 'react'

function Pantalla(props) {
    return <output>{props.valor}</output>
}

function Botonera({onSube, onBaja}) {
    return (
        <div>
            <input type='button' value='-' onClick={ev => onBaja && onBaja(ev)} />
            {onSube && <input type='button' value='+' onClick={onSube} />}
        </div>
    )
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
        // this.setState(prev => ({contador: prev.contador + value}))
        this.setState(prev => {
            let result = { contador: prev.contador + value }
            if (this.props.onChange)
                this.props.onChange(result.contador)
            return result;
        })
    }

    sube() {
        this.changeContador(this.delta)
    }

    render() {
        return (
            <div>
                <Pantalla valor={this.state.contador} />
                <Botonera onBaja={this.baja} onSube={this.sube.bind(this)} />
                {/* <div>
            <input type='button' value='-' onClick={this.baja} />
            <input type='button' value='+' onClick={this.sube.bind(this)} />
            <input type='button' value='Init' onClick={() => this.setState({contador: 0})} />
        </div> */}
            </div>
        )
    }
}

export class Card extends Component {
    render() {
        return (
            <div>
                <h1>{this.props.tittle}</h1>
                {this.props.children}
            </div>
        )
    }
}
