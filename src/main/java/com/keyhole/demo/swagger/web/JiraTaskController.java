package com.keyhole.demo.swagger.web;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.keyhole.demo.swagger.data.Developer;
import com.keyhole.demo.swagger.data.DeveloperRepository;
import com.keyhole.demo.swagger.data.JiraTask;
import com.keyhole.demo.swagger.data.JiraTaskRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Agile Jira Tasks Services")
@RequestMapping(value = "jiraboard")
public class JiraTaskController {

	@Autowired
	private JiraTaskRepository jiraTaskRepository;

	@Autowired
	private DeveloperRepository developerRepository;

	@GetMapping("/developers")
	@ApiOperation(value = "Returns all developers with their Jira assignments")
	public Iterable<Developer> findAllDevelopers() {
		return developerRepository.findAll();
	}

	@GetMapping("/developer/{id}")
	@ApiOperation(value = "Returns a developer by Id")
	@ApiResponses({ @ApiResponse(code = 400, message = "Invalid request parameters supplied"),
			        @ApiResponse(code = 404, message = "Requested data not found") })
	public Optional<Developer> findDeveloperById(
			@ApiParam(value = "Input a whole number as Developer ID", example = "1") @PathVariable("id") Integer id) {
		return developerRepository.findById(id);
	}

	@GetMapping("/tasks")
	@ApiOperation(value = "Returns all Jira tasks created")
	public Iterable<JiraTask> findAllJiraTasks() {
		return jiraTaskRepository.findAll();
	}

	@GetMapping("/tasksStatus/{isBlocked}")
	@ApiOperation(value = "Find all Jira tasks status - if there is any blocker on it.")
	public Iterable<JiraTask> findJiraTasksBlockerStatus(
			@ApiParam(value = "Choose true/false from dropdown", example = "true") @PathVariable("isBlocked") Boolean isBlocked) {
		Iterable<JiraTask> allJiraTasks = jiraTaskRepository.findByBlocker(isBlocked);
		return allJiraTasks;
	}

	@PostMapping("/addJiraTask")
	@ApiOperation(value = "Add a new Jira task")
	@ApiResponses({ @ApiResponse(code = 400, message = "Invalid request parameters supplied"),
                    @ApiResponse(code = 404, message = "Requested data not found") })
	public void addNewJiraTask(
			@ApiParam(value = "A New Task ID", example = "1") @RequestParam(required = true) Integer taskId,
			@ApiParam(value = "A New Task Name", example = "taskName") @RequestParam(required = true) String taskName,
			@ApiParam(value = "A New Task Status", example = "status") @RequestParam(required = true) String status,
			@ApiParam(value = "A New Task Due Date in ISO Format", example = "2018-10-12") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate dueDate,
			@ApiParam(value = "If this Task Blocked", example = "true") @RequestParam(required = true) Boolean blocker) {
		JiraTask task = new JiraTask();
		task.setTaskId(taskId);
		task.setTaskName(taskName);
		task.setStatus(status);
		task.setDueDate(dueDate);
		task.setBlocker(blocker);

		jiraTaskRepository.save(task);
	}

	@PostMapping("/addDeveloper")
	@ApiOperation(value = "Add a new developer")
	public void addNewDeveloper(
			@ApiParam(value = "A new Developer ID", example = "1") @RequestParam(required = true) Integer developerId,
			@ApiParam(value = "A New Developer First Name", example = "firstName") @RequestParam(required = true) String firstName,
			@ApiParam(value = "A New Developer Last Name", example = "lastName") @RequestParam(required = true) String lastName,
			@ApiParam(value = "A New Developer Team Name", example = "teamName") @RequestParam(required = false) String teamName) {
		Developer developer = new Developer();
		developer.setDeveloperId(developerId);
		developer.setFirstName(firstName);
		developer.setLastName(lastName);
		developer.setTeamName(teamName);
		developer.setJiraTasks(null);

		developerRepository.save(developer);
	}

	@DeleteMapping("/deleteDeveloper/{id}")
	@ApiOperation(value = "Delete a developer")
	public void deleteDeveloper(
			@ApiParam(value = "Developer ID to delete", example = "1") @PathVariable("id") Integer id) {
		developerRepository.deleteById(id);
	}

	@DeleteMapping("/deleteTask/{id}")
	@ApiOperation(value = "Delete a task")
	public void deleteTask(@ApiParam(value = "Task ID to delete", example = "1") @PathVariable("id") Integer id) {
		jiraTaskRepository.deleteById(id);
	}
}
