package LibBaseDto.DtoReport;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class BaseReport {

    String tableName;
    Long userId;
    LocalDate dateFrom;
    LocalDate dateTo;
    String category;
    BigDecimal sum;
    List<BaseReport> baseReportsList;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public List<BaseReport> getBaseReportsList() {
        return baseReportsList;
    }

    public void setBaseReportsList(List<BaseReport> baseReportsList) {
        this.baseReportsList = baseReportsList;
    }
    
}
