package nl.hu.bep2.casino.blackjack.domain;

import java.util.List;

public class move {
    private Long value;
    private String name;
    private String discription;
    private String type;
    private List<String> action;

    public move(Long value, String name, String discription, String type, List<String> action) {
        this.value = value;
        this.name = name;
        this.discription = discription;
        this.type = type;
        this.action = action;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getAction() {
        return action;
    }

    public void setAction(List<String> action) {
        this.action = action;
    }
}