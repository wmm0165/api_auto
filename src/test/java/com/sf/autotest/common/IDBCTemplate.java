package com.sf.autotest.common;

import org.springframework.jdbc.core.JdbcTemplate;

public class IDBCTemplate {
    public static void main(String[] args) {
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
        String sql = "UPDATE `sf_audit_plan` SET `name` = \"住院\" WHERE id = ?";
        int count = template.update(sql,74003);//影响行数
        System.out.println(count);
    }
}
