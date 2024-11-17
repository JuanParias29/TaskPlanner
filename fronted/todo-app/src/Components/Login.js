import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post(
                'http://localhost:3000/api/auth/login',
                { username, password },
                { withCredentials: true } // Habilita el envío de cookies
            );

            if (response.status === 200) {
                navigate('/tasks');
            }
        } catch (err) {
            console.error('Error durante el login:', err.response || err.message);
            setError('Credenciales incorrectas');
        }
    };

    return (
        <div>
            <h2>Iniciar sesión</h2>
            <form onSubmit={handleLogin}>
                <input
                    type="text"
                    placeholder="Nombre de usuario"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
                <input
                    type="password"
                    placeholder="Contraseña"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <button type="submit">Iniciar sesión</button>
            </form>

            {/* Si hay un error de login, mostrarlo */}
            {error && <div style={{ color: 'red' }}>{error}</div>}

            {/* Enlace a la página de registro si no tienes cuenta */}
            <p>
                ¿No tienes cuenta? <a href="/register">Regístrate aquí</a>
            </p>
        </div>
    );
};

export default Login;
