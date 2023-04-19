import React, { Component } from 'react'
import { ErrorMessage, Esperando } from '../comunes'

class Ficha extends Component {
    // {"id":"0","author":"Alejandro Escamilla","width":5000,"height":3333,"url":"","download_url":"","visible":false}
    render() {
        return (
            <div className="card" style={{ width: "14rem" }}>
                {this.props.visible && <img src={this.props.download_url} className="card-img-top" alt={`Foto ${this.props.id} de ${this.props.author}`} />}
                <div className="card-body">
                    <h5 className="card-title">{this.props.author}</h5>
                    {!this.props.visible && <div className="card-text">
                        <p>Dimensión: {this.props.width}x{this.props.height}</p>
                        <p><a href={this.props.url} target="_blank" title={`Saber mas de la foto ${this.props.id}`}>Saber mas</a></p>
                        <input type="button" value="Ver foto" className="btn btn-primary" onClick={() => this.props.onVer && this.props.onVer(this.props.id)} />
                    </div>}
                </div>
            </div>
        )
    }
}
export default class Muro extends Component {
    constructor(props) {
        super(props);
        this.state = {
            listado: [],
            errorMsg: null,
            loading: true
        };
        this.page = 0;
        this.totalRecords = 1000;
        this.rows = 20;
        this.first = 0;
    }

    load(pagina = 0, filas = 20) {
        this.rows = filas;
        this.setState({ loading: true, errorMsg: null })
        fetch(`https://picsum.photos/v2/list?page=${pagina + 1}&limit=${this.rows}`)
            .then(
                resp => {
                    if (resp.ok) {
                        resp.json().then(
                            data => {
                                this.page = pagina;
                                this.first = pagina ? (pagina * this.rows) : 0
                                this.setState({ listado: data.map(item => ({ ...item, visible: false })), loading: false })
                            },
                            err => this.setError(`ERROR (respuesta): ${err.status}:2 ${err.statusText}`)
                        )
                    } else {
                        this.setError(`ERROR (servidor): ${resp.status}: ${resp.statusText}`)
                    }
                },
                err => {
                    this.setError(`ERROR (petición): ${err.status}: ${err.statusText}`)
                }
            )
    }
    mostrar(indice) {
        this.state.listado[indice].visible = true;
        this.setState({ listado: [...this.state.listado] })
    }
    render() {
        if (this.state.loading)
            return <Esperando />
        return (
            <div>
                {this.state.error && <ErrorMessage msg={this.state.error} />}
                <main className='container-fluid'>
                    {/* <Paginator first={this.first} rows={this.rows} totalRecords={this.totalRecords} rowsPerPageOptions={[10, 20, 50, 100]} 
                        onPageChange={e => this.load(e.page, e.rows)} /> */}
                    <div className='row'>
                        {this.state.listado.map((item, index) => <Ficha key={item.id} {...item} onVer={() => this.mostrar(index)} />)}
                    </div>
                </main>
            </div>
        )
    }

    componentDidMount() {
        this.load();
    }
}

// export default class Muro extends Component {
//     constructor(props) {
//         super(props)
//         this.state = {
//             listado: null,
//             loading: true,
//             error: null
//         }
//     }
//   render() {
//     if(this.state.loading)
//         return <Esperando />
//     return (
//         <>
//             {this.state.error && <ErrorMessage msg={this.state.error} />}
//             <h1>Muro</h1>
//             {JSON.stringify(this.state.listado)}
//         </>
//     )
//   }

//   setError(msg) {
//     this.setState({error: msg})
//   }
//   load(num) {
//     this.setState({loading: true})
//     fetch('https://picsum.photos/v2/list')
//         .then(resp => {
//             if(resp.ok) {
//                 resp.json().then(
//                     data => this.setState({listado: data})
//                 )
//             } else {
//                 this.setError(resp.status)
//             }
//         })
//         .catch(err => this.setError(JSON.stringify(err)))
//         .finally(() => this.setState({loading: false}))
//   }
//   componentDidMount() {
//     this.load(1)
//   }
// }
