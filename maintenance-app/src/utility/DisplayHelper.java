package utility;

import java.util.List;

import model.MaintenancePayment;
import model.Owner;
import model.Site;
import model.ApprovalRequest;

public class DisplayHelper {

    public static void displayOwners(List<Owner> owners) {
        System.out.println("\n========== ALL OWNERS ==========");
        System.out.printf("%-5s %-20s %-25s %-15s%n", "ID", "Name", "Email", "Phone");
        System.out.println("=".repeat(70));

        for (Owner owner : owners) {
            System.out.printf("%-5d %-20s %-25s %-15s%n",
                    owner.getOwnerId(),
                    owner.getName(),
                    owner.getEmail(),
                    owner.getPhone());
        }
    }

    public static void displaySites(List<Site> sites) {
        System.out.println("\n========== SITES ==========");
        System.out.printf("%-6s %-20s %-12s %-8s %-20s %-20s %-10s%n",
                "Site#", "Type", "Dimensions", "Area", "Owner", "Status", "Rate/sqft");
        System.out.println("=".repeat(110));

        for (Site site : sites) {
            System.out.printf("%-6d %-20s %-12s %-8d %-20s %-20s %-10.2f%n",
                    site.getSiteNumber(),
                    site.getSiteType(),
                    site.getDimensions(),
                    site.getAreaSqft(),
                    site.getOwnerName() != null ? site.getOwnerName() : "N/A",
                    site.getStatus(),
                    site.getMaintenanceRate());
        }
    }

    public static void displayMySites(List<Site> sites) {
        System.out.println("\n========== MY SITES ==========");
        System.out.printf("%-6s %-20s %-12s %-8s %-20s %-10s %-15s%n",
                "Site#", "Type", "Dimensions", "Area", "Status", "Rate/sqft", "Monthly Due");
        System.out.println("=".repeat(100));

        for (Site site : sites) {
            System.out.printf("%-6d %-20s %-12s %-8d %-20s %-10.2f %-15.2f%n",
                    site.getSiteNumber(),
                    site.getSiteType(),
                    site.getDimensions(),
                    site.getAreaSqft(),
                    site.getStatus(),
                    site.getMaintenanceRate(),
                    site.calculateMaintenance());
        }

        if (sites.isEmpty()) {
            System.out.println("You don't have any sites assigned yet.");
        }
    }

    public static void displayPendingSites(List<Site> sites, String month) {
        System.out.println("\n========== PENDING MAINTENANCE FOR " + month + " ==========");
        System.out.printf("%-6s %-20s %-12s %-8s %-20s %-15s %-10s%n",
                "Site#", "Type", "Dimensions", "Area", "Owner", "Status", "Due");
        System.out.println("=".repeat(100));

        for (Site site : sites) {
            System.out.printf("%-6d %-20s %-12s %-8d %-20s %-15s %-10.2f%n",
                    site.getSiteNumber(),
                    site.getSiteType(),
                    site.getDimensions(),
                    site.getAreaSqft(),
                    site.getOwnerName() != null ? site.getOwnerName() : "N/A",
                    site.getStatus(),
                    site.calculateMaintenance());
        }

        if (sites.isEmpty()) {
            System.out.println("No pending maintenance for this month!");
        }
    }

    public static void displayPendingApprovals(List<ApprovalRequest> requests) {
        System.out.println("\n========== PENDING APPROVAL REQUESTS ==========");
        System.out.printf("%-10s %-6s %-20s %-15s %-20s %-20s%n",
                "Request#", "Site#", "Owner", "Type", "Old Value", "New Value");
        System.out.println("=".repeat(100));

        for (ApprovalRequest req : requests) {
            System.out.printf("%-10d %-6d %-20s %-15s %-20s %-20s%n",
                    req.getRequestId(),
                    req.getSiteNumber(),
                    req.getOwnerName() != null ? req.getOwnerName() : "N/A",
                    req.getRequestType(),
                    req.getOldValue(),
                    req.getNewValue());
        }

        if (requests.isEmpty()) {
            System.out.println("No pending approval requests!");
        }
    }

    public static void displayMyRequests(List<ApprovalRequest> requests) {
        System.out.println("\n========== MY REQUESTS ==========");
        System.out.printf("%-10s %-6s %-15s %-15s %-15s %-10s%n",
                "Request#", "Site#", "Type", "Old Value", "New Value", "Status");
        System.out.println("=".repeat(85));

        for (ApprovalRequest req : requests) {
            System.out.printf("%-10d %-6d %-15s %-15s %-15s %-10s%n",
                    req.getRequestId(),
                    req.getSiteNumber(),
                    req.getRequestType(),
                    req.getOldValue(),
                    req.getNewValue(),
                    req.getStatus());
            if (req.getAdminNotes() != null && !req.getAdminNotes().isEmpty()) {
                System.out.println("   Admin Notes: " + req.getAdminNotes());
            }
        }

        if (requests.isEmpty()) {
            System.out.println("No requests found.");
        }
    }

    public static void displaySiteDetails(Site site, List<MaintenancePayment> payments) {
        System.out.println("\n========== SITE DETAILS ==========");
        System.out.println("Site Number      : " + site.getSiteNumber());
        System.out.println("Site Type        : " + site.getSiteType());
        System.out.println("Dimensions       : " + site.getDimensions());
        System.out.println("Area (sqft)      : " + site.getAreaSqft());
        System.out.println("Status           : " + site.getStatus());
        System.out.println("Maintenance Rate : Rs. " + site.getMaintenanceRate() + "/sqft");
        System.out.println("Monthly Due      : Rs. " + site.calculateMaintenance());
        System.out.println("==================================");

        System.out.println("\n--- Recent Payment History ---");
        System.out.printf("%-15s %-12s %-10s %-30s%n", "Date", "Month", "Amount", "Notes");
        System.out.println("-".repeat(70));

        if (payments.isEmpty()) {
            System.out.println("No payment history available.");
        } else {
            for (MaintenancePayment payment : payments) {
                System.out.printf("%-15s %-12s %-10.2f %-30s%n",
                        payment.getPaymentDate().toString(),
                        payment.getPaymentMonth(),
                        payment.getAmount(),
                        payment.getNotes() != null ? payment.getNotes() : "");
            }
        }
    }
}

