import React, { useState, useEffect } from 'react';
import TodoForms from './TodoForms';
import Todo from './Todo';
import { EditTodoForm } from './EditTodoForm';
import axios from 'axios'; // Importa axios

const TodoWrapper = () => {
    const [todos, setTodos] = useState([]);
    const [filter, setFilter] = useState('all'); // Estado para el filtro

    // Función para obtener tareas desde la API
    const fetchTodos = async () => {
        try {
            const response = await axios.get('/api/tasks/status/all'); // Cambia según la ruta que necesites
            setTodos(response.data);
        } catch (error) {
            console.error('Error fetching tasks:', error);
        }
    };

    useEffect(() => {
        fetchTodos(); // Obtén las tareas al cargar el componente
    }, []);

    const addTodo = async (todo) => {
        console.log('Intentando agregar una nueva tarea:', todo); // Muestra lo que estás tratando de agregar
        try {
            const response = await axios.post('/api/tasks', todo);
            console.log('Tarea agregada:', response.data); // Muestra la tarea que fue agregada
            setTodos([...todos, response.data]);
        } catch (error) {
            console.error('Error agregando tarea:', error); // Muestra el error en caso de que ocurra
        }
    };


    const deleteTodo = async (id) => {
        try {
            await axios.delete(`/api/tasks/${id}`); // Elimina la tarea
            setTodos(todos.filter((todo) => todo.id !== id)); // Actualiza la lista de tareas
        } catch (error) {
            console.error('Error deleting task:', error);
        }
    };

    const toggleComplete = async (id) => {
        const todoToToggle = todos.find((todo) => todo.id === id);
        const updatedTask = { ...todoToToggle, completed: !todoToToggle.completed };

        try {
            await axios.put(`/api/tasks/${id}`, updatedTask); // Actualiza la tarea en la API
            setTodos(
                todos.map((todo) =>
                    todo.id === id ? { ...todo, completed: !todo.completed } : todo
                )
            );
        } catch (error) {
            console.error('Error toggling task completion:', error);
        }
    };

    const editTask = async (updatedTask, id) => {
        try {
            await axios.put(`/api/tasks/${id}`, updatedTask); // Envía la tarea actualizada a la API
            setTodos(
                todos.map((todo) =>
                    todo.id === id ? { ...todo, ...updatedTask, isEditing: !todo.isEditing } : todo
                )
            );
        } catch (error) {
            console.error('Error editing task:', error);
        }
    };

    // Filtrar tareas según el estado
    const filteredTodos = todos.filter(todo => {
        if (filter === 'completed') return todo.completed;
        if (filter === 'incompleted') return !todo.completed;
        return true; // Para 'all'
    });

    return (
        <div className='TodoWrapper'>
            <div className="left-container">
                <h1>Agregar Tarea</h1>
                <TodoForms addTodo={addTodo} />
            </div>
            <div className="right-container">
                <div className="filter">
                    <label htmlFor="taskFilter">Filtrar tareas:</label>
                    <select id="taskFilter" onChange={(e) => setFilter(e.target.value)}>
                        <option value="all">Todas</option>
                        <option value="completed">Completas</option>
                        <option value="incompleted">Incompletas</option>
                    </select>
                </div>
                <div className="tasks-container">
                    {filteredTodos.map((todo) =>
                        todo.isEditing ? (
                            <EditTodoForm key={todo.id} editTodo={editTask} task={todo} />
                        ) : (
                            <Todo
                                key={todo.id}
                                task={todo}
                                deleteTodo={deleteTodo}
                                editTodo={editTask}
                                toggleComplete={toggleComplete}
                            />
                        )
                    )}
                </div>
            </div>
        </div>
    );
};

export default TodoWrapper;