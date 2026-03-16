import { useState } from 'react';
import { useDispatch } from 'react-redux';
import { setLoanData, resetLoan } from '../store/loanSlice';
import { LOAN_RULES, calculateEMI, formatCurrency } from '../utils/emiCalculator';
import styles from './EmiForm.module.css';

const LOAN_OPTIONS = [
  { value: 'home', label: 'Home Loan' },
  { value: 'personal', label: 'Personal Loan' },
  { value: 'car', label: 'Car Loan' },
];

const initialForm = {
  fullName: '',
  phone: '',
  loanType: '',
  loanAmount: '',
  tenure: '',
};

export default function EmiForm({ onResult }) {
  const dispatch = useDispatch();
  const [form, setForm] = useState(initialForm);
  const [errors, setErrors] = useState({});
  const [result, setResult] = useState(null);

  const selectedRule = form.loanType ? LOAN_RULES[form.loanType] : null;

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

    if (!form.loanType) {
      errs.loanType = 'Please select a loan type.';
    }

    if (!form.loanAmount) {
      errs.loanAmount = 'Loan amount is required.';
    } else {
      const amount = parseFloat(form.loanAmount);
      if (isNaN(amount) || amount <= 0) {
        errs.loanAmount = 'Enter a valid loan amount.';
      } else if (selectedRule && amount < selectedRule.minAmount) {
        errs.loanAmount = `Minimum amount for ${selectedRule.label} is ₹${selectedRule.minAmount.toLocaleString('en-IN')}.`;
      }
    }

    if (!form.tenure) {
      errs.tenure = 'Tenure is required.';
    } else {
      const t = parseInt(form.tenure, 10);
      if (isNaN(t) || t <= 0) {
        errs.tenure = 'Enter a valid tenure.';
      } else if (selectedRule) {
        if (t < selectedRule.minTenure || t > selectedRule.maxTenure) {
          errs.tenure = `Tenure must be between ${selectedRule.minTenure} and ${selectedRule.maxTenure} years for ${selectedRule.label}.`;
        }
      }
    }

    return errs;
  }

  function handleChange(e) {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
    // Clear error on change
    if (errors[name]) {
      setErrors((prev) => ({ ...prev, [name]: '' }));
    }
    // Reset result if form changes
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
    const rule = LOAN_RULES[form.loanType];
    const emi = calculateEMI(parseFloat(form.loanAmount), rule.rate, parseInt(form.tenure, 10));
    const payload = {
      loanType: form.loanType,
      loanAmount: parseFloat(form.loanAmount),
      tenure: parseInt(form.tenure, 10),
      interestRate: rule.rate,
      emi: parseFloat(emi.toFixed(2)),
    };
    dispatch(setLoanData(payload));
    setResult(payload);
  }

  function handleReset() {
    setForm(initialForm);
    setErrors({});
    setResult(null);
    dispatch(resetLoan());
  }

  return (
    <div className={styles.wrapper}>
      <form className={styles.form} onSubmit={handleSubmit} noValidate>
        <div className={styles.field}>
          <label htmlFor="fullName" className={styles.label}>Full Name</label>
          <input
            id="fullName"
            name="fullName"
            type="text"
            className={`${styles.input} ${errors.fullName ? styles.inputError : ''}`}
            placeholder="e.g. Rahul Sharma"
            value={form.fullName}
            onChange={handleChange}
          />
          {errors.fullName && <span className={styles.error}>{errors.fullName}</span>}
        </div>

        <div className={styles.field}>
          <label htmlFor="phone" className={styles.label}>Phone Number</label>
          <input
            id="phone"
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
          <label htmlFor="loanType" className={styles.label}>Loan Type</label>
          <select
            id="loanType"
            name="loanType"
            className={`${styles.input} ${errors.loanType ? styles.inputError : ''}`}
            value={form.loanType}
            onChange={handleChange}
          >
            <option value="">-- Select Loan Type --</option>
            {LOAN_OPTIONS.map((opt) => (
              <option key={opt.value} value={opt.value}>{opt.label}</option>
            ))}
          </select>
          {errors.loanType && <span className={styles.error}>{errors.loanType}</span>}
          {selectedRule && (
            <p className={styles.hint}>
              Interest Rate: <strong>{selectedRule.rate}%</strong> &nbsp;|&nbsp;
              Tenure: <strong>{selectedRule.minTenure}–{selectedRule.maxTenure} years</strong> &nbsp;|&nbsp;
              Min Amount: <strong>₹{selectedRule.minAmount.toLocaleString('en-IN')}</strong>
            </p>
          )}
        </div>

        <div className={styles.field}>
          <label htmlFor="loanAmount" className={styles.label}>Loan Amount (₹)</label>
          <input
            id="loanAmount"
            name="loanAmount"
            type="number"
            className={`${styles.input} ${errors.loanAmount ? styles.inputError : ''}`}
            placeholder="Enter loan amount"
            value={form.loanAmount}
            onChange={handleChange}
            min={0}
          />
          {errors.loanAmount && <span className={styles.error}>{errors.loanAmount}</span>}
        </div>

        <div className={styles.field}>
          <label htmlFor="tenure" className={styles.label}>Loan Tenure (years)</label>
          <input
            id="tenure"
            name="tenure"
            type="number"
            className={`${styles.input} ${errors.tenure ? styles.inputError : ''}`}
            placeholder={selectedRule ? `${selectedRule.minTenure}–${selectedRule.maxTenure} years` : 'Enter tenure'}
            value={form.tenure}
            onChange={handleChange}
            min={1}
          />
          {errors.tenure && <span className={styles.error}>{errors.tenure}</span>}
        </div>

        <div className={styles.actions}>
          <button type="submit" className={styles.btnPrimary}>
            Calculate EMI
          </button>
          <button type="button" className={styles.btnSecondary} onClick={handleReset}>
            Reset
          </button>
        </div>
      </form>

      {result && (
        <div className={styles.resultCard}>
          <div className={styles.resultHeader}>
            <span className={styles.resultIcon}>🏦</span>
            <h3>Loan Summary</h3>
          </div>
          <div className={styles.resultGrid}>
            <div className={styles.resultItem}>
              <span className={styles.resultLabel}>Loan Type</span>
              <span className={styles.resultValue}>{LOAN_RULES[result.loanType].label}</span>
            </div>
            <div className={styles.resultItem}>
              <span className={styles.resultLabel}>Principal</span>
              <span className={styles.resultValue}>{formatCurrency(result.loanAmount)}</span>
            </div>
            <div className={styles.resultItem}>
              <span className={styles.resultLabel}>Interest Rate</span>
              <span className={styles.resultValue}>{result.interestRate}% p.a.</span>
            </div>
            <div className={styles.resultItem}>
              <span className={styles.resultLabel}>Tenure</span>
              <span className={styles.resultValue}>{result.tenure} years ({result.tenure * 12} months)</span>
            </div>
          </div>
          <div className={styles.emiHighlight}>
            <span className={styles.emiLabel}>Monthly EMI</span>
            <span className={styles.emiAmount}>{formatCurrency(result.emi)}</span>
          </div>
        </div>
      )}
    </div>
  );
}
