package com.example.agentic;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ask")
public class TeamController {

	private final TicketService ticketService;

	public TeamController(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	@PostMapping(value = "/agent")
	public Answer askAbout(@RequestBody Ticket ticket) {
		return ticketService.processTicket(ticket.ticket());
	}

	record Ticket(String ticket) {}

}
