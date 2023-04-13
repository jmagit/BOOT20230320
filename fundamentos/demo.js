console.log('Soy el fichero en node')
x = 'En demo'
a='5s'
let b=2
c= a = b = 3
var imprimir = false;
// console.log(`-------> ${a+b}`)

function kk() {
    let a = '7'
    if(true) {
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
for(v of t) {
    console.log(v)
}
// cmp = 'x'
// t[cmp]
// t.x === t['x']

let cond = 0;

console.log(cond ? `Cierto` : `Falso`)

