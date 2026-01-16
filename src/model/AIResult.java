package model;

public class AIResult {

    private String severity;
    private String category;
    private String action;
    private double confidence;
    private String reason;

    public AIResult(String severity, String category, String action,
            double confidence, String reason) {
        this.severity = severity;
        this.category = category;
        this.action = action;
        this.confidence = confidence;
        this.reason = reason;
    }

    public String getSeverity() {
        return severity;
    }

    public String getCategory() {
        return category;
    }

    public String getAction() {
        return action;
    }

    public double getConfidence() {
        return confidence;
    }

    public String getReason() {
        return reason;
    }
}
