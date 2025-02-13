import React, { useState } from 'react';

// Componente EditTodoForm: permite editar una tarea existente.
export const EditTodoForm = ({ editTodo, task }) => {

    // Estado 'value' inicializado con el texto de la tarea actual ('task.title').
    const [title, setTitle] = useState(task.title);

    // Estado 'description' inicializado con la descripción actual de la tarea ('task.description').
    const [description, setDescription] = useState(task.description);

    // Estado 'status' inicializado con el estado actual de la tarea ('task.status').
    const [status, setStatus] = useState(task.status);

    // Función que se ejecuta al enviar el formulario.
    const handleSubmit = (e) => {
        e.preventDefault(); // Evita que la página se recargue al enviar el formulario.
        // Llama a la función 'editTodo' pasando un objeto con la nueva tarea, descripción y estado, además del ID de la tarea.
        editTodo({ title, description, status }, task.id);
    };

    // Longitud máxima para la descripción
    const maxDescriptionLength = 255;

    return (
        <form onSubmit={handleSubmit} className="TodoForm">
            {/* Campo de texto para editar la tarea */}
            <input
                type="text"
                value={title}
                onChange={(e) => setTitle(e.target.value)} // Actualiza el estado 'title' cuando el usuario cambia el valor
                className="todo-input"
                placeholder="Actualizar Nombre" // Texto de ayuda en el input
            />

            {/* Contenedor para el área de texto y el contador de caracteres */}
            <div className="textarea-container">
                {/* Área de texto para editar la descripción */}
                <textarea
                    value={description}
                    onChange={(e) => setDescription(e.target.value)} // Actualiza el estado 'description' cuando el usuario cambia el valor
                    className="todo-textarea"
                    placeholder="Actualizar Descripción"
                    maxLength={maxDescriptionLength}
                />

                {/* Contador de caracteres */}
                <div className="character-counter">
                    {description.length}/{maxDescriptionLength}
                </div>
            </div>

            {/* Campo de selección para editar el estado */}
            <select
                value={status}
                onChange={(e) => setStatus(e.target.value)} // Actualiza el estado 'status' cuando el usuario cambia el valor
                className="todo-select"
            >
                <option value="PENDIENTE">Pendiente</option>
                <option value="COMPLETADO">Completado</option>
            </select>

            {/* Botón para enviar el formulario */}
            <button type="submit" className="todo-btn">Update Task</button>
        </form>
    );
};

export default EditTodoForm;
