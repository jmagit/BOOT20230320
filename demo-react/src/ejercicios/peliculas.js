import React, { Component, useState, useEffect } from "react";
import { ValidationMessage, ErrorMessage, Esperando, PaginacionCmd as Paginacion } from "../biblioteca/comunes";
import { titleCase } from '../biblioteca/formateadores';
import userNotFoundMaleImage from '../imagenes/user-not-found-male.png';
import userNotFoundFemaleImage from '../imagenes/user-not-found-female.png';

export function PeliculasMnt() {
    const [modo, setModo] = useState('list');
    const [listado, setListado] = useState(null);
    const [elemento, setElemento] = useState(null);
    const [loading, setLoading] = useState(true);
    const [paginacion, setPaginacion] = useState({ pagina: 0, paginas: 0 })
    const [errorMsg, setErrorMsg] = useState(null);
    const [idOriginal, setIdOriginal] = useState(null);
    let url = (process.env.REACT_APP_API_URL || 'http://localhost:8010/') + 'peliculas/v1';

    const setError = (msg) => {
        setErrorMsg(msg)
        setLoading(false)
    }

    const list = (num) => {
        let pagina = paginacion.pagina
        if (num || num === 0) pagina = num
        setLoading(true)
        fetch(`${url}?sort=title&page=${pagina}&size=30`)
            .then(response => {
                response.json().then(response.ok ? data => {
                    setModo('list')
                    setListado(data.content)
                    setPaginacion({ pagina: data.number, paginas: data.totalPages })
                    setLoading(false)
                } : error => setError(`${error.status}: ${error.error}`))
            })
            .catch(error => setError(error.message))
    }

    const add = () => {
        setModo('add')
        setElemento({
            filmId: 0,
            description: "",
            length: 0,
            rating: "G",
            releaseYear: (new Date()).getFullYear(),
            rentalDuration: 3,
            rentalRate: 4.99,
            replacementCost: 19.99,
            title: "",
            languageId: 1,
            languageVOId: '',
            actors: [],
            categories: []
        })
    }
    const edit = (key) => {
        setLoading(true)
        fetch(`${url}/${key}?mode=edit`)
            .then(response => {
                response.json().then(response.ok ? data => {
                    setModo('edit')
                    setElemento(data)
                    setLoading(false)
                    setIdOriginal(key)
                } : error => setError(`${error.status}: ${error.error}`))
            })
            .catch(error => setError(error))
    }
    const view = (key) => {
        setLoading(true)
        fetch(`${url}/${key}?mode=details`)
            .then(response => {
                response.json().then(response.ok ? data => {
                    setModo('view')
                    setElemento(data)
                    setLoading(false)
                } : error => setError(`${error.status}: ${error.error}`))
            })
            .catch(error => setError(error))
    }
    const remove = (key) => {
        if (!window.confirm("¿Seguro?")) return;
        setLoading(true)
        fetch(`${url}/${key}`, { method: 'DELETE' })
            .then(response => {
                if (response.ok)
                    list()
                else
                    response.json().then(error => setError(`${error.status}: ${error.error}`))
                setLoading(false)
            })
            .catch(error => setError(error))
    }
    const cancel = () => {
        list();
    }
    const send = (elemento) => {
        setLoading(true)
        // eslint-disable-next-line default-case
        switch (modo) {
            case "add":
                fetch(`${url}`, {
                    method: 'POST',
                    body: JSON.stringify(elemento),
                    headers: {
                        'Content-Type': 'application/json'
                    }
                })
                    .then(response => {
                        if (response.ok)
                            cancel()
                        else
                            response.json().then(error => setError(`${error.status}: ${error.detail}`))
                        setLoading(false)
                    })
                    .catch(error => setError(error))
                break;
            case "edit":
                fetch(`${url}/${idOriginal}`, {
                    method: 'PUT',
                    body: JSON.stringify(elemento),
                    headers: {
                        'Content-Type': 'application/json'
                    }
                })
                    .then(response => {
                        if (response.ok)
                            cancel()
                        else
                            response.json().then(error => setError(`${error.status}: ${error.detail}`))
                        setLoading(false)
                    })
                    .catch(error => setError(error))
                break;
        }
    }
    useEffect(() => list(0), [])

    if (loading) return <Esperando />;
    let result = [<ErrorMessage key="error" msg={errorMsg} onClear={() => setErrorMsg(null)} />]
    switch (modo) {
        case "add":
        case "edit":
            result.push(
                <PeliculasForm key="main"
                    isAdd={modo === "add"}
                    elemento={elemento}
                    onCancel={e => cancel()}
                    onSend={e => send(e)}
                    onDelete={key => remove(key)}
                />
            )
            break
        case "view":
            result.push(
                <PeliculasView key="main"
                    elemento={elemento}
                    onEdit={key => edit(key)}
                    onCancel={e => cancel()}
                />
            )
            break
        default:
            if (listado) result.push(
                <PeliculasList key="main"
                    listado={listado}
                    {...paginacion}
                    onAdd={() => add()}
                    onView={key => view(key)}
                    onChangePage={num => list(num)}
                />
            );
            break;
    }
    return result
}

