import React, { useState, useEffect } from 'react';
import TodoForms from './TodoForms';
import Todo from './Todo';
import { EditTodoForm } from './EditTodoForm';
import axios from 'axios';
import { Bar } from 'react-chartjs-2';
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js';

// Register Chart.js components
ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale);

const TodoWrapper = () => {
    const [todos, setTodos] = useState([]);
    const [filter, setFilter] = useState('all');
    const [loading, setLoading] = useState(false);
    const [view, setView] = useState('week'); // Estado para controlar la vista de la gráfica (semana o día)

    // Función para obtener tareas desde la API
    const fetchTodos = async () => {
        setLoading(true);
        try {
            const response = await axios.get('/api/tasks');
            setTodos(response.data.map((todo) => ({ ...todo, isEditing: false })));
        } catch (error) {
            console.error('Error fetching tasks:', error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchTodos(); // Obtén las tareas al cargar el componente
    }, []);

    // Gráfico de tareas pendientes y completadas
    const taskStatusCounts = {
        completed: todos.filter((todo) => todo.status === 'COMPLETADO').length,
        pending: todos.filter((todo) => todo.status === 'PENDIENTE').length,
    };

    const data = {
        labels: ['Pendientes', 'Completadas'],
        datasets: [
            {
                label: 'Número de Tareas',
                data: [taskStatusCounts.pending, taskStatusCounts.completed],
                backgroundColor: ['#FF6347', '#32CD32'],
                borderColor: ['#FF6347', '#32CD32'],
                borderWidth: 1,
            },
        ],
    };

    // Gráfico de tareas completadas por semana o día
    const completedTasksByTime = view === 'week' ? [3, 5, 2, 6, 4] : [2, 4, 1, 5, 3, 6, 7];  // Datos simulados

    const dataByTime = {
        labels: view === 'week' ? ['Semana 1', 'Semana 2', 'Semana 3', 'Semana 4', 'Semana 5'] : ['Día 1', 'Día 2', 'Día 3', 'Día 4', 'Día 5', 'Día 6', 'Día 7'],
        datasets: [
            {
                label: 'Completadas por ' + (view === 'week' ? 'Semana' : 'Día'),
                data: completedTasksByTime,
                backgroundColor: completedTasksByTime.map((_, index) => `rgba(${255 - (index * 30)}, ${100 + (index * 10)}, 255, 0.6)`),
                borderColor: completedTasksByTime.map((_, index) => `rgba(${255 - (index * 30)}, ${100 + (index * 10)}, 255, 1)`),
                borderWidth: 1,
            },
        ],
    };

    // Funciones de manipulación de tareas
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
        return true;
    });

    return (
        <div className="TodoWrapper">
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
                                editTodo={editTodo}
                                toggleComplete={toggleComplete}
                            />
                        )
                    )}
                </div>
                <div className="charts-container">
                    <h2>Gráfico de Tareas</h2>
                    <Bar data={data} options={{ responsive: true }} />
                    <h2>Completadas por {view === 'week' ? 'Semana' : 'Día'}</h2>
                    <Bar data={dataByTime} options={{ responsive: true }} />
                    <div className="view-toggle">
                        <button onClick={() => setView('week')}>Semana</button>
                        <button onClick={() => setView('day')}>Día</button>
                    </div>
                </div>
            </div>
            {/* Enlace a GitHub */}
            <div className="github-link">
                <a href="https://github.com/JuanParias29/TaskPlanner" target="_blank" rel="noopener noreferrer">
                    GitHub
                </a>
            </div>
        </div>
    );
};

export default TodoWrapper;
