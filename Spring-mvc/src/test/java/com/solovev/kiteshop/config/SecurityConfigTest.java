package com.solovev.kiteshop.config;

import com.solovev.kiteshop.common.APINamings;
import com.solovev.kiteshop.model.order.Cart;
import com.solovev.kiteshop.model.order.Order;
import com.solovev.kiteshop.model.product.Product;
import com.solovev.kiteshop.model.user.User;
import com.solovev.kiteshop.service.OrderPositionService;
import com.solovev.kiteshop.service.OrderService;
import com.solovev.kiteshop.service.ProductService;
import com.solovev.kiteshop.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.NestedTestConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.solovev.kiteshop.common.PageableDefaultConfig.DEFAULT_PAGE_SIZE;
import static com.solovev.kiteshop.common.PageableDefaultConfig.SORT_COLUMN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class SecurityConfigTest {
    private static final String userName = "user";
    private static final String userServiceName = "userService";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    @MockBean
    private OrderPositionService orderPositionService;
    @MockBean
    private OrderService orderService;
    @Autowired
    private User userWithDetails;

    @NestedTestConfiguration(NestedTestConfiguration.EnclosingConfiguration.OVERRIDE)
    @Nested
    @WithAnonymousUser
    public class AnonymousUserTests {
        @Test
        public void anonymousUserAllowedCatalog() throws Exception {
            performGetExpectOk(APINamings.CATALOG);
        }

        @Test
        public void anonymousUserAllowedCart() throws Exception {
            performGetExpectOk(APINamings.CART);
            mockMvc.perform(post(APINamings.CART + "/1")
                            .param("quantity", "1").with(csrf()))
                    .andExpect(status().is3xxRedirection());
        }

        @Test
        public void anonymousUserAllowedLogin() throws Exception {
            performGetExpectOk(APINamings.LOGIN);
            mockMvc.perform(post(APINamings.LOGIN)
                            .param("username", "user")
                            .param("password", "pass")
                    )
                    .andExpect(status().is3xxRedirection());
        }

        @Test
        public void anonymousUserAllowedRegistration() throws Exception {
            performGetExpectOk(APINamings.REGISTRATION);

            mockMvc.perform(post(APINamings.REGISTRATION)
                            .param("username", "user")
                            .param("password", "pass"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl(APINamings.LOGIN));
        }

        @Test
        public void orderMethodsNotAllowed() throws Exception {
            Cart cart = new Cart();

            performGetAndExpectRedirectToLogin(APINamings.ORDER);
            performGetAndExpectRedirectToLogin(APINamings.ORDER + "/1");

            mockMvc.perform(
                            post(APINamings.ORDER)
                                    .sessionAttr(SessionAttributesControllerAdvice.CART_ATTR, cart).with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl(APINamings.withMain(APINamings.LOGIN)));
        }
    }


    @Test
    @WithUserDetails(value = userName, userDetailsServiceBeanName = userServiceName)
    public void basicUserOrderAllowedMethods() throws Exception {
        Cart cart = new Cart();
        Order foundOrder = mock(Order.class);
        when(foundOrder.getUser()).thenReturn(userWithDetails);
        when(orderService.createAndAddOrder(userWithDetails, cart)).thenReturn(foundOrder);
        when(orderService.getOrder(1)).thenReturn(foundOrder);

        performGetExpectOk(APINamings.ORDER);
        performGetExpectOk(APINamings.ORDER + "/1");

        mockMvc.perform(
                        post(APINamings.ORDER).with(csrf()).sessionAttr(SessionAttributesControllerAdvice.CART_ATTR,
                                cart))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void shouldLogoutSuccessfully() throws Exception {
        mockMvc.perform(post(APINamings.LOGOUT))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?logout"));
    }

    private void performGetExpectOk(String apiEndpoint) throws Exception {
        mockMvc.perform(get(apiEndpoint).with(csrf())).andExpect(status().isOk());
    }

    private void performGetAndExpectRedirectToLogin(String apiEndpoint) throws Exception {
        mockMvc.perform(get(apiEndpoint))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(APINamings.withMain(APINamings.LOGIN)));
    }

    @TestConfiguration
    public static class ConfigService {

        @Bean(name = userServiceName)
        public UserService getService(User userToReturn) {
            UserService userService = mock(UserService.class);
            when(userService.loadUserByUsername(userName)).thenReturn(userToReturn);
            return userService;
        }

        @Bean
        public User getUser() {
            User userWithDetails = mock(User.class);
            when(userWithDetails.getUsername()).thenReturn(userName);
            return userWithDetails;
        }
    }

    private final Pageable expectedDefaultPage =
            PageRequest.of(0, DEFAULT_PAGE_SIZE, Sort.by(SORT_COLUMN).descending());

    @BeforeEach
    public void setUp() throws Exception {
        Page<Product> result = new PageImpl<>(List.of(mock(Product.class)), expectedDefaultPage, 0);

        when(productService.get(any(Pageable.class))).thenReturn(result);
    }
}