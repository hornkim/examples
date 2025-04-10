package org.ai.samples.helloworld.simple;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ask")
public class SimpleAiController {

	private final RagService ragService;

	public SimpleAiController(RagService ragService) {
		this.ragService = ragService;
	}

	@PostMapping(value = "/rag", produces = "text/plain")
	public String askAbout(@RequestBody Question question) {
		return ragService.generateResponse(question.question());
	}

	@PostMapping(value = "/entity")
	public Invoice entity(@RequestBody Question question) {
		return ragService.generateEntity(question.question());
	}

	record Question(String question) {}
}
