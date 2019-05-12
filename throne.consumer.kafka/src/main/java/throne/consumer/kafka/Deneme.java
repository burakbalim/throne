package throne.consumer.kafka;

import throne.orchestration.common.IData;

public class Deneme implements IData {

    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
