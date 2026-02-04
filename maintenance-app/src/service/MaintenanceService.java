package service;

import java.sql.Date;
import java.util.List;

import dao.MaintenancePaymentDAO;
import dao.SiteDAO;
import dao.impl.MaintenancePaymentDAOImpl;
import dao.impl.SiteDAOImpl;
import model.MaintenancePayment;
import model.Site;

public class MaintenanceService {
    private SiteDAO siteDAO;
    private MaintenancePaymentDAO paymentDAO;

    public MaintenanceService() {
        this.siteDAO = new SiteDAOImpl();
        this.paymentDAO = new MaintenancePaymentDAOImpl();
    }

    public boolean collectMaintenance(int siteNumber, String paymentMonth, double amount, int collectedBy,
            String notes) {
        Site site = siteDAO.findBySiteNumber(siteNumber);
        if (site == null) {
            System.out.println("Site not found!");
            return false;
        }

        MaintenancePayment payment = new MaintenancePayment();
        payment.setSiteId(site.getSiteId());
        payment.setPaymentDate(new Date(System.currentTimeMillis()));
        payment.setAmount(amount);
        payment.setPaymentMonth(paymentMonth);
        payment.setCollectedBy(collectedBy);
        payment.setNotes(notes);

        if (paymentDAO.createPayment(payment)) {
            System.out.println("Maintenance collected successfully!");
            return true;
        }
        return false;
    }

    public List<Site> getPendingSites(String month) {
        return paymentDAO.findPendingSites(month);
    }

    public List<MaintenancePayment> getPaymentHistory(int siteId) {
        return paymentDAO.findBySiteId(siteId);
    }
}

