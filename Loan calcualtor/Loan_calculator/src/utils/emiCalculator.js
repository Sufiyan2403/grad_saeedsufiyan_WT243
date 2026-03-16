export const LOAN_RULES = {
  home: {
    label: 'Home Loan',
    minAmount: 1000000,
    rate: 9,
    minTenure: 5,
    maxTenure: 30,
  },
  personal: {
    label: 'Personal Loan',
    minAmount: 50000,
    rate: 15,
    minTenure: 1,
    maxTenure: 5,
  },
  car: {
    label: 'Car Loan',
    minAmount: 200000,
    rate: 12,
    minTenure: 1,
    maxTenure: 7,
  },
};

/**
 * Calculates EMI using standard formula:
 * EMI = P × r × (1+r)^n / ((1+r)^n − 1)
 * @param {number} P - Principal amount
 * @param {number} annualRate - Annual interest rate in %
 * @param {number} tenureYears - Tenure in years
 * @returns {number} Monthly EMI
 */
export function calculateEMI(P, annualRate, tenureYears) {
  const r = annualRate / 12 / 100;
  const n = tenureYears * 12;
  if (r === 0) return P / n;
  const emi = (P * r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
  return emi;
}

export function formatCurrency(amount) {
  return new Intl.NumberFormat('en-IN', {
    style: 'currency',
    currency: 'INR',
    maximumFractionDigits: 2,
  }).format(amount);
}

export function formatIndianNumber(num) {
  return new Intl.NumberFormat('en-IN').format(num);
}
