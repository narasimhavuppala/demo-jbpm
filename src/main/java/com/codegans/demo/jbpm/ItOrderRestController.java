package com.codegans.demo.jbpm;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.ProcessInstance;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 * @since 17.10.2017 10:06
 */
@RestController
public class ItOrderRestController {
//    private final KieBase knowledgeBase;
    private final RuntimeEngine runtimeEngine;

//    public ItOrderRestController(KieBase knowledgeBase) {
//        this.knowledgeBase = knowledgeBase;
//    }

    public ItOrderRestController(RuntimeEngine runtimeEngine) {
        this.runtimeEngine = runtimeEngine;
    }

    @RequestMapping("/order/create")
    public String create() {
        Map<String,Object> data = new HashMap<>();

//        KieSession session = knowledgeBase.newKieSession();
        KieSession session = runtimeEngine.getKieSession();

        try {
            ProcessInstance processInstance = session.startProcess("itorders.orderhardware", data);

            System.out.println(processInstance);
        } finally {
            session.dispose();
        }
        
        return null;
    }
}
