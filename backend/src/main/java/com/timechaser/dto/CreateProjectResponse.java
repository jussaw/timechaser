package com.timechaser.dto;

import com.timechaser.entity.Project;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectResponse {
    private Long id;
    private String name;

    public CreateProjectResponse(Project project) {
        this.id = project.getId();
        this.name = project.getName();
    }
}
