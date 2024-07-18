package com.timechaser.dto;

import javax.validation.constraints.NotBlank;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDto {
    private Long id;
    @NotBlank(message = "Project requires a name!")
    private String name;
}
