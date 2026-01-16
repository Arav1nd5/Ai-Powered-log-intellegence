package ai;

import model.AIResult;
import util.ConfigLoader;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AIIncidentAnalyzer {

    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=";

    private AIResult parseResponse(String response, String logMessage) {

        String text = response.toLowerCase();
        String msg = logMessage.toLowerCase();

        String action = "LOG_ONLY";
        String category = "GENERAL";
        String severity = "LOW";
        double confidence = 0.5;
        String reason = "Derived from semantic analysis";

        if (text.contains("payment") || msg.contains("payment")) {
            action = "ESCALATE";
            category = "PAYMENT";
            severity = "HIGH";
            confidence = 0.9;
            reason = "Payment failure blocks transactions";
        } else if (text.contains("database") || msg.contains("database")) {
            action = "ESCALATE";
            category = "DATABASE";
            severity = "HIGH";
            confidence = 0.85;
            reason = "Database failure impacts system availability";
        } else if (text.contains("security") || msg.contains("unauthorized")) {
            action = "ESCALATE";
            category = "SECURITY";
            severity = "HIGH";
            confidence = 0.95;
            reason = "Security incident detected";
        } else if (msg.contains("timeout") || msg.contains("slow")) {
            action = "NOTIFY";
            category = "PERFORMANCE";
            severity = "MEDIUM";
            confidence = 0.7;
            reason = "Performance degradation detected";
        } else if (msg.contains("exception") || msg.contains("error")) {
            action = "NOTIFY";
            category = "APPLICATION";
            severity = "MEDIUM";
            confidence = 0.65;
            reason = "Application error detected";
        }

        return new AIResult(severity, category, action, confidence, reason);
    }

    private String extractJsonBlock(String text) {
        int start = text.indexOf("{");
        int end = text.lastIndexOf("}");
        if (start == -1 || end == -1 || end <= start) {
            return "";
        }
        return text.substring(start, end + 1);
    }

    private String extractString(String json, String key) {
        int i = json.indexOf("\"" + key + "\"");
        if (i == -1)
            return "UNKNOWN";
        int start = json.indexOf(":", i) + 1;
        int q1 = json.indexOf("\"", start) + 1;
        int q2 = json.indexOf("\"", q1);
        return json.substring(q1, q2);
    }

    private double extractDouble(String json, String key) {
        int i = json.indexOf("\"" + key + "\"");
        if (i == -1)
            return 0.0;
        int start = json.indexOf(":", i) + 1;
        int end = json.indexOf(",", start);
        if (end == -1)
            end = json.indexOf("}", start);
        return Double.parseDouble(json.substring(start, end).trim());
    }

    private static final int MAX_RETRIES = 2;

    public AIResult analyze(String message) {

        int attempts = 0;

        while (attempts <= MAX_RETRIES) {
            try {
                attempts++;

                String apiKey = ConfigLoader.get("GEMINI_API_KEY");

                String prompt = """
                        Analyze this system log message and provide insight:
                        %s
                        """.formatted(message);

                String requestBody = """
                        {
                        "contents": [{
                            "parts": [{
                            "text": "%s"
                            }]
                        }]
                        }
                        """.formatted(prompt.replace("\"", "\\\""));

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(API_URL + apiKey))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                        .timeout(java.time.Duration.ofSeconds(10))
                        .build();

                HttpClient client = HttpClient.newHttpClient();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                return parseResponse(response.body(), message);

            } catch (Exception e) {
                if (attempts > MAX_RETRIES) {
                    break;
                }
            }
        }

        return new AIResult(
                "LOW",
                "GENERAL",
                "LOG_ONLY",
                0.0,
                "AI unavailable after retries");
    }
}
