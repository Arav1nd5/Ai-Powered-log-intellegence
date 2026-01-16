package action;

import model.Incident;

public class NotifyAction implements Action {
    public void execute(Incident incident) {
        System.out.println("NOTIFICATION SENT");
    }
}
