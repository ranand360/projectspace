/**
 * @author Anand Raju
 */
package com.openbid.projectspace.rest.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.openbid.projectspace.repository.ProjectRepository;

/**
 * Schedulable component to terminate expired project resources
 * @author Anand Raju
 */

@Component
public class ProjectTerminator {

	// Scheduled to run every second
	private static final int SCHEDULE_INTERVAL_MILLIS = 1000;

	@Scheduled(fixedRate = SCHEDULE_INTERVAL_MILLIS)
	public void runJob() {

		ProjectRepository.getSoleInstance().terminateExpiredProjects();

	}

}
