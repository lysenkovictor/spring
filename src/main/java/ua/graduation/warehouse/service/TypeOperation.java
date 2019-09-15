package ua.graduation.warehouse.service;

public enum  TypeOperation {

    ADD(1),
    UPDATE(2);

    private int type;

    public int getType() {
        return type;
    }

    TypeOperation(int type) {
        this.type = type;
    }

}
