package engine;

import ai.AIIncidentAnalyzer;
import model.AIResult;
import model.Incident;
import model.LogEntry;
import model.RuleResult;

public class IncidentDetectionEngine {

    private AIIncidentAnalyzer aiAnalyzer = new AIIncidentAnalyzer();

    public Incident detect(LogEntry log) {

        RuleResult ruleResult = applyRules(log);
        AIResult aiResult = aiAnalyzer.analyze(log.getMessage());

        String severity;
        String category;

        if (aiResult.getConfidence() < 0.6 && ruleResult != null) {
            severity = ruleResult.getSeverity();
            category = ruleResult.getCategory();
        } else {
            severity = aiResult.getSeverity();
            category = aiResult.getCategory();
        }

        if (severity.equals("LOW") && aiResult.getAction().equals("LOG_ONLY")) {
            return null;
        }

        return new Incident(
                severity,
                category,
                log.getMessage(),
                aiResult);
    }

    private RuleResult applyRules(LogEntry log) {

        String level = log.getLevel();
        String msg = log.getMessage().toLowerCase();

        if (level.equals("FATAL")) {
            return new RuleResult("HIGH", "SYSTEM");
        }

        if (level.equals("ERROR")) {
            if (msg.contains("database")) {
                return new RuleResult("HIGH", "DATABASE");
            }
            return new RuleResult("MEDIUM", "APPLICATION");
        }

        if (level.equals("WARN")) {
            return new RuleResult("LOW", "PERFORMANCE");
        }

        return null;
    }
}
