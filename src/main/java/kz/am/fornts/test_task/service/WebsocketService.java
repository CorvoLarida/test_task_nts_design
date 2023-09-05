package kz.am.fornts.test_task.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.am.fornts.test_task.domain.FileInfo;
import kz.am.fornts.test_task.domain.UserLog;
import kz.am.fornts.test_task.repository.FileInfoRepository;
import kz.am.fornts.test_task.repository.UserLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WebsocketService {
    private final Logger LOGGER = LoggerFactory.getLogger(WebsocketService.class);
    private final UserLogRepository userLogRepository;
    private final FileInfoRepository fileInfoRepository;

    @Autowired
    public WebsocketService(UserLogRepository userLogRepository, FileInfoRepository fileInfoRepository) {
        this.userLogRepository = userLogRepository;
        this.fileInfoRepository = fileInfoRepository;
    }

    public String handleMessage(Map<String, String> message) {
        String feedback;
        String valueFromTextArea = message.get("text");
        String userName = message.get("userName");
        ObjectMapper JSONMapper = new ObjectMapper();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Map<String, String> textMap = JSONMapper.readValue(valueFromTextArea, new TypeReference<HashMap<String, String>>() {
            });
            if (!textMap.containsKey("command")) {
                feedback = "No command was given";
            } else {
                switch (textMap.get("command")) {
                    case "fileInfo":
                        if (!textMap.containsKey("fileName")) {
                            feedback = "Please provide file name";
                        } else {
                            FileInfo foundFile = fileInfoRepository.findByFileName(textMap.get("fileName"));
                            if (foundFile == null) feedback = "File Not Found";
                            else {
                                stringBuilder.append(String.format("{\"fileName\":\"%s\", \"fileDate\":\"%s\", \"filePath\":\"%s\"}",
                                        foundFile.getFileName(),
                                        foundFile.getFileDate().format(DateTimeFormatter.ofPattern(FileInfo.getDatePattern())),
                                        foundFile.getFilePath().replace("\\", "\\\\")));
                                feedback = stringBuilder.toString();
                            }
                        }
                        break;
                    case "addLog":
                        if (!textMap.containsKey("content")) {
                            feedback = "Please provide content";
                        } else {
                            UserLog log = new UserLog();
                            log.setLogContent(textMap.get("content"));
                            log.setUserName(userName);
                            userLogRepository.save(log);
                            feedback = "Log added";
                        }
                        break;
                    case "logs":
                        List<UserLog> userLogs = userLogRepository.findByUserName(userName);
                        stringBuilder.append("[");
                        if (!userLogs.isEmpty()) {
                            for (UserLog userLog : userLogs) {
                                stringBuilder.append(String.format("\"%s\",", userLog.getLogContent()));
                            }
                            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                        }
                        stringBuilder.append("]");
                        feedback = stringBuilder.toString();
                        break;
                    default:
                        feedback = "Invalid command";
                        break;
                }
            }
        } catch (JsonProcessingException e) {
            feedback = "An error occurred. Could not parse JSON.";
        }
        return feedback;
    }
}
