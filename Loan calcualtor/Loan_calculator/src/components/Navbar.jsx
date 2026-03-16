import { NavLink } from 'react-router-dom';
import styles from './Navbar.module.css';

export default function Navbar() {
  return (
    <nav className={styles.navbar}>
      <div className={styles.brand}>
        <span className={styles.brandIcon}>💰</span>
        FinCalc
      </div>
      <ul className={styles.navLinks}>
        <li>
          <NavLink
            to="/emi"
            className={({ isActive }) =>
              isActive ? `${styles.link} ${styles.active}` : styles.link
            }
          >
            EMI Calculator
          </NavLink>
        </li>
        <li>
          <NavLink
            to="/deposit"
            className={({ isActive }) =>
              isActive ? `${styles.link} ${styles.active}` : styles.link
            }
          >
            Deposit Calculator
          </NavLink>
        </li>
      </ul>
    </nav>
  );
}
