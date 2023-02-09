package com.github.my.movie.tickets.discount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DiscountHandlerFactory {
    public static final String CHILDREN_TICKET_DISCOUNT_HANDLER = "childrenTicketDiscountHandler";
    @Autowired
    private Map<String, DiscountHandler> discountHandlerMap;

    public DiscountHandler getChildrenTicketDiscountHandler() {
        return discountHandlerMap.get(CHILDREN_TICKET_DISCOUNT_HANDLER);
    }
}
