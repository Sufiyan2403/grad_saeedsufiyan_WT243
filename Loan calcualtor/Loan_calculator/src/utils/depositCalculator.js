/**
 * Calculates maturity amount using Simple Interest:
 * Maturity = P + (P × R × T) / 100
 * @param {number} P - Principal amount
 * @param {number} R - Rate of interest in %
 * @param {number} T - Tenure in years
 * @returns {number} Maturity amount
 */
export function calculateMaturity(P, R, T) {
  return P + (P * R * T) / 100;
}
