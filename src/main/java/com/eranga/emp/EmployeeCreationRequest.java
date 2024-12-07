package com.eranga.emp;

import lombok.Getter;

@Getter
public class EmployeeCreationRequest {

    private String empName;

    private String empNumber;

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    @Override
    public String toString() {
        return "com.eranga.gatling.EmployeeCreationRequest{" + "employeename='" + empName + '\'' + '}';
    }

    public void setEmpNumber(String empNumber) {
        this.empNumber = empNumber;
    }
}
