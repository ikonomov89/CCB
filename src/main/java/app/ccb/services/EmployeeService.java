package app.ccb.services;

import java.io.IOException;

public interface EmployeeService {

    String readEmployeesJsonFile() throws IOException;

    String importEmployees(String employees);

    String exportTopEmployees();
}
