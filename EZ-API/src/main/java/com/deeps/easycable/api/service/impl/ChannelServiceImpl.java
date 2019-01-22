package com.deeps.easycable.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.deeps.easycable.api.entity.Channel;
import com.deeps.easycable.api.repo.ChannelRepo;
import com.deeps.easycable.api.repo.OperatorRepo;
import com.deeps.easycable.api.repo.SubscriptionPackageRepo;
import com.deeps.easycable.api.request.ChannelRequest;
import com.deeps.easycable.api.request.ChannelSearchType;
import com.deeps.easycable.api.response.ChannelCollectionResponse;
import com.deeps.easycable.api.response.ResponseStatus;
import com.deeps.easycable.api.response.ServiceResponse;
import com.deeps.easycable.api.service.ChannelService;

@Service
public class ChannelServiceImpl implements ChannelService {	

	@Autowired
	ChannelRepo channelRepo;

	@Autowired
	OperatorRepo opRepo;

	@Autowired
	SubscriptionPackageRepo spRepo;

	public Channel setChannel(ChannelRequest channelRequest, Channel channel) {
		channel.setCost(channelRequest.getCost());
		channel.setImage(channelRequest.getChannelImage());
		channel.setName(channelRequest.getChannelName());
		channel.setQuality(channelRequest.getChannelQuality());
		channel.setLanguage(channelRequest.getLanguage().name());
		channel.setBroadcastername(channelRequest.getBroadcasterName());
		channel.setOperatorId(channelRequest.getOperatorId());
		channel.setGenre(channelRequest.getGenre().name());
		channel.setType(channelRequest.getType());
		return channel;

	}

	@Override
	public Channel addChannel(ChannelRequest channelRequest) {
		return channelRepo.save(setChannel(channelRequest, new Channel()));
	}

	@Override
	public Channel updateChannel(ChannelRequest channelRequest, Long channelId) {
		return channelRepo.save(setChannel(channelRequest, channelRepo.findById(channelId).get()));
	}

	public ServiceResponse deleteChannel(Long channelId) {
		channelRepo.delete(channelRepo.findById(channelId).get());
		return new ServiceResponse(new ResponseStatus(200, "Channel deleted sucessfully"));
	}

	public Channel getChannelDetails(Long channelId) {
		return channelRepo.findById(channelId).get();
	}

	@Override
	@Cacheable("channel")
	public ChannelCollectionResponse getChannelList(int pageNo, int pageSize, String searchValue,
			ChannelSearchType searchKey) {
		Pageable pageable = PageRequest.of((pageNo - 1), pageSize, Direction.ASC, "name");
		Page<Channel> channelPageContent = null;

		if (searchKey == null) {
			channelPageContent = channelRepo.findAll(pageable);
		} else {
			switch (searchKey) {
			case NAME:
				channelPageContent = channelRepo.findByNameContaining(searchValue, pageable);
				break;
			case TYPE:
				channelPageContent = channelRepo.findByType(searchValue, pageable);
				break;
			case GENRE:
				channelPageContent = channelRepo.findByGenre(searchValue, pageable);
				break;
			case BROADCASTER:
				channelPageContent = channelRepo.findByBroadcastername(searchValue, pageable);
				break;
			case LANGUAGE:
				channelPageContent = channelRepo.findByLanguage(searchValue, pageable);
				break;
			default:
				channelPageContent = channelRepo.findAll(pageable);
			}
		}

		ChannelCollectionResponse cr = new ChannelCollectionResponse();
		cr.setChannel(channelPageContent.getContent());
		cr.setHasMore(
				(channelPageContent.getTotalPages() > (channelPageContent.getPageable().getPageNumber() + 1)) ? true
						: false);
		cr.setPage(channelPageContent.getPageable().getPageNumber() + 1);
		cr.setPageSize(channelPageContent.getNumberOfElements());
		cr.setTotalCount(channelPageContent.getTotalElements());
		cr.setTotalPages(channelPageContent.getTotalPages());
		return cr;
	}
}
