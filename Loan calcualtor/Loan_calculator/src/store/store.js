import { configureStore } from '@reduxjs/toolkit';
import loanReducer from './loanSlice';
import depositReducer from './depositSlice';

export const store = configureStore({
  reducer: {
    loan: loanReducer,
    deposit: depositReducer,
  },
});
