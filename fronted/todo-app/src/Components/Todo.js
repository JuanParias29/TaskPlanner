import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPenToSquare, faTrash } from '@fortawesome/free-solid-svg-icons';
import { faCheckSquare, faSquare } from '@fortawesome/free-regular-svg-icons'; // Añadir los iconos de check vacío y marcado
import logo from '../images/logo.jpg';  // Importa la imagen del logo (ajusta la ruta según tu estructura)

// Cargar el sonido (desde la carpeta public)
const clickSound = new Audio('/click-sound.mp3');  // Ruta al archivo de sonido (usando la carpeta public)

export const Todo = ({ task, deleteTodo, editTodo, toggleComplete }) => {

    // Función que maneja el clic y reproduce el sonido
    const handleClick = () => {
        clickSound.play();  // Reproduce el sonido
        toggleComplete(task.id);  // Cambia el estado de completado de la tarea
    };

    return (
        <div className="Todo">
            {/* Agrega el logo en la parte superior izquierda del Todo */}
            <div className="logo-container">
                <img src={logo} alt="Logo" className="logo" />
            </div>

            <div className="task-actions">
                <span className="check-container" onClick={handleClick}>
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
