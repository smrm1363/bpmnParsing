package com.mohammadrezaMirali.camundaCodeChallenge.model;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.ModelElementType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Created by mmirali on 13/01/2019.
 */
@Service
public class BpmnService {
    @Value("${bpmn-file-url}")
    private String bpmnUrlPath;
    public BpmnModel readBpmn()
    {
        Client client = ClientBuilder.newBuilder().newClient().register(JacksonJsonProvider.class);
        WebTarget target = client.target(bpmnUrlPath);
        Invocation.Builder builder = target.request();
        try {
        Response response = builder.get();
        }
        catch (ProcessingException e)
        {
            System.out.println(e.getMessage());
        }
        BpmnModel bpmnModel = builder.get(BpmnModel.class);
        return bpmnModel;
    }

    public void printBpmnBetweenTwoNode( String sourceNode, String destNode)
    {
        BpmnModel bpmnModel = readBpmn();
        InputStream inputStream = new ByteArrayInputStream(bpmnModel.getBpmn20Xml().getBytes());
        BpmnModelInstance modelInstance = Bpmn.readModelFromStream(inputStream);

        // find all elements of the type task
        ModelElementType sequenceFlowType = modelInstance.getModel().getType(SequenceFlow.class);
        Collection<ModelElementInstance> sequenceFlowInstances = modelInstance.getModelElementsByType(sequenceFlowType);

        Optional<SequenceFlow> source = sequenceFlowInstances.stream().filter(modelElementInstance ->
        {
            SequenceFlow sequenceFlow1 = (SequenceFlow) modelElementInstance;
            return sequenceFlow1.getSource().getId().equals(sourceNode);
        }).findFirst().map(modelElementInstance -> ((SequenceFlow)modelElementInstance));

        Optional<SequenceFlow> dest = sequenceFlowInstances.stream().filter(modelElementInstance ->
        {
            SequenceFlow sequenceFlow1 = (SequenceFlow) modelElementInstance;
            return sequenceFlow1.getTarget().getId().equals(destNode);
        }).findFirst().map(modelElementInstance -> ((SequenceFlow)modelElementInstance));

        List<String> path = new ArrayList<>();
        try {
            path.add(source.get().getSource().getId());
            path.add(source.get().getTarget().getId());
            System.out.println("The path from approveInvoice to invoiceProcessed is:");
            bpmnModel.printPath(source.get(), dest.get(), path, new HashSet<>());
        }
        catch (NoSuchElementException e)
        {
            System.out.println("One of the source or destination element's ID is not correct, means not found in the BPMN");
        }

    }
}
