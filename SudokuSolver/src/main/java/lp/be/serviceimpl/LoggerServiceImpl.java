package lp.be.serviceimpl;

import lp.be.service.LoggerService;
import org.apache.log4j.Logger;

public class LoggerServiceImpl implements LoggerService {

    private static LoggerService loggerService;
    private static Logger log;

    public static <T> LoggerService getInstance(Class<T> classCalledLog) {
        if (loggerService == null) {
            loggerService = new LoggerServiceImpl();
        }
        log = Logger.getLogger(classCalledLog);
        return loggerService;
    }

    private LoggerServiceImpl() {

    }

    @Override
    public Logger getLog() {
        return log;
    }
}
