package co.istad.project.features.project;

import co.istad.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

    Boolean existsByProjectName(String projectName);

    Optional<Project> findByProjectName(String projectName);

    @Transactional
    void deleteProjectByProjectName(String projectName);

}
