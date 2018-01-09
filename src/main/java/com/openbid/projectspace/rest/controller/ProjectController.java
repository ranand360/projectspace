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
import com.openbid.projectspace.rest.resource.BuyerResource;
import com.openbid.projectspace.rest.resource.ProjectResource;
import com.openbid.projectspace.rest.resource.Resource;
import com.openbid.projectspace.rest.resource.SellerResource;

@RestController
public class ProjectController implements RequestConstants{
	
	private static final Logger log = LoggerFactory.getLogger(ProjectController.class);
    private ProjectRepository projectRepository;
    private SellerRepository sellerRepository;
    private BuyerRepository buyerRepository;
    
	@RequestMapping(value= "/projects" , method= RequestMethod.GET)
    public List<Resource> getProjects(@RequestParam(value="id", defaultValue="") String id,
    		@RequestParam(value="openProjectsOnly", defaultValue="true") String open) {
		
		List<Resource> projects = new ArrayList<Resource>();
		
		Resource project = null;
		
		projectRepository = ProjectRepository.getSoleInstance();
		
		boolean openProjectsOnly = Boolean.getBoolean(open);
		
		//Checking id if a specific project is being requested
		if(!id.isEmpty()) {
			project = projectRepository.findById(id);
			projects.add(project);
		}else {
			if(openProjectsOnly) {
				projects = projectRepository.getAllActiveProjects();
			}else {
				projects = projectRepository.getAll();
			}
		}
     
		return projects;
    }
	
	@RequestMapping(value= "/projects", method= RequestMethod.POST)
    public Resource updateProject(
    		@RequestParam(value="requirement", defaultValue="") String requirement,
    		@RequestParam(value="maxBudget", defaultValue=_0_0) String maxBudget,
    		@RequestParam(value="endTime", defaultValue=_0_0) String endTime,
    		@RequestParam(value="sellerId", defaultValue="") String sellerId
    		) {
		
		projectRepository = ProjectRepository.getSoleInstance();
		sellerRepository = SellerRepository.getSoleInstance();
		//buyerRepository = BuyerRepository.getSoleInstance();
		
		
    	Resource seller;
    	if(sellerId.isEmpty()) {
    		seller = new SellerResource();
    		sellerRepository.add(seller);
    	}else {
    		seller = sellerRepository.findById(sellerId);
    		if(seller == null) {
    			log.debug("Seller not found: ", seller);
    			return null;
    		}
    	}
    	
    	Resource project = new ProjectResource(requirement, maxBudget, endTime, seller.getId());
    	projectRepository.add(project, false);
        return project;
    }	
	
	@RequestMapping(value= "/bid", method= RequestMethod.POST)
	public boolean bid(@RequestParam(value="buyerId", defaultValue="") String buyerId,
			@RequestParam(value="projectId", defaultValue="") String projectId,
			@RequestParam(value="bidAmount", defaultValue="") String bidAmount
			) {
		
		projectRepository = ProjectRepository.getSoleInstance();
		//sellerRepository = SellerRepository.getSoleInstance();
		buyerRepository = BuyerRepository.getSoleInstance();
		
		Resource buyer;
		if(buyerId.isEmpty()) {
			buyer = new BuyerResource();
			buyerRepository.add(buyer);
		}else {
			buyer = (BuyerResource) buyerRepository.findById(buyerId);
			if(buyer == null) {
    			log.debug("Buyer not found: ", buyer);
    			return false;
    		}
		}
		
		Resource project;
		if(projectId.isEmpty()) {
			log.debug("projectId parameter has to provided");
			return false;
		}else {
			project = projectRepository.findById(projectId);
			if(project == null) {
    			log.debug("Project not found: ", project);
    			return false;
    		}
		}
		
		return ((ProjectResource)project).bid(bidAmount, buyer.getId());
	}
}
