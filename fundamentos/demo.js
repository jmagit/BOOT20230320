// console.log('Soy el fichero en node')
// x = 'En demo'
// a = '5s'
// let b = 2
// c = a = b = 3
// var imprimir = false;
// // console.log(`-------> ${a+b}`)

// function kk() {
//     let a = '7'
//     if (true) {
//         let x = 'bloque'
//     }
//     d = a + x;
//     console.log(`-------> ${d}`)
// }
// kk();

// console.log(`-------> fuera ${d}`)

// function usaKK() {
//     kk();
// }
// if(a=1) {
//     console.log(`Cierto`)
// }
// inprimir = true
// if(imprimir) {
//     console.log(`Cierto`)
// }

t = [10, 20, 30]
//t = { x: 10, y: 20}
// for(v in t) {
//     console.log(t[v])
// }
// for (v of t) {
//     console.log(v)
// }
// cmp = 'x'
// t[cmp]
// t.x === t['x']

// let cond = 0;

// console.log(cond ? `Cierto` : `Falso`)

// function avg() {
//     var rslt = 0;
//     for (var i = 0; i < arguments.length; i++) {
//         rslt += arguments[i];
//     }
//     return arguments.length ? (rslt / arguments.length) : 0;
// }
// console.log(avg(10,20,30))
// console.log(avg(...t))
// punto = { x: 10, y: 20}
// function coordenadas(x, y) {
//     return x + y;
// }
// coordenadas = (x, y) =>  x + y;
// console.log(coordenadas(punto.x, punto.y))
// console.log((coordenadas)(punto.x, punto.y))

// console.log(coordenadas(...punto))

// function suma(a, b) { return a + b; }
// suma = (a, b) => a + b;

// t.filter(item => item % 2)

// t.filter(item => item > 10)
// for(let i=0; i < 10; i++)
//     t.filter(function(item) { return item > a; })

// filtrado = function(item) { return item > 10; }

// for(let i=0; i < 10; i++)
//     t.filter(filtrado);


// let tab = new Array();
// tab = [10, 20, 30];
// tab[5] = (a, b) => a + b;
// tab[10] = 'otro'
// tab.push('añade')
// tab.splice(2, 1)
// console.log('Indices')
// for (v in tab) {
//     console.log(v)
// }
// console.log('Valores')
// for (v of tab) {
//     console.log(v)
// }
// console.log(tab[5](2,3))

let o = new Object();
o = {}
o.nombre = 'Pepito'
o['apellidos'] = 'Grillo'
o.nom = () => this.nombre + ' ' + this.apellidos;
o.nom.bind(o)
console.log(`${o.nombre} ${o.apellidos} ${o.nom()} `)
p = { nombre: 'carmelo', apellidos: 'coton', nom: function() { return this.nombre + ' ' + this.apellidos; } }
o = p;
console.log(`${o.nombre} ${o.apellidos} ${o.nom()} `)
o.nom.bind({ nombre: 'otro', apellidos: 'nombre'})
console.log(`${o.nombre} ${o.apellidos} ${o.nom()} `)
const ponNombre = function(tratamiento) { 
    return tratamiento + ' ' + this.nombre + ' ' + this.apellidos; 
}
console.log(`ponNombre: ${ponNombre('Sr.', 1, 2)} `)
console.log(`ponNombre: ${ponNombre.apply(o, ['Sr.'])} `)
console.log(`ponNombre: ${ponNombre.call(o, 'Sr.', 1, 2)} `)
o.ponNombre = ponNombre;
console.log(`ponNombre: ${o.ponNombre('Sr.', 1, 2)} `)

function MiClase(elId, elNombre) {
    let vm = this;
    vm.id = elId;
    vm.nombre = elNombre;
    vm.muestraId = function () {
        console.warn("El ID del objeto es " + vm.id);
    }
    this.ponNombre = function (nom) {
        vm.nombre = nom.toUpperCase();
    }
    return 'Nada'
}
MiClase.prototype.cotilla = () => console.log('estoy en el prototipo')
MiClase.prototype.resumen = function() { console.log(`id: ${this.id} nombre: ${this.nombre}`) }


let o1 = new MiClase("99", "Objeto de prueba");
o1.apellidos = 'Grillo'
delete o1.nombre
let o2 = new MiClase("66", "Otro Objeto");
o1.muestraId.call(o2)

// let otroObjeto = MiClase("99", "Objeto de prueba");
// console.log(o1, o2)
// console.log(otroObjeto)
// o1.ponNombre("PEPITO")
// o1.cotilla()
// o1.cotilla = () => console.log('estoy en otro prototipo')
// // MiClase.prototype.cotilla = () => console.log('estoy en otro prototipo')
// o2.cotilla()
// o1.cotilla()
// o2.resumen()
// o1.nonbre='kk'
// console.log(globalThis)
// console.log(o1, o2)
// Array.prototype.kk = () => console.log('ahora tengo kk')

// t = []
// t.kk()

let x=10, y=20;
let punto = { x: x, y: y, suma: function() { return this.x + this.y }}
punto = {x, y, suma() { return this.x + this.y }}

class Persona {
    constructor(id, nombre, apellidos) {
        this.id = id
        this.nombre = nombre
        this.apellidos = apellidos
    }
    get nombreCompleto() { return `${this.apellidos}, ${this.nombre}` }

    pinta() {
        console.log(this.nombreCompleto)
    }
}

let p1 = new Persona(1, "Pepito", "Grillo")
let p2 = new Persona(2, "Carmelo", "Coton")
// let p3 = Persona(1, "Pepito", "Grillo")

p1.pinta()
console.log(p2.nombreCompleto)
p2.nombreCompleto = 'kk'
console.log(typeof(Persona))