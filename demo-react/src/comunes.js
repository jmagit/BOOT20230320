import React from 'react';

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
