package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.ApprovalRequestDAO;
import dao.SiteDAO;
import dao.impl.ApprovalRequestDAOImpl;
import dao.impl.SiteDAOImpl;
import database.DatabaseConnection;
import model.ApprovalRequest;
import model.Site;

public class ApprovalService {
    private ApprovalRequestDAO requestDAO;
    private SiteDAO siteDAO;

    public ApprovalService() {
        this.requestDAO = new ApprovalRequestDAOImpl();
        this.siteDAO = new SiteDAOImpl();
    }

    public boolean createStatusUpdateRequest(int siteNumber, String newStatus, int userId, int ownerId) {
        Site site = siteDAO.findBySiteNumberAndOwner(siteNumber, ownerId);
        if (site == null) {
            System.out.println("Site not found or you don't own this site.");
            return false;
        }

        ApprovalRequest request = new ApprovalRequest();
        request.setSiteId(site.getSiteId());
        request.setRequestedBy(userId);
        request.setRequestType("STATUS_UPDATE");
        request.setOldValue(site.getStatus());
        request.setNewValue(newStatus);

        if (requestDAO.createRequest(request) > 0) {
            System.out.println("Status update request submitted! Waiting for admin approval.");
            return true;
        }
        return false;
    }

    public boolean processApproval(int requestId, boolean approve, int adminUserId, String notes) {
        Connection conn = DatabaseConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            if (approve) {
                ApprovalRequest request = requestDAO.findById(requestId);
                if (request != null && request.getRequestType().equals("STATUS_UPDATE")) {
                    Site site = siteDAO.findBySiteNumber(request.getSiteNumber());
                    if (site != null) {
                        site.setStatus(request.getNewValue());
                        siteDAO.updateSite(site);
                    }
                }
            }

            // Update request status
            String status = approve ? "APPROVED" : "REJECTED";
            requestDAO.updateRequestStatus(requestId, status, adminUserId, notes);

            conn.commit();
            System.out.println("Request " + (approve ? "approved" : "rejected") + " successfully!");
            return true;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.err.println("Error processing approval: " + e.getMessage());
            return false;
        }
    }

    public List<ApprovalRequest> getPendingApprovals() {
        return requestDAO.findPendingRequests();
    }

    public List<ApprovalRequest> getRequestsByOwner(int ownerId) {
        return requestDAO.findByOwnerId(ownerId);
    }
}

