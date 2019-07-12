package org.kangspace.springcloud.graphql.core.graphql.type;

import lombok.Builder;
import lombok.Data;

/**
 * 2019/7/9 16:35
 *
 * @author kangxuefeng@etiantian.com
 */
@Builder
@Data
public class Book {
    private Integer id;
    private String name;
    private Author author;
    private String publisher;
}