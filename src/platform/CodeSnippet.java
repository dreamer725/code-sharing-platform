package platform;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

@Entity
@JsonIgnoreProperties({"uuid", "restricted", "restrictedByTime", "restrictedByViews", "actualDate", "checkDate"})
public class CodeSnippet {
    @Id
    @Type(type = "uuid-char")
    private UUID uuid = UUID.randomUUID();
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String date;

    private LocalDateTime actualDate;

    private LocalDateTime checkDate;

    @Column(nullable = false)
    private int time;

    @Column(nullable = false)
    private int views;
    private boolean restricted;
    private boolean restrictedByTime;

    private boolean restrictedByViews;
    public CodeSnippet(String code, int time, int views) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//        formatter.withZone(ZoneId.systemDefault()).format(Instant.now());

        this.code = code;
        this.date = LocalDateTime.now().format(formatter);
        this.actualDate = LocalDateTime.now();
        this.checkDate = actualDate;
        this.time = time;
        this.views = views;

        if (time > 0) {
            this.restrictedByTime = true;
        }
        if (views > 0) {
            this.restrictedByViews = true;
        }
        this.restricted = (restrictedByTime || restrictedByViews);
    }

    public CodeSnippet() {
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getCode() {
        return code;
    }

    public String getDate() {
        return date;
    }

    public LocalDateTime getActualDate() {
        return actualDate;
    }

    public LocalDateTime getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(LocalDateTime checkDate) {
        this.checkDate = checkDate;
    }

    public int getTime() {
        return time;
    }

    public int getViews() {
        return views;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeSnippet that = (CodeSnippet) o;
        return code.equals(that.code) && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, date);
    }

    @Override
    public String toString() {
        return "CodeSnippet{" +
                "code='" + code + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public boolean isRestricted() {
        return restricted;
    }

    public boolean isRestrictedByTime() {
        return restrictedByTime;
    }

    public boolean isRestrictedByViews() {
        return restrictedByViews;
    }
}

