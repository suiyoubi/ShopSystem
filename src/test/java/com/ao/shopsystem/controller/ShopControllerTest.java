package com.ao.shopsystem.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ao.shopsystem.controller.dto.shop.ShopResponseDto;
import com.ao.shopsystem.entity.Shop;
import com.ao.shopsystem.service.ShopService;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Tests for the {@link ShopController}
 * Created by ao on 2018-09-23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@EnableWebMvc
@SpringBootTest
public class ShopControllerTest {

    private static final Long EXIST_ID = 1L;
    private static final Long NOT_EXIST_ID = 999L;

    private static final String NOT_FOUND = "SHOP NOT FOUND";
    private static final String SHOP_NAME = "NAME";
    private static final String ADDRESS = "ADDRESS";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @MockBean
    private ShopService shopService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void findById_should_throwNotFoundException_when_noSuchIdBeingFound() throws Exception {

        doThrow(new NotFoundException(NOT_FOUND))
                .when(this.shopService)
                .findById(NOT_EXIST_ID);

        this.mvc.perform(get("/shops/".concat(NOT_EXIST_ID.toString()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findById_should_returnResponseDto_when_IdExist() throws Exception {

        doReturn(getShop()).when(this.shopService).findById(EXIST_ID);

        MvcResult mvcResult = this.mvc.perform(get("/shops/".concat(EXIST_ID.toString()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        ShopResponseDto shopResponseDto = this.objectMapper.readValue(
                response,
                ShopResponseDto.class
        );

        assertThat(shopResponseDto).isNotNull();

        assertThat(shopResponseDto.getAddress()).isEqualTo(ADDRESS);
        assertThat(shopResponseDto.getName()).isEqualTo(SHOP_NAME);
        assertThat(shopResponseDto.getShopId()).isEqualTo(EXIST_ID);

    }

    private static Shop getShop() {

        Shop shop = new Shop();

        shop.setId(EXIST_ID);
        shop.setName(SHOP_NAME);
        shop.setAddress(ADDRESS);

        return shop;
    }
}
