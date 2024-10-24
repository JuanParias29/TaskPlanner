import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPenToSquare, faTrash } from '@fortawesome/free-solid-svg-icons';
import { faCheckSquare, faSquare } from '@fortawesome/free-regular-svg-icons'; // Añadir los iconos de check vacío y marcado

export const Todo = ({ task, deleteTodo, editTodo, toggleComplete }) => {
    return (
        <div className="Todo">
            <div className="task-actions">
                <span className="check-container" onClick={() => toggleComplete(task.id)}>
                    {/* Comprobación de tarea completada */}
                    {task.status === 'COMPLETADO' ? (
                        <FontAwesomeIcon icon={faCheckSquare} className="check-icon" />
                    ) : (
                        <FontAwesomeIcon icon={faSquare} className="check-icon" />
                    )}
                </span>
                <div className="task-text">
                    <p className={`${task.status === 'COMPLETADO' ? 'completed' : 'incompleted'}`}>{task.title}</p>
                    <p className={`description-text ${task.status === 'COMPLETADO' ? 'completed' : 'incompleted'}`}>
                        {task.description}
                    </p>
                </div>
            </div>
            <div>
                <FontAwesomeIcon className="edit-icon" icon={faPenToSquare} onClick={() => editTodo(task.id)} />
                <FontAwesomeIcon className="delete-icon" icon={faTrash} onClick={() => deleteTodo(task.id)} />
            </div>
        </div>
    );
};

export default Todo;