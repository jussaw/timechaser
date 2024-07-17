package com.timechaser.mapper;

import com.timechaser.dto.ProjectDto;
import com.timechaser.entity.Project;

public class ProjectMapper {
    public static Project toEntity(ProjectDto projectDto) {
        return Project.builder()
            .id(projectDto.getId())
            .name(projectDto.getName())
            .build();
    }

    public static ProjectDto toDto(Project project) {
        return ProjectDto.builder()
            .id(project.getId())
            .name(project.getName())
            .build();
    }
}
