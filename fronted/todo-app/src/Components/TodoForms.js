import React, { useState } from 'react';

const TodoForms = ({ addTodo }) => {
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [status, setStatus] = useState('PENDIENTE');
    const handleSubmit = (e) => {
        e.preventDefault();
        if (title && description && status) {

            addTodo({ title, description, status });

            setTitle('');
            setDescription('');
            setStatus('PENDIENTE');
        }
    };

    return (
        <form onSubmit={handleSubmit} className="TodoForm">
            <input
                type="text"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                className="todo-input"
                placeholder='Nombre '
            />
            <textarea
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                className="todo-input"
                placeholder='Descripción'
            />
            <select
                value={status}
                onChange={(e) => setStatus(e.target.value)}
                className="todo-input"
            >
                <option value="PENDIENTE">Pendiente</option>
                <option value="COMPLETADO">Completado</option>
            </select>
            <button type="submit" className='todo-btn'>Crear</button>
        </form>
    );
};

export default TodoForms;