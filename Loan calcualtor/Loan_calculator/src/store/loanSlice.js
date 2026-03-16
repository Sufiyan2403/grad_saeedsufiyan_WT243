import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  loanType: '',
  loanAmount: '',
  tenure: '',
  interestRate: 0,
  emi: null,
};

const loanSlice = createSlice({
  name: 'loan',
  initialState,
  reducers: {
    setLoanData(state, action) {
      const { loanType, loanAmount, tenure, interestRate, emi } = action.payload;
      state.loanType = loanType;
      state.loanAmount = loanAmount;
      state.tenure = tenure;
      state.interestRate = interestRate;
      state.emi = emi;
    },
    resetLoan() {
      return initialState;
    },
  },
});

export const { setLoanData, resetLoan } = loanSlice.actions;
export default loanSlice.reducer;
