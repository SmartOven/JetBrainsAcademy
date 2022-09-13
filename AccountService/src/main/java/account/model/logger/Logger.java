package account.model.logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * LogService
 */
@Service
public class Logger {

    public void log(LogEvent action, String subject, String object, String path) {
        Log log = Log.builder()
                .date(LocalDateTime.now())
                .action(action)
                .subject(subject)
                .object(object)
                .path(path)
                .build();
        log(log);
    }

    public void log(Log log) {
        repository.save(log);
    }

    public List<Log> getLogs() {
        return repository.findAll();
    }

    public Logger(@Autowired LogRepository repository) {
        this.repository = repository;
    }

    private final LogRepository repository;
}
