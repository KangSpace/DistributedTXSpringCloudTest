package org.kangspace.springcloud.graphql.core.graphql;

import graphql.schema.DataFetcher;
import org.kangspace.springcloud.graphql.core.graphql.type.Author;
import org.kangspace.springcloud.graphql.core.graphql.type.Book;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 2019/7/11 18:35
 *
 * @author kangxuefeng@etiantian.com
 */
@Component
public class GraphQLDataFetchers{
    public GraphQLDataFetchers(){
        initBookList();
    }
    List<Book> bookList = new ArrayList<Book>();
    List<Author> authorList = new ArrayList<Author>();

    public void initBookList(){
        Author author = Author.builder()
                .id(1)
                .name("Bill Venners")
                .age(40)
                .build();
        Book b = Book.builder()
                .id(1)
                .name("scala编程第三版")
                .author(author)
                .publisher("电子工业出版社")
                .build();
        bookList.add(b);
        authorList.add(author);
    }
    /**
     * 无参方法
     * @return
     */
    public DataFetcher<List<Book>> findBooks() {
        return dataFetchingEnvironment -> bookList;
    }

    /**
     * 有参方法
     * @return
     */
    public DataFetcher<List<Book>> findBook() {
        return dataFetchingEnvironment -> {
            Integer id = dataFetchingEnvironment.getArgument("id");
            String name = dataFetchingEnvironment.getArgument("name");
            return bookList.stream().filter(t -> (id == null || (id != null && t.getId().equals(id))))
                    .filter(t -> (name == null || (name != null && t.getName().indexOf(name) > 0))).collect(Collectors.toList());
        };
    }
}