# AI-Powered Log Intelligence Engine
Lightweight, explainable, production-minded engine that blends deterministic rules with AI to find and prioritize incidents in noisy logs.

[![Demo badge](https://img.shields.io/badge/demo-interactive-green)](#try-it-now)
[![License](https://img.shields.io/badge/license-MIT-blue)](LICENSE)
[![Issues Welcome](https://img.shields.io/badge/contributions-welcome-orange)](https://github.com/Arav1nd5/Ai-Powered-log-intellegence/issues)

---

Why you'll love this project
- Hybrid safety-first architecture: rules + AI with confidence fallbacks.
- Explainable decisions: action + confidence + reason.
- Format-agnostic: normalize logs early so logic stays simple.
- Extensible: add integrations (Slack, PagerDuty) easily.

Table of contents
- Quick TL;DR
- Interactive: Try it now (no AI key required)
- Interactive Scenarios (copy & run)
- How it works (short)
- Quickstart (run locally)
- Tuning & Tips
- Extending the project
- FAQ
- Contributing & Contact

---

TL;DR (read in 10 seconds)
- This tool normalizes logs, applies deterministic rules, asks AI for semantic signals (when needed), merges results, and performs actions (log / notify / escalate). Rules win for safety; AI advises with confidence and human-readable reasons.

---

Try it now — interactive demo (zero setup)
This section lets you experience the behavior without an API key. The repository includes a "demo mode" you can run locally to see how the engine groups noise, notifies, and escalates.

1. Save a sample log file (copy the example below to `demo-logs.txt`).
2. Run the demo command that uses a simulated AI (no API key required).

Sample logs — copy into `demo-logs.txt`
```text
2026-01-16 09:01:10,112 INFO Starting service X
2026-01-16 09:02:15,333 WARN Slow response detected: /checkout
2026-01-16 09:02:20,001 ERROR Payment gateway timeout for order=12345
2026-01-16 09:03:00,104 INFO Background job completed
2026-01-16 09:04:00,000 FATAL OutOfMemoryError in PaymentProcessor
2026-01-16 09:05:00,500 WARN Possible brute force: failed_auth=12
```

Demo run (no API key)
- Unix / macOS:
```bash
# demo mode uses a simulated AI for instant results
java -cp bin Main --demo demo-logs.txt
```
- Windows (PowerShell):
```powershell
java -cp bin Main --demo demo-logs.txt
```

Expected demo output (friendly)
```text
[AI Simulation] Payment issue detected → ESCALATE (confidence: 0.92) - "Payment gateway timeout blocks transactions"
ESCALATED INCIDENT: PAYMENT

[FATAL] OutOfMemoryError in PaymentProcessor → IMMEDIATE ESCALATION

Summary (non-critical logs):
- 2 WARN(s) grouped: Slow response, possible brute force
- 2 INFO(s) grouped
```

Why demo mode is useful
- See the incident prioritization flow without wiring an AI API key.
- Tweak the sample logs and immediately observe rule vs AI behavior.
- Great for onboarding teammates or running workshops.

---

Interactive Scenarios — try these (copy & run)
Each scenario shows how the engine reacts to different signals. Save each scenario into a file and run the demo command above.

Scenario A — Payment stall (copy to `scenario-payment.txt`)
```text
2026-01-16 12:00:01 INFO Request: /pay
2026-01-16 12:00:02 INFO Payment attempt: order=9999 status=pending
2026-01-16 12:00:10 ERROR Connection reset while calling gateway
2026-01-16 12:00:15 INFO Retrying gateway call
```
What to expect: AI should detect "payment blocked" even if severity is not FATAL → escalate or notify depending on confidence and rule matches.

Scenario B — Database flakiness (copy to `scenario-db.txt`)
```text
2026-01-16 13:00:01 WARN DB query slow: 5200ms
2026-01-16 13:00:05 WARN DB connection pool maxed
2026-01-16 13:00:06 ERROR Failed to obtain connection
```
What to expect: Rules tag database outages for escalation; AI provides reason and confidence for the team.

Scenario C — Security anomaly (copy to `scenario-sec.txt`)
```text
2026-01-16 14:01:30 WARN multiple failed logins user=alice count=7
2026-01-16 14:01:40 WARN unusual IP range 203.0.113.45
```
What to expect: AI may surface "possible brute force" and recommend notify/escalate based on thresholds.

---

How it works (simple)
- Normalize: Parse timestamp, level, message into LogEntry.
- Rule engine: Fast deterministic checks (e.g., FATAL → escalate).
- AI analyzer: Semantic understanding + confidence + reason (advisory).
- Merger (IncidentDetectionEngine): rules + AI → final incident.
- Action resolver: choose action (Log, Notify, Escalate).

Design principle: AI advises, rules enforce safety.

---

Quickstart — run locally
1. Clone
```bash
git clone https://github.com/Arav1nd5/Ai-Powered-log-intellegence.git
cd Ai-Powered-log-intellegence
```

2. Build
- Unix/macOS:
```bash
javac -d bin $(find src -name "*.java")
```
- Windows (PowerShell):
```powershell
javac -d bin (Get-ChildItem -Recurse -Filter *.java | Select-Object -ExpandProperty FullName)
```

3. Configure (optional)
- Copy example config:
```bash
cp config/application.properties.example config/application.properties
```
- Edit `config/application.properties`:
```
GEMINI_API_KEY=YOUR_API_KEY_HERE
AI_CONFIDENCE_THRESHOLD=0.75
```
If you want to skip an AI provider for quick tests, use the `--demo` flag shown earlier.

4. Run
```bash
java -cp bin Main
# or
java -cp bin Main --demo demo-logs.txt
```

---

Tune it — become the master
- Confidence threshold: raise to be conservative, lower to be permissive.
- Add domain-specific rules (e.g., payment gateway codes).
- Add suppression patterns for noisy logs (e.g., health-checks).
- Grouping rules: adjust aggregation window to control how logs are summarized.

---

Extending integrations (quick ideas)
- Add notifiers: Slack, PagerDuty, Opsgenie, email.
- Add sinks: write incidents to DB or event stream (Kafka).
- Add ingestors: CloudWatch, Stackdriver, Datadog logs.

Tip: Implement a new Action class (e.g., SlackAction) and register it in the ActionResolver — the layered design makes this straightforward.

---

FAQ — quick answers
Q: Does the AI decide everything?
A: No. AI is advisory. Rules enforce safety (e.g., FATAL always escalates).

Q: Can I run without paying for an AI provider?
A: Yes — run in demo mode (`--demo`) to exercise behavior using simulated AI responses.

Q: Is this production-ready?
A: The design is production-minded: fallback, retries, timeouts, and explainability are core ideas. Additional hardening (persistence, observability) is recommended before wide roll-out.

---

Contributing — interactive checklist
- [ ] Star the repo ⭐
- [ ] Open an issue describing a bug or feature
- [ ] Fork → implement → open PR
- [ ] Add tests for new features
- [ ] Keep secrets out of commits

If you'd like a starter issue, search "good first issue" or open one and tag it as "help wanted".

---

Developer notes & keyboard shortcuts (for demoing live)
- Run in terminal with `--demo` to show immediate results.
- Use `--sample N` to generate N random logs for load testing (if implemented).
- Use `<details>` sections when expanding docs in workshops.

---

Security & configuration
- Never commit API keys. Use config file or environment variables.
- Limit AI calls via rate limiting in controller layer.
- Ensure audit logs record AI decisions for post-mortem.

---

Contact & license
Author: Arav1nd5 — open issues or PRs on the repo.
License: MIT (add LICENSE file).

---

Interactive walkthrough (one-minute quick-play)
1. Clone and compile.
2. Create `demo-logs.txt` from the sample above.
3. Run: `java -cp bin Main --demo demo-logs.txt`
4. Observe how fatal rules escalate immediately and how the simulated AI surfaces semantic incidents with confidence and a short reason.
5. Tweak logs and watch how outputs change — instant feedback is the fastest teacher.

---

Thanks for checking out a safety-first AI log assistant — designed to help engineers surface what matters, faster and more reliably.
If you'd like, I can:
- Add a runnable demo script (bash & PowerShell),
- Provide ready-made sample log generators,
- Or produce GIFs of the CLI in action for the README.

Would you like any of those added?
