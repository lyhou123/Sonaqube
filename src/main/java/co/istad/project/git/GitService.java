package co.istad.project.git;

import co.istad.project.git.dto.GitRespone;

public interface GitService {

    /**
     * Clone a git repository
     *
     */

   GitRespone getGitBranchAndOwnder(String repoUrl);


}
