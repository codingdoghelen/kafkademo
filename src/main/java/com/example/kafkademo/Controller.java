package com.example.kafkademo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private Repository repository;

    @Autowired
    public Controller(Repository repository) {
        this.repository = repository;
    }

    @PostMapping(path = "message")
    public String postString(@RequestBody String text){
        return repository.addMessage(text);

    }
}
// .\bin\windows\kafka-console-consumer.bat --topic helen-test-topic --from-beginning --bootstrap-server localhost:9092