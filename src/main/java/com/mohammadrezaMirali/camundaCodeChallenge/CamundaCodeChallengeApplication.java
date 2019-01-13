package com.mohammadrezaMirali.camundaCodeChallenge;

import com.mohammadrezaMirali.camundaCodeChallenge.model.BpmnService;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.Task;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.ModelElementType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;

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
			System.out.println("Hiiiii ...");
			InputStream inputStream = new ByteArrayInputStream(bpmnService.readBpmn().getBpmn20Xml().getBytes());
			BpmnModelInstance modelInstance = Bpmn.readModelFromStream(inputStream);
			StartEvent start = (StartEvent) modelInstance.getModelElementById("StartEvent_1");
			ModelElementType taskType = modelInstance.getModel().getType(Task.class);
			Collection<ModelElementInstance> taskInstances = modelInstance.getModelElementsByType(taskType);
			System.out.println(start.getName());
			SequenceFlow sequenceFlow = (SequenceFlow) modelInstance.getModelElementById("approveInvoice");
			System.out.println("Hiiiii ...2");
		}

	}

}

