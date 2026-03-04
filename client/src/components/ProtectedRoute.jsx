import { useContext } from 'react';
import { Navigate } from 'react-router-dom';
import { UserContext } from '../context/UserContext';

export default function ProtectedRoute({ children, requireAdmin = false }) {

  const { user } = useContext(UserContext);

  if (!user) return <Navigate to="/login" replace />;

  if (requireAdmin && !user.role?.includes("ADMIN")) {
    return (
      <Navigate
        to="/login" 
        replace
        state={{message: "You must be logged in as an admin to do this."}} 
      />
    );  
  }

  return children;
};
