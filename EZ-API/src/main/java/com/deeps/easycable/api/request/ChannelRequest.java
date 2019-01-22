package com.deeps.easycable.api.request;

import lombok.Data;

@Data
public class ChannelRequest {

	private String channelName;

	private String channelImage;

	private String channelQuality;

	private String type;

	private String broadcasterName;

	private ChannelLanguage language;

	private Genre genre;
	
	private Double cost;
	
	private Long operatorId;
}
