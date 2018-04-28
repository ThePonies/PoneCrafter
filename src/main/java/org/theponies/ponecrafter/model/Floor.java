package org.theponies.ponecrafter.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class Floor implements JsonModel {
    public static final int TEXTURE_SIZE = 128;
    private static final String GUID = "guid";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String PRICE = "price";

    private final UUID uuid;
    private final String name;
    private final String description;
    private final int price;

    public Floor (UUID uuid, String name, String description, int price) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Floor (JSONObject jsonObject) throws JSONException {
        this(
            UUID.fromString(jsonObject.getString(GUID)),
            jsonObject.getString(NAME),
            jsonObject.getString(DESCRIPTION),
            jsonObject.getInt(PRICE)
        );
    }

    public Floor (String json) {
        this(new JSONObject(json));
    }

    @Override
    public JSONObject toJsonObject () {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(GUID, uuid);
        jsonObject.put(NAME, name);
        jsonObject.put(DESCRIPTION, description);
        jsonObject.put(PRICE, price);
        return jsonObject;
    }

    public String getName () {
        return name;
    }

    public String getDescription () {
        return description;
    }

    public int getPrice () {
        return price;
    }
}
