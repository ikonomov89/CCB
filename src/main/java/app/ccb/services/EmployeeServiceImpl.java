package app.ccb.services;

import app.ccb.domain.dtos.EmployeeDto;
import app.ccb.domain.entities.Branch;
import app.ccb.domain.entities.Employee;
import app.ccb.repositories.BranchRepository;
import app.ccb.repositories.EmployeeRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final String EMPLOYEE_JSON_FILE_PATH = "D:\\GKI\\SoftUni\\Java\\Java Projects\\ColonialCouncilBank\\src\\main\\resources\\files\\json\\employees.json";

    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, BranchRepository branchRepository, FileUtil fileUtil, ValidationUtil validationUtil, Gson gson, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.branchRepository = branchRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }


    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() != 0;

    }

    @Override
    public String readEmployeesJsonFile() throws IOException {
        return this.fileUtil.readFile(EMPLOYEE_JSON_FILE_PATH);
    }

    @Override
    public String importEmployees(String employees)  {
        StringBuilder sb = new StringBuilder();

        EmployeeDto[] employeeDto = this.gson.fromJson(employees, EmployeeDto[].class);

        for (EmployeeDto dto : employeeDto) {
            Employee employee = this.modelMapper.map(dto, Employee.class);
            String[] fullName = dto.getFullName().split(" ");
            employee.setFirstName(fullName[0]);
            employee.setLastName(fullName[1]);

            Branch branch = this.branchRepository.findByName(dto.getBranchName());
            if (branch != null) {
                employee.setBranch(branch);
            }


            if (!this.validationUtil.isValid(employee)) {
                sb.append("Error: Incorrect Data!").append(System.lineSeparator());
            }

            this.employeeRepository.saveAndFlush(employee);
            sb.append(String.format("Successfully imported Employee - %s %s.", employee.getFirstName(), employee.getLastName())).append(System.lineSeparator());
        }

        return sb.toString();
    }

    @Override
    public String exportTopEmployees() {
        // TODO : Implement Me
        return null;
    }
}
