package account.exception.handler;

import account.model.logger.Log;
import account.model.logger.LogEvent;
import account.model.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Locale;

@Component
public class ApiAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException exc) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            String email = auth.getName().toLowerCase(Locale.ROOT);
            String path = request.getRequestURI();
            logger.log(Log.builder()
                    .date(LocalDateTime.now())
                    .action(LogEvent.ACCESS_DENIED)
                    .subject(email)
                    .object(path)
                    .path(path)
                    .build()
            );
        }

        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied!");
    }

    public ApiAccessDeniedHandler(@Autowired Logger logger) {
        this.logger = logger;
    }

    private final Logger logger;
}
