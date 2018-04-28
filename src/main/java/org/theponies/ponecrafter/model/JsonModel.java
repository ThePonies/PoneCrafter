package org.theponies.ponecrafter.model;

import org.json.JSONObject;

public interface JsonModel {
    JSONObject toJsonObject ();

    default String toJson () {
        return toJsonObject().toString();
    }
}
