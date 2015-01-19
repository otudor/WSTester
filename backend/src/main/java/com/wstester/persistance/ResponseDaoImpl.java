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
    
    @SuppressWarnings("unchecked")
	@Override
	public Response getLastResponseForStepId(String stepId, Date runDate) {
    	
    	Response response = null;
    	try {
    		response = entityManager.createNamedQuery("getLastByStepId", Response.class).setParameter("stepId", stepId).setParameter("runDate", runDate).getSingleResult();
    	} catch (NoResultException e) {
    		// Do nothing and return null
    		return null;
    	}
		return response;
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
	public Boolean hasStepFinished(String stepId, Date runDate) {
    	try {
    		Response response = entityManager.createNamedQuery("getLastByStepId", Response.class).setParameter("stepId", stepId).setParameter("runDate", runDate).getSingleResult();
    		if(response != null) {
    			return true;
    		}
    	} catch (NoResultException e) {
    		// Do nothing and return null
    		return false;
    	}
    	return false;
	}
}
