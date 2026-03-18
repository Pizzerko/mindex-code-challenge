package com.mindex.challenge.data;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

public class Compensation {
    @Id
    private String compensationId; // unique identifier for compensation record, allows for multiple compensation records for the same employee over time
    private String employeeId;
    private Double salary;
    private Double bonus;
    private LocalDate effectiveDate;
    
    public Compensation() {
    }

    public String getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
