package ru.dvsokolov.protobuf.clientservice;

public class ValueProcessor {

    private long currentValue;
    private long currentServerValue;
    private long lastUsedServerValue;

    public ValueProcessor(){
        this.currentValue = 0;
        this.currentServerValue = 0;
        this.lastUsedServerValue = 0;
    }

    public void setCurrentServerValue (long l){
        this.currentServerValue = l;
    }

    public void calculateCurrentValue(){
        if (currentServerValue == lastUsedServerValue){
            currentValue++;
        }else{
            currentValue = currentValue + currentServerValue + 1;
            lastUsedServerValue = currentServerValue;
        }
        System.out.printf("{Current value calculated: %d}%n",
                currentValue);
    }


}
