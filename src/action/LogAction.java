package action;

import model.Incident;

public class LogAction implements Action {
    public void execute(Incident incident) {
        System.out.println("LOGGED INCIDENT");
    }

}