function PeliculasList(props) {
    return (
        <>
            <h1 className='display-1'>Películas <button
                type="button"
                className="btn btn-primary"
                title="Añadir"
                onClick={e => props.onAdd()}
            ><i className="far fa-plus"></i></button></h1>
            <div className='container-fluid' >
                <div className='row'>
                    {props.listado.map(item => (
                        <div key={item.filmId} className="card" style={{ width: "14rem" }}>
                            <img src={`https://picsum.photos/id/${item.filmId}/500/400`} className="card-img-top" alt={`Foto de ${item.title}`} />
                            <div className="card-body">
                                <h5 className="card-title"><button type="button"
                                    className="btn btn-link" title={`Editar ${item.title}`} onClick={e => props.onView(item.filmId)}
                                >{item.title}</button></h5>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
            <Paginacion actual={props.pagina} total={props.paginas} onChange={num => props.onChangePage(num)} />
        </>
    )
}

function PeliculasView({ elemento, onEdit, onCancel }) {
    return (
        <div>
            <img src={`https://picsum.photos/id/${elemento.filmId}/500/300`} className="img-fluid" alt={`Foto de ${elemento.title}`} />
            <span className="position-relative top-0 translate-middle badge rounded-pill bg-danger"><h1>{elemento.rating}</h1></span>
            <ul className="list-inline">
                {elemento.categories.map(item => <li ley={item} className="list-inline-item badge bg-primary">{item}</li>)}
            </ul>
            <h1 className="display-1">
                {elemento.title} <button
                    type="button" className="btn btn-link" title={`Editar ${elemento.title}`} onClick={e => onEdit(elemento.filmId)}
                ><i className="fas fa-pen"></i></button>
            </h1>
            <p className="lead">{elemento.description}</p>
            <p>
                Estrenada en el {elemento.releaseYear} con una duración de {elemento.length} minutos.
                Disponible en {elemento.language}{elemento.languageVO && ` y en versión original en ${elemento.languageVO}`}.
                Puedes alquilarla {elemento.rentalDuration} días por tan solo {elemento.rentalRate} €.
                <span className='text-danger'> (Penalización si no se devuelve o se devuelve en un estado dañado: {elemento.replacementCost} €)</span>
            </p>
            <h1 className="display-3">Reparto</h1>
            <ul className="list">
                {elemento.actors.map((item, index) => <li ley={index}>{titleCase(item)}</li>)}
            </ul>

            <p>
                <button className="btn btn-primary" type="button" onClick={e => onCancel()} >Volver</button>
            </p>
        </div>
    )
}

function PeliculasForm(props) {
    const [elemento, setElemento] = useState(props.elemento)
    const [msgErr, setMsgErr] = useState([])
    const [invalid, setInvalid] = useState(false)
    const [categorias, setCategorias] = useState([])
    const [actores, setActores] = useState([])
    const [idiomas, setIdiomas] = useState([])
    const [clasificaciones, setClasificaciones] = useState([])
    const [idCategoria, setIdCategoria] = useState(null);
    const [idActor, setIdActor] = useState(null);
    let url = (process.env.REACT_APP_API_URL || 'http://localhost:8010/');
    const form = React.createRef();
    const onSend = () => { props.onSend && props.onSend(elemento); }
    const onCancel = () => {
        if (props.onCancel) props.onCancel();
    };

    const handleChange = (event) => {
        const cmp = event.target.name;
        const valor = event.target.value;
        elemento[cmp] = valor;
        setElemento(elemento)
        validar();
    }
    const validarCntr = (cntr) => {
        if (cntr.name) {
            // eslint-disable-next-line default-case
            switch (cntr.name) {
                case "apellidos":
                    cntr.setCustomValidity(cntr.value !== cntr.value.toUpperCase()
                        ? "Debe estar en mayúsculas" : '');
                    break;
            }
        }
    }
    const validar = () => {
        if (form.current) {
            const errors = {};
            let invalid = false;
            for (var cntr of form.current.elements) {
                if (cntr.name) {
                    validarCntr(cntr);
                    errors[cntr.name] = cntr.validationMessage;
                    invalid = invalid || !cntr.validity.valid;
                }
            }
            setMsgErr(errors)
            setInvalid(invalid)
        }
    }
    const setError = (cmp, msg) => {
        msgErr[cmp] = msg
        setMsgErr(msgErr)
    }
    useEffect(() => validar(), [])

    useEffect(() => {
        fetch(`${url}peliculas/v1/clasificaciones`)
            .then(response => {
                response.json().then(response.ok ? data => {
                    setClasificaciones(data)
                } : error => setError('rating', `${error.status}: ${error.error}`))
            })
            .catch(error => setError('rating', error.message))
    }, [])

    useEffect(() => {
        fetch(`${url}idiomas/v1`)
            .then(response => {
                response.json().then(response.ok ? data => {
                    setIdiomas(data)
                } : error => setError('languageId', `${error.status}: ${error.error}`))
            })
            .catch(error => setError('languageId', error.message))
    }, [])

    useEffect(() => {
        fetch(`${url}categorias/v1`)
            .then(response => {
                response.json().then(response.ok ? data => {
                    if (data.length > 0) setIdCategoria(data[0].id)
                    setCategorias(data)
                } : error => setError('categories', `${error.status}: ${error.error}`))
            })
            .catch(error => setError('categories', error.message))
    }, [])

    useEffect(() => {
        fetch(`${url}actores/v1?sort=firstName`)
            .then(response => {
                response.json().then(response.ok ? data => {
                    if (data.length > 0) setIdActor(data[0].actorId)
                    setActores(data)
                } : error => setError('actors', `${error.status}: ${error.error}`))
            })
            .catch(error => setError('actors', error.message))
    }, [])

    return (
        <form ref={form} >
            <div className="form-group">
                <label htmlFor="title">Título</label>
                <input type="text" className="form-control"
                    id="title" name="title" value={elemento.title}
                    onChange={handleChange} required minLength="1" maxLength="128" />
                <ValidationMessage msg={msgErr.title} />
            </div>
            <div className="form-group">
                <label htmlFor="description">Descripción</label>
                <textarea className="form-control"
                    id="description" name="description" value={elemento.description}
                    onChange={handleChange} minLength="2" />
                <ValidationMessage msg={msgErr.description} />
            </div>
            <div className="form-group">
                <label htmlFor="releaseYear">Año</label>
                <input type="number" className="form-control"
                    id="releaseYear" name="releaseYear" value={elemento.releaseYear}
                    onChange={handleChange} min={1895} max={(new Date()).getFullYear()} />
                <ValidationMessage msg={msgErr.releaseYear} />
            </div>
            <div className="form-group">
                <label htmlFor="length">Duración</label>
                <div className="input-group">
                    <input type="number" className="form-control"
                        id="length" name="length" value={elemento.length}
                        onChange={handleChange} min={1} />
                    <span className="input-group-text">minutos</span>
                </div>
                <ValidationMessage msg={msgErr.length} />
            </div>
            <div className="form-group">
                <label htmlFor="languageId">Idioma</label>
                <select className="form-select"
                    id="languageId" name="languageId" value={elemento.languageId}
                    onChange={handleChange} >
                    {idiomas.map(item => <option key={item.id} value={item.id}>{item.idioma}</option>)}
                </select>
                <ValidationMessage msg={msgErr.languageId} />
            </div>
            <div className="form-group">
                <label htmlFor="languageVOId">Versión original</label>
                <select className="form-select"
                    id="languageVOId" name="languageVOId" value={elemento.languageVOId}
                    onChange={handleChange} >
                    <option value=''></option>
                    {idiomas.map(item => <option key={item.id} value={item.id}>{item.idioma}</option>)}
                </select>
                <ValidationMessage msg={msgErr.languageVOId} />
            </div>
            <div className="form-group">
                <label htmlFor="rating">Clasificación</label>
                <select className="form-select"
                    id="rating" name="rating" value={elemento.rating}
                    onChange={handleChange} >
                    {clasificaciones.map(item => <option key={item.key} value={item.key}>{item.value}</option>)}
                </select>
                <ValidationMessage msg={msgErr.rating} />
            </div>
            <div className="form-group">
                <label htmlFor="rentalDuration">Duración alquiler</label>
                <div className="input-group">
                    <input type="number" className="form-control"
                        id="rentalDuration" name="rentalDuration" value={elemento.rentalDuration}
                        onChange={handleChange} min={1} />
                    <span className="input-group-text">días</span>
                </div>
                <ValidationMessage msg={msgErr.rentalDuration} />
            </div>
            <div className="form-group">
                <label htmlFor="rentalRate">Coste alquiler</label>
                <div className="input-group">
                    <input type="number" step={.01} className="form-control"
                        id="rentalRate" name="rentalRate" value={elemento.rentalRate}
                        onChange={handleChange} min={0.01} />
                    <span className="input-group-text">€</span>
                </div>
                <ValidationMessage msg={msgErr.rentalRate} />
            </div>
            <div className="form-group">
                <label htmlFor="replacementCost">Coste reposición</label>
                <div className="input-group">
                    <input type="number" step={.01} className="form-control"
                        id="replacementCost" name="replacementCost" value={elemento.replacementCost}
                        onChange={handleChange} min={0.01} />
                    <span className="input-group-text">€</span>
                </div>
                <ValidationMessage msg={msgErr.replacementCost} />
            </div>
            <div className="form-group">
                <label htmlFor="categories">Categorías</label>
                <div className="input-group">
                    <select className="form-select" id="categories" name="categories" onChange={ev => setIdCategoria(ev.target.value)}>
                        {categorias.map(item => <option key={item.id} value={item.id}>{item.categoria}</option>)}
                    </select>
                    <button className="btn btn-outline-secondary" type="button" onClick={() => {
                        if (elemento.categories.includes(idCategoria)) return
                        elemento.categories.push(idCategoria)
                        setElemento({ ...elemento })
                    }} ><i className="far fa-plus"></i></button>
                </div>
                <ValidationMessage msg={msgErr.categories} />
                <ul>
                    {elemento.categories.map((item, index) =>
                        <li key={index}>{categorias.find(ele => ele.id == item)?.categoria} <button className="btn btn-link" type="button"
                            title='Quita categoría' onClick={() => {
                                elemento.categories.splice(index, 1)
                                setElemento({ ...elemento })
                            }}><i className="far fa-trash-alt"></i></button></li>
                    )}
                </ul>
            </div>
            <div className="form-group">
                <label htmlFor="actors">Reparto</label>
                <div className="input-group">
                    <select className="form-select" id="actors" name="actors" onChange={ev => setIdActor(ev.target.value)}>
                        {actores.map(item => <option key={item.actorId} value={item.actorId}>{titleCase(item.nombre)}</option>)}
                    </select>
                    <button className="btn btn-outline-secondary" type="button" onClick={() => {
                        if (elemento.actors.includes(idActor)) return
                        elemento.actors.push(idActor)
                        setElemento({ ...elemento })
                    }} ><i className="far fa-plus"></i></button>
                </div>
                <ValidationMessage msg={msgErr.actors} />
                <ul>
                    {elemento.actors.map((item, index) =>
                        <li key={index}>{titleCase(actores.find(ele => ele.actorId == item)?.nombre)} <button className="btn btn-link" type="button"
                            title='Quita categoría' onClick={() => {
                                elemento.actors.splice(index, 1)
                                setElemento({ ...elemento })
                            }}><i className="far fa-trash-alt"></i></button></li>
                    )}
                </ul>
            </div>
            <div className="btn-group">
                <button className="btn btn-success" type="button" disabled={invalid} onClick={onSend} >Enviar</button>
                {!props.isAdd && <button type="button" className="btn btn-danger" title="Borrar" onClick={e => props.onDelete(elemento.filmId)}>Borrar</button>}
                <button className="btn btn-primary" type="button" onClick={onCancel} >Volver</button>
            </div>
        </form>
    );
}
