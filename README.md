# AI-Powered Log Intelligence Engine ( Console Based )

A lightweight, explainable, production-minded engine that combines deterministic rules with AI to detect and prioritize incidents in noisy application logs.

-------------------------------------------------------------------------------------------------------------------------------------------

[![Language: Java](https://img.shields.io/badge/Language-Java-orange.svg)](#)
[![Status: Prototype](https://img.shields.io/badge/Status-Prototype-yellowgreen.svg)](#)

❓Quick Overview
- Reads logs across formats and normalizes them.
- Filters noise using deterministic rules.
- Uses AI for semantic understanding with confidence scores.
- Always explains decisions and falls back safely when AI is uncertain.
- Final actions: LOG_ONLY, NOTIFY, or ESCALATE.

-------------------------------------------------------------------------------------------------------------------------------------------

## Why this project exists
Most systems produce too many logs. Rules alone miss semantic issues; AI alone can be unsafe or unpredictable. This project combines both:
- Rules for safety and deterministic checks.
- AI for semantic reasoning and context.
- Confidence-based arbitration so AI is never blindly trusted.

Result: fewer false alarms, transparent reasoning, and actionable incidents.

-------------------------------------------------------------------------------------------------------------------------------------------

🧠Core ideas
- Normalize early — convert any log format into one canonical structure.
- Rule Engine handles obvious failures deterministically.
- AI Analyzer provides semantic labels, reasons, and a confidence score.
- ActionResolver decides final action using rule results + AI + confidence thresholds.
- Explainability: every decision includes a human-readable rationale.

-------------------------------------------------------------------------------------------------------------------------------------------

🧱 Architecture (at a glance)
MenuController
  ↓
IncidentDetectionEngine
  ├─ Rule Engine (deterministic)
  └─ AI Analyzer (semantic + confidence)
  ↓
ActionResolver
  ↓
Log | Notify | Escalate

Design goals: layered separation, testability, and safe AI integration.

-------------------------------------------------------------------------------------------------------------------------------------------

## Features
- Flexible log normalization (supports multiple formats)
- Regex-driven rule engine for deterministic safety checks
- AI-driven semantic analysis with confidence
- Confidence-based merge logic (rules can override poor-confidence AI)
- Explainable decisions (human-readable reasons)
- Console-first flow with summaries and rate-limiting
- Extensible action hooks (Slack, Email, PagerDuty, persistence)

-------------------------------------------------------------------------------------------------------------------------------------------

## Decision model summary

- Rule Engine: deterministic verdicts (HIGH_PRIORITY issues)
- AI Analyzer: semantic verdict + confidence (0.0–1.0) + textual reason
- ActionResolver:
  - If rule says ESCALATE → ESCALATE
  - Else if AI confidence ≥ threshold → follow AI recommendation
  - Else → conservative fallback (LOG_ONLY or NOTIFY depending on rule hints)

Example actions:
- LOG_ONLY — store and summarize
- NOTIFY — send to monitoring channels
- ESCALATE — page on-call / create incident

-------------------------------------------------------------------------------------------------------------------------------------------

## How it works (step-by-step)
1. User selects "Analyze logs"
2. Log Normalizer parses raw logs → canonical events
3. Rule Engine scans events for deterministic hits
4. AI Analyzer runs semantic classification + returns reason + confidence
5. Detection Engine merges rule + AI safely via ActionResolver
6. Actions execute immediately; low-value logs are summarized at end

-------------------------------------------------------------------------------------------------------------------------------------------

## Sample console output

```
AI Decision:
  Action    : ESCALATE
  Confidence: 0.91
  Reason    : Payment failure blocks transactions
ESCALATED INCIDENT: PAYMENT

---- Semantic Analysis Summary ----
[2010-04-24 08:01:10,112] Initializing BulkOpsClient
[N/A] Application started successfully
```

-------------------------------------------------------------------------------------------------------------------------------------------

🛠️ Tech stack
- Java (console backend)
- Regex parsing for normalization
- Gemini AI (semantic analysis / embeddings)
- Plain file-based configuration

-------------------------------------------------------------------------------------------------------------------------------------------

▶️ Quick start 

1. Clone
```bash
git clone https://github.com/Arav1nd5/Ai-Powered-log-intellegence.git
cd Ai-Powered-log-intellegence
```

2. Configure
```bash
GEMINI_API_KEY="YOUR_API_KEY_HERE"
```

3. Build & Run (examples)
- macOS / Linux (javac)
```bash
find . -name "*.java" > sources.txt
javac -d bin @sources.txt
java -cp bin Main
```

- Windows (PowerShell)
```powershell
javac -d bin (Get-ChildItem -Recurse -Filter *.java).FullName
java -cp bin Main
```

Notes:
- The project assumes Java is installed (JDK 11+ recommended).
- For large projects, use a build tool (Maven/Gradle) to simplify compilation.

-------------------------------------------------------------------------------------------------------------------------------------------

## Configuration highlights
- config/application.properties
  - GEMINI_API_KEY — required to enable AI Analyzer

Example application.properties:
```
GEMINI_API_KEY = YOUR_API_KEY
```

-------------------------------------------------------------------------------------------------------------------------------------------

## Design principles
- Normalize early
- Explain every AI decision
- Suppress noise, not information
- Fail safe — never fail open
- Keep AI advisory, not authoritative

-------------------------------------------------------------------------------------------------------------------------------------------

## Contributing
1. Open an issue to discuss large changes.
2. Fork, implement, and submit a PR.
3. Keep changes small and focused. Provide tests for new logic (rules, parsing, action resolver).
4. Follow the existing code style and add documentation to config files.

-------------------------------------------------------------------------------------------------------------------------------------------

## Author
Arav1nd5 — open to feedback, issues, and discussions.

- GitHub: [Arav1nd5](https://github.com/Arav1nd5)
- Project: [Ai-Powered-log-intellegence](https://github.com/Arav1nd5/Ai-Powered-log-intellegence)



