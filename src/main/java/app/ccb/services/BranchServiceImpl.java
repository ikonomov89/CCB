package app.ccb.services;

import app.ccb.domain.dtos.BranchDto;
import app.ccb.domain.entities.Branch;
import app.ccb.repositories.BranchRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BranchServiceImpl implements BranchService {

    private static final String BRANCH_JSON_FILE_PATH = "D:\\GKI\\SoftUni\\Java\\Java Projects\\ColonialCouncilBank\\src\\main\\resources\\files\\json\\branches.json";

    private final BranchRepository branchRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.branchRepository = branchRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }


    @Override
    public Boolean branchesAreImported() {
        return this.branchRepository.count() != 0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        return this.fileUtil.readFile(BRANCH_JSON_FILE_PATH);
    }

    @Override
    public String importBranches(String branchesJson) {
        StringBuilder sb = new StringBuilder();

        BranchDto[] dtos = this.gson.fromJson(branchesJson, BranchDto[].class);

        for (BranchDto dto : dtos) {
            Branch branch = this.modelMapper.map(dto, Branch.class);
            if (!this.validationUtil.isValid(branch)) {
                sb.append("Error: Incorrect Data!").append(System.lineSeparator());
                continue;
            }


            this.branchRepository.saveAndFlush(branch);

            sb.append(String.format("Successfully imported Branch - %s.", branch.getName())).append(System.lineSeparator());
        }


        return sb.toString();
    }
}
