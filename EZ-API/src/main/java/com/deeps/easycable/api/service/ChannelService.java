package com.deeps.easycable.api.service;

import com.deeps.easycable.api.entity.Channel;
import com.deeps.easycable.api.request.ChannelRequest;
import com.deeps.easycable.api.request.ChannelSearchType;
import com.deeps.easycable.api.response.ChannelCollectionResponse;
import com.deeps.easycable.api.response.ServiceResponse;

public interface ChannelService {

	public Channel addChannel(ChannelRequest channelRequest);

	public Channel updateChannel(ChannelRequest channelRequest, Long channelId);

	public ServiceResponse deleteChannel(Long channelId);

	public Channel getChannelDetails(Long channelId);

	public ChannelCollectionResponse getChannelList(int pageNo,int pageSize,String searchValue,ChannelSearchType searchKey);

}
