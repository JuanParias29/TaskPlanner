import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom';

const Register = () => {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleRegister = async (e) => {
        e.preventDefault();

        if (!username || !email || !password) {
            setError('Por favor, completa todos los campos.');
            return;
        }

        if (password.length < 6) {
            setError('La contraseña debe tener al menos 6 caracteres.');
            return;
        }

        try {
            const response = await axios.post('/api/auth/register', { username, email, password });
            if (response.status === 201) {
                navigate('/login');
            }
        } catch (error) {
            console.error('Error durante el registro:', error.response?.data || error.message);
            setError(error.response?.data || 'Ocurrió un error durante el registro.');
        }
    };

    const formStyle = {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        gap: '10px',
        maxWidth: '300px',
        margin: 'auto',
    };

    const inputStyle = {
        padding: '10px',
        width: '100%',
        borderRadius: '5px',
        border: '1px solid #ccc',
    };

    const buttonStyle = {
        padding: '10px 20px',
        border: 'none',
        borderRadius: '5px',
        backgroundColor: '#007BFF',
        color: 'white',
        cursor: 'pointer',
    };

    return (
        <div>
            <h2>Registro</h2>
            <form onSubmit={handleRegister} style={formStyle}>
                <input
                    type="text"
                    placeholder="Nombre de usuario"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    style={inputStyle}
                />
                <input
                    type="email"
                    placeholder="Correo electrónico"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    style={inputStyle}
                />
                <input
                    type="password"
                    placeholder="Contraseña"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    style={inputStyle}
                />
                <button type="submit" style={buttonStyle}>Registrar</button>
            </form>
            {error && <div style={{ color: 'red' }}>{error}</div>}
            <p>¿Ya tienes cuenta? <Link to="/login">Inicia sesión</Link></p>
        </div>
    );
};

export default Register;