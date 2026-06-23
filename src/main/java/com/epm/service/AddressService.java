package com.epm.service;

import com.epm.dao.EmployeeAddressDAO;
import com.epm.model.EmployeeAddress;

import java.sql.SQLException;
import java.util.Optional;

public class AddressService {
    private final EmployeeAddressDAO dao = new EmployeeAddressDAO();

    public EmployeeAddress addAddress(int empId, String city, String state, String pincode) throws SQLException {
        EmployeeAddress a = new EmployeeAddress();
        a.setEmployeeId(empId);
        a.setCity(city.trim());
        a.setState(state.trim());
        a.setPincode(pincode.trim());
        dao.addAddress(a);
        return a;
    }

    public Optional<EmployeeAddress> getByEmployee(int empId) throws SQLException {
        return dao.getAddressByEmployeeId(empId);
    }

    public boolean updateAddress(int empId, String city, String state, String pincode) throws SQLException {
        EmployeeAddress a = new EmployeeAddress();
        a.setEmployeeId(empId);
        a.setCity(city.trim());
        a.setState(state.trim());
        a.setPincode(pincode.trim());
        return dao.updateAddress(a);
    }
}
