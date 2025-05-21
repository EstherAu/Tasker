package org.esther.bean;

import java.sql.Timestamp;

/**
 * カテゴリー情報エンティティ
 * タスク分類を管理するBeanクラス。
 *
 * @author Esther
 * @since 2025/02/26
 */
public class Category {
    /** カテゴリーID */
    private Long id;

    /** カテゴリー名 */
    private String cateName;

    /** 作成日付 */
    private Timestamp date;

    public Category() {
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }
}
