import React, { useState } from 'react';
import TodoForms from './TodoForms';
import { v4 as uuidv4 } from 'uuid';
import Todo from './Todo';
import { EditTodoForm } from './EditTodoForm';

const TodoWrapper = () => {
    const [todos, setTodos] = useState([]);
    const [filter, setFilter] = useState('all'); // Estado para el filtro

    const addTodo = (todo) => {
        setTodos([
            ...todos,
            { id: uuidv4(), task: todo.task, description: todo.description, completed: false, isEditing: false },
        ]);
    };

    const deleteTodo = (id) => setTodos(todos.filter((todo) => todo.id !== id));

    const toggleComplete = (id) => {
        setTodos(
            todos.map((todo) =>
                todo.id === id ? { ...todo, completed: !todo.completed } : todo
            )
        );
    };

    const editTodo = (id) => {
        setTodos(
            todos.map((todo) =>
                todo.id === id ? { ...todo, isEditing: !todo.isEditing } : todo
            )
        );
    };

    const editTask = (updatedTask, id) => {
        setTodos(
            todos.map((todo) =>
                todo.id === id ? { ...todo, ...updatedTask, isEditing: !todo.isEditing } : todo
            )
        );
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
                                editTodo={editTodo}
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
