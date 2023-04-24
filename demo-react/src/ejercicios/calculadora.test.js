import { fireEvent, getByText, render, screen } from '@testing-library/react';
import { unmountComponentAtNode } from "react-dom";
import Calculadora from './calculadora'

describe('Pruebas del componente de la calculadora', () => {
    let container = null;
    const click = (tecla) => {
        fireEvent(
            screen.getByText(tecla),
            new MouseEvent('click', {
            bubbles: true,
            cancelable: true,
            })
        )
    }
    beforeEach(() => {
        // configurar un elemento del DOM como objetivo del renderizado
        container = document.createElement("div");
        document.body.appendChild(container);
    });

    afterEach(() => {
        // limpieza al salir
        unmountComponentAtNode(container);
        container.remove();
        container = null;
    });

    test('Create', () => {
        render(<Calculadora />);
        const element = screen.getByText(/Calculadora/i);
        expect(element).toBeInTheDocument();
    });

    test('Dígitos', () => {
        render(<Calculadora />, { container });
        "98765,01234±".split('').forEach(tecla => click(tecla))
        expect(container.querySelector(`.Pantalla`).textContent).toEqual('-98765,01234');
    });

    test('Borrado', () => {
        render(<Calculadora />, { container });
        "123±".split('').forEach(tecla => click(tecla))
        expect(container.querySelector(`.Pantalla`).textContent).toEqual('-123');
        click('↶ BORRAR')
        expect(container.querySelector(`.Pantalla`).textContent).toEqual('-12');
        click('↶ BORRAR')
        expect(container.querySelector(`.Pantalla`).textContent).toEqual('-1');
        click('↶ BORRAR')
        expect(container.querySelector(`.Pantalla`).textContent).toEqual('0');
    });

    test('Operaciones', () => {
        render(<Calculadora />, { container });
        click('1')
        click('+')
        expect(container.querySelector(`.Pantalla`).textContent).toEqual('1');
        expect(container.querySelector(`.Resumen`).textContent).toEqual('1 +');
        click('2')
        click('-')
        expect(container.querySelector(`.Pantalla`).textContent).toEqual('3');
        expect(container.querySelector(`.Resumen`).textContent).toEqual('3 -');
        click('5')
        click('*')
        expect(container.querySelector(`.Pantalla`).textContent).toEqual('-2');
        expect(container.querySelector(`.Resumen`).textContent).toEqual('-2 *');
        click('3')
        click('/')
        expect(container.querySelector(`.Pantalla`).textContent).toEqual('-6');
        expect(container.querySelector(`.Resumen`).textContent).toEqual('-6 /');
        click('2')
        click('=')
        expect(container.querySelector(`.Pantalla`).textContent).toEqual('-3');
        expect(container.querySelector(`.Resumen`).textContent).toEqual('');
        click('C')
        expect(container.querySelector(`.Pantalla`).textContent).toEqual('0');
    });
})

