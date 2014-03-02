package fr.ele.dto;

public abstract class HasIdAndCodeDto extends HasIdDto {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
