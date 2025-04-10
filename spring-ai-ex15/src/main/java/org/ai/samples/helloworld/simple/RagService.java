package org.ai.samples.helloworld.simple;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;

@Service
public class RagService {

     private final ChatClient aiClient;
     private final VectorStore vectorStore;

     public RagService(ChatClient aiClient, VectorStore vectorStore) {
          this.aiClient = aiClient;
          this.vectorStore = vectorStore;
     }

     public String generateResponse(String message) {
          SearchRequest sr = SearchRequest.builder()
            .query(message)
            .build();
          return aiClient.prompt()
                    .advisors(new QuestionAnswerAdvisor(vectorStore, sr))
                    .advisors(new SimpleLoggerAdvisor(10))
                    .user(message)
                    .call()
                    .content();
     }

     public Invoice generateEntity(String message) {
          SearchRequest sr = SearchRequest.builder()
            .query(message)
            .build();
          return aiClient.prompt()
                    .advisors(new QuestionAnswerAdvisor(vectorStore, sr))
                    .advisors(new SimpleLoggerAdvisor())
                    .user(message)
                    .call()
                    .entity(Invoice.class);
     }
}
