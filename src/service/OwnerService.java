package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.OwnerDAO;
import dao.SiteDAO;
import dao.UserDAO;
import dao.impl.OwnerDAOImpl;
import dao.impl.SiteDAOImpl;
import dao.impl.UserDAOImpl;
import database.DatabaseConnection;
import model.Owner;
import model.Site;

public class OwnerService {
    private UserDAO userDAO;
    private OwnerDAO ownerDAO;
    private SiteDAO siteDAO;

    public OwnerService() {
        this.userDAO = new UserDAOImpl();
        this.ownerDAO = new OwnerDAOImpl();
        this.siteDAO = new SiteDAOImpl();
    }

    public boolean addOwner(String name, String email, String phone, String address, String username, String password) {
        Connection conn = DatabaseConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            // Create user
            int userId = userDAO.createUser(username, password, "OWNER");
            if (userId == -1) {
                conn.rollback();
                return false;
            }

            // Create owner
            Owner owner = new Owner();
            owner.setUserId(userId);
            owner.setName(name);
            owner.setEmail(email);
            owner.setPhone(phone);
            owner.setAddress(address);

            int ownerId = ownerDAO.createOwner(owner);
            if (ownerId == -1) {
                conn.rollback();
                return false;
            }

            conn.commit();
            System.out.println("Owner added successfully!");
            return true;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.err.println("Error in addOwner: " + e.getMessage());
            return false;
        }
    }

    public boolean updateOwner(int ownerId, String name, String email, String phone, String address) {
        Owner owner = ownerDAO.findById(ownerId);
        if (owner == null) {
            System.out.println("Owner not found!");
            return false;
        }

        owner.setName(name);
        owner.setEmail(email);
        owner.setPhone(phone);
        owner.setAddress(address);

        if (ownerDAO.updateOwner(owner)) {
            System.out.println("Owner updated successfully!");
            return true;
        }
        return false;
    }

    public boolean removeOwner(int ownerId) {
        Connection conn = DatabaseConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            // Unassign all sites
            List<Site> sites = siteDAO.findByOwnerId(ownerId);
            for (Site site : sites) {
                site.setOwnerId(null);
                site.setStatus("Open");
                site.setMaintenanceRate(6.00);
                siteDAO.updateSite(site);
            }

            // Delete owner
            if (ownerDAO.deleteOwner(ownerId)) {
                conn.commit();
                System.out.println("Owner removed successfully!");
                return true;
            }

            conn.rollback();
            return false;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.err.println("Error removing owner: " + e.getMessage());
            return false;
        }
    }

    public List<Owner> getAllOwners() {
        return ownerDAO.findAll();
    }

    public Owner getOwnerByUserId(int userId) {
        return ownerDAO.findByUserId(userId);
    }
}

