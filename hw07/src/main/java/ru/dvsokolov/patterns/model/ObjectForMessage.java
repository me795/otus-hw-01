package ru.dvsokolov.patterns.model;
import java.util.ArrayList;
import java.util.List;

public class ObjectForMessage {
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public ObjectForMessage copy(){
        var copyObjectForMessage = new ObjectForMessage();
        List<String> copyData = new ArrayList<>(this.data);
        copyObjectForMessage.setData(copyData);
        return copyObjectForMessage;
    }
}
