package dao;

import java.util.List;

import model.ApprovalRequest;

public interface ApprovalRequestDAO {
    int createRequest(ApprovalRequest request);

    boolean updateRequestStatus(int requestId, String status, int approvedBy, String adminNotes);

    ApprovalRequest findById(int requestId);

    List<ApprovalRequest> findPendingRequests();

    List<ApprovalRequest> findByOwnerId(int ownerId);
}