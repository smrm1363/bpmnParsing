package com.mohammadrezaMirali.camundaCodeChallenge.model;

import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by mmirali on 13/01/2019.
 */
public class BpmnModel implements Serializable{
    private String id;
    private String bpmn20Xml;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBpmn20Xml() {
        return bpmn20Xml;
    }

    public void setBpmn20Xml(String bpmn20Xml) {
        this.bpmn20Xml = bpmn20Xml;
    }


    public void printPath(SequenceFlow source, SequenceFlow dest, List<String> path, Set<String> visitedSet)
    {

        if(source.getTarget().getId().equals(dest.getTarget().getId()))
        {
            System.out.println(path);
            return;
        }
        visitedSet.add(source.getTarget().getId());
        source.getTarget().getOutgoing().forEach(sequenceFlow ->
        {
            if(!visitedSet.contains(sequenceFlow.getTarget().getId()))
            {
                path.add(sequenceFlow.getTarget().getId());
                printPath(sequenceFlow,dest,path,visitedSet);
                path.remove(sequenceFlow.getTarget().getId());
            }
        });

        visitedSet.remove(source.getTarget().getId());


//        source.getOutgoing().forEach(sequenceFlow -> System.out.println(sequenceFlow.getTarget().getId()));
//        System.out.println(source.getOutgoing());
    }
}
