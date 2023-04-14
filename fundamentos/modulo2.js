let nombre = 'Modulo 2'

function resta(a, b) {
    return a - b;
}

function calc(a, b) {
    return resta(a, b);
}

function display() {
    console.log(`Soy ${nombre}`)
}

export { nombre, calc, display }