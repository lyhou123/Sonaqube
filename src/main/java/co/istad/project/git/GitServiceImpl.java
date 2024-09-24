package co.istad.project.git;

import co.istad.project.config.GitConfig;
import co.istad.project.git.dto.GitRespone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GitServiceImpl implements GitService {

    private final GitConfig gitConfig;
    @Override
    public GitRespone getGitBranchAndOwnder(String repoUrl) {
        return null;
    }
}
