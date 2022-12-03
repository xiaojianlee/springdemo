package com.example.demo.flowabletest;

import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricActivityInstanceQuery;
import org.flowable.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class TestProcessor {

    ProcessEngineConfiguration configuration = null;
    @Before
    public void before(){
        configuration = new StandaloneInMemProcessEngineConfiguration();
        configuration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        configuration.setJdbcUsername("root");
        configuration.setJdbcPassword("root");
        configuration.setJdbcUrl("jdbc:mysql://localhost:3306/flowable_learn?serverTimezone=UTC&nullCatalogMeansCurrent=true");
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
       // ProcessEngine processEngine = configuration.buildProcessEngine();
    }

    /**
     * 流程的部署，
     */
    @Test
    public void testDeploy(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder deployment = repositoryService.createDeployment();
        Deployment de = deployment.addClasspathResource("holiday-request.bpmn20.xml").name("请假流程")
                .deploy();
        System.out.println("de = " + de.getId());
        System.out.println("de = " + de.getName());
    }

    /**
     * 查询部署流程定义
     * 删除等
     */
    @Test
    public void testDeloy(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        ProcessDefinition processDefinition = processDefinitionQuery.deploymentId("1").singleResult();
        System.out.println("processDefinition = " + processDefinition.getId());
        System.out.println("processDefinition = " + processDefinition.getName());
        System.out.println("processDefinition = " + processDefinition.getDeploymentId());
    }

    /**
     * 删、查
     */
    @Test
    public void testDeleteDeploy(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 如果部署的流程启动了，就不允许删除
        //repositoryService.deleteDeployment("");
        // true 是级联删除，如果流程启动了，一并删除
        repositoryService.deleteDeployment("1",true);
    }

    /**
     * 正式启动流程
     */
    @Test
    public void testStart(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        // 启动流程实例
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //  构建流程变量
        Map<String, Object> variable = new HashMap<>();
        variable.put("employee", "张三");
        ProcessInstance holidayRequest = runtimeService.startProcessInstanceByKey("holidayRequest", variable);

        System.out.println("holidayRequest = " + holidayRequest.getId());

        System.out.println("holidayRequest = " + holidayRequest.getBusinessKey());

        System.out.println(holidayRequest.getProcessDefinitionId());

        System.out.println(holidayRequest.getProcessDefinitionKey());

        System.out.println(holidayRequest.getProcessVariables());

    }

    /**
     * 查询某个用户的所有任务
     */
    @Test
    public void testQueryTask(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();

        List<Task> list = taskService.createTaskQuery().processDefinitionKey("holidayRequest")
                .taskAssignee("zhangsan").list();

        for (Task task : list) {
            System.out.println(task.getProcessDefinitionId());
            System.out.println(task.getAssignee());
            System.out.println(task.getDescription());
            System.out.println(task.getOwner());
        }

    }

    /**
     * 排他网关
     */
    @Test
    public void decide(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> holidayRequest = taskService.createTaskQuery().processDefinitionKey("holidayRequest")
                .processInstanceId("7501").list();

        for (Task task : holidayRequest) {
            Map<String, Object> variable = new HashMap<>();
            // 创建流程变量
            variable.put("approved", false);
            // 完成任务
            taskService.complete(task.getId(),variable);
        }

    }

    @Test
    public void testHistory(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        HistoryService historyService = processEngine.getHistoryService();
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();

        List<HistoricActivityInstance> list = historicActivityInstanceQuery.processDefinitionId("holidayRequest:1:5003")
                .finished()
                .orderByHistoricActivityInstanceEndTime().asc().list();
        for (HistoricActivityInstance historicActivityInstance : list) {
            System.out.println(historicActivityInstance.getActivityName());
            System.out.println(historicActivityInstance.getExecutionId());
            System.out.println(historicActivityInstance.getAssignee());
        }

    }


}
