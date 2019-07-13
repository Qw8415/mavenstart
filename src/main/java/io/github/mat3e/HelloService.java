package io.github.mat3e;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

class HelloService {
    public static final String FALLBACK_NAME = "world";
    public static final Lang FALLBACK_LANG = new Lang(1L, "Hello", "en");
    private final Logger logger = LoggerFactory.getLogger(HelloService.class);
    private LangRepository repository;

    HelloService() {
        this(new LangRepository());
    }

    public HelloService(LangRepository repository) {
        this.repository = repository;
    }

    String prepareGreeting(String name, String lang) {
        Long langId;
        try {
            langId = Optional.ofNullable(lang).map(Long::valueOf).orElse(FALLBACK_LANG.getId());
        } catch (NumberFormatException e) {
            logger.warn("Non-numeric language id used: " + lang);
            langId = FALLBACK_LANG.getId();
        }
        var welcomeMsg = repository.findById(langId).orElse(FALLBACK_LANG).getWelcameMsg();
        var nameOfWelcome = Optional.ofNullable(name).orElse(FALLBACK_NAME);
        return welcomeMsg + " " + nameOfWelcome + "!";
    }
}
