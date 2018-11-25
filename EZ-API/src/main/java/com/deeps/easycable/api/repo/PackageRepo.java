package com.deeps.easycable.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepo extends JpaRepository<Package, Long> {

	public Package findById();

}
