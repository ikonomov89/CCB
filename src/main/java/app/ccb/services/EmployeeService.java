package app.ccb.services;

import java.io.IOException;
import java.text.ParseException;

public interface EmployeeService {

    Boolean employeesAreImported();

    String readEmployeesJsonFile() throws IOException;

    String importEmployees(String employees) throws ParseException;

    String exportTopEmployees();
}
