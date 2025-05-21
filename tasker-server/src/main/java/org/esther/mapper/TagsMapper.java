package org.esther.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * タグ管理用Mapperインターフェース
 * ※現時点では未使用。将来的なタスクタグ機能拡張を見越して定義。
 *
 * @author Esther
 * @since 2025/02/26
 */
@Mapper
public interface TagsMapper {

    /**
     * タスクIDでタグ紐付けを削除
     */
    int deleteTagsByAid(Long aid);

    /**
     * タグ名リストを新規保存
     */
    int saveTags(@Param("tags") String[] tags);

    /**
     * タグ名からタグIDを取得
     */
    List<Long> getTagsIdByTagName(@Param("tagNames") String[] tagNames);

    /**
     * タグIDリストをタスクに紐付け（中間テーブルへ保存）
     */
    int saveTags2TaskTags(@Param("tagIds") List<Long> tagIds, @Param("aid") Long aid);
}
