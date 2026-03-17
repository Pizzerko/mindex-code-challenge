
package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportingStructureController { // Controller for the ReportingStructure endpoint which will return the number of reports for a given employee
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/reportingStructure/{id}")
    public ReportingStructure read(@PathVariable String id) {
        LOG.debug("Received reporting structure read request for id [{}]", id);

        return employeeService.readReportingStructure(id);
    }
}

// separated the ReportingStructureController from the EmployeeController to keep code organized, follow single responsibility principle, simialr to how the data file is structured with separate Employee and ReportingStructure classes