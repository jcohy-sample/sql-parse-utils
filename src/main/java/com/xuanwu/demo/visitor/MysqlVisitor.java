package com.xuanwu.demo.visitor;

import java.util.List;

import com.xuanwu.demo.domain.DbTableInfo;

/**
 * 描述: MySQL 访问者，输出 MySql 语句.
 *
 * <p>
 * Copyright © 2023 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 1.0.0 2023/2/27 9:54
 * @since 1.0.0
 */
public class MysqlVisitor extends SQLVisitorImpl {

    public MysqlVisitor(Appendable appendable) {
        super(appendable);
    }

    /**
     * 打印表明或字段名，需要加上飘号
     * @param name name
     */
    @Override
    public void printNames(String name) {
        print('`');
        print(name);
        print('`');
    }

    /**
     * 打印外健
     * @param table table
     */
    @Override
    public void printForeignKey(DbTableInfo table) {
        List<String> foreign = table.getForeign();
        table.getColumns().forEach((column) -> {
            if(column.isForeignKey()) {
                StringBuilder builder = new StringBuilder();
                String referencedTable = column.getReferencedTable();
                String referencedTableKey = column.getReferencedTableKey();
                // 表创建后：ALTER TABLE `表名` ADD FOREIGN KEY `外键名` (`外键字段`) REFERENCES `连接表` (`连接字段`);
                // 表创建时：CONSTRAINT `pk_post_author` FOREIGN KEY (`author_id`) REFERENCES `member_profile` (`id`)
                builder.append("ALTER TABLE ");
                builder.append(getNames(table.getTableName()));
                builder.append(" ADD FOREIGN KEY ");
                builder.append("(" + getNames(column.getName()) + ")");
//                String foreignName = "pk_" + table.getTableName() + "_" + referencedTable;
//                builder.append(getNames(foreignName) + "("+ getNames(column.getName()) + ")");
                builder.append(" REFERENCES ");
                builder.append(getNames(referencedTable) + "(" + getNames(referencedTableKey) + ");");
                builder.append("\n");
                foreign.add(builder.toString());
            }
        });
    }

    @Override
    public String getNames(String name) {
        return "`" + name + "`";
    }
}
