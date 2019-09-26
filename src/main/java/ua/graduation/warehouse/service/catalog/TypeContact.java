package ua.graduation.warehouse.service.catalog;

public enum  TypeContact {
    WORK(1),
    PRIVATE(2);

    private int typeId;

    public int getTypeId() {
        return typeId;
    }

    TypeContact(int typeId) {
        this.typeId = typeId;
    }
}
