package com.solovev.kiteshop.controller;

import com.solovev.kiteshop.common.APINamings;
import com.solovev.kiteshop.config.SessionAttributesControllerAdvice;
import com.solovev.kiteshop.model.order.Cart;
import com.solovev.kiteshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession;

@WebMvcTest(controllers = {CartController.class})
class CartControllerTest {
    private static final String cartAttributeName = SessionAttributesControllerAdvice.CART_ATTR;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ProductService productService;

    @Test
    public void shouldRedirectAndKeepThatItemInSession_WhenAddedNewItemToExistingCart() throws Exception {
        //given
        long productId = 1;

        Cart existingCart = new Cart();
        existingCart.addItem(productId, 1);

        var requestBuilder = post(APINamings.CART + "/" + productId)
                .param("quantity", "2")
                .sessionAttr(cartAttributeName, existingCart);
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(APINamings.CATALOG));

        assertEquals(3, existingCart.getQuantity(productId));
    }

    @Test
    public void ShouldCreateCartSessionAttribute_WhenNotExistingCart() throws Exception {
        //given
        var requestBuilder = post(APINamings.CART + "/1")
                .param("quantity", "2");
        //then
        var cart = (Cart) mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(APINamings.CATALOG))
                .andReturn()
                .getRequest()
                .getSession()
                .getAttribute(cartAttributeName);

        assertEquals(2, cart.getQuantity(1));
    }

    @BeforeEach
    public void setUpMock() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(this.webApplicationContext)
                .apply(sharedHttpSession()) // use this session across requests
                .build();
    }

}