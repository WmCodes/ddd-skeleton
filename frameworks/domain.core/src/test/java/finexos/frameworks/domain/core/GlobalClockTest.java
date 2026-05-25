package finexos.frameworks.domain.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalClockTest {

    @BeforeEach
    void setUp() {
        GlobalClock.reset(Clock.systemUTC());
    }

    @Test
    @DisplayName("now 返回当前时间")
    void nowReturnsCurrentTime() {
        Instant now = GlobalClock.now();
        assertThat(now).isNotNull();
    }

    @Test
    @DisplayName("reset 可以设置固定时间")
    void resetSetsFixedClock() {
        Instant fixed = Instant.parse("2024-01-01T00:00:00Z");
        GlobalClock.reset(Clock.fixed(fixed, ZoneId.of("UTC")));
        assertThat(GlobalClock.now()).isEqualTo(fixed);
    }
}
