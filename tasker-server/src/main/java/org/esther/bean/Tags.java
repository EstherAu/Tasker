package org.esther.bean;

/**
 * タグエンティティクラス
 * タスクや記事などに関連付けるためのタグ情報を管理
 *
 * @author Esther
 * @since 2025/02/26
 */
public class Tags {
    /** タグID */
    private Long id;

    /** タグ名 */
    private String tagName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
