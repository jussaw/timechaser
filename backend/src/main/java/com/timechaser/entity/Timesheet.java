package com.timechaser.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

//import com.fasterxml.jackson.annotation.JsonIdentityInfo;
//import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.timechaser.enums.TimesheetStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@SQLDelete(sql = "UPDATE timesheet SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Timesheet extends Auditable {
	@Id
	@GeneratedValue(strategy  = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
	private User user;
	@Column(nullable = false)
	private Integer year;
	@Column(nullable = false)
	private Integer weekNumber;
	@Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TimesheetStatus status;

	@OneToMany(mappedBy = "timesheet", cascade = CascadeType.ALL)
    private List<TimesheetEntry> timesheetEntries;
}
