package org.kangspace.springcloud.graphql.core.graphql.schema;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.kangspace.springcloud.graphql.core.graphql.type.Author;
import org.kangspace.springcloud.graphql.core.graphql.type.Book;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * GraphQL QueryResolver
 * 2019/7/9 16:32
 *
 * @author kangxuefeng@etiantian.com
 */
@Component
public class QueryResolver implements GraphQLQueryResolver, GraphQLMutationResolver {
    List<Book> bookList = new ArrayList<Book>();
    List<Author> authorList = new ArrayList<Author>();

    @PostConstruct
    public void initBookList() {
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
     *
     * @return
     */
    public List<Book> findBooks() {
        return bookList;
    }

    /**
     * 有参方法
     *
     * @param id
     * @param name
     * @return
     */
    public List<Book> findBook(Integer id, String name) {
        return bookList.stream().filter(t -> (id == null || (id != null && t.getId().equals(id))))
                .filter(t -> (name == null || (name != null && t.getName().indexOf(name) > 0))).collect(Collectors.toList());
    }

    /**
     * 有参方法
     *
     * @param id
     * @return
     */
    public Author findAuthor(Integer id) {
        return authorList.stream().filter(t -> (id == null || (id != null && t.getId().equals(id)))).collect(Collectors.toList()).get(0);
    }

    /**
     * Mutation 添加
     *
     * @param books
     * @return
     */
    public List<Book> addBooks(List<Book> books) {
        bookList.addAll(books);
        return bookList;
    }

    /**
     * Mutation 删除
     *
     * @param bookIds
     * @return
     */
    public List<Book> removeBooks(List<Integer> bookIds) {
        bookList = bookList.stream().filter(t -> !bookIds.contains(t.getId())).collect(Collectors.toList());
        return bookList;
    }

    /**
     * Mutation 更新
     *
     * @param
     * @return
     */
    public Book updateBook(Integer id, Book book) {
        AtomicReference<Book> returnBook = new AtomicReference<>();
        bookList.stream().filter(t -> t.getId().equals(id)).forEach(t -> {
            BeanUtils.copyProperties(book, t);
            returnBook.set(t);
        });
        return returnBook.get();
    }

}
