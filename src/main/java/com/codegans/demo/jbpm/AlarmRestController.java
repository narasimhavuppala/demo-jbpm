package com.codegans.demo.jbpm;

import com.codegans.demo.jbpm.model.Alarm;
import com.codegans.demo.jbpm.repository.AlarmRepository;
import org.drools.core.command.runtime.process.StartProcessCommand;
import org.jbpm.services.api.ProcessService;
import org.jbpm.services.api.RuntimeDataService;
import org.kie.api.runtime.process.ProcessInstance;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Collection;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 * @since 17.10.2017 10:06
 */
@RestController
@RequestMapping("/alarm")
@Transactional
public class AlarmRestController {
    private final AlarmRepository repository;
    private final RuntimeDataService runtimeDataService;
    private final ProcessService processService;

    public AlarmRestController(AlarmRepository repository, RuntimeDataService runtimeDataService, ProcessService processService) {
        this.repository = repository;
        this.runtimeDataService = runtimeDataService;
        this.processService = processService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Collection<Alarm> list() {
        return repository.findAll();
    }

    @RequestMapping("/create/{id}")
    public int create() {
        ProcessInstance processInstance = processService.execute("latest", new StartProcessCommand("main"));

        return processInstance.getState();
    }
}
