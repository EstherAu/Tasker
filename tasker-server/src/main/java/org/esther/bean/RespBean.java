package org.esther.bean;

/**
 * APIレスポンスの標準Beanクラス
 * フロントエンドへの統一レスポンス形式を提供
 *
 * @author Esther
 * @since 2025/02/26
 */
public class RespBean {
    /** ステータス（例："success", "error"） */
    private String status;

    /** メッセージ（例："操作成功", "エラー内容"） */
    private String msg;

    /** データ本体（任意オブジェクト） */
    private Object data;

    public RespBean() {
    }

    /**
     * メッセージのみ返す場合のコンストラクタ
     */
    public RespBean(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    /**
     * データ付きレスポンス用コンストラクタ
     */
    public RespBean(String status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
}