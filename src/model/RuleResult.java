package model;

public class RuleResult {

    private String severity;
    private String category;

    public RuleResult(String severity, String category) {
        this.severity = severity;
        this.category = category;
    }

    public String getSeverity() {
        return severity;
    }

    public String getCategory() {
        return category;
    }
}
