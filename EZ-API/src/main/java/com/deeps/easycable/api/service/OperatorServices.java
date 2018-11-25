package com.deeps.easycable.api.service;

import java.util.List;

import com.deeps.easycable.api.entity.Operator;
import com.deeps.easycable.api.request.OperatorRequest;
import com.deeps.easycable.api.response.ServiceResponse;

public interface OperatorServices {

	public List<Operator> getOperatorDetails();

	public Operator addOperator(OperatorRequest oprRequest);

	public Operator updateOperator(OperatorRequest request, long operatorId);

	public Operator getOperator(long operatorId);

	public ServiceResponse deleteOperator(long operatorId);

}
