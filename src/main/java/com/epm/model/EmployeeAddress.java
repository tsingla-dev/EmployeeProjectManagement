package com.epm.model;

public class EmployeeAddress {
    private int    addressId;
    private int    employeeId;
    private String city;
    private String state;
    private String pincode;

    public EmployeeAddress() {}

    public int    getAddressId()  { return addressId; }
    public int    getEmployeeId() { return employeeId; }
    public String getCity()       { return city; }
    public String getState()      { return state; }
    public String getPincode()    { return pincode; }

    public void setAddressId(int addressId)    { this.addressId  = addressId; }
    public void setEmployeeId(int employeeId)  { this.employeeId = employeeId; }
    public void setCity(String city)           { this.city       = city; }
    public void setState(String state)         { this.state      = state; }
    public void setPincode(String pincode)     { this.pincode    = pincode; }

    @Override
    public String toString() {
        return String.format("| %-5d | %-5d | %-20s | %-20s | %-10s |",
                addressId, employeeId, city, state, pincode);
    }
}
