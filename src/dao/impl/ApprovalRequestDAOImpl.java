package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ApprovalRequestDAO;
import database.DatabaseConnection;
import model.ApprovalRequest;

public class ApprovalRequestDAOImpl implements ApprovalRequestDAO {
    private Connection conn;

    public ApprovalRequestDAOImpl() {
        this.conn = DatabaseConnection.getConnection();
    }

    @Override
    public int createRequest(ApprovalRequest request) {
        String sql = "INSERT INTO approval_requests (site_id, requested_by, request_type, old_value, new_value) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING request_id";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, request.getSiteId());
            stmt.setInt(2, request.getRequestedBy());
            stmt.setString(3, request.getRequestType());
            stmt.setString(4, request.getOldValue());
            stmt.setString(5, request.getNewValue());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("request_id");
            }
        } catch (SQLException e) {
            System.err.println("Error creating request: " + e.getMessage());
        }
        return -1;
    }

    @Override
    public boolean updateRequestStatus(int requestId, String status, int approvedBy, String adminNotes) {
        String sql = "UPDATE approval_requests SET status = ?, approved_by = ?, admin_notes = ?, " +
                "response_date = CURRENT_TIMESTAMP WHERE request_id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setInt(2, approvedBy);
            stmt.setString(3, adminNotes);
            stmt.setInt(4, requestId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating request status: " + e.getMessage());
        }
        return false;
    }

    @Override
    public ApprovalRequest findById(int requestId) {
        String sql = "SELECT ar.*, s.site_number FROM approval_requests ar " +
                "JOIN sites s ON ar.site_id = s.site_id WHERE ar.request_id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, requestId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToRequest(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error finding request: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<ApprovalRequest> findPendingRequests() {
        List<ApprovalRequest> requests = new ArrayList<>();
        String sql = "SELECT ar.*, s.site_number, o.name as owner_name FROM approval_requests ar " +
                "JOIN sites s ON ar.site_id = s.site_id " +
                "LEFT JOIN owners o ON s.owner_id = o.owner_id " +
                "WHERE ar.status = 'PENDING' ORDER BY ar.request_date";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ApprovalRequest request = mapResultSetToRequest(rs);
                request.setOwnerName(rs.getString("owner_name"));
                requests.add(request);
            }
        } catch (SQLException e) {
            System.err.println("Error finding pending requests: " + e.getMessage());
        }
        return requests;
    }

    @Override
    public List<ApprovalRequest> findByOwnerId(int ownerId) {
        List<ApprovalRequest> requests = new ArrayList<>();
        String sql = "SELECT ar.*, s.site_number FROM approval_requests ar " +
                "JOIN sites s ON ar.site_id = s.site_id " +
                "WHERE s.owner_id = ? ORDER BY ar.request_date DESC";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ownerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                requests.add(mapResultSetToRequest(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding requests by owner: " + e.getMessage());
        }
        return requests;
    }

    private ApprovalRequest mapResultSetToRequest(ResultSet rs) throws SQLException {
        ApprovalRequest request = new ApprovalRequest();
        request.setRequestId(rs.getInt("request_id"));
        request.setSiteId(rs.getInt("site_id"));
        request.setRequestedBy(rs.getInt("requested_by"));
        request.setRequestType(rs.getString("request_type"));
        request.setOldValue(rs.getString("old_value"));
        request.setNewValue(rs.getString("new_value"));
        request.setStatus(rs.getString("status"));
        request.setAdminNotes(rs.getString("admin_notes"));
        request.setRequestDate(rs.getTimestamp("request_date"));
        request.setResponseDate(rs.getTimestamp("response_date"));
        request.setSiteNumber(rs.getInt("site_number"));
        return request;
    }
}
