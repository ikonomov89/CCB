package app.ccb.web.controllers;

import app.ccb.services.BranchService;
import app.ccb.services.ClientService;
import app.ccb.services.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping("/import")
public class ImportController extends BaseController {

    private final BranchService branchService;
    private final EmployeeService employeeService;
    private final ClientService clientService;

    public ImportController(BranchService branchService, EmployeeService employeeService, ClientService clientService) {
        this.branchService = branchService;
        this.employeeService = employeeService;
        this.clientService = clientService;
    }

    @GetMapping("/json")
    public ModelAndView importJson() {
        return super.view("json/import-json");
    }

    @GetMapping("/branches")
    public ModelAndView importBranches() throws IOException {
        String branchesJsonFile = this.branchService.readBranchesJsonFile();
        return super.view("json/import-branches", "branches", branchesJsonFile);
    }

    @PostMapping(value = "/branches")
    public ModelAndView importBranchesConfirm(@RequestParam(required = false, name = "branches") String branches) {
        String importResult = this.branchService.importBranches(branches);
        System.out.println(importResult);

        return super.redirect("/");
    }

    @GetMapping("/employees")
    public ModelAndView importEmployees() throws IOException {
        String employeesJsonFile = this.employeeService.readEmployeesJsonFile();
        return super.view("json/import-employees", "employees", employeesJsonFile);
    }

    @PostMapping("/employees")
    public ModelAndView importEmployeesConfirm(@RequestParam(required = false, name = "employees") String employees) {
        String importResult = this.employeeService.importEmployees(employees);
        System.out.println(importResult);

        return super.redirect("/");
    }

    @GetMapping("/clients")
    public ModelAndView importClients() throws IOException {
        String clientJsonFile = this.clientService.readClientsJsonFile();
        return super.view("json/import-clients", "clients", clientJsonFile);
    }

    @PostMapping("/clients")
    public ModelAndView importClientsConfirm(@RequestParam(required = false, name = "clients") String clients) {
        String importResult = this.clientService.importClients(clients);
        System.out.println(importResult);

        return super.redirect("/");
    }


}
