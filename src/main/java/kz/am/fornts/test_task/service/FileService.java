package kz.am.fornts.test_task.service;

import kz.am.fornts.test_task.domain.FileInfo;
import kz.am.fornts.test_task.repository.FileInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class FileService {
    private final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\files";
    private final Logger LOGGER = LoggerFactory.getLogger(FileService.class);
    private final FileInfoRepository fileInfoRepository;

    @Autowired
    public FileService(FileInfoRepository fileInfoRepository) {
        this.fileInfoRepository = fileInfoRepository;
    }

    public List<FileInfo> getAll() {
        return fileInfoRepository.findAll();
    }

    public FileInfo getFileByName(String name) {
        return fileInfoRepository.findByFileName(name);
    }

    public boolean fileNameIsOfCorrectPattern(String fileName) {
        return Pattern.compile("^(0[1-9]|[12]\\d|3[01])(0[1-9]|1[0-2])\\d{4}_(0[1-9]|1\\d|2[0-3])[0-5][0-9][0-5][0-9]\\.[a-z]+").matcher(fileName).matches();
    }

    public FileInfo saveFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String[] fileNameArray = fileName.split("\\.");
        String[] timestamp = fileNameArray[0].split("_");
        int[] timestampInt = new int[6];
        for (int i = 0, j = 0; i < 2; i++, j += 3) {
            String timestampPart = timestamp[i];
            timestampInt[j] = Integer.parseInt(timestampPart.substring(0, 2));
            timestampInt[j + 1] = Integer.parseInt(timestampPart.substring(2, 4));
            timestampInt[j + 2] = Integer.parseInt(timestampPart.substring(4));
        }
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, fileName);
        FileInfo fileInfo = new FileInfo();
        LocalDateTime ltd;
        try {
            ltd = LocalDateTime.of(timestampInt[2], timestampInt[1], timestampInt[0], timestampInt[3], timestampInt[4], timestampInt[5]);
            fileInfo.setFileName(fileName);
            fileInfo.setFileDate(ltd);
            fileInfo.setFilePath(fileNameAndPath.toString());
            Files.write(fileNameAndPath, file.getBytes());
            fileInfoRepository.save(fileInfo);
        } catch (DateTimeException ignored) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileInfo;
    }
}
