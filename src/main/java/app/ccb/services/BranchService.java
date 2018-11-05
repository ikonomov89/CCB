package app.ccb.services;

import java.io.IOException;

public interface BranchService {

    String readBranchesJsonFile() throws IOException;

    String importBranches(String branchesJson);
}
