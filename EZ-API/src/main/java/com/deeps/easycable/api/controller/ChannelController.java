package com.deeps.easycable.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deeps.easycable.api.entity.Channel;
import com.deeps.easycable.api.request.ChannelRequest;
import com.deeps.easycable.api.request.ChannelSearchType;
import com.deeps.easycable.api.response.ChannelCollectionResponse;
import com.deeps.easycable.api.response.ServiceResponse;
import com.deeps.easycable.api.service.ChannelService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class ChannelController {
	

	@Autowired
	ChannelService channelService;

	@GetMapping("/channel")
	public ChannelCollectionResponse getChannelList(
			@RequestParam(defaultValue = "1") int page, 
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(required = false) String searchValue,
			@RequestParam(required = false) ChannelSearchType searchKey) {
		log.debug("Get Request to view Customer Details");
		return channelService.getChannelList(page, pageSize, searchValue, searchKey);
	}

	@GetMapping("/channel/{channelId}")
	// @PreAuthorize("hasAnyAuthority('admin')")
	public Channel getChannelDetails(@PathVariable(required = false) Long channelId) {
		log.debug("Get Request to view Customer Details");
		return channelService.getChannelDetails(channelId);
	}

	@PostMapping("/channel")
	public Channel addChannel(@RequestBody ChannelRequest channelRequest) {
		return channelService.addChannel(channelRequest);
	}

	@PutMapping("/channel/{channelId}")
	public Channel updateChannel(@PathVariable("channelId") Long channelId,
			@RequestBody ChannelRequest channelRequest) {
		return channelService.updateChannel(channelRequest, channelId);
	}

	@DeleteMapping("/channel/{channelId}")
	public ServiceResponse deleteChannel(@PathVariable("channelId") Long channelId) {
		return channelService.deleteChannel(channelId);
	}
}
