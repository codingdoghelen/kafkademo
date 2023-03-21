package com.example.kafkademo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Scope("singleton")
public class Repository {

    private static Logger logger = LoggerFactory.getLogger(Repository.class);
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public Repository(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
//... rest of the code

    public String addMessage(String testString) {
//        MongoDatabase database = mongo.getDatabase("airbnb");
//        MongoCollection<Document> calendarCol = database.getCollection("calendar");


//        ListenableFuture<SendResult<String, String>> result = this.kafkaTemplate.send("helen-test-topic", testString);
//        result.addCallback(successResult -> {
//            logger.info("Message sent successfully");
//        }, error -> {
//            logger.error(error.getMessage());
//        });

        CompletableFuture<SendResult<String, String>> future = CompletableFuture.supplyAsync(() ->
                {
                    try {
                        return kafkaTemplate.send("helen-test-topic", testString).get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        future.thenAccept(sendResult -> {
            logger.info("Message sent successfully");
        }).exceptionally(ex -> {
            logger.error(ex.getMessage());
            return null;
        });

//        CompletableFuture<SendResult<String, String>> completed = result.whenComplete(logger.info("Completed"));


        return "Success";
    }
}
