package com.efindi.batch.commons.util;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

public class TypeReferenceUtils {
    private TypeReferenceUtils() {}

    public static final MapTypeReference MAP_TYPE_REFERENCE = new MapTypeReference();
}

class MapTypeReference extends TypeReference<Map<String, Object>> {}
