package throne.feeder.test;

import com.google.gson.Gson;
import throne.orchestration.common.IData;

public class TestData implements IData {

    private String name;

    public TestData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return new Gson().toJson(this, TestData.class);
    }
}
