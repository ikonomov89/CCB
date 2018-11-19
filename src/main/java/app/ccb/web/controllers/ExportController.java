package app.ccb.web.controllers;

import app.ccb.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExportController extends BaseController {

    private final EmployeeService employeeService;

    @Autowired
    public ExportController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ModelAndView exportTopEmployees() {
        this.employeeService.exportTopEmployees();
        return super.view("export-top-employees");
    }
}
