package app.ccb.web.controllers;

import app.ccb.services.BranchesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class JsonController extends BaseController {

    private final BranchesService branchesService;

    public JsonController(BranchesService branchesService) {
        this.branchesService = branchesService;
    }

    @GetMapping("/import/json")
    public ModelAndView importJson() {
        return super.view("json/import-json");
    }

    @GetMapping("/import/branches")
    public ModelAndView importBranches() throws IOException {
        String branchesJsonFile = this.branchesService.readBranchesJsonFile();
        return super.view("json/import-branches", "branches", branchesJsonFile);
    }

    @PostMapping(value = "/import/branches")
    public ModelAndView importBranchesConfirm(@RequestParam(required = false, name = "branches") String branches, RedirectAttributes redirectAttributes) {
        String result = this.branchesService.importBranches(branches);
        System.out.println(result);

        return super.redirect("/");
    }


}
