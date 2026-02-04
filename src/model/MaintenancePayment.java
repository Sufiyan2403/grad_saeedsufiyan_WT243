package model;

import java.sql.Date;

public class MaintenancePayment {
    private int paymentId;
    private int siteId;
    private Date paymentDate;
    private double amount;
    private String paymentMonth;
    private int collectedBy;
    private String notes;

    public MaintenancePayment() {
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMonth() {
        return paymentMonth;
    }

    public void setPaymentMonth(String paymentMonth) {
        this.paymentMonth = paymentMonth;
    }

    public int getCollectedBy() {
        return collectedBy;
    }

    public void setCollectedBy(int collectedBy) {
        this.collectedBy = collectedBy;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

