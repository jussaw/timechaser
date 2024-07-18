package com.timechaser.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SQLDelete(sql = "UPDATE timesheet_entry SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetEntry extends Auditable{
	@Id
	@GeneratedValue(strategy  = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Long timesheetId;
	@Column(nullable = false)
	private LocalDate date;
	@Column(scale=2)
	private BigDecimal hoursWorked;
	@ManyToMany
	@JoinTable(
	    name = "timesheet_entry_project",
	    joinColumns = @JoinColumn(name = "timesheet_entry_id", referencedColumnName = "id"),
	    inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id")
	)
	private Set<Project> projects;
}
