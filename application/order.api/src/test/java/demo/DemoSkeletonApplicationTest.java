package demo;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

class DemoSkeletonApplicationTest {

    @Test
    void mainStartsSpringApplication() {
        try (MockedStatic<SpringApplication> mocked = Mockito.mockStatic(SpringApplication.class)) {
            DemoSkeletonApplication.main(new String[]{});
            mocked.verify(() -> SpringApplication.run(DemoSkeletonApplication.class, new String[]{}));
        }
    }
}
