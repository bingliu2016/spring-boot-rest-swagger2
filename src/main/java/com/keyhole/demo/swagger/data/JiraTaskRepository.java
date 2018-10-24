package com.keyhole.demo.swagger.data;

import org.springframework.data.repository.CrudRepository;

public interface JiraTaskRepository extends CrudRepository<JiraTask, Integer>{

	Iterable<JiraTask> findByBlocker(Boolean isBlocker);
}
