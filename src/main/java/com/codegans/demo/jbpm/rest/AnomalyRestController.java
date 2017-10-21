package com.codegans.demo.jbpm.rest;

import com.codegans.demo.jbpm.model.Anomaly;
import com.codegans.demo.jbpm.repository.AnomalyRepository;
import org.jbpm.process.audit.AuditLogService;
import org.jbpm.services.api.UserTaskService;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.Task;
import org.kie.internal.runtime.manager.context.EmptyContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 * @since 17.10.2017 10:06
 */
@RestController
@RequestMapping("/anomaly")
@Transactional
public class AnomalyRestController {
    private static final Logger LOG = LoggerFactory.getLogger(AnomalyRestController.class);
    private final AnomalyRepository repository;
    private final TaskService taskService;
    private final AuditLogService auditLogService;
    private final UserTaskService userTaskService;
    private final RuntimeManager runtimeManager;

    public AnomalyRestController(AnomalyRepository repository, TaskService taskService, AuditLogService auditLogService, UserTaskService userTaskService, RuntimeManager runtimeManager) {
        this.repository = repository;
        this.taskService = taskService;
        this.auditLogService = auditLogService;
        this.userTaskService = userTaskService;
        this.runtimeManager = runtimeManager;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Collection<Anomaly> list() {
        return repository.findAllNotTreated();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Anomaly show(@PathVariable long id) {
        return repository.findOne(id);
    }

    @RequestMapping(value = "/{id}/accept", method = RequestMethod.GET)
    public Anomaly accept(@PathVariable long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Anomaly anomaly = repository.findOne(id);

        Task task = taskService.getTaskById(anomaly.getTaskId());
        long pid = task.getTaskData().getProcessInstanceId();

        taskService.claim(anomaly.getTaskId(), auth.getName());
        LOG.info("Claimed task #{}: {}", anomaly.getId(), task.getTaskData().getStatus());
        taskService.start(anomaly.getTaskId(), auth.getName());
        LOG.info("Started task #{}: {}", anomaly.getId(), task.getTaskData().getStatus());
        taskService.complete(anomaly.getTaskId(), auth.getName(), null);
        LOG.info("Completed task #{}: {}", anomaly.getId(), task.getTaskData().getStatus());

        Long taskId = getCurrentTaskId(pid);

        if (Objects.equals(anomaly.getTaskId(), taskId)) {
            return anomaly;
        }

        anomaly.setTaskId(taskId);

        return repository.save(anomaly);
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public Anomaly generate() {
        return doGenerate();
    }

    private Anomaly doGenerate() {
        Anomaly anomaly = repository.save(new Anomaly(LocalDateTime.now(), false));

        Map<String, Object> input = new HashMap<>();

        input.put("anomaly", anomaly);

        KieSession kieSession = runtimeManager.getRuntimeEngine(EmptyContext.get()).getKieSession();

        ProcessInstance pi = kieSession.startProcess("introduce.anomaly", input);

        if (pi.getState() == ProcessInstance.STATE_ACTIVE) {
            Long taskId = getCurrentTaskId(pi.getId());

            anomaly.setTaskId(taskId);

            anomaly = repository.save(anomaly);
        }

        switch (pi.getState()) {
            case ProcessInstance.STATE_ABORTED:
                System.out.println(pi.getId() + ":ABORTED");
                break;
            case ProcessInstance.STATE_ACTIVE:
                System.out.println(pi.getId() + ":ACTIVE");
                break;
            case ProcessInstance.STATE_COMPLETED:
                System.out.println(pi.getId() + ":COMPLETED");
                break;
            case ProcessInstance.STATE_PENDING:
                System.out.println(pi.getId() + ":PENDING");
                break;
            case ProcessInstance.STATE_SUSPENDED:
                System.out.println(pi.getId() + ":SUSPENDED");
                break;
        }

        return anomaly;
    }

    private Long getCurrentTaskId(Long pid) {
        List<Long> currentTasks = taskService.getTasksByProcessInstanceId(pid);

        if (currentTasks.size() == 1) {
            return currentTasks.get(0);
        }

        return null;
    }
}
