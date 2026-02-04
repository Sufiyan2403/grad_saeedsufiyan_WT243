package app;

import java.util.List;
import java.util.Scanner;

import database.DatabaseConnection;
import model.MaintenancePayment;
import model.Owner;
import model.Site;
import model.User;
import service.ApprovalService;
import service.AuthService;
import service.MaintenanceService;
import service.OwnerService;
import service.SiteService;
import utility.AdminOperations;
import utility.DisplayHelper;
import utility.OwnerOperations;

public class LayoutMaintenanceApp {
    private static Scanner scanner = new Scanner(System.in);
    private static AuthService authService = new AuthService();
    private static OwnerService ownerService = new OwnerService();
    private static SiteService siteService = new SiteService();
    private static MaintenanceService maintenanceService = new MaintenanceService();
    private static ApprovalService approvalService = new ApprovalService();

    private static AdminOperations opr1 = new AdminOperations();
    private static OwnerOperations opr2 = new OwnerOperations();

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   LAYOUT MAINTENANCE APPLICATION");
        System.out.println("========================================\n");

        // Test database connection
        if (DatabaseConnection.getConnection() == null) {
            System.err.println("Failed to connect to database. Exiting...");
            return;
        }

        // Login
        User currentUser = null;
        while (currentUser == null) {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            currentUser = authService.login(username, password);
            if (currentUser == null) {
                System.out.print("\nTry again? (y/n): ");
                if (!scanner.nextLine().equalsIgnoreCase("y")) {
                    System.out.println("Exiting...");
                    DatabaseConnection.closeConnection();
                    return;
                }
            }
        }

        System.out.println("\nWelcome, " + currentUser.getUsername() + "! (" + currentUser.getRole() + ")");

        
        if (currentUser.getRole().equals("ADMIN")) {
            adminMenu(currentUser);
        } else if (currentUser.getRole().equals("OWNER")) {
            ownerMenu(currentUser);
        }

        DatabaseConnection.closeConnection();
        scanner.close();
        System.out.println("\nThank you for using Layout Maintenance Application!");
    }

    private static void adminMenu(User admin) {
        boolean running = true;

        while (running) {
            System.out.println("\n========== ADMIN MENU ==========");
            System.out.println("1.  Add Owner");
            System.out.println("2.  Update Owner");
            System.out.println("3.  Remove Owner");
            System.out.println("4.  List All Owners");
            System.out.println("5.  Assign Site to Owner");
            System.out.println("6.  Update Site Status");
            System.out.println("7.  List All Sites");
            System.out.println("8.  Collect Maintenance");
            System.out.println("9.  View Pending Maintenance");
            System.out.println("10. View Pending Approvals");
            System.out.println("11. Process Approval Request");
            System.out.println("0.  Logout");
            System.out.print("\nChoice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    opr1.addOwner();
                    break;
                case "2":
                    opr1.updateOwner();
                    break;
                case "3":
                    opr1.removeOwner();
                    break;
                case "4":
                    DisplayHelper.displayOwners(ownerService.getAllOwners());
                    break;
                case "5":
                    opr1.assignSite();
                    break;
                case "6":
                    opr1.updateSiteStatus();
                    break;
                case "7":
                    DisplayHelper.displaySites(siteService.getAllSites());
                    break;
                case "8":
                    opr1.collectMaintenance(admin);
                    break;
                case "9":
                    opr1.viewPendingMaintenance();
                    break;
                case "10":
                    DisplayHelper.displayPendingApprovals(approvalService.getPendingApprovals());
                    break;
                case "11":
                    opr1.processApproval(admin);
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void ownerMenu(User owner) {
        boolean running = true;
        Owner ownerProfile = ownerService.getOwnerByUserId(owner.getUserId());

        if (ownerProfile == null) {
            System.out.println("Error: Owner profile not found!");
            return;
        }

        while (running) {
            System.out.println("\n========== OWNER MENU ==========");
            System.out.println("1. View My Sites");
            System.out.println("2. View Site Details");
            System.out.println("3. Request Status Update");
            System.out.println("4. View My Requests");
            System.out.println("0. Logout");
            System.out.print("\nChoice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    DisplayHelper.displayMySites(siteService.getSitesByOwner(ownerProfile.getOwnerId()));
                    break;
                case "2":
                    opr2.viewSiteDetails(ownerProfile.getOwnerId());
                    break;
                case "3":
                    opr2.requestStatusUpdate(owner, ownerProfile.getOwnerId());
                    break;
                case "4":
                    DisplayHelper.displayMyRequests(approvalService.getRequestsByOwner(ownerProfile.getOwnerId()));
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    /*Admin operations
    private static void addOwner() {
        System.out.println("\n--- Add New Owner ---");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Username (for login): ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        ownerService.addOwner(name, email, phone, address, username, password);
    }

    private static void updateOwner() {
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

    private static void removeOwner() {
        System.out.println("\n--- Remove Owner ---");
        DisplayHelper.displayOwners(ownerService.getAllOwners());
        System.out.print("\nEnter Owner ID to remove: ");
        int ownerId = Integer.parseInt(scanner.nextLine());
        System.out.print("Are you sure? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            ownerService.removeOwner(ownerId);
        }
    }

    private static void assignSite() {
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

    private static void updateSiteStatus() {
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

    private static void collectMaintenance(User admin) {
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

    private static void viewPendingMaintenance() {
        System.out.print("\nEnter Month (YYYY-MM): ");
        String month = scanner.nextLine();
        DisplayHelper.displayPendingSites(maintenanceService.getPendingSites(month), month);
    }

    private static void processApproval(User admin) {
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

    // Owner operations
    private static void viewSiteDetails(int ownerId) {
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

    private static void requestStatusUpdate(User owner, int ownerId) {
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
        */
}
