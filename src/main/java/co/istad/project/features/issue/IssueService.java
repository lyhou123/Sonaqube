package co.istad.project.features.issue;

public interface IssueService {

    Object getIssueByProjectName(String projectName) throws Exception;

    Object getIssueByIssueKey(String issueKey, String ruleKey) throws Exception;

}
