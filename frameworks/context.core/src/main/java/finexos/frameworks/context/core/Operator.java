package finexos.frameworks.context.core;

import java.util.Collections;
import java.util.Set;

public class Operator {
    private final String id;
    private final String name;
    private final Set<String> authorities;

    public Operator(String id, String name, Set<String> authorities) {
        this.id = id;
        this.name = name;
        this.authorities = authorities;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Set<String> authorities() {
        return Collections.unmodifiableSet(authorities);
    }
}
