package com.cleytonongaratto.desafioanotaai.service.aws;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.Topic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AwsSnsService {

    private final AmazonSNS snsClient;
    @Qualifier("catalogEventsTopic")
    private final Topic catalogTopic;

    public AwsSnsService(AmazonSNS snsClient, Topic catalogTopic){
        this.snsClient = snsClient;
        this.catalogTopic = catalogTopic;
    }

    public void publish(MessageDTO message){
        this.snsClient.publish(catalogTopic.getTopicArn(), message.toString());
    }

}
