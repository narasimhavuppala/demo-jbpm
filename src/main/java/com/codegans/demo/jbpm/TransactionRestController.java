package com.codegans.demo.jbpm;

import com.codegans.demo.jbpm.model.BusinessTransaction;
import com.codegans.demo.jbpm.repository.BusinessTransactionRepository;
import org.jbpm.services.api.ProcessService;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.internal.runtime.manager.context.EmptyContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 * @since 17.10.2017 10:06
 */
@RestController
@RequestMapping("/transaction")
@Transactional
public class TransactionRestController {
    private final BusinessTransactionRepository repository;
    private final ProcessService processService;
    private final RuntimeManager runtimeManager;

    public TransactionRestController(BusinessTransactionRepository repository, ProcessService processService, RuntimeManager runtimeManager) {
        this.repository = repository;
        this.processService = processService;
        this.runtimeManager = runtimeManager;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Collection<BusinessTransaction> list() {
        return repository.findAllNotTreated();
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public BusinessTransaction show(UUID id) {
        return repository.findOne(id);
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public BusinessTransaction show() {
        return repository.findAllNotTreated().stream().findFirst().orElse(null);
    }

    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    public BusinessTransaction generate() {
        return doGenerate();
    }

    @RequestMapping(value = "/generate/{count}", method = RequestMethod.POST)
    public Collection<BusinessTransaction> generate(@RequestParam(required = false, name = "count", defaultValue = "1") int count) {
        return IntStream.range(0, count)
                .mapToObj(e -> doGenerate())
                .collect(Collectors.toList());
    }

    private BusinessTransaction doGenerate() {
        BusinessTransaction transaction = repository.save(new BusinessTransaction(UUID.randomUUID(), LocalDateTime.now(), false));

        KieSession kieSession = runtimeManager.getRuntimeEngine(EmptyContext.get()).getKieSession();

        kieSession.insert(transaction);

        kieSession.startProcess("introduce.transaction");

        return transaction;
    }
}
