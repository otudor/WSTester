package com.wstester.model;

import java.util.Date;

// This class is used in the StepManager to know when a step has been last run
public class StepExecution {

	private Step step;
	private Date lastRun;
	
	public StepExecution(Step step, Date date) {
		this.step = step;
		this.lastRun = date;
	}
	
	public Step getStep() {
		return step;
	}
	public void setStep(Step step) {
		this.step = step;
	}
	public Date getLastRun() {
		return lastRun;
	}
	public void setLastRun(Date lastRun) {
		this.lastRun = lastRun;
	}
}
