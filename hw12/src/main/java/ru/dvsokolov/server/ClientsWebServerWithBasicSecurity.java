package ru.dvsokolov.server;

import com.google.gson.Gson;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.security.Constraint;
import ru.dvsokolov.dao.ClientDao;
import ru.dvsokolov.services.TemplateProcessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientsWebServerWithBasicSecurity extends ClientsWebServerSimple {
    private static final String ROLE_NAME_USER = "user";
    private static final String ROLE_NAME_ADMIN = "admin";
    private static final String CONSTRAINT_NAME = "auth";

    private final LoginService loginService;

    public ClientsWebServerWithBasicSecurity(int port,
                                             LoginService loginService,
                                             ClientDao clientDao,
                                             Gson gson,
                                             TemplateProcessor templateProcessor) {
        super(port, clientDao, gson, templateProcessor);
        this.loginService = loginService;
    }

    protected Handler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
        Constraint constraint = new Constraint();
        constraint.setName(CONSTRAINT_NAME);
        constraint.setAuthenticate(true);
        constraint.setRoles(new String[]{ROLE_NAME_USER, ROLE_NAME_ADMIN});

        List<ConstraintMapping> constraintMappings = new ArrayList<>();
        Arrays.stream(paths).forEachOrdered(path -> {
            ConstraintMapping mapping = new ConstraintMapping();
            mapping.setPathSpec(path);
            mapping.setConstraint(constraint);
            constraintMappings.add(mapping);
        });

        ConstraintSecurityHandler security = new ConstraintSecurityHandler();
        //как декодировать стороку с юзером:паролем https://www.base64decode.org/
        security.setAuthenticator(new BasicAuthenticator());

        security.setLoginService(loginService);
        security.setConstraintMappings(constraintMappings);
        security.setHandler(new HandlerList(servletContextHandler));

        return security;

    }
}
