package org.aileen.lc.model;

import java.util.Date;

public class TSDataModel {
    private String modelID;

    private String modelName;

    private String modelTitle;

    private String description;

    private String modelCategory;

    private String modelStatus;

    private String modelRevision;

    private String modelSchemaVersion;

    private Byte isSystemDefault;

    private Date createTime;

    private Date changeTime;

    private String createBy;

    private String changeBy;

    private String moduleID;

    private String moduleGroupID;

    private String moduleFunctionID;

    private String serializationUrl;

    public String getModelID() {
        return modelID;
    }

    public void setModelID(String modelID) {
        this.modelID = modelID;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelTitle() {
        return modelTitle;
    }

    public void setModelTitle(String modelTitle) {
        this.modelTitle = modelTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModelCategory() {
        return modelCategory;
    }

    public void setModelCategory(String modelCategory) {
        this.modelCategory = modelCategory;
    }

    public String getModelStatus() {
        return modelStatus;
    }

    public void setModelStatus(String modelStatus) {
        this.modelStatus = modelStatus;
    }

    public String getModelRevision() {
        return modelRevision;
    }

    public void setModelRevision(String modelRevision) {
        this.modelRevision = modelRevision;
    }

    public String getModelSchemaVersion() {
        return modelSchemaVersion;
    }

    public void setModelSchemaVersion(String modelSchemaVersion) {
        this.modelSchemaVersion = modelSchemaVersion;
    }

    public Byte getIsSystemDefault() {
        return isSystemDefault;
    }

    public void setIsSystemDefault(Byte isSystemDefault) {
        this.isSystemDefault = isSystemDefault;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getChangeBy() {
        return changeBy;
    }

    public void setChangeBy(String changeBy) {
        this.changeBy = changeBy;
    }

    public String getModuleID() {
        return moduleID;
    }

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }

    public String getModuleGroupID() {
        return moduleGroupID;
    }

    public void setModuleGroupID(String moduleGroupID) {
        this.moduleGroupID = moduleGroupID;
    }

    public String getModuleFunctionID() {
        return moduleFunctionID;
    }

    public void setModuleFunctionID(String moduleFunctionID) {
        this.moduleFunctionID = moduleFunctionID;
    }

    public String getSerializationUrl() {
        return serializationUrl;
    }

    public void setSerializationUrl(String serializationUrl) {
        this.serializationUrl = serializationUrl;
    }
}