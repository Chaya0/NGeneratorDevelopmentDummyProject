package com.lms.utils;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.node.*;
import com.fasterxml.jackson.databind.*;
import org.springframework.data.domain.Sort;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomSortDeserializer extends JsonDeserializer<Sort> {

    @Override
    public Sort deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        TreeNode treeNode = jp.getCodec().readTree(jp);
        if (treeNode.isArray()) {
            ArrayNode arrayNode = (ArrayNode) treeNode;
            return toSort(arrayNode);
        } else if (treeNode.get("orders") != null && treeNode.get("orders").isArray()) {
            ArrayNode arrayNode = (ArrayNode) treeNode.get("orders");
            return toSort(arrayNode);
        }
        return null;
    }

    @Override
    public Class<Sort> handledType() {
        return Sort.class;
    }

    private static Sort toSort(ArrayNode arrayNode) {
        List<Sort.Order> orders = new ArrayList<>();
        for (JsonNode jsonNode : arrayNode) {
            Sort.Order order = new Sort.Order(Sort.Direction.valueOf(jsonNode.get("direction").textValue()),
                    jsonNode.get("property").textValue());
            orders.add(order);
        }
        return Sort.by(orders);
    }
}
