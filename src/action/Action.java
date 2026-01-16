package action;

import model.Incident;

public interface Action {
    void execute(Incident incident);
}
