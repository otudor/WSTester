package com.wstester.persistance;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.wstester.log.CustomLogger;
import com.wstester.model.Response;

@Repository
public class ResponseDaoImpl implements ResponseDao {
	
	private final CustomLogger log = new CustomLogger(ResponseDaoImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
    
	@Override
	public List<Response> getLastResponseListForStepId(String stepId, Date runDate) {
		
    	List<Response> responseList = entityManager.createNamedQuery("getLastByStepId", Response.class).setParameter("stepId", stepId).setParameter("runDate", runDate).getResultList();
    	if (responseList.size() == 0) {
    		// Do nothing and return null
    		log.info(stepId, "No response wes found for step");
    		return null;
    	}
		return responseList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Response> getAllResponsesForStepId(String stepId) {
		
		List<Response> responseList = entityManager.createNamedQuery("getAllByStepId", Response.class).setParameter("stepId", stepId).getResultList();
		return responseList;
	}

	@Override
	public void persistResponse(Response response) {

		entityManager.persist(response);
		log.info(response.getStepId(), "Persisted response: " + response);
	}

	@Override
	public int getNumberOfReceivedResponses(String stepId, Date runDate) {
    	try {
    		List<Response> responseList = entityManager.createNamedQuery("getLastByStepId", Response.class).setParameter("stepId", stepId).setParameter("runDate", runDate).getResultList();
    		if(responseList != null) {
    			return responseList.size();
    		}
    	} catch (NoResultException e) {
    		// Do nothing and return null
    		return 0;
    	}
    	return 0;
	}
}
