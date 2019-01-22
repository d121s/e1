package com.deeps.easycable.api.response;

import java.util.List;

import com.deeps.easycable.api.entity.Channel;

import lombok.Data;

@Data
public class ChannelCollectionResponse {

	private long page;
	private long pageSize;
	private boolean hasMore;
	private long totalPages;
	private long totalCount;
	private List<Channel> channel;	
}
