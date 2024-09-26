package co.istad.project.features.source;

import java.util.HashMap;

public interface SourceService {

    HashMap getCodeIssue(String issueKey) throws Exception;

}
