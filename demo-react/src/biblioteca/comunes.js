import React from 'react';
import imgAjaxLoading from '../imagenes/loading.gif'

export class ErrorBoundary extends React.Component {
    constructor(props) {
        super(props);
        this.state = { hasError: false, error: null, errorInfo: null };
    }
    static getDerivedStateFromError(error) {  // Actualiza el estado para que el siguiente renderizado lo muestre
        return { hasError: true };
    }
    componentDidCatch(error, info) {  // También puedes registrar el error en un servicio de reporte de errores
        this.setState({ hasError: true, error: error, errorInfo: info })
    }
    render() {
        if (this.state.hasError) { // Puedes renderizar cualquier interfaz de repuesto
            return <div>
                <h1>ERROR</h1>
                {this.state.error && <p>{this.state.error.toString()}</p>}
                {this.state.errorInfo && <p>{this.state.errorInfo.componentStack}</p>}
            </div>;
        }
        return this.props.children;
    }
}


export function Esperando() {
    return (
        <img src={imgAjaxLoading} alt='Esperando respuesta del servidor' />
    )
}

export function ErrorMessage({msg}) {
    return (
        <div className="alert alert-danger d-flex align-items-center" role="alert">
        <svg
            xmlns="http://www.w3.org/2000/svg"
            className="bi bi-exclamation-triangle-fill flex-shrink-0 me-2"
            viewBox="0 0 16 16"
            width={26}
            role="img"
            aria-label="Error:"
        >
            <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z" />
        </svg>
        <div>{msg}</div>
    </div>
)
}

export function PaginacionCmd({ actual, total, onChange }) {
    let click = (number, ev) => {
        ev.preventDefault()
        if (onChange) onChange(number)
    }
    let items = [];
    for (let number = 0; number < total; number++) {
        items.push(
            number === actual ?
                <li key={number} className="page-item active" aria-current="page"><a href='.' className="page-link" onClick={click.bind(this, number)} >{number + 1}</a></li>
                :
                <li key={number} className="page-item"><a href='.' className="page-link" onClick={click.bind(this, number)} >{number + 1}</a></li>
        );
    }
    return (
        <nav aria-label="Page navigation">
            <ul className="pagination">
                {items}
            </ul>
        </nav>
    )
}
