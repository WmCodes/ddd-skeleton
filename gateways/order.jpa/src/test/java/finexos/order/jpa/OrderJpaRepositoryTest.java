package finexos.order.jpa;

import finexos.order.core.Order;
import finexos.order.core.OrderId;
import finexos.order.core.OrderStatus;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SuppressWarnings("checkstyle:MagicNumber")
class OrderJpaRepositoryTest {

    @Container
    @SuppressWarnings("checkstyle:VisibilityModifier")
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Autowired
    private EntityManager entityManager;

    private OrderJpaRepository repository;

    @BeforeEach
    void setUp() {
        repository = new OrderJpaRepository(entityManager);
    }

    @Test
    @DisplayName("save and findById persist and retrieve order")
    void saveAndFindById() {
        Order order = Order.create("customer-1");
        order.addItem("product-1", 2, 10.0);
        repository.save(order);

        Order found = repository.findById(order.id());

        assertThat(found.customerId()).isEqualTo("customer-1");
        assertThat(found.status()).isEqualTo(OrderStatus.CREATED);
        assertThat(found.items()).hasSize(1);
        assertThat(found.totalAmount()).isEqualTo(20.0);
    }

    @Test
    @DisplayName("findById throws when order not found")
    void findByIdNotFound() {
        assertThatThrownBy(() -> repository.findById(new OrderId("missing")))
                .isInstanceOf(finexos.frameworks.domain.core.AggregateNotFoundException.class);
    }

    @Test
    @DisplayName("findAll returns all orders")
    void findAll() {
        Order order1 = Order.create("customer-1");
        Order order2 = Order.create("customer-2");
        repository.save(order1);
        repository.save(order2);

        List<Order> result = repository.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("deleteById removes order")
    void deleteById() {
        Order order = Order.create("customer-1");
        repository.save(order);

        repository.deleteById(order.id());

        assertThatThrownBy(() -> repository.findById(order.id()))
                .isInstanceOf(finexos.frameworks.domain.core.AggregateNotFoundException.class);
    }

    @Test
    @DisplayName("save updates existing order status")
    void saveUpdateStatus() {
        Order order = Order.create("customer-1");
        repository.save(order);

        order.process();
        repository.save(order);

        Order found = repository.findById(order.id());
        assertThat(found.status()).isEqualTo(OrderStatus.PROCESSING);
    }

    @Test
    @DisplayName("persist and load order with PROCESSING status")
    void saveAndFindByIdProcessing() {
        Order order = Order.create("customer-1");
        order.process();
        repository.save(order);

        Order found = repository.findById(order.id());

        assertThat(found.status()).isEqualTo(OrderStatus.PROCESSING);
    }

    @Test
    @DisplayName("persist and load order with COMPLETED status")
    void saveAndFindByIdCompleted() {
        Order order = Order.create("customer-1");
        order.process();
        order.complete();
        repository.save(order);

        Order found = repository.findById(order.id());

        assertThat(found.status()).isEqualTo(OrderStatus.COMPLETED);
    }

    @Test
    @DisplayName("persist and load order with CANCELLED status")
    void saveAndFindByIdCancelled() {
        Order order = Order.create("customer-1");
        order.cancel();
        repository.save(order);

        Order found = repository.findById(order.id());

        assertThat(found.status()).isEqualTo(OrderStatus.CANCELLED);
    }

    @Test
    @DisplayName("save update with new items")
    void saveUpdateWithItems() {
        Order order = Order.create("customer-1");
        order.addItem("product-1", 2, 10.0);
        repository.save(order);

        order.addItem("product-2", 1, 25.0);
        repository.save(order);

        Order found = repository.findById(order.id());
        assertThat(found.items()).hasSize(2);
        assertThat(found.totalAmount()).isEqualTo(45.0);
    }
}
