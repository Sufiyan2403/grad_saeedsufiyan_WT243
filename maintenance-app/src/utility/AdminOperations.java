package utility;

import java.util.Scanner;

import model.User;
import service.ApprovalService;
import service.AuthService;
import service.MaintenanceService;
import service.OwnerService;
import service.SiteService;

public class AdminOperations {

    private static Scanner scanner = new Scanner(System.in);
    private static AuthService authService = new AuthService();
    private static OwnerService ownerService = new OwnerService();
    private static SiteService siteService = new SiteService();
    private static MaintenanceService maintenanceService = new MaintenanceService();
    private static ApprovalService approvalService = new ApprovalService();

    public  void addOwner() {
        System.out.println("\n--- Add New Owner ---");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        while(phone==null || !phone.matches("^[6-9][0-9]{9}$"))
        {
            System.out.println("enter valid phone number: ");
            phone = scanner.nextLine();
        }

        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Username (for login): ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        ownerService.addOwner(name, email, phone, address, username, password);
    }

    public  void updateOwner() {
        System.out.println("\n--- Update Owner ---");
        DisplayHelper.displayOwners(ownerService.getAllOwners());
        System.out.print("\nEnter Owner ID: ");
        int ownerId = Integer.parseInt(scanner.nextLine());
        System.out.print("New Name: ");
        String name = scanner.nextLine();
        System.out.print("New Email: ");
        String email = scanner.nextLine();
        System.out.print("New Phone number: ");
        String phone = scanner.nextLine();
        while(phone==null || !phone.matches("^[6-9][0-9]{9}$"))
        {
            System.out.println("enter valid phone number: ");
            phone = scanner.nextLine();
        }

        System.out.print("New Address: ");
        String address = scanner.nextLine();

        ownerService.updateOwner(ownerId, name, email, phone, address);
    }

    public  void removeOwner() {
        System.out.println("\n--- Remove Owner ---");
        DisplayHelper.displayOwners(ownerService.getAllOwners());
        System.out.print("\nEnter Owner ID to remove: ");
        int ownerId = Integer.parseInt(scanner.nextLine());
        System.out.print("Are you sure? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            ownerService.removeOwner(ownerId);
        }
    }

    public void assignSite() {
        System.out.println("\n--- Assign Site to Owner ---");
        DisplayHelper.displayOwners(ownerService.getAllOwners());
        System.out.print("\nEnter Owner ID: ");
        int ownerId = Integer.parseInt(scanner.nextLine());

        DisplayHelper.displaySites(siteService.getAllSites());
        System.out.print("\nEnter Site Number: ");
        int siteNumber = Integer.parseInt(scanner.nextLine());

        System.out.println("Site Type:");
        System.out.println("1. Villa");
        System.out.println("2. Apartment");
        System.out.println("3. Independent House");
        System.out.print("Choice: ");
        String typeChoice = scanner.nextLine();
        String siteType = typeChoice.equals("1") ? "Villa" : typeChoice.equals("2") ? "Apartment" : "Independent House";

        System.out.println("Status:");
        System.out.println("1. Under Construction");
        System.out.println("2. Built");
        System.out.print("Choice: ");
        String statusChoice = scanner.nextLine();
        String status = statusChoice.equals("2") ? "Built" : "Under Construction";

        siteService.assignSiteToOwner(siteNumber, ownerId, siteType, status);
    }

    public void updateSiteStatus() {
        System.out.println("\n--- Update Site Status ---");
        DisplayHelper.displaySites(siteService.getAllSites());
        System.out.print("\nEnter Site Number: ");
        int siteNumber = Integer.parseInt(scanner.nextLine());

        System.out.println("New Status:");
        System.out.println("1. Open");
        System.out.println("2. Under Construction");
        System.out.println("3. Built");
        System.out.print("Choice: ");
        String choice = scanner.nextLine();

        String status = choice.equals("1") ? "Open"
                : choice.equals("2") ? "Under Construction" : choice.equals("3") ? "Built" : null;

        if (status != null) {
            siteService.updateSiteStatus(siteNumber, status);
        } else {
            System.out.println("Invalid choice!");
        }
    }

    public void collectMaintenance(User admin) {
        System.out.println("\n--- Collect Maintenance ---");
        DisplayHelper.displaySites(siteService.getAllSites());
        System.out.print("\nEnter Site Number: ");
        int siteNumber = Integer.parseInt(scanner.nextLine());
        System.out.print("Payment Month (YYYY-MM): ");
        String month = scanner.nextLine();
        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.print("Notes (optional): ");
        String notes = scanner.nextLine();

        maintenanceService.collectMaintenance(siteNumber, month, amount, admin.getUserId(), notes);
    }

    public  void viewPendingMaintenance() {
        System.out.print("\nEnter Month (YYYY-MM): ");
        String month = scanner.nextLine();
        DisplayHelper.displayPendingSites(maintenanceService.getPendingSites(month), month);
    }

    public void processApproval(User admin) {
        System.out.println("\n--- Process Approval Request ---");
        DisplayHelper.displayPendingApprovals(approvalService.getPendingApprovals());
        System.out.print("\nEnter Request ID: ");
        int requestId = Integer.parseInt(scanner.nextLine());
        System.out.print("Approve? (y/n): ");
        boolean approve = scanner.nextLine().equalsIgnoreCase("y");
        System.out.print("Admin Notes: ");
        String notes = scanner.nextLine();

        approvalService.processApproval(requestId, approve, admin.getUserId(), notes);
    }
    
}
