package tec.bd.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BlockbusterLog {
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/YYYY");

    private int logId;
    private String tableName;
    private Date createdOn;
    private String entryText;

    public BlockbusterLog() {

    }

    public BlockbusterLog(int logId, String tableName, Date createdOn, String entryText) {
        this.logId = logId;
        this.tableName = tableName;
        this.createdOn = createdOn;
        this.entryText = entryText;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createOn) {
        this.createdOn = createOn;
    }

    public String getEntryText() {
        return entryText;
    }

    public void setEntryText(String entryText) {
        this.entryText = entryText;
    }

    public String getCreatedOnOnly() {
        return DATE_FORMATTER.format(this.createdOn);
    }
}
