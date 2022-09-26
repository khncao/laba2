package com.buildingcompany.utility;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

public class MapResultHandler<K, V> implements ResultHandler {
    Map<K, V> result = new HashMap<K, V>();

    public Map<K, V> getResult() {
        return result;
    }

    @Override
    public void handleResult(ResultContext rc) {
        Map<String, Object> map = (Map<String, Object>) rc.getResultObject();
        result.put(
            (K) map.get("key"), 
            (V) map.get("value"));
    }
}
