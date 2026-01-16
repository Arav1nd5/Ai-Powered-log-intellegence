package controller;

import action.Action;
import engine.ActionResolver;
import engine.IncidentDetectionEngine;
import model.AIResult;
import model.Incident;
import model.LogEntry;
import util.FileUtil;

import java.util.*;

public class MenuController {

    public void start() {

        Scanner scanner = new Scanner(System.in);
        IncidentDetectionEngine detector = new IncidentDetectionEngine();
        ActionResolver resolver = new ActionResolver();

        while (true) {

            System.out.println("---- Log Analyzing Intelligence Engine ----");
            System.out.println("1. Analyze logs");
            System.out.println("2. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();

            if (choice == 1) {

                List<LogEntry> logs = FileUtil.readLogFile("logs/sample.log");
                List<String> semanticLogs = new ArrayList<>();

                for (LogEntry log : logs) {

                    Incident incident = detector.detect(log);
                    if (incident != null) {

                        AIResult ai = incident.getAiResult();

                        if (ai.getAction().equals("LOG_ONLY") && ai.getConfidence() <= 0.5) {

                            String entry = "[" + log.getTimestamp() + "] " + log.getMessage();
                            semanticLogs.add(entry);

                        } else {

                            System.out.println("AI Decision:");
                            System.out.println("  Action    : " + ai.getAction());
                            System.out.println("  Confidence: " + ai.getConfidence());
                            System.out.println("  Reason    : " + ai.getReason());

                            Action action = resolver.resolve(incident);
                            action.execute(incident);
                            System.out.println();
                        }
                    }

                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                if (!semanticLogs.isEmpty()) {
                    System.out.println("---- Semantic Analysis Summary (Non-critical Logs) ----");
                    for (String s : semanticLogs) {
                        System.out.println(s);
                    }
                    System.out.println("----------------------------------------------------");
                }

            } else if (choice == 2) {
                System.out.println("Exiting system");
                break;
            } else {
                System.out.println("Invalid option");
            }
        }
    }
}
