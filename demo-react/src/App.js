import logo from './logo.svg';
import myLogo from './imagenes/logo.png'
import './App.css';


import React, { Component } from 'react'
import { Card, Contador } from './componentes';
import { ErrorBoundary } from './comunes';
import Calculadora from './ejercicios/calculadora';
import Muro from './ejercicios/muro';

export default class App extends Component {
  constructor(props) {
    super(props)
    this.state = {
      cont: 0,
      main: 0
    }
    this.menu = [
      { texto: 'muro', url: '/muro', componente: <Muro /> },
      { texto: 'inicio', url: '/', componente: <Home /> },
      { texto: 'demos', url: '/demos', componente: <DemosJSX /> },
      { texto: 'contador', url: '/contador', componente: <Contador init={69} /> },
      { texto: 'calculadora', url: '/calculadora', componente: <Calculadora /> },
      { texto: 'ejemplos', url: '/ejemplos', componente: <Ejemplos /> },
    ]
  }

  render() {
    return (
      <>
        <Cabecera menu={this.menu} actual={this.state.main} onSelectMenu={indice => this.setState({ main: indice })} />
        <main className='container-fluid'>
          <ErrorBoundary>
            {this.menu[this.state.main].componente}
          </ErrorBoundary>
        </main>
        <Pie />
      </>
    )
  }
}

function Cabecera(props) {
  return (
    <header>
      <nav className="navbar navbar-expand-lg bg-body-tertiary">
        <div className="container-fluid">
          <a className="navbar-brand" href="#">
            <img src={myLogo} height={50} alt='Logotipo corporativo' />
          </a>
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon" />
          </button>
          <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <Menu {...props} />
            <Buscar />
          </div>
        </div>
      </nav>

    </header>
  );
}

function Menu({ menu, actual, onSelectMenu }) {
  return (
    <ul className="navbar-nav me-auto mb-2 mb-lg-0">
      {menu.map((item, index) =>
        <li key={index} className="nav-item">
          <a className={'nav-link' + (actual === index ? ' active' : '')} aria-current="page" href="."
            onClick={ev => { 
              ev.preventDefault()
              onSelectMenu && onSelectMenu(index) 
            }}>{item.texto}</a>
        </li>
      )
      }
    </ul>
  );
}

function Buscar() {
  return (
    <form className="d-flex" role="search">
    <input
      className="form-control me-2"
      type="search"
      placeholder="Search"
      aria-label="Search"
    />
    <button className="btn btn-outline-success" type="submit">
      Search
    </button>
  </form>
)
}
function Pie() {
  return null;
}

class Ejemplos extends Component {
  constructor(props) {
    super(props)
    this.state = {
      cont: 0,
    }
  }

  render() {
    return (
      <>
        <main className='container-fluid'>
          <Card tittle="Ejemplo de componente">
            <Contador init={10} delta={2}
              onChange={num => this.setState({ cont: num })} />
          </Card>
          <p>El contador: {this.state.cont}</p>
          <input className='btn btn-bg-danger' type='button' value='No tocar' onClick={() => { throw new Error('No tocar') }} />
        </main>
      </>
    )
  }
}

class DemosJSX extends Component {
  render() {
    let nombre = '<b>mundo</b>'
    let estilo = 'App-link'
    let saluda = <h1>Hola {nombre.toUpperCase() + '!'}!!</h1>
    let dim = { width: 100, height: 50 }
    let errorStyle = { color: 'white', backgroundColor: 'red' }
    let limpia = true
    let falsa
    let list = [
      { id: 1, nombre: 'Madrid' },
      { id: 2, nombre: 'Barcelona' },
      { id: 3, nombre: 'Sevilla' },
      { id: 4, nombre: 'Valencia' },
    ]
    return (
      <>
        {saluda}
        {limpia ? <b>verdadero</b> : <i>falso {nombre}</i>}
        {limpia && <h2>Limpia</h2>}
        {/* {falsa ?? <b>no existe</b>} */}
        {/* {falsa !== null && falsa.persona !== null && falsa.persona.nombre} */}
        {falsa?.persona?.nombre ?? <b>no existe</b>}
        <div style={{ color: 'white', backgroundColor: 'red' }}>DemosJSX</div>
        <h2 className={estilo} style={errorStyle} >Hola
          <span dangerouslySetInnerHTML={{ __html: nombre }} /></h2>
        <ul>
          {[1, 2, 3, 4, 3, 2, 1].map((item, index) => <li key={index}>Elemento {item}</li>)}
        </ul>
        <select>
          {list.map(item => <option key={item.id} value={item.id}>{item.nombre}</option>)}
        </select>
        <img src={logo} className="App-logo" alt="logo" {...dim} hidden={false} />
      </>
    )
  }
}

function Home() {
  let url = process.env.REACT_APP_API_URL

  return (
    // eslint-disable-next-line jsx-quotes
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <h1>Hola mundo</h1>
        <h2>url: {url}</h2>
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

