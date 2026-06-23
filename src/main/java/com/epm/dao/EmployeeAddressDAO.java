package com.epm.dao;

import com.epm.model.EmployeeAddress;
import com.epm.util.DBConnection;
import java.sql.*;
import java.util.Optional;

public class EmployeeAddressDAO {

    private EmployeeAddress mapRow(ResultSet rs) throws SQLException {
        EmployeeAddress a = new EmployeeAddress();
        a.setAddressId(rs.getInt("address_id"));
        a.setEmployeeId(rs.getInt("employee_id"));
        a.setCity(rs.getString("city"));
        a.setState(rs.getString("state"));
        a.setPincode(rs.getString("pincode"));
        return a;
    }

    public void addAddress(EmployeeAddress addr) throws SQLException {
        String sql = "INSERT INTO EmployeeAddress (employee_id, city, state, pincode) VALUES (?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, addr.getEmployeeId());
            ps.setString(2, addr.getCity());
            ps.setString(3, addr.getState());
            ps.setString(4, addr.getPincode());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) addr.setAddressId(keys.getInt(1));
            }
        }
    }

    public Optional<EmployeeAddress> getAddressByEmployeeId(int empId) throws SQLException {
        String sql = "SELECT * FROM EmployeeAddress WHERE employee_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        }
        return Optional.empty();
    }

    public boolean updateAddress(EmployeeAddress addr) throws SQLException {
        String sql = "UPDATE EmployeeAddress SET city=?, state=?, pincode=? WHERE employee_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, addr.getCity());
            ps.setString(2, addr.getState());
            ps.setString(3, addr.getPincode());
            ps.setInt(4, addr.getEmployeeId());
            return ps.executeUpdate() > 0;
        }
    }
}
