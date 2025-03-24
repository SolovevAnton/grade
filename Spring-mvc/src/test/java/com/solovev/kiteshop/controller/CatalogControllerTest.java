package com.solovev.kiteshop.controller;

import com.solovev.kiteshop.common.APINamings;
import com.solovev.kiteshop.config.SessionAttributesControllerAdvice;
import com.solovev.kiteshop.model.order.Cart;
import com.solovev.kiteshop.service.ProductService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

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

        private static Stream<Arguments> provideParameterValuesAnsSorts() {
            return Stream.of(
                    Arguments.of("model,desc", Sort.by("model").descending()),
                    Arguments.of("price,asc", Sort.by("price").ascending()),
                    Arguments.of("price,desc", Sort.by("price").descending()),
                    Arguments.of("productionYear,asc", Sort.by("productionYear").ascending()),
                    Arguments.of("productionYear,desc", Sort.by("productionYear").descending()));
        }

        @ParameterizedTest
        @MethodSource("provideParameterValuesAnsSorts")
        public void argumentsShouldFormCorrectSortAndReturnOkStatus(String sortParameterValues, Sort expectedSort)
                throws Exception {
            //given
            var requestBuilder = get(APINamings.CATALOG).param(SORT_ARGUMENT_NAME, sortParameterValues);
            //when
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk());
            //then
            verify(productService, times(1)).get(expectedSort);
        }

        @Test
        public void sortWithNonExistentFieldShouldReturnBadRequest() throws Exception {
            //given
            Sort expectedCorruptedSort = Sort.by(CORRUPTED_SORT_PARAMETER);
            when(productService.get(expectedCorruptedSort)).thenThrow(PropertyReferenceException.class);
            var requestBuilder = get(APINamings.CATALOG).param(SORT_ARGUMENT_NAME, CORRUPTED_SORT_PARAMETER);
            //when
            mockMvc.perform(requestBuilder)
                    .andExpect(status().is4xxClientError());
        }

        @Test
        public void sortNotMappedCorrectlyFormArgumentsShouldResultInDefaultSort() throws Exception {
            Sort expectedDefaultSort = Sort.by("model");
            var requestBuilder = get(APINamings.CATALOG).param("sortByCorrupted", "price,desc");
            //when
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk());
            //then
            verify(productService, times(1)).get(expectedDefaultSort);
        }

        @Test
        public void NoArgumentsShouldResultInDefaultSort() throws Exception {
            Sort expectedDefaultSort = Sort.by("model");
            var requestBuilder = get(APINamings.CATALOG);
            //when
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk());
            //then
            verify(productService, times(1)).get(expectedDefaultSort);
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
}