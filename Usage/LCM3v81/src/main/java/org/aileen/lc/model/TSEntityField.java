package org.aileen.lc.model;

public class TSEntityField {
    private String ID;

    private String name;

    private String title;

    private String dataType;

    private String referenceID;

    private Byte isPrimaryKey;

    private Byte isNullable;

    private Byte isAutoIncrement;

    private Byte isUnique;

    private String defaultValue;

    private String descirption;

    private String parentID;

    private Byte sort;

    private Integer size;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(String referenceID) {
        this.referenceID = referenceID;
    }

    public Byte getIsPrimaryKey() {
        return isPrimaryKey;
    }

    public void setIsPrimaryKey(Byte isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    public Byte getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(Byte isNullable) {
        this.isNullable = isNullable;
    }

    public Byte getIsAutoIncrement() {
        return isAutoIncrement;
    }

    public void setIsAutoIncrement(Byte isAutoIncrement) {
        this.isAutoIncrement = isAutoIncrement;
    }

    public Byte getIsUnique() {
        return isUnique;
    }

    public void setIsUnique(Byte isUnique) {
        this.isUnique = isUnique;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDescirption() {
        return descirption;
    }

    public void setDescirption(String descirption) {
        this.descirption = descirption;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public Byte getSort() {
        return sort;
    }

    public void setSort(Byte sort) {
        this.sort = sort;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}