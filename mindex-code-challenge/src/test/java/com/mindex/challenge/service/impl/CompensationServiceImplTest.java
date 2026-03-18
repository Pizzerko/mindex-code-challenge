package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {
    
    private static final String TEST_EMPLOYEE_ID = "16a596ae-edd3-4847-99fe-c4518e82c86f";

    private String compensationPostUrl;
    private String compensationAllUrl;
    private String compensationLatestUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationPostUrl = "http://localhost:" + port + "/employee/compensations";
        compensationAllUrl = "http://localhost:" + port + "/employee/{employeeId}/compensations/all";
        compensationLatestUrl = "http://localhost:" + port + "/employee/{employeeId}/compensations/latest";
    }

    @Test
    public void testCreateReadAll() {
        Compensation testCompensation = new Compensation();
        testCompensation.setEmployeeId(TEST_EMPLOYEE_ID);
        testCompensation.setSalary(100000.00);
        testCompensation.setBonus(10000.00);
        testCompensation.setEffectiveDate(LocalDate.now());

        Compensation testCompensation2 = new Compensation();
        testCompensation2.setEmployeeId(TEST_EMPLOYEE_ID);
        testCompensation2.setSalary(110000.00);
        testCompensation2.setBonus(12000.00);
        testCompensation2.setEffectiveDate(LocalDate.of(2026, 1, 1));

        // Create checks
        Compensation createdCompensation1 =
                restTemplate.postForEntity(compensationPostUrl, testCompensation, Compensation.class).getBody();

        Compensation createdCompensation2 =
                restTemplate.postForEntity(compensationPostUrl, testCompensation2, Compensation.class).getBody();

        assertNotNull(createdCompensation1.getEmployeeId());
        assertCompensationEquivalence(testCompensation, createdCompensation1);

        assertNotNull(createdCompensation2.getEmployeeId());
        assertCompensationEquivalence(testCompensation2, createdCompensation2);

        // Read all check
        Compensation[] allCompensations = restTemplate.getForEntity(compensationAllUrl, Compensation[].class, TEST_EMPLOYEE_ID).getBody();        
        assertNotNull(allCompensations);
        assertTrue(allCompensations.length >= 2); // checks for presence of at least the two compensations we just created, there may be more if other tests are run before this one

        boolean foundFirst = false;
        boolean foundSecond = false;

        for (Compensation compensation : allCompensations) { // checks to see if the specific compensations we created are in the list of all compensations for the employee
            if (compensation.getSalary().equals(createdCompensation1.getSalary())
                    && compensation.getBonus().equals(createdCompensation1.getBonus())
                    && compensation.getEffectiveDate().equals(createdCompensation1.getEffectiveDate())) {
                foundFirst = true;
            }

            if (compensation.getSalary().equals(createdCompensation2.getSalary())
                    && compensation.getBonus().equals(createdCompensation2.getBonus())
                    && compensation.getEffectiveDate().equals(createdCompensation2.getEffectiveDate())) {
                foundSecond = true;
            }
        }

        assertTrue(foundFirst);
        assertTrue(foundSecond);
        
    }    

    @Test
    public void testReadLatest() {
        Compensation olderCompensation = new Compensation();
        olderCompensation.setEmployeeId(TEST_EMPLOYEE_ID);
        olderCompensation.setSalary(100000.00);
        olderCompensation.setBonus(10000.00);
        olderCompensation.setEffectiveDate(LocalDate.of(2025, 1, 1));

        Compensation latestCompensation = new Compensation();
        latestCompensation.setEmployeeId(TEST_EMPLOYEE_ID);
        latestCompensation.setSalary(120000.00);
        latestCompensation.setBonus(15000.00);
        latestCompensation.setEffectiveDate(LocalDate.of(2026, 1, 1));

        // Create checks
        Compensation createdCompensation1 =
                restTemplate.postForEntity(compensationPostUrl, olderCompensation, Compensation.class).getBody();

        Compensation createdCompensation2 =
                restTemplate.postForEntity(compensationPostUrl, latestCompensation, Compensation.class).getBody();

        assertNotNull(createdCompensation1.getEmployeeId());
        assertCompensationEquivalence(olderCompensation, createdCompensation1);

        assertNotNull(createdCompensation2.getEmployeeId());
        assertCompensationEquivalence(latestCompensation, createdCompensation2);

        // Read latest check
        Compensation latestComp = restTemplate.getForEntity(compensationLatestUrl, Compensation.class, TEST_EMPLOYEE_ID).getBody();        
        assertNotNull(latestComp);
        assertCompensationEquivalence(latestCompensation, latestComp);
    }

    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getEmployeeId(), actual.getEmployeeId());
        assertEquals(expected.getSalary(), actual.getSalary(), 0.001);
        assertEquals(expected.getBonus(), actual.getBonus(), 0.001);
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }


}
