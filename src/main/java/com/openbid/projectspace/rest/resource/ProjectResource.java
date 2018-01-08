package com.openbid.projectspace.rest.resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openbid.projectspace.rest.constant.RequestConstants;


/**
 * @author Anand Raju
 *
 */

public class ProjectResource implements RequestConstants{
	
	private static final int DEFAULT_EXPIRY_DAYS = 1;
    private static final double DEFAULT_MAX_BUDGET = 100000.0;
    private static final String PRJ = "prj-";
    
	private String id;
	private String requirement;
	private double maxBudget;
	private Date endTime;
	private boolean expired=false;
	private String sellerId;
	private String lowestBidderId;
	private double lowestBid;
	
	public ProjectResource(String requirement, String maxBudget, String endTime, String sellerId) {
		this.id=new String(PRJ+UUID.randomUUID().toString());
		
		this.requirement = requirement;
		
		this.maxBudget = DEFAULT_MAX_BUDGET;
		try {
			this.maxBudget = Double.parseDouble(maxBudget);
		}catch (NumberFormatException e) {
			System.out.println("Parse exception on maxBudget:"+maxBudget+ " Defaulting to " + DEFAULT_MAX_BUDGET);
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		Date currentTime = calendar.getTime();
	    if(endTime == null || endTime.equals(_0_0)) {
		    this.endTime = getDefaulEndTime();
	    } else {
	    	try {
	    		this.endTime = dateFormat.parse(endTime);
	    		if(currentTime.after(this.endTime)) {
	    			this.endTime = getDefaulEndTime();
	    		}
	    	} catch(ParseException e) {
	    		System.out.println("Parse Exception with endTime:"+endTime);
	    	}
	    }
	    
		this.sellerId = sellerId;
		
		this.lowestBid = this.maxBudget;
	}
	
	/**
	 * @return
	 */
	private Date getDefaulEndTime() {
		Calendar calendar = Calendar.getInstance();
    	// If endTime is not passed, set Endtime to 24h from now
    	calendar.add(Calendar.DAY_OF_YEAR, DEFAULT_EXPIRY_DAYS);
	    return calendar.getTime();
	}

	public boolean bid(String bidAmount, String buyerId) {
		boolean status = false;
		if(bidAmount == null || bidAmount.isEmpty()) {
			status = false;
		}else {
			status = bid(Double.parseDouble(bidAmount), buyerId);
		}
		return status;
	}
	
	protected boolean bid(double bidAmount, String buyerId) {
		boolean status = false;
		if(bidAmount == 0 || bidAmount >= this.lowestBid || this.expired) {
			status=false;
		}else {
			this.lowestBid = bidAmount;
			this.lowestBidderId = buyerId;
			status=true;
		}
		return status;
	}
		
	public String getId() {
		return id;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
	
	public double getMaxBudget() {
		return maxBudget;
	}

	public void setMaxBudget(double maxBudget) {
		this.maxBudget = maxBudget;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="US/Pacific")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public boolean isExpired() {
		return expired;
	}
	
	public void setExpired(boolean expired) {
		this.expired = expired;
	}
	
	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	
	@JsonIgnore
	public String getLowestBidderId() {
		return lowestBidderId;
	}
	
	@JsonIgnore
	public void setLowestBidderId(String lowestBidderId) {
		this.lowestBidderId = lowestBidderId;
	}

	public double getLowestBid() {
		return lowestBid;
	}

	public void setLowestBid(double lowestBid) {
		this.lowestBid = lowestBid;
	}
	 
}
