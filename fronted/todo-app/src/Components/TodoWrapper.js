import React, { useState, useEffect } from 'react';
import TodoForms from './TodoForms';
import Todo from './Todo';
import { EditTodoForm } from './EditTodoForm';
import axios from 'axios';

const TodoWrapper = () => {
    const [todos, setTodos] = useState([]);
    const [filter, setFilter] = useState('all'); // Estado para el filtro

    // Función para obtener tareas desde la API
    const fetchTodos = async () => {
        try {
            const response = await axios.get('/api/tasks'); // Cambia según la ruta que necesites
            setTodos(response.data.map((todo) => ({ ...todo, isEditing: false }))); // Añadir 'isEditing' a cada tarea
        } catch (error) {
            console.error('Error fetching tasks:', error);
        }
    };

    useEffect(() => {
        fetchTodos(); // Obtén las tareas al cargar el componente
    }, []);

    const addTodo = async (todo) => {
        try {
            const response = await axios.post('/api/tasks', todo);
            setTodos([...todos, { ...response.data, isEditing: false }]);
        } catch (error) {
            console.error('Error agregando tarea:', error);
        }
    };

    const deleteTodo = async (id) => {
        try {
            await axios.delete(`/api/tasks/${id}`);
            setTodos(todos.filter((todo) => todo.id !== id));
        } catch (error) {
            console.error('Error deleting task:', error);
        }
    };

    const toggleComplete = async (id) => {
        const todoToToggle = todos.find((todo) => todo.id === id);
        const updatedTask = { ...todoToToggle, status: todoToToggle.status === 'PENDIENTE' ? 'COMPLETADO' : 'PENDIENTE' };

        try {
            await axios.put(`/api/tasks/${id}`, updatedTask);
            setTodos(
                todos.map((todo) =>
                    todo.id === id ? { ...todo, status: updatedTask.status } : todo
                )
            );
        } catch (error) {
            console.error('Error toggling task completion:', error);
        }
    };

    const editTodo = (id) => {
        setTodos(
            todos.map((todo) =>
                todo.id === id ? { ...todo, isEditing: !todo.isEditing } : todo
            )
        );
    };

    const updateTask = async (updatedTask, id) => {
        try {
            await axios.put(`/api/tasks/${id}`, updatedTask);
            setTodos(
                todos.map((todo) =>
                    todo.id === id ? { ...todo, ...updatedTask, isEditing: false } : todo
                )
            );
        } catch (error) {
            console.error('Error updating task:', error);
        }
    };

    // Filtrar tareas según el estado
    const filteredTodos = todos.filter((todo) => {
        if (filter === 'completed') return todo.status === 'COMPLETADO';
        if (filter === 'incompleted') return todo.status === 'PENDIENTE';
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
                            <EditTodoForm key={todo.id} editTodo={updateTask} task={todo} />
                        ) : (
                            <Todo
                                key={todo.id}
                                task={todo}
                                deleteTodo={deleteTodo}
                                editTodo={editTodo} // Cambiar estado para habilitar edición
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
