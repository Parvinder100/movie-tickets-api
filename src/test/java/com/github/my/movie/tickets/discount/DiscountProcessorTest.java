package com.github.my.movie.tickets.discount;

import com.github.my.movie.tickets.dto.TicketCart;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class DiscountProcessorTest {
    private DiscountProcessor discountProcessor = new DiscountProcessor();

    @Test
    public void testAddHandlerTest_whenMultipleHandlersAdded() {
        DiscountHandler oneDiscountHandler = createHandler();
        DiscountHandler secondDiscountHandler = createHandler();
        DiscountHandler threeDiscountHandler = createHandler();
        discountProcessor.addHandler(oneDiscountHandler);
        discountProcessor.addHandler(secondDiscountHandler);
        discountProcessor.addHandler(threeDiscountHandler);

        DiscountHandler firstDiscountHandler = (DiscountHandler)ReflectionTestUtils.getField(discountProcessor, "firstDiscountHandler");
        DiscountHandler lastDiscountHandler = (DiscountHandler)ReflectionTestUtils.getField(discountProcessor, "lastDiscountHandler");

        Assert.assertEquals(oneDiscountHandler, firstDiscountHandler);
        Assert.assertEquals(secondDiscountHandler, firstDiscountHandler.nextDiscountHandler);
        Assert.assertEquals(threeDiscountHandler, firstDiscountHandler.nextDiscountHandler.nextDiscountHandler);
        Assert.assertEquals(threeDiscountHandler, lastDiscountHandler);
    }

    @Test
    public void testAddHandlerTest_whenOneHandlersAdded() {
        DiscountHandler oneDiscountHandler = createHandler();
        discountProcessor.addHandler(oneDiscountHandler);

        DiscountHandler firstDiscountHandler = (DiscountHandler)ReflectionTestUtils.getField(discountProcessor, "firstDiscountHandler");
        DiscountHandler lastDiscountHandler = (DiscountHandler)ReflectionTestUtils.getField(discountProcessor, "lastDiscountHandler");

        Assert.assertEquals(oneDiscountHandler, firstDiscountHandler);
        Assert.assertEquals(null, firstDiscountHandler.nextDiscountHandler);;
        Assert.assertEquals(oneDiscountHandler, lastDiscountHandler);
    }

    private static DiscountHandler createHandler() {
        DiscountHandler oneDiscountHandler = new DiscountHandler() {
            @Override
            public void applyDiscount(TicketCart ticketCart) {

            }
        };
        return oneDiscountHandler;
    }
}
