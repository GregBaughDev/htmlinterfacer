package com.htmlinterfacer.htmlinterfacer.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ref {
    String ref;

    @JsonProperty("node_id")
    String nodeId;

    String url;

    @JsonProperty("object")
    RefObject refObject;

    public Ref() {}

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNode_id(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RefObject getRefObject() {
        return refObject;
    }

    public void setRefObject(RefObject refObject) {
        this.refObject = refObject;
    }

    public class RefObject {
        String sha;

        String type;

        String url;

        public RefObject() {}

        public String getSha() {
            return sha;
        }

        public void setSha(String sha) {
            this.sha = sha;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
