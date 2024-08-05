package com.hhplus.assignment.ecommerce.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ServerCache {

    TOP_SALES_PRODUCT("top-sales-product", 100, 300), // 5 minutes
    ;

    private final String name;
    private final long maximumSize;
    private final int expireAfterWriteInSec;

}
