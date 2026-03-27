package org.javacream.training.activemq.web;

import org.javacream.training.activemq.service.MessageSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
	private final MessageSender sender;

	public MessageController(MessageSender sender) {
		this.sender = sender;
	}

	@PostMapping
	public String send(@RequestBody String body) {
		sender.send(body);
		return "sent";
	}
}