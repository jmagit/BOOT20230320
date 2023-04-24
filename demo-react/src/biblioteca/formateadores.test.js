import { titleCase } from './formateadores'

describe('Pruebas de los formateadores', () => {
    describe('titleCase', () => {
        [['hola mundo', 'Hola Mundo'], ['AÑO DOMINÍ', 'Año Dominí'], 
        ['palaBra', 'Palabra'], [' blancos ', 'Blancos'], [123, 123],        
        [null, null],[undefined, undefined]]
        .forEach(([origen, resultado]) => {
            it(`${origen} => ${resultado}`, function () {
                expect(titleCase(origen)).toBe(resultado)
            })
        })
    })
})
