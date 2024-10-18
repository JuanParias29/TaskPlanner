import React, { useState } from 'react';

const TodoForms = ({ addTodo }) => {
    const [title, setTitle] = useState('');  // Nombre de la tarea
    const [description, setDescription] = useState('');  // Descripción de la tarea
    const [status, setStatus] = useState('PENDIENTE');  // Estado de la tarea, por defecto 'PENDIENTE'

    const handleSubmit = (e) => {
        e.preventDefault();
        if (title && description && status) {
            // Añadir la tarea con su descripción y estado
            addTodo({ title, description, status });
            // Limpiar los campos
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