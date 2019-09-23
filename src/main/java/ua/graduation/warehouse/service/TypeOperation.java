package ua.graduation.warehouse.service;

public enum  TypeOperation {

    ADD(1),
    WITHDRAW(2);

    private int typeId;

    public int getTypeId() {
        return typeId;
    }

    TypeOperation(int typeId) {
        this.typeId = typeId;
    }

}
