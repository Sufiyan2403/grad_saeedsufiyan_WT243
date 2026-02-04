package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.MaintenancePaymentDAO;
import database.DatabaseConnection;
import model.MaintenancePayment;
import model.Site;

public class MaintenancePaymentDAOImpl implements MaintenancePaymentDAO {
    private Connection conn;

    public MaintenancePaymentDAOImpl() {
        this.conn = DatabaseConnection.getConnection();
    }

    @Override
    public boolean createPayment(MaintenancePayment payment) {
        String sql = "INSERT INTO maintenance_payments (site_id, payment_date, amount, payment_month, collected_by, notes) "
                +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, payment.getSiteId());
            stmt.setDate(2, payment.getPaymentDate());
            stmt.setDouble(3, payment.getAmount());
            stmt.setString(4, payment.getPaymentMonth());
            stmt.setInt(5, payment.getCollectedBy());
            stmt.setString(6, payment.getNotes());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error creating payment: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<MaintenancePayment> findBySiteId(int siteId) {
        List<MaintenancePayment> payments = new ArrayList<>();
        String sql = "SELECT * FROM maintenance_payments WHERE site_id = ? ORDER BY payment_date DESC LIMIT 12";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, siteId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MaintenancePayment payment = new MaintenancePayment();
                payment.setPaymentId(rs.getInt("payment_id"));
                payment.setSiteId(rs.getInt("site_id"));
                payment.setPaymentDate(rs.getDate("payment_date"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setPaymentMonth(rs.getString("payment_month"));
                payment.setCollectedBy(rs.getInt("collected_by"));
                payment.setNotes(rs.getString("notes"));
                payments.add(payment);
            }
        } catch (SQLException e) {
            System.err.println("Error finding payments: " + e.getMessage());
        }
        return payments;
    }

    @Override
    public List<Site> findPendingSites(String month) {
        List<Site> sites = new ArrayList<>();
        String sql = "SELECT s.site_number, s.site_type, s.dimensions, s.area_sqft, s.status, " +
                "o.name as owner_name, s.maintenance_rate, " +
                "(s.area_sqft * s.maintenance_rate) as due_amount, " +
                "COALESCE(mp.amount, 0) as paid_amount " +
                "FROM sites s " +
                "LEFT JOIN owners o ON s.owner_id = o.owner_id " +
                "LEFT JOIN maintenance_payments mp ON s.site_id = mp.site_id AND mp.payment_month = ? " +
                "WHERE COALESCE(mp.amount, 0) < (s.area_sqft * s.maintenance_rate) " +
                "ORDER BY s.site_number";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, month);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Site site = new Site();
                site.setSiteNumber(rs.getInt("site_number"));
                site.setSiteType(rs.getString("site_type"));
                site.setDimensions(rs.getString("dimensions"));
                site.setAreaSqft(rs.getInt("area_sqft"));
                site.setStatus(rs.getString("status"));
                site.setOwnerName(rs.getString("owner_name"));
                site.setMaintenanceRate(rs.getDouble("maintenance_rate"));
                sites.add(site);
            }
        } catch (SQLException e) {
            System.err.println("Error finding pending sites: " + e.getMessage());
        }
        return sites;
    }
}

