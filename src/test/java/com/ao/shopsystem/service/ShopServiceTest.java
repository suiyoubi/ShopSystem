package com.ao.shopsystem.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import com.ao.shopsystem.entity.Shop;
import com.ao.shopsystem.repository.ShopRepository;
import java.util.Optional;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests for {@link com.ao.shopsystem.service.ShopService}
 * Created by ao on 2018-09-22
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ShopServiceTest {

    private static final Long TEST_ID = 1L;
    private static final String SHOP_NAME = "NAME";
    private static final String ADDRESS = "ADDRESS";

    @Mock
    private ShopRepository shopRepository;

    private ShopService shopService;

    @Before
    public void setUp() {
        this.shopService = new ShopService(shopRepository);
    }

    @Test
    public void findById_should_returnShop_when_shopExists() throws NotFoundException {

        doReturn(Optional.of(getShop())).when(this.shopRepository).findById(TEST_ID);

        Shop actualShop = this.shopService.findById(TEST_ID);

        assertThat(actualShop).isNotNull().isEqualToComparingFieldByField(getShop());
    }

    @Test(expected = NotFoundException.class)
    public void findById_should_throwNotFoundException_when_shopNotExist()
            throws NotFoundException {

        doReturn(Optional.empty()).when(this.shopRepository).findById(TEST_ID);

        Shop actualShop = this.shopService.findById(TEST_ID);

        assertThat(actualShop).isNotNull().isEqualToComparingFieldByField(getShop());
    }

    private static Shop getShop() {

        Shop shop = new Shop();

        shop.setId(TEST_ID);
        shop.setName(SHOP_NAME);
        shop.setAddress(ADDRESS);

        return shop;
    }
}
