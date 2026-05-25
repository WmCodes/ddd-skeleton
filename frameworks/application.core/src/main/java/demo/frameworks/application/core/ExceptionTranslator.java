package demo.frameworks.application.core;

public interface ExceptionTranslator {
    RuntimeException translate(Throwable throwable);

    String translate(String message);
}
