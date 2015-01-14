package com.wstester.persistance;

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
	public Response getLastResponseForStepId(String stepId) {
    	
    	Response response = null;
    	try {
    		response = entityManager.createNamedQuery("getLastByStepId", Response.class).setParameter("stepId", stepId).getSingleResult();
    	} catch (NoResultException e) {
    		// Do nothing and return null
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
}
