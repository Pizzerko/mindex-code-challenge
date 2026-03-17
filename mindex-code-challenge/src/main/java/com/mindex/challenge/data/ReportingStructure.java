package com.mindex.challenge.data;

public class ReportingStructure { // made different from Employee class as it has different fields and is used for a different purpose
    private Employee employee;
    private int numberOfReports; // probably needs dfs/bfs to calculate this field

    public ReportingStructure() {
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getNumberOfReports() {
        return numberOfReports;
    }

    public void setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }
}
