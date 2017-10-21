package com.codegans.demo.jbpm.rest;

import org.jbpm.services.api.RuntimeDataService;
import org.jbpm.services.api.model.ProcessDefinition;
import org.kie.api.runtime.query.QueryContext;
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
@RequestMapping("/process")
@Transactional
public class ProcessRestController {
    private final RuntimeDataService runtimeDataService;

    public ProcessRestController(RuntimeDataService runtimeDataService) {
        this.runtimeDataService = runtimeDataService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Collection<ProcessDefinition> list() {
        return runtimeDataService.getProcessesByFilter(".*", new QueryContext(0, 100));
    }
}
