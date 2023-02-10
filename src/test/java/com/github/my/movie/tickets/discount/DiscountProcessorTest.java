package com.github.my.movie.tickets.discount;

import com.github.my.movie.tickets.dto.TicketCart;
import org.junit.jupiter.api.Assertions;
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

        Assertions.assertEquals(oneDiscountHandler, firstDiscountHandler);
        Assertions.assertEquals(secondDiscountHandler, firstDiscountHandler.nextDiscountHandler);
        Assertions.assertEquals(threeDiscountHandler, firstDiscountHandler.nextDiscountHandler.nextDiscountHandler);
        Assertions.assertEquals(threeDiscountHandler, lastDiscountHandler);
    }

    @Test
    public void testAddHandlerTest_whenOneHandlersAdded() {
        DiscountHandler oneDiscountHandler = createHandler();
        discountProcessor.addHandler(oneDiscountHandler);

        DiscountHandler firstDiscountHandler = (DiscountHandler)ReflectionTestUtils.getField(discountProcessor, "firstDiscountHandler");
        DiscountHandler lastDiscountHandler = (DiscountHandler)ReflectionTestUtils.getField(discountProcessor, "lastDiscountHandler");

        Assertions.assertEquals(oneDiscountHandler, firstDiscountHandler);
        Assertions.assertEquals(null, firstDiscountHandler.nextDiscountHandler);;
        Assertions.assertEquals(oneDiscountHandler, lastDiscountHandler);
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
