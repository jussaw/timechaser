package com.timechaser.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.timechaser.dto.RoleDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Role extends Auditable{
	@Id
	@GeneratedValue(strategy  = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	public Role(RoleDto roleDto) {
		this.id = roleDto.getId();
		this.name = roleDto.getName();
	}
}
