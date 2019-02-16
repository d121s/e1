package com.deeps.easycable.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deeps.easycable.api.ApiUtils;
import com.deeps.easycable.api.entity.Operator;
import com.deeps.easycable.api.exception.UnAuthorizedTenantException;
import com.deeps.easycable.api.repo.OperatorRepo;
import com.deeps.easycable.api.request.OperatorRequest;
import com.deeps.easycable.api.request.SubscriptionType;
import com.deeps.easycable.api.response.AuthResponse;
import com.deeps.easycable.api.response.ResponseStatus;
import com.deeps.easycable.api.response.ServiceResponse;
import com.deeps.easycable.api.service.OperatorServices;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OperatorServiceImpl implements OperatorServices {

	@Autowired
	ApiUtils apiUtils;
	
	@Autowired
	OperatorRepo operatorRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	private Operator setOperator(OperatorRequest request, Operator ops) {
		ops.setBillingDay(
				null == request.getBillingDate() || request.getBillingDate() < 1 || request.getBillingDate() > 30 ? 1
						: request.getBillingDate());
		ops.setEmail(request.getEmailId());
		ops.setMaxUser(null == request.getMaxUser() ? 0 : request.getMaxUser());
		ops.setName(request.getName());
		ops.setOperatorAgencyName(request.getOperatorAgencyName());
		ops.setPassword(passwordEncoder.encode(request.getPassword()));
		ops.setSubscriptionCost(request.getSubscriptionCost());
		ops.setSubscriptionStatus(null == request.getSubscriptionStatus() ? "Active" : request.getSubscriptionStatus());
		ops.setSubscriptionType(
				null == request.getSubscriptionType() ? SubscriptionType.BASIC : request.getSubscriptionType());
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
			Operator opr = setOperator(request, operatorRepo.findById(operatorId).get());
			operatorRepo.save(opr);
			return opr;
		} else {
			return null;
		}

	}

	@Override
	public Operator getOperator(long operatorId) {
		return operatorRepo.findById(operatorId).get();
	}

	@Override
	public ServiceResponse deleteOperator(long operatorId) {
		operatorRepo.delete(operatorRepo.findById(operatorId).get());
		return new ServiceResponse(new ResponseStatus(200, "Delete Successfull"));
	}

	@Override
	public AuthResponse validateUser(String userName, String password) {
		Operator ops = operatorRepo.findByName(userName);
		if(ops==null) {
			throw new UnAuthorizedTenantException(": Invalid username");
		}			
		if (passwordEncoder.matches(password, ops.getPassword())) {			
			String authKey = apiUtils.getAuthToken(userName);
			log.info("Validate user Info >>" + ops.getName() + ">> authentication >> Success" + ". withAuthKey >>"+authKey);
			return new AuthResponse(ops.getId(), ops.getName(), ops.getSubscriptionType().name(), authKey ,
					ops.getSubscriptionStatus(), true);
		} else {
			log.info("Validation for user >>" + ops.getName() + ">> authentication >> Failure");
			throw new UnAuthorizedTenantException(": Invalid password");
		}
	}

}
