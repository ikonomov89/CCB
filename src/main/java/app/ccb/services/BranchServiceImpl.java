package app.ccb.services;

import app.ccb.domain.dtos.BranchImportDto;
import app.ccb.domain.entities.Branch;
import app.ccb.repositories.BranchRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BranchServiceImpl implements BranchService {

    private static final String BRANCHES_JSON_FILE_PATH = "D:\\Repositories\\BitBucket\\ColonialCouncilBank\\src\\main\\resources\\files\\json\\branches.json";

    private final BranchRepository branchRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public BranchServiceImpl(BranchRepository branchRepository, FileUtil fileUtil, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.branchRepository = branchRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        return this.fileUtil.readFile(BRANCHES_JSON_FILE_PATH);
    }

    @Override
    public String importBranches(String branchesJson) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        BranchImportDto[] branchImportDtos = gson.fromJson(branchesJson, BranchImportDto[].class);

        StringBuilder sb = new StringBuilder();
        for (BranchImportDto branchImportDto : branchImportDtos) {
            Branch branch = this.modelMapper.map(branchImportDto, Branch.class);

            if (this.validationUtil.isValid(branch)) {
                this.branchRepository.saveAndFlush(branch);
                sb.append(String.format("Successfully imported Branch - %s", branch.getName()))
                        .append(System.lineSeparator());
            } else {
                sb.append("Invalid Branch").append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }


}
