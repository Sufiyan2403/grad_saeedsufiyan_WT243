package utility;

import java.util.List;
import java.util.Scanner;

import model.MaintenancePayment;
import model.Site;
import model.User;
import service.ApprovalService;
import service.AuthService;
import service.MaintenanceService;
import service.OwnerService;
import service.SiteService;

public class OwnerOperations {

    private static Scanner scanner = new Scanner(System.in);
    private static AuthService authService = new AuthService();
    private static OwnerService ownerService = new OwnerService();
    private static SiteService siteService = new SiteService();
    private static MaintenanceService maintenanceService = new MaintenanceService();
    private static ApprovalService approvalService = new ApprovalService();




    public void viewSiteDetails(int ownerId) {
        System.out.print("\nEnter Site Number: ");
        int siteNumber = Integer.parseInt(scanner.nextLine());
        Site site = siteService.getSiteByNumberAndOwner(siteNumber, ownerId);

        if (site != null) {
            List<MaintenancePayment> payments = maintenanceService.getPaymentHistory(site.getSiteId());
            DisplayHelper.displaySiteDetails(site, payments);
        } else {
            System.out.println("Site not found or you don't own this site.");
        }
    }

    public  void requestStatusUpdate(User owner, int ownerId) {
        System.out.println("\n--- Request Status Update ---");
        DisplayHelper.displayMySites(siteService.getSitesByOwner(ownerId));
        System.out.print("\nEnter Site Number: ");
        int siteNumber = Integer.parseInt(scanner.nextLine());

        System.out.println("New Status:");
        System.out.println("1. Under Construction");
        System.out.println("2. Built");
        System.out.print("Choice: ");
        String choice = scanner.nextLine();

        String status = choice.equals("2") ? "Built" : "Under Construction";

        approvalService.createStatusUpdateRequest(siteNumber, status, owner.getUserId(), ownerId);
    }
    
}
