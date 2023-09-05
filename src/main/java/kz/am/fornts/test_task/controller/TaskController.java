package kz.am.fornts.test_task.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TaskController {
    private final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    @GetMapping(value = "/")
    private String getHome() {
        return "home";
    }

    @GetMapping(value = "/task1")
    private String getTask1() {
        return "task1";
    }

    @GetMapping(value = "/task2")
    private String getTask2() {
        return "task2";
    }
}
