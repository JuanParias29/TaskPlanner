import React, {useState} from 'react';

const TodoForms = ({addTodo}) => {
    const [task, setTask] = useState('');  // Nombre de la tarea
    const [description, setDescription] = useState('');  // Descripción de la tarea

    const handleSubmit = (e) => {
        e.preventDefault();
        if (task && description) {
            // Añadir la tarea con su descripción
            addTodo({task, description});
            // Limpiar los campos
            setTask('');
            setDescription('');
        }
    };

    return (
        <form onSubmit={handleSubmit} className="TodoForm">
            <input
                type="text"
                value={task}
                onChange={(e) => setTask(e.target.value)}
                className="todo-input"
                placeholder='Nombre '
            />
            <textarea
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                className="todo-input"
                placeholder='Descripción'
            />
            <button type="submit" className='todo-btn'>Crear</button>
        </form>
    );
};

export default TodoForms;


