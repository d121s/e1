package com.deeps.easycable.api.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name="SubscriptionPackage")
@Data
public class SubscriptionPackageCollection implements Serializable {

	private static final long serialVersionUID = 7774563428562961990L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY,optional=false)
	@JoinColumn(name = "operator_id", nullable = false)
	private Operator operator;

	@Column(unique = true, nullable = false)
	private String name;

	@Column(nullable = false)
	private Double cost;
}
