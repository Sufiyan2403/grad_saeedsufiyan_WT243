import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { Provider } from 'react-redux';
import { store } from './store/store';
import Navbar from './components/Navbar';
import EmiCalculator from './pages/EmiCalculator';
import DepositCalculator from './pages/DepositCalculator';

export default function App() {
  return (
    <Provider store={store}>
      <BrowserRouter>
        <Navbar />
        <Routes>
          <Route path="/" element={<Navigate to="/emi" replace />} />
          <Route path="/emi" element={<EmiCalculator />} />
          <Route path="/deposit" element={<DepositCalculator />} />
        </Routes>
      </BrowserRouter>
    </Provider>
  );
}
