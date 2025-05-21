package org.esther.bean;

import java.time.LocalDateTime;

/**
 * タスクエンティティ
 * タスク管理システムのメインオブジェクト
 *
 * @author Esther
 * @since 2025/02/26
 */
public class Task {

    /** タスクID（主キー） */
    private Long id;
    /** タスクコード（例: TASK-202504-001） */
    private String taskCode;
    /** タスク名 */
    private String taskName;
    /** タスク概要（サマリー） */
    private String description;
    /** タスク詳細説明 */
    private String detailedDescription;
    /** カテゴリID */
    private Long categoryId;
    /** 担当者ID（1対1） */
    private Long assigneeId;
    /** 作成者ID */
    private Long creatorId;
    /** 開始日時 */
    private LocalDateTime startDate;
    /** 更新日時 */
    private LocalDateTime updatedAt;
    /** ステータスID（未開始/進行中/完了など） */
    private Integer statusId;
    /** 締切日時 */
    private LocalDateTime deadline;
    /** 優先度ID（低・中・高など） */
    private Integer priorityId;
    /** 進捗率（0-100%） */
    private Integer progress;

    // SQLで結合取得するフィールド
    /** ステータス名（例: 未開始、進行中、完了） */
    private String statusName;
    /** 優先度名（例: 中、高、緊急など） */
    private String priorityName;
    /** 担当者ニックネーム */
    private String nickname;


    // Getter & Setter
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getTaskCode(){
        return taskCode;
    }

    public void setTaskCode(String taskCode){
        this.taskCode = taskCode;
    }

    public String getTaskName(){
        return taskName;
    }

    public void setTaskName(String taskName){
        this.taskName = taskName;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public  String getDetailedDescription(){
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription){
        this.detailedDescription = detailedDescription;
    }

    public Long getCategoryId(){
        return categoryId;
    }

    public void setCategoryId(Long categoryId){
        this.categoryId = categoryId;
    }

    public Long getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Long assigneeId) {
        this.assigneeId = assigneeId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Integer getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Integer priorityId) {
        this.priorityId = priorityId;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getStatusName(){
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getPriorityName(){
        return priorityName;
    }

    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }

    public  String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
