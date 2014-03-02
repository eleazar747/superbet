package fr.ele.dto;

public abstract class HasIdDto extends SuperbetDto {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
