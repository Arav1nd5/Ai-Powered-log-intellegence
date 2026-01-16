# AI-Powered Log Intelligence Engine

A lightweight, explainable, production-minded engine that combines deterministic rules with AI to detect and prioritize incidents in noisy application logs.

---

## TL;DR (What this project does)
- Read logs in many formats, normalize them, and detect incidents.
- Use deterministic rules for safety-critical detection.
- Use AI to provide semantic understanding, confidence scores, and reasons.
- Merge rule and AI outputs with a confidence-based fallback so AI is advisory — never the single source of truth.
- Produce human-friendly actions: log, notify, or escalate.

---

## The problem (simple)
Modern apps produce huge volumes of logs. Most logs are harmless; a few indicate real problems (payment failures, DB outages, security breaches). Manually finding those actionable logs is slow, error-prone, and not scalable.

Existing tools either:
- Rely on brittle rules (lots of false negatives/positives), or
- Rely purely on AI (hard to trust in production).

This project bridges both approaches: rules for safety and AI for semantic detection and explainability.

---

## Key ideas (in plain English)
- Use rules for obvious, high-risk cases (e.g., FATAL → immediate escalate).
- Use AI to find hidden intent (e.g., “payment stuck” without an ERROR level).
- Combine both using confidence thresholds so the final decision is traceable and safe.
- Suppress low-value noise and group non-critical logs into summaries.

---

## What you get (features)
- Log normalization for heterogeneous formats (timestamp, level, message).
- Rule engine for deterministic mapping (severity, category).
- AI analyzer that returns: action, confidence, explanation.
- Incident detection engine that merges rule + AI and decides a final action.
- Action resolver to map decisions to actions: Log, Notify, Escalate.
- Console UI to run analysis, view summaries, and simulate workflows.
- Extensible design (add new actions: Slack, PagerDuty, email, etc.)

---

## High-level architecture (text diagram)
Console UI (MenuController)
  ↓
Incident Detection Engine (merges rule + AI)
  ├─ Rule Engine (deterministic)
  └─ AI Analyzer (semantic)
  ↓
Action Resolver
  ↓
Actions (LogAction, NotifyAction, EscalateAction)

---

## Layer-by-layer (short & simple)
- Log Normalization (util): Parse many formats into a single LogEntry object.
- Domain Models (model): LogEntry, Incident, AIResult, RuleResult — keep data clear and auditable.
- Rule Engine: Fast, deterministic checks (e.g., FATAL => HIGH).
- AI Analyzer: Returns semantic action, confidence, and human-readable reason.
- Incident Detection Engine: Merges rule + AI; applies confidence-based fallback.
- Action Resolver & Actions: Encapsulate "what to do" (log, notify, escalate).
- Controller (MenuController): Console flow, rate-limit, aggregation.
- Configuration (config/application.properties): API keys and environment settings.

---

## How it works (end-to-end)
1. Choose "Analyze logs" from the console.
2. Logs are read and normalized.
3. Rules flag obvious incidents.
4. For everything else, AI provides semantic analysis and confidence.
5. The engine merges results:
   - If rules say escalate, escalate.
   - Else, if AI confidence is high enough, follow AI recommendation.
   - Else, fall back to conservative/default behavior.
6. Critical incidents are escalated immediately; non-critical logs are grouped and summarized.

---

## Why this design is safe & practical
- Rules guarantee safety for critical cases.
- AI provides explainability (reason + confidence) rather than opaque decisions.
- Confidence thresholds and fallbacks prevent AI from being a single point of failure.
- Layered architecture makes testing, auditing, and extension straightforward.

---

## How to get started (quick)
1. Clone the repo:
```bash
git clone https://github.com/Arav1nd5/Ai-Powered-log-intellegence.git
cd Ai-Powered-log-intellegence
```

2. Configure AI key:
- Copy example config:
```bash
cp config/application.properties.example config/application.properties
```
- Edit `config/application.properties` and set:
```
GEMINI_API_KEY=YOUR_API_KEY_HERE
```

3. Compile (simple Java approach):
- On Unix/macOS:
```bash
javac -d bin $(find src -name "*.java")
```
- On Windows (PowerShell):
```powershell
javac -d bin (Get-ChildItem -Recurse -Filter *.java | Select-Object -ExpandProperty FullName)
```

4. Run:
```bash
java -cp bin Main
```
5. Choose from the menu:
- `1. Analyze logs`
- `2. Exit`

---

## Sample output (friendly)
AI Decision:
  Action    : ESCALATE  
  Confidence: 0.91  
  Reason    : Payment failure blocks transactions

ESCALATED INCIDENT: PAYMENT

---- Semantic Analysis Summary (Non-critical Logs) ----
[2010-04-24 08:01:10,112] Initializing BulkOpsClient  
[N/A] Application started successfully
----------------------------------------------------

---

## Configuration notes
- Keep API keys out of source control — use `config/application.properties` or environment variables.
- Tune confidence thresholds in config to match your risk tolerance.
- Add rule patterns for domain-specific signals (e.g., payment gateway error codes).

---

## Extensibility ideas
- Add outputs: Slack, PagerDuty, SMS, webhook sinks.
- Add connectors: cloud logging services (CloudWatch, Stackdriver).
- Add persistence: store incidents in a DB for long-term audit.
- Add a lightweight web UI for richer dashboards.

---

## Design & engineering takeaways
- Hybrid = safety + semantic power.
- Explainability matters: show action, confidence, reason.
- Normalize early so downstream logic is simpler.
- Keep AI advisory: production systems must be resilient to model failures.

---

## Contributing
- Bug reports and PRs welcome. Open an issue describing the problem or improvement.
- Please follow repository code style and add tests for new behavior.
- If adding external integration (e.g., PagerDuty), keep secrets out of the repo.

---

## License & contact
- License: MIT
- Author: Arav1nd5 — feel free to open issues or PRs on the repo.

---

Thanks for building a safety-first AI log assistant — designed to help engineers surface what matters, faster and more reliably.

