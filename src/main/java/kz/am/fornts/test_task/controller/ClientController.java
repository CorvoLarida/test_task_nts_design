package kz.am.fornts.test_task.controller;

import kz.am.fornts.test_task.domain.FileInfo;
import kz.am.fornts.test_task.service.FileService;
import kz.am.fornts.test_task.service.WebsocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
public class ClientController implements ErrorController {
    private final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);
    private final WebsocketService websocketService;
    private final FileService fileService;

    @Autowired
    public ClientController(WebsocketService websocketService, FileService fileService) {
        this.websocketService = websocketService;
        this.fileService = fileService;
    }

    @MessageMapping("/command")
    @SendToUser(value = "/test-task-broker/messages")
    public String sendMessageToUser(Map<String, String> message) {
        return websocketService.handleMessage(message);
    }

    @PostMapping(value = "/task1")
    private String saveFileFromClient(Model model, @RequestParam(value = "file") MultipartFile file) {
        String message = "Please select a file to upload.";
        String fileName = file.getOriginalFilename();
        String errorMessage = String.format("Upload of file \"%s\" stopped: Error parsing file name. You can only upload files with name pattern: \"DDMMYYYY_HHMMSS.format\".", fileName);
        if (!fileName.isEmpty()) {
            if (!fileService.fileNameIsOfCorrectPattern(fileName)) {
                message = errorMessage;
            } else {
                FileInfo foundFile = fileService.getFileByName(fileName);
                if (foundFile == null) {
                    foundFile = fileService.saveFile(file);
                    if (foundFile.getFileName() == null) message = errorMessage;
                    else message = String.format("File \"%s\" saved.", fileName);
                } else {
                    message = String.format("File \"%s\" is already uploaded.", fileName);
                }
            }
        }
        model.addAttribute("message", message);
        return "/task1";
    }

    @RequestMapping("/error")
    private String error(Model model) {
        model.addAttribute("message", "Error: Selected file is too big to upload.");
        return "/task1";
    }

}

