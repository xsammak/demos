package org.xsammak.demos.scaleapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Redirects root URI to Swagger-UI page.
 * Using relative redirect to avoid protocol changing from HTTPS to HTTP in redirect.
 * Use configuration: server.tomcat.use-relative-redirects=true
 *
 * @author xsammak
 */
@Controller
@RequestMapping("/")
public class RootController {

    public static final String SWAGGERR_UI_REDIRECT = "redirect:/swagger-ui.html";

    @GetMapping()
    public String swaggerUi() {
        return SWAGGERR_UI_REDIRECT;
    }
}
