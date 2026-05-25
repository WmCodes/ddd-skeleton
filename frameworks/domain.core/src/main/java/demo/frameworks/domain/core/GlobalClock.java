package demo.frameworks.domain.core;

import java.time.Clock;
import java.time.Instant;

public final class GlobalClock {
    private static Clock clock;

    private GlobalClock() {
    }

    public static Instant now() {
        return clock.instant();
    }

    public static void reset(Clock clock) {
        GlobalClock.clock = clock;
    }
}
