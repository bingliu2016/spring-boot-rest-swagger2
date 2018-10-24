package com.keyhole.demo.swagger.data;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "JIRATASK")
public class JiraTask implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "TASK_ID")
	private Integer taskId;

	@Column(name = "TASK_NAME")
	private String taskName;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "DUEDATE")
	private LocalDate dueDate;

	@Column(name = "BLOCKER")
	private Boolean blocker;

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Boolean getBlocker() {
		return blocker;
	}

	public void setBlocker(Boolean blocker) {
		this.blocker = blocker;
	}

}
