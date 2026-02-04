package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.OwnerDAO;
import database.DatabaseConnection;
import model.Owner;

public class OwnerDAOImpl implements OwnerDAO {
    private Connection conn;

    public OwnerDAOImpl() {
        this.conn = DatabaseConnection.getConnection();
    }

    @Override
    public int createOwner(Owner owner) {
        String sql = "INSERT INTO owners (user_id, name, email, phone, address) VALUES (?, ?, ?, ?, ?) RETURNING owner_id";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, owner.getUserId());
            stmt.setString(2, owner.getName());
            stmt.setString(3, owner.getEmail());
            stmt.setString(4, owner.getPhone());
            stmt.setString(5, owner.getAddress());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("owner_id");
            }
        } catch (SQLException e) {
            System.err.println("Error creating owner: " + e.getMessage());
        }
        return -1;
    }

    @Override
    public boolean updateOwner(Owner owner) {
        String sql = "UPDATE owners SET name = ?, email = ?, phone = ?, address = ? WHERE owner_id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, owner.getName());
            stmt.setString(2, owner.getEmail());
            stmt.setString(3, owner.getPhone());
            stmt.setString(4, owner.getAddress());
            stmt.setInt(5, owner.getOwnerId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating owner: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteOwner(int ownerId) {
        String sql = "DELETE FROM owners WHERE owner_id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ownerId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting owner: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Owner findById(int ownerId) {
        String sql = "SELECT * FROM owners WHERE owner_id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ownerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Owner owner = new Owner();
                owner.setOwnerId(rs.getInt("owner_id"));
                owner.setUserId(rs.getInt("user_id"));
                owner.setName(rs.getString("name"));
                owner.setEmail(rs.getString("email"));
                owner.setPhone(rs.getString("phone"));
                owner.setAddress(rs.getString("address"));
                return owner;
            }
        } catch (SQLException e) {
            System.err.println("Error finding owner: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Owner findByUserId(int userId) {
        String sql = "SELECT * FROM owners WHERE user_id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Owner owner = new Owner();
                owner.setOwnerId(rs.getInt("owner_id"));
                owner.setUserId(rs.getInt("user_id"));
                owner.setName(rs.getString("name"));
                owner.setEmail(rs.getString("email"));
                owner.setPhone(rs.getString("phone"));
                owner.setAddress(rs.getString("address"));
                return owner;
            }
        } catch (SQLException e) {
            System.err.println("Error finding owner by user ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Owner> findAll() {
        List<Owner> owners = new ArrayList<>();
        String sql = "SELECT o.*, u.username, COUNT(s.site_id) as site_count " +
                "FROM owners o " +
                "JOIN users u ON o.user_id = u.user_id " +
                "LEFT JOIN sites s ON o.owner_id = s.owner_id " +
                "GROUP BY o.owner_id, o.user_id, o.name, o.email, o.phone, o.address, u.username " +
                "ORDER BY o.owner_id";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Owner owner = new Owner();
                owner.setOwnerId(rs.getInt("owner_id"));
                owner.setUserId(rs.getInt("user_id"));
                owner.setName(rs.getString("name"));
                owner.setEmail(rs.getString("email"));
                owner.setPhone(rs.getString("phone"));
                owner.setAddress(rs.getString("address"));
                owners.add(owner);
            }
        } catch (SQLException e) {
            System.err.println("Error finding all owners: " + e.getMessage());
        }
        return owners;
    }
}
