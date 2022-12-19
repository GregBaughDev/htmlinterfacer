package com.htmlinterfacer.htmlinterfacer.api.record;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Ref(
        String ref,
        @JsonProperty("node_id")
        String nodeId,
        String url,
        @JsonProperty("object")
        RefObject refObject
) {}
