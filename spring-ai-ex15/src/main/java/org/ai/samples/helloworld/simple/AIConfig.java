package org.ai.samples.helloworld.simple;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;

@Configuration
public class AIConfig {

    @Value("classpath:/Invoice1.pdf")
    private Resource invoice;

     @Bean
    SimpleVectorStore vectorStore(EmbeddingModel embeddingClient) {
        return SimpleVectorStore.builder(embeddingClient).build();
    }

    @Bean
    ApplicationRunner loadKnowledge(SimpleVectorStore vectorStore) {
        return args -> {
            addDoc(vectorStore, invoice);
        };
    }

    // Add the invoice as is to the vector store..
    private void addDoc(SimpleVectorStore vectorStore, Resource resource ) {
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(resource);
        vectorStore.add(tikaDocumentReader.get());
    }


    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("You are an expert accountant with invoices")
                .build();
    }

}