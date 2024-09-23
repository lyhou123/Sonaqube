package co.istad.project.features.project;

import co.istad.project.domain.Project;
import co.istad.project.domain.User;
import co.istad.project.features.project.dto.ProjectRequest;
import co.istad.project.features.project.dto.ProjectResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface ProjectMapper {

     User mapToProjectResponse(ProjectRequest projectRequest);

     ProjectResponse mapToProjectResponse(Project project);

}
