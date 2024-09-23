package co.istad.project.features.project;

import co.istad.project.features.project.dto.ProjectRequest;
import co.istad.project.features.project.dto.ProjectResponse;


import java.util.List;


public interface ProjectService {

    /**
     * Create a new project
     * @param projectRequest
     * @return ProjectResponse
     * @Author: Lyhou
     */
   ProjectResponse createProject(ProjectRequest projectRequest) throws Exception;

    /**
     * Get all project
     * @return List<ProjectResponse>
     *     @Author: Lyhou
     */
   List<ProjectResponse> getAllProject();


   /**
    * Get project by project name
    * @param projectName
    * @return ProjectResponse
    * @Author: Lyhou
    */
   ProjectResponse getProjectByProjectName(String projectName);


   /**
    * Delete project by project name
    * @param projectName
    * @return ProjectResponse
    * @Author: Lyhou
    */

   String deleteProjectByProjectName(String projectName);


}
