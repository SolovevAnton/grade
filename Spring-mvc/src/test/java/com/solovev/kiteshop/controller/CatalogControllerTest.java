package com.solovev.kiteshop.controller;

import com.solovev.kiteshop.common.APINamings;
import com.solovev.kiteshop.config.SessionAttributesControllerAdvice;
import com.solovev.kiteshop.model.order.Cart;
import com.solovev.kiteshop.model.product.Product;
import com.solovev.kiteshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CatalogController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CatalogControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;

    @Nested
    public class getMethodTestsWithoutCartSessionAttribute {
        private static final String SORT_ARGUMENT_NAME = "sort";
        private static final String CORRUPTED_SORT_PARAMETER = "nonExistingField";

        private static List<Arguments> provideParameterValuesAnsSorts() {
            return List.of(
                    Arguments.of("0", "1", "model,desc", PageRequest.of(0, 1, Sort.by("model").descending())),
                    Arguments.of("0", "1", "price,asc", PageRequest.of(0, 1, Sort.by("price").ascending())),
                    Arguments.of("0", "1", "price,desc", PageRequest.of(0, 1, Sort.by("price").descending())),
                    Arguments.of("0", "1", "productionYear,asc",
                            PageRequest.of(0, 1, Sort.by("productionYear").ascending())),
                    Arguments.of("0", "1", "productionYear,desc",
                            PageRequest.of(0, 1, Sort.by("productionYear").descending())));
        }

        @ParameterizedTest
        @MethodSource("provideParameterValuesAnsSorts")
        public void argumentsShouldFormCorrectSortAndReturnOkStatus(String page, String size,
                                                                    String sortParameterValues, Pageable expectedSort)
                throws Exception {

            //given
            var requestBuilder = get(APINamings.CATALOG)
                    .param("page", page)
                    .param("size", size)
                    .param(SORT_ARGUMENT_NAME, sortParameterValues);
            //when
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk());
            //then
            verify(productService, times(1)).get(expectedSort);
        }

        @Test
        public void sortWithNonExistentFieldShouldReturnBadRequest() throws Exception {
            //given
            when(productService.get(any(Pageable.class))).thenThrow(PropertyReferenceException.class);
            var requestBuilder = get(APINamings.CATALOG).param(SORT_ARGUMENT_NAME, CORRUPTED_SORT_PARAMETER);
            //when
            mockMvc.perform(requestBuilder)
                    .andExpect(status().is4xxClientError());
        }

        @Test
        public void sortNotMappedCorrectlyFormArgumentsShouldResultInDefaultSort() throws Exception {
            var requestBuilder = get(APINamings.CATALOG).param("sortByCorrupted", "price,desc");
            //when
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk());
            //then
            verify(productService, times(1)).get(expectedDefaultPage);
        }

        @Test
        public void NoArgumentsShouldResultInDefaultSort() throws Exception {
            var requestBuilder = get(APINamings.CATALOG);
            //when
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk());
            //then
            verify(productService, times(1)).get(expectedDefaultPage);
        }

    }

    @Nested
    public class getMethodTestsWithCartSessionAttribute {
        private static final String cartAttributeName = SessionAttributesControllerAdvice.CART_ATTR;
        private static final String viewCartAttributeName = "cart";

        @Test
        public void shouldAddExistingInSessionCartToModel() throws Exception {
            Cart existingCart = new Cart();

            var model = mockMvc
                    .perform(get(APINamings.CATALOG).sessionAttr(cartAttributeName, existingCart))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute(cartAttributeName, existingCart))
                    .andReturn().getModelAndView().getModel();

            assertSame(existingCart, model.get(viewCartAttributeName));
        }

        @Test
        public void shouldCreateNonExistentInSessionCartAndAndToModelAndSession() throws Exception {

            var cartInSession = mockMvc
                    .perform(get(APINamings.CATALOG))
                    .andExpect(model().attributeExists(viewCartAttributeName))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getRequest()
                    .getSession()
                    .getAttribute(cartAttributeName);

            assertNotNull(cartInSession);
            //test its empty cart
            assertEquals(new Cart(), cartInSession);
        }

    }

    private final Pageable expectedDefaultPage = PageRequest.of(0, 10, Sort.by("productionYear").descending());

    @BeforeEach
    public void setUp() throws Exception {
        Page<Product> result = new PageImpl<>(List.of(mock(Product.class)), expectedDefaultPage, 0);

        when(productService.get(any(Pageable.class))).thenReturn(result);
    }
}