import EmiForm from '../forms/EmiForm';
import styles from './Page.module.css';

export default function EmiCalculator() {
  return (
    <main className={styles.page}>
      <div className={styles.pageHeader}>
        <h1 className={styles.title}>EMI Calculator</h1>
        <p className={styles.subtitle}>
          Calculate your monthly EMI for Home, Personal, or Car loans instantly.
        </p>
      </div>
      <EmiForm />
    </main>
  );
}
