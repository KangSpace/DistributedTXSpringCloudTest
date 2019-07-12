package org.kangspace.springcloud.graphql.core.graphql.type;

import lombok.*;

/**
 * 2019/7/9 16:35
 *
 * @author kangxuefeng@etiantian.com
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Author {
    @NonNull
    private Integer id;
    private String name;
    private Integer age;
}