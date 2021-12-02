package com.team1.discoveryourchef.JsonHandler;

import java.util.List;

public class JsonResponse {

    private List<JsonHitsResponse> hits;

    public List<JsonHitsResponse> getHits() {
        return hits;
    }

    public void setHits(List<JsonHitsResponse> hits) {
        this.hits = hits;
    }

    @Override
    public String toString() {
        return "JsonResponse{" +
                "hits=" + hits +
                '}';
    }
}
