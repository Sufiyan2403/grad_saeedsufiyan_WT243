import { useState } from 'react';
import { useDispatch } from 'react-redux';
import { setDepositData, resetDeposit } from '../store/depositSlice';
import { calculateMaturity } from '../utils/depositCalculator';
import { formatCurrency } from '../utils/emiCalculator';
import styles from './EmiForm.module.css';

const initialForm = {
  fullName: '',
  phone: '',
  depositAmount: '',
  interestRate: '',
  tenure: '',
};

export default function DepositForm() {
  const dispatch = useDispatch();
  const [form, setForm] = useState(initialForm);
  const [errors, setErrors] = useState({});
  const [result, setResult] = useState(null);

  function validate() {
    const errs = {};

    if (!form.fullName.trim()) {
      errs.fullName = 'Full name is required.';
    } else if (form.fullName.trim().length < 3) {
      errs.fullName = 'Name must be at least 3 characters.';
    } else if (!/^[a-zA-Z\s]+$/.test(form.fullName.trim())) {
      errs.fullName = 'Only alphabets and spaces are allowed.';
    }

    if (!form.phone.trim()) {
      errs.phone = 'Phone number is required.';
    } else if (!/^\d{10}$/.test(form.phone.trim())) {
      errs.phone = 'Phone number must be exactly 10 digits.';
    }

    if (!form.depositAmount) {
      errs.depositAmount = 'Deposit amount is required.';
    } else {
      const amt = parseFloat(form.depositAmount);
      if (isNaN(amt) || amt <= 0) {
        errs.depositAmount = 'Deposit amount must be greater than 0.';
      }
    }

    if (!form.interestRate) {
      errs.interestRate = 'Rate of interest is required.';
    } else {
      const rate = parseFloat(form.interestRate);
      if (isNaN(rate) || rate < 1 || rate > 20) {
        errs.interestRate = 'Interest rate must be between 1% and 20%.';
      }
    }

    if (!form.tenure) {
      errs.tenure = 'Tenure is required.';
    } else {
      const t = parseInt(form.tenure, 10);
      if (isNaN(t) || t < 1 || t > 10) {
        errs.tenure = 'Tenure must be between 1 and 10 years.';
      }
    }

    return errs;
  }

  function handleChange(e) {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
    if (errors[name]) {
      setErrors((prev) => ({ ...prev, [name]: '' }));
    }
    setResult(null);
  }

  function handleSubmit(e) {
    e.preventDefault();
    const errs = validate();
    if (Object.keys(errs).length > 0) {
      setErrors(errs);
      return;
    }
    setErrors({});
    const P = parseFloat(form.depositAmount);
    const R = parseFloat(form.interestRate);
    const T = parseInt(form.tenure, 10);
    const maturityAmount = calculateMaturity(P, R, T);
    const payload = {
      depositAmount: P,
      interestRate: R,
      tenure: T,
      maturityAmount: parseFloat(maturityAmount.toFixed(2)),
    };
    dispatch(setDepositData(payload));
    setResult(payload);
  }

  function handleReset() {
    setForm(initialForm);
    setErrors({});
    setResult(null);
    dispatch(resetDeposit());
  }

  return (
    <div className={styles.wrapper}>
      <form className={styles.form} onSubmit={handleSubmit} noValidate>
        <div className={styles.field}>
          <label htmlFor="dep-fullName" className={styles.label}>Full Name</label>
          <input
            id="dep-fullName"
            name="fullName"
            type="text"
            className={`${styles.input} ${errors.fullName ? styles.inputError : ''}`}
            placeholder="e.g. Priya Mehta"
            value={form.fullName}
            onChange={handleChange}
          />
          {errors.fullName && <span className={styles.error}>{errors.fullName}</span>}
        </div>

        <div className={styles.field}>
          <label htmlFor="dep-phone" className={styles.label}>Phone Number</label>
          <input
            id="dep-phone"
            name="phone"
            type="tel"
            className={`${styles.input} ${errors.phone ? styles.inputError : ''}`}
            placeholder="10-digit mobile number"
            value={form.phone}
            onChange={handleChange}
            maxLength={10}
          />
          {errors.phone && <span className={styles.error}>{errors.phone}</span>}
        </div>

        <div className={styles.field}>
          <label htmlFor="depositAmount" className={styles.label}>Deposit Amount (₹)</label>
          <input
            id="depositAmount"
            name="depositAmount"
            type="number"
            className={`${styles.input} ${errors.depositAmount ? styles.inputError : ''}`}
            placeholder="Enter deposit amount"
            value={form.depositAmount}
            onChange={handleChange}
            min={0}
          />
          {errors.depositAmount && <span className={styles.error}>{errors.depositAmount}</span>}
        </div>

        <div className={styles.field}>
          <label htmlFor="interestRate" className={styles.label}>Rate of Interest (%)</label>
          <input
            id="interestRate"
            name="interestRate"
            type="number"
            className={`${styles.input} ${errors.interestRate ? styles.inputError : ''}`}
            placeholder="1% – 20%"
            value={form.interestRate}
            onChange={handleChange}
            min={1}
            max={20}
            step="0.01"
          />
          {errors.interestRate && <span className={styles.error}>{errors.interestRate}</span>}
        </div>

        <div className={styles.field}>
          <label htmlFor="dep-tenure" className={styles.label}>Tenure (years)</label>
          <input
            id="dep-tenure"
            name="tenure"
            type="number"
            className={`${styles.input} ${errors.tenure ? styles.inputError : ''}`}
            placeholder="1 – 10 years"
            value={form.tenure}
            onChange={handleChange}
            min={1}
            max={10}
          />
          {errors.tenure && <span className={styles.error}>{errors.tenure}</span>}
        </div>

        <div className={styles.actions}>
          <button type="submit" className={styles.btnPrimary}>
            Calculate Maturity
          </button>
          <button type="button" className={styles.btnSecondary} onClick={handleReset}>
            Reset
          </button>
        </div>
      </form>

      {result && (
        <div className={styles.resultCard}>
          <div className={styles.resultHeader}>
            <span className={styles.resultIcon}>🏛️</span>
            <h3>Deposit Summary</h3>
          </div>
          <div className={styles.resultGrid}>
            <div className={styles.resultItem}>
              <span className={styles.resultLabel}>Principal</span>
              <span className={styles.resultValue}>{formatCurrency(result.depositAmount)}</span>
            </div>
            <div className={styles.resultItem}>
              <span className={styles.resultLabel}>Interest Rate</span>
              <span className={styles.resultValue}>{result.interestRate}% p.a.</span>
            </div>
            <div className={styles.resultItem}>
              <span className={styles.resultLabel}>Tenure</span>
              <span className={styles.resultValue}>{result.tenure} years</span>
            </div>
            <div className={styles.resultItem}>
              <span className={styles.resultLabel}>Interest Earned</span>
              <span className={styles.resultValue}>
                {formatCurrency(result.maturityAmount - result.depositAmount)}
              </span>
            </div>
          </div>
          <div className={styles.emiHighlight}>
            <span className={styles.emiLabel}>Maturity Amount</span>
            <span className={styles.emiAmount}>{formatCurrency(result.maturityAmount)}</span>
          </div>
        </div>
      )}
    </div>
  );
}
