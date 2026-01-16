package engine;

import action.Action;
import action.EscalateAction;
import action.LogAction;
import action.NotifyAction;
import model.Incident;

public class ActionResolver {

    public Action resolve(Incident incident) {

        String action = incident.getAiResult().getAction();

        if (action.equals("ESCALATE")) {
            return new EscalateAction();
        }
        if (action.equals("NOTIFY")) {
            return new NotifyAction();
        }
        return new LogAction();
    }

}
