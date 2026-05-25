package finexos.frameworks.domain.core;

import java.time.Clock;
import java.time.Instant;

public class GlobalClock {
    private static Clock clock;

    public static Instant now() {
        return clock.instant();
    }

    public static void reset(Clock clock) {
        GlobalClock.clock = clock;
    }
}
