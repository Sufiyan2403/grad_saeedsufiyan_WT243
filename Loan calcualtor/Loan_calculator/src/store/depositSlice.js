import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  depositAmount: '',
  interestRate: '',
  tenure: '',
  maturityAmount: null,
};

const depositSlice = createSlice({
  name: 'deposit',
  initialState,
  reducers: {
    setDepositData(state, action) {
      const { depositAmount, interestRate, tenure, maturityAmount } = action.payload;
      state.depositAmount = depositAmount;
      state.interestRate = interestRate;
      state.tenure = tenure;
      state.maturityAmount = maturityAmount;
    },
    resetDeposit() {
      return initialState;
    },
  },
});

export const { setDepositData, resetDeposit } = depositSlice.actions;
export default depositSlice.reducer;
