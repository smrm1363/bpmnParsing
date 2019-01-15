package com.mohammadrezaMirali.camundaCodeChallenge;


import com.mohammadrezaMirali.camundaCodeChallenge.model.BpmnModel;
import com.mohammadrezaMirali.camundaCodeChallenge.model.BpmnService;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.cmmn.instance.Task;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.ModelElementType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

@SpringBootApplication
public class CamundaCodeChallengeApplication {

	@Autowired
	BpmnService bpmnService;
	public static void main(String[] args) {
		SpringApplication.run(CamundaCodeChallengeApplication.class, args);
	}

	@Component
	public class CommandLiner implements CommandLineRunner {

		@Override
		public void run(String... args) throws Exception {
			// Put your logic here

			BpmnModel bpmnModel = bpmnService.readBpmn();
			InputStream inputStream = new ByteArrayInputStream(bpmnModel.getBpmn20Xml().getBytes());
			BpmnModelInstance modelInstance = Bpmn.readModelFromStream(inputStream);

			// find all elements of the type task
			ModelElementType sequenceFlowType = modelInstance.getModel().getType(SequenceFlow.class);
			Collection<ModelElementInstance> sequenceFlowInstances = modelInstance.getModelElementsByType(sequenceFlowType);
			sequenceFlowInstances.forEach(modelElementInstance ->
			{
				SequenceFlow sequenceFlow1 = (SequenceFlow) modelElementInstance;
				System.out.println(sequenceFlow1.getSource().getElementType().getTypeName());
			});

			Optional<SequenceFlow> source = sequenceFlowInstances.stream().filter(modelElementInstance ->
			{
				SequenceFlow sequenceFlow1 = (SequenceFlow) modelElementInstance;
				return sequenceFlow1.getSource().getId().equals(args[0]);
			}).findFirst().map(modelElementInstance -> ((SequenceFlow)modelElementInstance));

			Optional<SequenceFlow> dest = sequenceFlowInstances.stream().filter(modelElementInstance ->
			{
				SequenceFlow sequenceFlow1 = (SequenceFlow) modelElementInstance;
				return sequenceFlow1.getTarget().getId().equals(args[1]);
			}).findFirst().map(modelElementInstance -> ((SequenceFlow)modelElementInstance));

			List<String> path = new ArrayList<>();
			path.add(source.get().getSource().getId());
			path.add(source.get().getTarget().getId());
			bpmnModel.printPath(source.get(),dest.get(),path,new HashSet<>());
//			printPath(source.get(),dest.get());

//			StartEvent start = (StartEvent) modelInstance.getModelElementById("approveInvoice");
			ModelElementInstance modelElementInstance =  modelInstance.getModelElementById("approveInvoice");
			SequenceFlow sequenceFlow = (SequenceFlow) modelInstance.getModelElementById("sequenceFlow_180");
			// get the source and target element
//			FlowNode source = sequenceFlow.getSource();
			FlowNode target = sequenceFlow.getTarget();

// get all outgoing sequence flows of the source
//			Collection<SequenceFlow> outgoing = source.getOutgoing();
//			assert(outgoing.contains(sequenceFlow));
			System.out.println("Hiiiii ...2");
		}

	}

	private void printPath(FlowNode source,FlowNode dest, List<String> flowNodeIdList)
	{

		while (source.getOutgoing() != null || source.getOutgoing().size()>0)
		{

		}
		source.getOutgoing().forEach(sequenceFlow -> System.out.println(sequenceFlow.getTarget().getId()));
		System.out.println(source.getOutgoing());
	}

}

