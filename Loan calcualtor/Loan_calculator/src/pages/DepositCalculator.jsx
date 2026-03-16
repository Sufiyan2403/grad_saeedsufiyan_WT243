import DepositForm from '../forms/DepositForm';
import styles from './Page.module.css';

export default function DepositCalculator() {
  return (
    <main className={styles.page}>
      <div className={styles.pageHeader}>
        <h1 className={styles.title}>Deposit Calculator</h1>
        <p className={styles.subtitle}>
          Find out the maturity amount for your fixed or recurring deposit using simple interest.
        </p>
      </div>
      <DepositForm />
    </main>
  );
}
