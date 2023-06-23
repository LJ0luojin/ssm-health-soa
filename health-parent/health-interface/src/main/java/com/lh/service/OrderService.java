package com.lh.service;

import java.util.Map;

public interface OrderService {
    Integer submit(Map<String, String> dataMap) throws Exception;
}
