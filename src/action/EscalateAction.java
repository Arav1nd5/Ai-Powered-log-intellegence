package action;

import model.Incident;

public class EscalateAction implements Action {
    public void execute(Incident incident) {
        System.out.println("ESCALATED INCIDENT: " + incident.getCategory());
    }

}
