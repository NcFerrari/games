package lp;

import lp.be.service.LoggerService;
import lp.be.serviceimpl.LoggerServiceImpl;
import org.apache.log4j.Logger;

public class Manager {

    private static Manager manager;
    private final LoggerService loggerService = LoggerServiceImpl.getInstance(Manager.class);
    private Logger log = loggerService.getLog();

    public static Manager getInstance() {
        if (manager == null) {
            manager = new Manager();
        }
        return manager;
    }

    private Manager() {

    }

    public static void main(String[] args) {
        getInstance();
    }
}
