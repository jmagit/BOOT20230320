console.log('Soy el fichero en node')
x = 'En demo'
a = '5s'
let b = 2
c = a = b = 3
var imprimir = false;
// console.log(`-------> ${a+b}`)

function kk() {
    let a = '7'
    if (true) {
        let x = 'bloque'
    }
    d = a + x;
    console.log(`-------> ${d}`)
}
kk();

console.log(`-------> fuera ${d}`)

function usaKK() {
    kk();
}
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


let tab = new Array();
tab = [10, 20, 30];
tab[5] = (a, b) => a + b;
tab[10] = 'otro'
tab.push('añade')
tab.splice(2, 1)
console.log('Indices')
for (v in tab) {
    console.log(v)
}
console.log('Valores')
for (v of tab) {
    console.log(v)
}
// console.log(tab[5](2,3))

let o = new Object();
o = {}
o.nombre = 'Pepito'
o['apellidos'] = 'Grillo'
o.nom = () => this.nombre + ' ' + this.apellidos;
p = { nombre: 'carmelo', apellidos: 'coton', nom: () => this.nombre + ' ' + this.apellidos }
o = p;
console.log(`${o.nombre} ${o.apellidos} ${o.nom()} `)

function MiClase(elId, elNombre) {
    this.id = elId;
    this.nombre = elNombre;
    this.muestraId = function () {
        alert("El ID del objeto es " + this.id);
    }
    this.ponNombre = function (nom) {
        this.nombre = nom.toUpperCase();
    }
    return 'Nada'
}
MiClase.prototype.cotilla = () => console.log('estoy en el prototipo')

var o1 = new MiClase("99", "Objeto de prueba");
o1.apellidos = 'Grillo'
delete o1.nombre
var o2 = new MiClase("66", "Otro Objeto");
var otroObjeto = MiClase("99", "Objeto de prueba");
console.log(o1, o2)
console.log(otroObjeto)
o1.cotilla()
o1.cotilla = () => console.log('estoy en otro prototipo')
// MiClase.prototype.cotilla = () => console.log('estoy en otro prototipo')
o2.cotilla()
o1.cotilla()
