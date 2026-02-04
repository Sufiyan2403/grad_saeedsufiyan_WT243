package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import dao.SiteDAO;
import database.DatabaseConnection;
import model.Site;

public class SiteDAOImpl implements SiteDAO {
    private Connection conn;

    public SiteDAOImpl() {
        this.conn = DatabaseConnection.getConnection();
    }

    @Override
    public Site findBySiteNumber(int siteNumber) {
        String sql = "SELECT s.*, o.name as owner_name FROM sites s " +
                "LEFT JOIN owners o ON s.owner_id = o.owner_id " +
                "WHERE s.site_number = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, siteNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToSite(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error finding site: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Site findBySiteNumberAndOwner(int siteNumber, int ownerId) {
        String sql = "SELECT s.*, o.name as owner_name FROM sites s " +
                "LEFT JOIN owners o ON s.owner_id = o.owner_id " +
                "WHERE s.site_number = ? AND s.owner_id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, siteNumber);
            stmt.setInt(2, ownerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToSite(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error finding site: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Site> findByOwnerId(int ownerId) {
        List<Site> sites = new ArrayList<>();
        String sql = "SELECT s.*, o.name as owner_name FROM sites s " +
                "LEFT JOIN owners o ON s.owner_id = o.owner_id " +
                "WHERE s.owner_id = ? ORDER BY s.site_number";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ownerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                sites.add(mapResultSetToSite(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding sites by owner: " + e.getMessage());
        }
        return sites;
    }

    @Override
    public List<Site> findAll() {
        List<Site> sites = new ArrayList<>();
        String sql = "SELECT s.*, o.name as owner_name FROM sites s " +
                "LEFT JOIN owners o ON s.owner_id = o.owner_id " +
                "ORDER BY s.site_number";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                sites.add(mapResultSetToSite(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding all sites: " + e.getMessage());
        }
        return sites;
    }

    @Override
    public boolean updateSite(Site site) {
        String sql = "UPDATE sites SET site_type = ?, status = ?, owner_id = ?, " +
                "maintenance_rate = ?, updated_at = CURRENT_TIMESTAMP WHERE site_id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, site.getSiteType());
            stmt.setString(2, site.getStatus());
            if (site.getOwnerId() != null) {
                stmt.setInt(3, site.getOwnerId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            stmt.setDouble(4, site.getMaintenanceRate());
            stmt.setInt(5, site.getSiteId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating site: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean assignSiteToOwner(int siteNumber, int ownerId, String siteType, String status) {
        String sql = "UPDATE sites SET owner_id = ?, site_type = ?, status = ?, maintenance_rate = 9.00 WHERE site_number = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ownerId);
            stmt.setString(2, siteType);
            stmt.setString(3, status);
            stmt.setInt(4, siteNumber);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error assigning site: " + e.getMessage());
        }
        return false;
    }

    private Site mapResultSetToSite(ResultSet rs) throws SQLException {
        Site site = new Site();
        site.setSiteId(rs.getInt("site_id"));
        site.setSiteNumber(rs.getInt("site_number"));
        site.setSiteType(rs.getString("site_type"));
        site.setDimensions(rs.getString("dimensions"));
        site.setAreaSqft(rs.getInt("area_sqft"));
        site.setStatus(rs.getString("status"));
        site.setOwnerId((Integer) rs.getObject("owner_id"));
        site.setMaintenanceRate(rs.getDouble("maintenance_rate"));
        site.setOwnerName(rs.getString("owner_name"));
        return site;
    }
}
