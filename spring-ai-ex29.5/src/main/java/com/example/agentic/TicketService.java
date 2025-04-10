package com.example.agentic;

import org.springframework.ai.chat.client.ChatClient;
import java.util.Map;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import com.example.agentic.Route;
import com.example.agentic.Answer;

@Service
public class TicketService{

    private final ChatClient.Builder chatClientBuilder;

    private Map<String, String> supportRoutes = Map.of(
                    "billing",
					"""
							You are a billing support specialist. Follow these guidelines:
							1. Always start with "Billing Support Response:"
							2. First acknowledge the specific billing issue
							3. Explain any charges or discrepancies clearly
							4. List concrete next steps with timeline
							5. End with payment options if relevant

							Keep responses professional but friendly.

							Input: {ticket}""",

					"technical",
					"""
							You are a technical support engineer. Follow these guidelines:
							1. Always start with "Technical Support Response:"
							2. List exact steps to resolve the issue
							3. Include system requirements if relevant
							4. Provide workarounds for common problems
							5. End with escalation path if needed

							Use clear, numbered steps and technical details.

							Input: {ticket}""",

					"account",
					"""
							You are an account security specialist. Follow these guidelines:
							1. Always start with "Account Support Response:"
							2. Prioritize account security and verification
							3. Provide clear steps for account recovery/changes
							4. Include security tips and warnings
							5. Set clear expectations for resolution time

							Maintain a serious, security-focused tone.

							Input: {ticlket}""",

					"product",
					"""
							You are a product specialist. Follow these guidelines:
							1. Always start with "Product Support Response:"
							2. Focus on feature education and best practices
							3. Include specific examples of usage
							4. Link to relevant documentation sections
							5. Suggest related features that might help

							Be educational and encouraging in tone.

							Input: {ticket}"""
							);
		
    public TicketService(ChatClient.Builder chatClientBuilder) {
        this.chatClientBuilder = chatClientBuilder;
    }

	// Routing Agent Patter.
	// Two step process: First work out the team, then find answer with routed team
    public Answer processTicket(String ticket) {
		String options = this.supportRoutes.keySet().toString();
		Route route = this.getRoute(ticket, options);
		String answer = getAnswer(ticket, route.selection());
		return new Answer(route.selection(), options, route.reasoning(), answer);    
	}

	// Use a One Shot Prompt providing an Example to get a Route
    private Route getRoute(String ticket, String options) {
		System.out.println("\nTicket: " + ticket);
		System.out.println("\nAvailable routes: " + options);
		
        String promptText = """
           Analyse the Input Ticket below and select the most appropriate support team from these options: {options}
           The analysis should provide a Reasoning and then a Selection.
		   - The Reasoning is a brief explanation of why this ticket should be routed to a specific team. Consider key terms, user intent, and urgency level.
		   - The Selection is a sinlge team chosen from the list of options.

		   An Example is 
		   -------------
		   Ticket: My Email produces an eror and is not receiving emails, I need help urgently.
		   Team Options: [technical, billing]
		  
		   The Example Answer is
		   -------------
		   Reasoning: The user is asking for ehelp for an error in email,indicating a technical issue. The urgent nature of the request suggest that this is a high-priority technical issue.
		   Selection: technical

		   Now apply this example to the Input Ticket below.
		   Input Ticket
		   -------------
		   {ticket}
		   """;
		ChatClient chatClient = chatClientBuilder.build();
    
        Route route = chatClient
			.prompt()
			.user(u -> u.text(promptText).param("ticket", ticket).param("options", options))
            .call()
            .entity(Route.class);

        System.out.println(String.format("Routing Analysis:%s\nSelected Team: %s",
            route.reasoning(), route.selection()));

        return route;
    }


	private String getAnswer(String ticket, String selection) {
		ChatClient chatClient = chatClientBuilder.build();
		String answer = chatClient
			.prompt()
			.user(u -> u.text(selection).param("ticket", ticket))
			.call()
			.content();
		System.out.println("\nAnswer: " + answer);
		return answer;
	}

}