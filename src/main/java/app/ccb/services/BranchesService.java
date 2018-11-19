package app.ccb.services;

import java.io.IOException;

public interface BranchesService {

    String readBranchesJsonFile() throws IOException;

    String importBranches(String branchesJson);
}
