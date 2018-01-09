package com.openbid.projectspace.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openbid.projectspace.repository.BuyerRepository;
import com.openbid.projectspace.repository.ProjectRepository;
import com.openbid.projectspace.repository.SellerRepository;
import com.openbid.projectspace.rest.constant.RequestConstants;
import com.openbid.projectspace.rest.exception.ResourceNotFoundException;
import com.openbid.projectspace.rest.exception.IllegalArgumentException;
import com.openbid.projectspace.rest.resource.BuyerResource;
import com.openbid.projectspace.rest.resource.ProjectResource;
import com.openbid.projectspace.rest.resource.Resource;
import com.openbid.projectspace.rest.resource.SellerResource;

/**
 * Main Controller for orchestrating all access to the REST services
 * @author Anand Raju
 */
@RestController
public class ProjectController implements RequestConstants {

	private static final Logger log = LoggerFactory.getLogger(ProjectController.class);
	private ProjectRepository projectRepository;
	private SellerRepository sellerRepository;
	private BuyerRepository buyerRepository;


	/**
	 * API to get projects that are available
	 * API allows the GET action to get a project by id (or) get all projects (or) get only open projects
	 * @param id - id for the project being request (optional)
	 * @param open - true/false to filter only projects that are open in the result (optional)
	 * @return
	 */
	@RequestMapping(value = "/projects", method = RequestMethod.GET)
	public List<Resource> getProjects(@RequestParam(value = "id", defaultValue = "") String id,
			@RequestParam(value = "openProjectsOnly", defaultValue = "true") String open) {

		List<Resource> projects = new ArrayList<Resource>();

		Resource project = null;

		projectRepository = ProjectRepository.getSoleInstance();

		boolean openProjectsOnly = Boolean.valueOf(open);

		// Checking id if a specific project is being requested
		if (!id.isEmpty()) {
			project = projectRepository.findById(id);
			if (project == null) {
				throw new ResourceNotFoundException("Project with id " + id + " could not be found");
			}
			projects.add(project);
		} else {
			if (openProjectsOnly) {
				projects = projectRepository.getAllActiveProjects();
			} else {
				projects = projectRepository.getAll();
			}
		}

		return projects;
	}

	/**
	 * API to create a project
	 * API allows the POST action to submit a project for auction
	 * @param requirement - Requirement for the project.
	 * @param maxBudget - Maximum budget to bid for the project. 
	 * @param endTime - End time to stop accepting bids for the project
	 * @param sellerId - seller's Id who is creating the project
	 * @return
	 */
	@RequestMapping(value = "/projects", method = RequestMethod.POST)
	public Resource createProject(@RequestParam(value = "requirement", defaultValue = "") String requirement,
			@RequestParam(value = "maxBudget", defaultValue = "") String maxBudget,
			@RequestParam(value = "endTime", defaultValue = _0_0) String endTime,
			@RequestParam(value = "sellerId", defaultValue = "") String sellerId) {

		projectRepository = ProjectRepository.getSoleInstance();
		sellerRepository = SellerRepository.getSoleInstance();
		
		double maximumBudget = validateAndParseDouble(maxBudget);
		
		Resource seller;
		if (sellerId.isEmpty()) {
			seller = new SellerResource();
			sellerRepository.add(seller);
		} else {
			seller = sellerRepository.findById(sellerId);
			if (seller == null) {
				log.debug("Seller not found: ", sellerId);
				throw new ResourceNotFoundException("Seller with id " + sellerId + " could not be found");
			}
		}

		Resource project = new ProjectResource(requirement, maximumBudget, endTime, seller.getId());
		projectRepository.add(project, false);
		return project;
	}

	/**
	 * API to bid for a project
	 * API allows the POST action to submit a bid
	 * @param buyerId - the buyer who is making the bid
	 * @param projectId - the project for which the bid is being made
	 * @param bidAmount - the amount to bid for
	 * @return true/false based whether the bid was accepted
	 */
	@RequestMapping(value = "/bid", method = RequestMethod.POST)
	public boolean bid(@RequestParam(value = "buyerId", defaultValue = "") String buyerId,
			@RequestParam(value = "projectId", defaultValue = "") String projectId,
			@RequestParam(value = "bidAmount", defaultValue = "") String bidAmount) {

		projectRepository = ProjectRepository.getSoleInstance();
		buyerRepository = BuyerRepository.getSoleInstance();
		
		double biddingAmount = validateAndParseDouble(bidAmount);
		
		Resource buyer;
		if (buyerId.isEmpty()) {
			buyer = new BuyerResource();
			buyerRepository.add(buyer);
		} else {
			buyer = (BuyerResource) buyerRepository.findById(buyerId);
			if (buyer == null) {
				log.debug("Buyer not found: ", buyerId);
				throw new ResourceNotFoundException("Buyer with id " + buyerId + " could not be found");
			}
		}

		Resource project;
		if (projectId.isEmpty()) {
			log.debug("projectId parameter has to provided");
			throw new IllegalArgumentException("Project id cannot be empty");
		} else {
			project = projectRepository.findById(projectId);
			if (project == null) {
				log.debug("Project not found: ", projectId);
				throw new ResourceNotFoundException("Project with id " + projectId + " could not be found");
			}
		}
		return ((ProjectResource) project).bid(biddingAmount, buyer.getId());
	}

	/**
	 * @param Amount
	 * @return
	 */
	private double validateAndParseDouble(String Amount) {
		double biddingAmount=0.0;
		if(Amount.isEmpty()) {
			throw new IllegalArgumentException("Amount cannot be empty");
		}
		try {
			biddingAmount = Double.parseDouble(Amount);
		}catch(NumberFormatException e) {
			throw new IllegalArgumentException("Not a valid Amount");
		}
		if(biddingAmount <= 0) {
			throw new IllegalArgumentException("Amount has to be a positive value");
		}
		return biddingAmount;
	}
}
