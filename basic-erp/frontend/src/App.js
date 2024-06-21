// src/App.js
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navbar from './components/Navbar';
import Home from './pages/Home';
import Register from './pages/Register';
import Login from './pages/Login';
import UserDashboard from './pages/UserDashboard';
import AdminDashboard from './pages/AdminDashboard';
import ProtectedRoute from './components/ProtectedRoute';

const App = () => {
  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />
        <Route path="/user-dashboard" element={
          <ProtectedRoute roleRequired="user">
            <UserDashboard />
          </ProtectedRoute>
        } />
        <Route path="/admin-dashboard" element={
          <ProtectedRoute roleRequired="admin">
            <AdminDashboard />
          </ProtectedRoute>
        } />
      </Routes>
    </Router>
  );
};

export default App;
