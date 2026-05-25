package finexos.order.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import finexos.order.application.OrderUseCase;
import finexos.order.application.dto.OrderRequest;
import finexos.order.application.dto.OrderResponse;
import finexos.order.core.OrderId;
import finexos.order.core.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderUseCase orderUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public OrderUseCase orderUseCase() {
            return mock(OrderUseCase.class);
        }
    }

    @Test
    @DisplayName("POST /api/orders creates order and returns 201")
    void createOrder() throws Exception {
        OrderResponse response = new OrderResponse(
                "order-1", "customer-1", List.of(),
                OrderStatus.CREATED, 0.0, LocalDateTime.now());

        when(orderUseCase.createOrder(any(OrderRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new OrderRequest("customer-1"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("order-1"))
                .andExpect(jsonPath("$.customerId").value("customer-1"));
    }

    @Test
    @DisplayName("GET /api/orders/{id} returns order")
    void getOrder() throws Exception {
        OrderResponse response = new OrderResponse(
                "order-1", "customer-1", List.of(),
                OrderStatus.CREATED, 0.0, LocalDateTime.now());

        when(orderUseCase.getOrder(any(OrderId.class))).thenReturn(response);

        mockMvc.perform(get("/api/orders/order-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("order-1"));
    }

    @Test
    @DisplayName("GET /api/orders returns list")
    void listOrders() throws Exception {
        when(orderUseCase.listOrders()).thenReturn(List.of());

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("POST /api/orders/{id}/cancel returns cancelled order")
    void cancelOrder() throws Exception {
        OrderResponse response = new OrderResponse(
                "order-1", "customer-1", List.of(),
                OrderStatus.CANCELLED, 0.0, LocalDateTime.now());

        when(orderUseCase.cancelOrder(any(OrderId.class))).thenReturn(response);

        mockMvc.perform(post("/api/orders/order-1/cancel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELLED"));
    }

    @Test
    @DisplayName("POST /api/orders/{id}/process returns processing order")
    void processOrder() throws Exception {
        OrderResponse response = new OrderResponse(
                "order-1", "customer-1", List.of(),
                OrderStatus.PROCESSING, 0.0, LocalDateTime.now());

        when(orderUseCase.processOrder(any(OrderId.class))).thenReturn(response);

        mockMvc.perform(post("/api/orders/order-1/process"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PROCESSING"));
    }

    @Test
    @DisplayName("POST /api/orders/{id}/complete returns completed order")
    void completeOrder() throws Exception {
        OrderResponse response = new OrderResponse(
                "order-1", "customer-1", List.of(),
                OrderStatus.COMPLETED, 0.0, LocalDateTime.now());

        when(orderUseCase.completeOrder(any(OrderId.class))).thenReturn(response);

        mockMvc.perform(post("/api/orders/order-1/complete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }
}
