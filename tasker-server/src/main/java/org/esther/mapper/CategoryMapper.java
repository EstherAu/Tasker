package org.esther.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.esther.bean.Category;

import java.util.List;

/**
 * カテゴリ管理用Mapperインターフェース
 * ※現時点では未使用。将来的なタスクタグ機能拡張を見越して定義。
 *
 * @author Esther
 * @since 2025/02/26
 */
@Mapper
public interface CategoryMapper {

    /**
     * すべてのカテゴリ一覧を取得
     * @return カテゴリリスト
     */
    List<Category> getAllCategories();

    /**
     * 指定したIDのカテゴリを削除
     * @param ids 削除対象ID配列
     * @return 削除件数
     */
    int deleteCategoryByIds(@Param("ids") String[] ids);

    /**
     * カテゴリ情報を更新
     * @param category 更新内容
     * @return 更新件数
     */
    int updateCategoryById(Category category);

    /**
     * 新しいカテゴリを追加
     * @param category 追加内容
     * @return 追加件数
     */
    int addCategory(Category category);
}
