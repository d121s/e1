package com.deeps.easycable.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deeps.easycable.api.entity.Operator;


@Repository
public interface OperatorRepo extends JpaRepository<Operator,Long>{

	public Operator findByName(String username);
}
