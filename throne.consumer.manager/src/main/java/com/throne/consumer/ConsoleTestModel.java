package com.throne.consumer;

import com.google.gson.Gson;
import throne.orchestration.common.IData;

public class ConsoleTestModel implements IData {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return new Gson().toJson(this, ConsoleTestModel.class);
    }
}
