package com.deeps.easycable.api.response;

import java.util.List;

import com.deeps.easycable.api.entity.CustomerCollection;

import lombok.Data;

@Data
public class CustomerCollectionResponse {
	private long page;
	private long pageSize;
	private boolean hasMore;
	private long totalPages;
	private long totalCount;
	private List<CustomerCollection> customer;
}
