import React, { useState } from 'react';

// Componente EditTodoForm: permite editar una tarea existente.
export const EditTodoForm = ({ editTodo, task }) => {

    // Estado 'value' inicializado con el texto de la tarea actual ('task.task').
    const [value, setValue] = useState(task.task);

    // Estado 'description' inicializado con la descripción actual de la tarea ('task.description').
    const [description, setDescription] = useState(task.description);

    // Función que se ejecuta al enviar el formulario.
    const handleSubmit = (e) => {
        e.preventDefault(); // Evita que la página se recargue al enviar el formulario.
        // Llama a la función 'editTodo' pasando un objeto con la nueva tarea y descripción, además del ID de la tarea.
        editTodo({ task: value, description }, task.id);
    };

    return (
        <form onSubmit={handleSubmit} className="TodoForm">
            {/* Campo de texto para editar la tarea */}
            <input
                type="text"
                value={value}
                onChange={(e) => setValue(e.target.value)} // Actualiza el estado 'value' cuando el usuario cambia el valor
                className="todo-input"
                placeholder="Actualizar" // Texto de ayuda en el input
            />
            {/* Área de texto para editar la descripción */}
            <textarea
                value={description}
                onChange={(e) => setDescription(e.target.value)} // Actualiza el estado 'description' cuando el usuario cambia el valor
                className="todo-textarea"
                placeholder="Actualizar"
            />
            {/* Botón para enviar el formulario */}
            <button type="submit" className="todo-btn">Update Task</button>
        </form>
    );
};


export default EditTodoForm;



