import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import TodoWrapper from './Components/TodoWrapper'; // Página de tareas
import Login from './Components/Login'; // Página de inicio de sesión
import Register from './Components/Register'; // Página de registro

function App() {
    return (
        <Router>
            <Routes>

                <Route path="/register" element={<Register />} />
                <Route path="/login" element={<Login />} />
                <Route path="/tasks" element={<TodoWrapper />} />
                <Route path="/" element={<Register />} /> {/* Página por defecto, redirige al register */}
            </Routes>
        </Router>
    );
}

export default App;
