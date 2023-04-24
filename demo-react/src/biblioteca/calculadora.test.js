import { Calculadora } from './calculadora'

describe('Pruebas aisladas de la calculadora', () => {
    const spyError = jest.spyOn(console, 'error').mockImplementation(() => { });
    const spyWarn = jest.spyOn(console, 'warn').mockImplementation(() => { });
    const onPantallaChange = jest.fn().mockResolvedValue({})
    const onResumenChange = jest.fn().mockResolvedValue({})
    let calc;

    beforeAll(() => {
        calc = new Calculadora(onPantallaChange, onResumenChange)
        // calc.updated = { emit: jest.fn().mockResolvedValue({}) }
        // calc.equaled = { emit: jest.fn().mockResolvedValue({}) }
    });

    beforeEach(() => {
        calc.inicia();
        onPantallaChange.mockClear()
        onResumenChange.mockClear()
    });

    describe('Método: inicia', () => {
        it('Inicializa la calculadora', () => {
            calc.inicia()
            expect(calc.pantalla).toBe('0')
            expect(calc.resumen).toBe('')
        })
    });

    describe('Método: ponDigito', () => {
        '0123456789'.split('').forEach(digito => {
            it(`ponDigito ${digito} como string`, () => {
                calc.ponDigito(digito)
                expect(calc.pantalla).toBe(digito)
            })
        });
        'a-,'.split('').forEach(digito => {
            it(`ponDigito ${digito} como error`, () => {
                calc.ponDigito(digito)
                expect(spyError).toBeCalledWith('No es un valor numérico.')
                expect(calc.pantalla).toBe('0')
            })
        });
        for (let digito = 0; digito <= 9; digito++) {
            it(`ponDigito ${digito} como number`, () => {
                calc.ponDigito(digito)
                expect(calc.pantalla).toBe(digito.toString())
            })
        }
        ['1234567890', '9876543210', '666'].forEach(caso => {
            it(`Secuencia ${caso}`, () => {
                caso.split('').forEach(digito => calc.ponDigito(digito));
                expect(calc.pantalla).toBe(caso)
            })
        });
    });
    describe('Método: ponComa', () => {
        it('Pone la coma', () => {
            calc.ponDigito(1)
            calc.ponComa()
            calc.ponDigito(2)
            expect(calc.pantalla).toBe('1.2')
        })

        it('Repite la coma', () => {
            calc.ponOperando('0.1')
            calc.ponComa()
            expect(spyWarn).toBeCalledWith('Ya está la coma')
            calc.ponDigito(2)
            expect(calc.pantalla).toBe('0.12')
        })

        it('Empieza por la coma', () => {
            calc.ponComa()
            calc.ponDigito(2)
            expect(calc.pantalla).toBe('0.2')
        })
    });

    describe('Método: borrar', () => {
        it('Borra positivo', () => {
            calc.ponOperando('321')
            expect(calc.pantalla).toBe('321')
            calc.borrar()
            expect(calc.pantalla).toBe('32')
            calc.borrar()
            expect(calc.pantalla).toBe('3')
            calc.borrar()
            expect(calc.pantalla).toBe('0')
        })

        it('Borra negativo', () => {
            calc.ponOperando('-123')
            expect(calc.pantalla).toBe('-123')
            calc.borrar()
            expect(calc.pantalla).toBe('-12')
            calc.borrar()
            expect(calc.pantalla).toBe('-1')
            calc.borrar()
            expect(calc.pantalla).toBe('0')
        })
    });

    describe('Método: cambiaSigno', () => {
        it('Cambia positivo', () => {
            calc.ponOperando('555')
            calc.cambiaSigno()
            expect(calc.pantalla).toBe('-555')
        })

        it('Cambia negativo', () => {
            calc.ponOperando('-7032.333')
            calc.cambiaSigno()
            expect(calc.pantalla).toBe('7032.333')
        })

        it('Cambia infinito', () => {
            calc.ponOperando(Number.POSITIVE_INFINITY)
            calc.cambiaSigno()
            expect(calc.pantalla).toBe('-Infinity')
            calc.ponOperando(Number.NEGATIVE_INFINITY)
            calc.cambiaSigno()
            expect(calc.pantalla).toBe('Infinity')
        })
    });

    describe('Método: calcula', () => {
        describe('Operadores desconocidos', function () {
            '%&$^a9:'.split('').forEach(operador => {
                it(`Operador ${operador} desconocido`, () => {
                    calc.calcula(operador)
                    expect(spyError).toBeCalledWith(`Operación no soportada: ${operador}`)
                    expect(calc.pantalla).toBe('0')
                })
            });
        });

        describe('Calcula sumas', function () {
            [[22222, 22222, 44444], [-1, 2, 1], [2, -1, 1], [0, 0, 0],
            [0.1, 0.2, 0.3], [9.9, 1.3, 11.2]].forEach(([op1, op2, rslt]) => {
                it(`Suma: ${op1} + ${op2} = ${rslt}`, function () {
                    calc.ponOperando(op1)
                    calc.calcula('+')
                    expect(onPantallaChange).toBeCalledWith(op1);
                    expect(onResumenChange).toBeCalledWith(`${op1} +`);
                    calc.ponOperando(op2)
                    calc.calcula('=')
                    expect(calc.pantalla).toBe(rslt.toString())
                    expect(onPantallaChange.mock.lastCall).toEqual([`${rslt}`]);
                    expect(onResumenChange.mock.lastCall).toEqual(['']);
                })
            });
        });

        describe('Calcula sustracciones', function () {
            [[22222, 33333, -11111], [-1, 2, -3], [0, 0, 0],
            [1, 0.9, 0.1]].forEach(([op1, op2, rslt]) => {
                it(`Resta: ${op1} - ${op2} = ${rslt}`, function () {
                    calc.ponOperando(op1)
                    calc.calcula('-')
                    expect(onPantallaChange).toBeCalledWith(op1);
                    expect(onResumenChange).toBeCalledWith(`${op1} -`);
                    calc.ponOperando(op2)
                    calc.calcula('=')
                    expect(calc.pantalla).toBe(rslt.toString())
                    expect(onPantallaChange.mock.lastCall).toEqual([`${rslt}`]);
                    expect(onResumenChange.mock.lastCall).toEqual(['']);
                })
            });
        });

        describe('Calcula productos', function () {
            [[10, 5, 50], [1.5, 2, 3], [0, 0, 0], [2, 0, 0],
            ['Infinity', 0, 'NaN'], ['Infinity', 'NaN', 'Infinity'], ['Infinity', '-Infinity', '-Infinity'],].forEach(([op1, op2, rslt]) => {
                it(`Multiplica: ${op1} * ${op2} = ${rslt}`, function () {
                    calc.ponOperando(op1)
                    calc.calcula('*')
                    expect(onPantallaChange).toBeCalledWith(op1);
                    expect(onResumenChange).toBeCalledWith(`${op1} *`);
                    calc.ponOperando(op2)
                    calc.calcula('=')
                    expect(calc.pantalla).toBe(rslt.toString())
                    expect(onPantallaChange.mock.lastCall).toEqual([`${rslt}`]);
                    expect(onResumenChange.mock.lastCall).toEqual(['']);
                })
            });
        });

        describe('Calcula divisiones', function () {
            [[10, 5, 2], [1, 3, 0.333333333333333], [0, 0, 'NaN'], [2, 0, 'Infinity'],
            ['Infinity', 'Infinity', 'NaN']].forEach(([op1, op2, rslt]) => {
                it(`Divide: ${op1} / ${op2} = ${rslt}`, function () {
                    calc.ponOperando(op1)
                    calc.calcula('/')
                    expect(onPantallaChange).toBeCalledWith(op1);
                    expect(onResumenChange).toBeCalledWith(`${op1} /`);
                    calc.ponOperando(op2)
                    calc.calcula('=')
                    expect(calc.pantalla).toBe(rslt.toString())
                    expect(onPantallaChange.mock.lastCall).toEqual([`${rslt}`]);
                    expect(onResumenChange.mock.lastCall).toEqual(['']);
                })
            });
        });
        [
            [22, '+', 5, '-', 0.75, '*', 3, '/', 2, '=', 39.375],
            [10, '*', 0.5, '/', 0.2, '+', -5, '=', 20]
        ].forEach(secuencia => {
            it('Secuencia', () => {
                for (let i = 0; i < secuencia.length - 1; i++)
                    if (i % 2)
                        calc.calcula(secuencia[i].toString())
                    else
                        calc.ponOperando(secuencia[i])
                expect(calc.pantalla).toBe(secuencia[secuencia.length - 1].toString())
            });
        });
    });

    describe('Método: ponOperando', () => {
        ['1234', '98765', '6.66', Number.POSITIVE_INFINITY].forEach(caso => {
            it(`Operando validos ${caso}`, () => {
                calc.ponOperando(caso)
                expect(calc.pantalla).toEqual(caso)
            })
        });
        ['$98765', '1234$', ''].forEach(caso => {
            it(`Operando inválido ${caso}`, () => {
                calc.ponOperando(caso.toString())
                expect(spyError).toBeCalledWith('No es un valor numérico.')
                expect(calc.pantalla).toBe('0')
            })
        });
    });

})