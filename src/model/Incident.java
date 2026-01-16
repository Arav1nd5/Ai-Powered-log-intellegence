package model;

public class Incident {

    private String severity;
    private String category;
    private String description;
    private AIResult aiResult;

    public Incident(String severity, String category, String description, AIResult aiResult) {
        this.severity = severity;
        this.category = category;
        this.description = description;
        this.aiResult = aiResult;
    }

    public String getSeverity() {
        return severity;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public AIResult getAiResult() {
        return aiResult;
    }
}
