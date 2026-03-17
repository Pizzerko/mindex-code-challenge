package com.mindex.challenge.service;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;

public interface EmployeeService {
    Employee create(Employee employee);
    Employee read(String id);
    Employee update(Employee employee);
    ReportingStructure readReportingStructure(String id); // added this method to the EmployeeService interface to be used by the ReportingStructureController, returns the fully filled out ReportingStructure object
}
