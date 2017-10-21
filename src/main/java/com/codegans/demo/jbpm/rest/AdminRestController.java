package com.codegans.demo.jbpm.rest;

import org.jbpm.process.audit.AuditLogService;
import org.kie.api.runtime.manager.audit.ProcessInstanceLog;
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
@RequestMapping("/admin")
@Transactional
public class AdminRestController {
    private final AuditLogService auditLogService;

    public AdminRestController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @RequestMapping(value = "/process", method = RequestMethod.GET)
    public Collection<? extends ProcessInstanceLog> list() {
        return auditLogService.findActiveProcessInstances();
    }
}
