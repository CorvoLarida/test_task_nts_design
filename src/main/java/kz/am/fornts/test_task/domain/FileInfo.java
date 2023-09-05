package kz.am.fornts.test_task.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "files_info")
public class FileInfo {
    private static final String datePattern = "dd.MM.yyyy HH:mm:ss";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String fileName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = datePattern)
    private LocalDateTime fileDate;
    private String filePath;

    public FileInfo() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getFileDate() {
        return fileDate;
    }

    public void setFileDate(LocalDateTime fileDate) {
        this.fileDate = fileDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public static String getDatePattern() {
        return datePattern;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", fileDate=" + fileDate +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
