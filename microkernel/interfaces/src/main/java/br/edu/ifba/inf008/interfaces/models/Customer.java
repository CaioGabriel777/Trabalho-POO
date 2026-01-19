package br.edu.ifba.inf008.interfaces.models;

public class Customer {
    private int customerId;
    private String customerType;
    private String firstName;
    private String lastName;
    private String companyName;
    private String email;
    private String phone;

    public Customer() {
    }

    public Customer(int customerId, String customerType, String firstName,
            String lastName, String companyName, String email, String phone) {
        this.customerId = customerId;
        this.customerType = customerType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
        this.email = email;
        this.phone = phone;
    }

    // Getters
    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerType() {
        return customerType;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    // Setters
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDisplayName() {
        if (companyName != null && !companyName.isEmpty()) {
            return companyName;
        }
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return email;
    }
}
