package com.deeps.easycable.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

import lombok.Data;

@Entity
@Table
@Data
public class Channel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;

	@Nationalized
	@Column(unique = true)
	private String name;
	
	private String quality;
	
	@Nationalized
	private String language;
	
	private String genre;
	
	@Column(nullable = true)
	private String broadcastername;
	
	private Double cost;
	
	@Column(nullable = true)
	private Long operatorId;
	
	@Column(nullable = true)
	private String type;

	@Nationalized
	@Column(nullable = true)
	private String image;
}
