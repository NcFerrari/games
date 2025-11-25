package cz.games.lp.service_impl;

import cz.games.lp.service.LoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerServiceImpl implements LoggerService {

    private static LoggerService loggerService;

    private static Logger logger;

    public static <T> LoggerService getInstance(Class<T> clas) {
        if (loggerService == null) {
            logger = LoggerFactory.getLogger(clas);
            loggerService = new LoggerServiceImpl();
        }
        return loggerService;
    }

    private LoggerServiceImpl() {

    }

    @Override
    public void info(String text) {
        logger.info(text);
    }
}
