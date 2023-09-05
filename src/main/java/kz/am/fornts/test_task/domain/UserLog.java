package kz.am.fornts.test_task.domain;

import javax.persistence.*;

@Entity
@Table(name = "users_logs")
public class UserLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String userName;
    private String logContent;

    public UserLog() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String user) {
        this.userName = user;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String context) {
        this.logContent = context;
    }

    @Override
    public String toString() {
        return "UserLog{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", logContent='" + logContent + '\'' +
                '}';
    }
}
