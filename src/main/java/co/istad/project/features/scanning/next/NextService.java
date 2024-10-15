package co.istad.project.features.scanning.next;

public interface NextService {

    String nextScanning(String gitUrl, String branch, String projectName) throws Exception;

    String newNextScanner(String gitUrl, String branch, String projectName) throws Exception;

}
