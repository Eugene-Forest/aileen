package org.aileen.datasourcesettest.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MssqlTestVo {
    private Long Id;
    private String Name;

    private Integer Age;

    @JsonProperty("Id")
    public Long getId() {
        return Id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        Id = id;
    }
    @JsonProperty("Name")
    public String getName() {
        return Name;
    }
    @JsonProperty("name")
    public void setName(String name) {
        Name = name;
    }

    @JsonProperty("Age")
    public Integer getAge() {
        return Age;
    }
    @JsonProperty("age")
    public void setAge(Integer age) {
        Age = age;
    }
}
