import React, { useState } from 'react';

export const EditTodoForm = ({ editTodo, task }) => {
    const [value, setValue] = useState(task.task);
    const [description, setDescription] = useState(task.description); // Estado para la descripción

    const handleSubmit = (e) => {
        e.preventDefault();
        editTodo({ task: value, description }, task.id); // Pasamos tanto la tarea como la descripción
    };

    return (
        <form onSubmit={handleSubmit} className="TodoForm">
            <input
                type="text"
                value={value}
                onChange={(e) => setValue(e.target.value)}
                className="todo-input"
                placeholder="Actualizar"
            />
            <textarea
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                className="todo-textarea"
                placeholder="Actualizar"
            />
            <button type="submit" className="todo-btn">Update Task</button>
        </form>
    );
};

export default EditTodoForm;


