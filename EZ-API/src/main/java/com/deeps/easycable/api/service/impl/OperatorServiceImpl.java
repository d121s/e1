package com.deeps.easycable.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deeps.easycable.api.entity.Operator;
import com.deeps.easycable.api.repo.OperatorRepo;
import com.deeps.easycable.api.request.OperatorRequest;
import com.deeps.easycable.api.response.ResponseStatus;
import com.deeps.easycable.api.response.ServiceResponse;
import com.deeps.easycable.api.service.OperatorServices;

@Service
public class OperatorServiceImpl implements OperatorServices {

	@Autowired
	OperatorRepo operatorRepo;

	private Operator setOperator(OperatorRequest request, Operator ops) {
		ops.setName(request.getName());
		ops.setSubscriptionType(request.getSubscriptionType());
		ops.setMaxUser(request.getMaxUser());
		ops.setSubscriptionStatus(request.getSubscriptionStatus());
		ops.setSubscriptionCost(request.getSubscriptionCost());
		return ops;
	}

	@Override
	public List<Operator> getOperatorDetails() {
		return operatorRepo.findAll();
	}

	@Override
	public Operator addOperator(OperatorRequest request) {
		return operatorRepo.save(setOperator(request, new Operator()));
	}

	@Override
	public Operator updateOperator(OperatorRequest request, long operatorId) {
		if (operatorRepo.existsById(operatorId)) {
			Operator opr = setOperator(request, operatorRepo.findById(operatorId));
			operatorRepo.save(opr);
			return opr;
		} else {
			return null;
		}

	}

	@Override
	public Operator getOperator(long operatorId) {
		return operatorRepo.findById(operatorId);
	}

	@Override
	public ServiceResponse deleteOperator(long operatorId) {
		operatorRepo.delete(operatorRepo.findById(operatorId));
		return new ServiceResponse(new ResponseStatus(200, "Delete Successfull"));
	}

}
