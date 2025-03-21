package com.duonghoang.test_input.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.duonghoang.quan_li_nhan_su.util.DBUtil.getInstance;

public abstract class BaseDAO {
    public static <T> List<T> executeQueryPrepareStatementFetchList(String query, Function<ResultSet, T> mapper, Object... params) throws SQLException {
        List<T> result = new ArrayList<>();
        try (Connection connection = getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            setParams(ps, params);
            try (ResultSet resultSet = ps.executeQuery())  {
                while (resultSet.next()) {
                    result.add(mapper.apply(resultSet));
                }
            }
        }
        return  result;
    }

    public static <T> T executeQueryPrepareStatementFetchOne(String query, Function<ResultSet, T> mapper, Object... params) throws SQLException{
        List<T> results = executeQueryPrepareStatementFetchList(query, mapper, params);
        return results.isEmpty() ? null: results.getFirst();
    }


    public static boolean executeUpdateWithPreparedStatement(String query, Object... params) throws SQLException {
        try (Connection connection = getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setParams(preparedStatement, params);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    public static <K, V> Map<K, V> executeQueryPrepareStatementFetchMap(String query, BiFunction<ResultSet, Map<K, V>, Void> mapper,
                                                                        Object... params) {
        Map<K, V> resultMap = new HashMap<>();
        try (Connection connection = getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setParams(preparedStatement, params);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                mapper.apply(resultSet, resultMap);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return resultMap;
    }


    private static void setParams(PreparedStatement preparedStatement, Object... params) throws SQLException {
        if (params.length == 0) {
            return;
        }
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
    }
}