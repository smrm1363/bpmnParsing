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
			try {
				bpmnService.printBpmnBetweenTwoNode(args[0], args[1]);
			}
			catch (ArrayIndexOutOfBoundsException e)
			{
				System.out.println("For running this program, you should specify two parameters as source and dest in BPMN");
			}

		}

	}


}

