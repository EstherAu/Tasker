package org.esther.utils;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * 日付型⇔文字列型の変換用TypeHandler
 * yyyy-MM-dd 形式で日付を文字列として扱うためのMyBatisハンドラー
 *
 * @author Esther
 * @since 2025/02/26
 */
@MappedJdbcTypes(JdbcType.DATE)
@MappedTypes(String.class)
public class DateTypeHandler implements TypeHandler<String> {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void setParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        // このメソッドは現在未実装
    }

    @Override
    public String getResult(ResultSet rs, String columnName) throws SQLException {
        return sdf.format(rs.getDate(columnName));
    }

    @Override
    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
        return sdf.format(rs.getDate(columnIndex));
    }

    @Override
    public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return sdf.format(cs.getDate(columnIndex));
    }
}
