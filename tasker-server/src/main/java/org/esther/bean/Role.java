package org.esther.bean;

/**
 * 役割（ロール）エンティティクラス
 * システムの権限管理に利用
 *
 * @author Esther
 * @since 2025/02/26
 */
public class Role {
    /** 役割ID */
    private Long id;

    /** 役割名（例：管理者、一般ユーザーなど） */
    private String name;


    public Role() {
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role(Long id, String name) {

        this.id = id;
        this.name = name;
    }
}
