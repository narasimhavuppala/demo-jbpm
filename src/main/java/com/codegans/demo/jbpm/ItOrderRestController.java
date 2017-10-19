package com.codegans.demo.jbpm;

import org.drools.core.command.runtime.process.StartProcessCommand;
import org.jbpm.services.api.ProcessService;
import org.jbpm.services.api.RuntimeDataService;
import org.jbpm.services.api.model.ProcessDefinition;
import org.kie.api.runtime.query.QueryContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 * @since 17.10.2017 10:06
 */
@RestController
@RequestMapping("/order")
public class ItOrderRestController {
    private final RuntimeDataService runtimeDataService;
    private final ProcessService processService;

    public ItOrderRestController(RuntimeDataService runtimeDataService, ProcessService processService) {
        this.runtimeDataService = runtimeDataService;
        this.processService = processService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Collection<ProcessDefinition> listProcesses() {
        return runtimeDataService.getProcesses(new QueryContext(0, 100));

    }

    @RequestMapping("/create")
    public String create() {
        return "Started " + processService.execute("", new StartProcessCommand("itorders.orderhardware"));
    }
}
