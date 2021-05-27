package top.frank.model.po;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class EsgAltPO {

    private String globalId;

    private Integer age;

    private Double size;

    private String textValue;

    private LocalDateTime timestamp;

    public ZonedDateTime getTimestampTz() {
        return timestampTz;
    }

    public void setTimestampTz(ZonedDateTime timestampTz) {
        this.timestampTz = timestampTz;
    }

    private ZonedDateTime timestampTz;

    public String getGlobalId() {
        return globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
