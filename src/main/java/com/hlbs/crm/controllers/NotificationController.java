package com.hlbs.crm.controllers;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/notifications")
@Slf4j
public class NotificationController {
	private final List<DeferredResult<String>> waitingRequests = new CopyOnWriteArrayList<DeferredResult<String>>();

	@GetMapping
	public DeferredResult<String> poll() {
		final DeferredResult<String> result = new DeferredResult<String>(30000L);

		waitingRequests.add(result);

		result.onCompletion(() -> waitingRequests.remove(result));
		result.onTimeout(() -> {
			result.setResult("timeout");
			waitingRequests.remove(result);
		});

		return result;
	}

	public void notifyClients(final String message) {
		waitingRequests.forEach(r -> r.setResult(message));
		waitingRequests.clear();
	}
}
