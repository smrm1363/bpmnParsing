package com.mohammadrezaMirali.camundaCodeChallenge.model;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Created by mmirali on 13/01/2019.
 */
@Service
public class BpmnService {
    public BpmnModel readBpmn()
    {
        Client client = ClientBuilder.newBuilder().newClient().register(JacksonJsonProvider.class);
        WebTarget target = client.target("https://elxkoom6p4.execute-api.eu-central-1.amazonaws.com/prod/engine-rest/process-definition/key/invoice/xml");

        Invocation.Builder builder = target.request();
        Response response = builder.get();
        BpmnModel bpmnModel = builder.get(BpmnModel.class);
        return bpmnModel;
    }

    public void printBpmnBetweenTwoNode(String source,String dest)
    {

    }
}
