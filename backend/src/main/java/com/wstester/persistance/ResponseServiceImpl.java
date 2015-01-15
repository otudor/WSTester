package com.wstester.persistance;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wstester.model.Response;

@Service
public class ResponseServiceImpl implements ResponseService {

	@Autowired
	private ResponseDao responseDao;
	
	@Override
	@Transactional
	public Response getLastResponseForStepId(String stepId, Date runDate) {
		return responseDao.getLastResponseForStepId(stepId, runDate);
	}

	@Override
	public List<Response> getAllResponsesForStepId(String stepId) {
		return responseDao.getAllResponsesForStepId(stepId);
	}

	@Override
	@Transactional
	public void persistResponse(Response response) {
		responseDao.persistResponse(response);
	}

}
