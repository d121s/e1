package com.deeps.easycable.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deeps.easycable.api.entity.Operator;


public interface OperatorRepo extends JpaRepository<Operator,Long>{

	public Operator findById(long id);
}
