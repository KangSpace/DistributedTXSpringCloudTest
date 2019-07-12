# GraphQL API Instance With SpringBoot
##   内容:
> References:  
  [官网: https://graphql.org/](https://graphql.org/)  
  [中文网: https://graphql.cn/](https://graphql.cn/)  
  [GraphQL规范：https://spec.graphql.cn/](https://spec.graphql.cn/)
  [HowToGraphql： https://www.howtographql.com/basics/2-core-concepts/](https://www.howtographql.com/basics/2-core-concepts/)
  [HowToGraphql(Java)：https://www.howtographql.com/graphql-java/1-getting-started/](https://www.howtographql.com/graphql-java/1-getting-started/)    
  [工具/库推荐： https://www.infoq.cn/article/bl*EA59lRYDE9XlkJGg0](https://www.infoq.cn/article/bl*EA59lRYDE9XlkJGg0)  
  [30分钟理解GraphQL核心概念：https://segmentfault.com/a/1190000014131950](https://segmentfault.com/a/1190000014131950)  
####   Commments
```
    1. 项目中
       RESTful (graphql-service\src\main\java\org\kangspace\springcloud\graphql\core\controller\RESTfulController.java)
       GraphQLAPI() 
       共存
    2. 
```
####   Dependency:
```
<!-- https://mvnrepository.com/artifact/com.graphql-java/graphql-spring-boot-starter -->
<!-- SpringBoot Dependency -->
<dependency>
    <groupId>com.graphql-java</groupId>
    <artifactId>graphql-spring-boot-starter</artifactId>
    <version>3.10.0</version>
</dependency>

<!-- https://mvnrepository.com/artifact/com.graphql-java/graphql-java-tools -->
<!-- graphql-java Dependency -->
<dependency>
    <groupId>com.graphql-java</groupId>
    <artifactId>graphql-java-tools</artifactId>
    <version>4.2.0</version>
</dependency>

<!-- Springboot graphiql tool -->
<dependency>
    <groupId>com.graphql-java</groupId>
    <artifactId>graphiql-spring-boot-starter</artifactId>
    <version>3.10.0</version>
</dependency>
```

###   1.1 Create GraphQL API Interface
> Refferences:  
https://www.graphql-java.com/tutorials/getting-started-with-spring-boot/ 
https://www.graphql-java-kickstart.com/tools/  
https://www.graphql-java.com/documentation/v11/    
[查询：https://graphql.cn/learn/queries/](https://graphql.cn/learn/queries/)
[Schema：https://graphql.cn/learn/schema/](https://graphql.cn/learn/schema/)
[spring-boot集成graphql入门教程: https://my.oschina.net/genghz/blog/1789240](https://my.oschina.net/genghz/blog/1789240)
```
    Code : graphql-service\src\main\java\org\kangspace\springcloud\graphql\core\graphql
    GraphiQL :  http://127.0.0.1:1001/graphiql
    config file: \graphql-service\src\main\resources\root.graphqls
            \graphql-service\src\main\resources\schema.graphqls
    GraphiQL test file: graphql-service\test\graphiql 
    
    Class: graphql-service\src\main\java\org\kangspace\springcloud\graphql\core\graphql\schema\QueryResolver.java
``` 
#### Schema
```
tempfile : graphql-service\src\main\resources\schema_temp.graphqls
```  
##### 对象类型
```
type Character1 {
  name: String!
  appearsIn: [Episode!]!
}
```
##### 参数（Arguments）  
GraphQL 对象类型上的每一个字段都可能有零个或者多个参数，例如下面的 length 字段：
```
type Starship {
  id: ID!
  name: String!
  length(unit: LengthUnit = METER): Float
}
```
##### 标量类型（Scalar Types）
GraphQL 自带一组默认标量类型：  
 Int：有符号 32 位整数。
 Float：有符号双精度浮点值。
 String：UTF‐8 字符序列。
 Boolean：true 或者 false。
 ID：ID 标量类型表示一个唯一标识符，通常用以重新获取对象或者作为缓存中的键。ID 类型使用和 String 一样的方式序列化；然而将其定义为 ID 意味着并不需要人类可读型。   
自定义标量
```
scalar Date
```

#####枚举
枚举类型是一种特殊的标量，它限制在一个特殊的可选值集合内。这让你能够：  
1.验证这个类型的任何参数是可选值的的某一个  
2.与类型系统沟通，一个字段总是一个有限值集合的其中一个值。  
```
enum Episode {
  NEWHOPE
  EMPIRE
  JEDI
}
```
#####接口
一个接口是一个抽象类型，它包含某些字段，而对象类型必须包含这些字段，才能算实现了这个接口。  
```
interface Character {
  id: ID!
  name: String!
  friends: [Character]
  appearsIn: [Episode]!
}
type Human implements Character {
  id: ID!
  name: String!
  friends: [Character]
  appearsIn: [Episode]!
  starships: [Starship]
  totalCredits: Int
}
type Droid implements Character {
  id: ID!
  name: String!
  friends: [Character]
  appearsIn: [Episode]!
  primaryFunction: String
}
```
#####联合类型（Union Types）
联合类型和接口十分相似，但是它并不指定类型之间的任何共同字段。  
```
union SearchResult = Human | Droid | Starship
```

##### 输入类型（Input Types）
目前为止，我们只讨论过将例如枚举和字符串等标量值作为参数传递给字段，但是你也能很容易地传递复杂对象。这在变更（mutation）中特别有用，因为有时候你需要传递一整个对象作为新建对象。在 GraphQL schema language 中，输入对象看上去和常规对象一模一样，除了关键字是 input 而不是 type：   
```
input ReviewInput {
  stars: Int!
  commentary: String
}
```
如:
```
mutation CreateReviewForEpisode($ep: Episode!, $review: ReviewInput!) {
  createReview(episode: $ep, review: $review) {
    stars
    commentary
  }
}
variables
{
  "ep": "JEDI",
  "review": {
    "stars": 5,
    "commentary": "This is a great movie!"
  }
}
```

#### 查询(SprigBoot:query):
##### 1. 无参方法
```
public List<Book> findBooks() {
    return bookList;
}
//调用
query findBooks{
  findBooks {
    id
    name
    author {
      id
      name
      age
    }
  }
}
```        
##### 2. 有参方法
a. 方法传参  
 ```
public List<Book> findBook(Integer id,String name) {
    return bookList.stream().filter(t -> (id == null || (id != null && t.getId().equals(id))))
            .filter(t -> (name == null || (name != null && t.getName().indexOf(name) > 0))).collect(Collectors.toList());
}
//调用
query findBook($a:Int=1,$hasAuthor:Boolean!){
    book:findBook(id:$a,name:"a"){
        i:id
        n:name
        ... on Book @include(if:$hasAuthor){
          a:author{
            i:id
            n:name
            ... on Author @skip(if:$hasAuthor){
                a:age
            }
          }
        }
    }
}
 ```                
b. Field传参 //TODO
##### 3. 别名
```
#query/mutation:操作类型 ,opName:操作名称, $a:变量名,[$a:Int=1]:变量默认值
#@include,@skip:指令,... on Object:内联Fragments/片段解构
query opName($a:Int=1,$hasAuthor:Boolean!){
  findBook(id:$a) {
    id
    name
    ... on Book @include(if:$hasAuthor){
      a:author{
        i:id
        n:name
        ... on Author @skip(if:$hasAuthor){
        	a:age
        }
      }
  	}
  }
}
query opName2{
  findBook(id:1) {
    ... bookFields_
  }
}
# fragment: 片段
fragment bookFields_ on Book{
    id
    name
    author{
    ... authorFields_
    }
}
fragment authorFields_ on Author{
	id
    name
}
# 多查询处理
query findBookBySubQuery{
  a:findBooks {
    id
    name
		author{
      id
    }
  }
  b:findBook(id:1) {
    id
    name
  }
}

```

    
#### 修改(SprigBoot:mutation):
输入对象必须使用input类型
```
// 自定义输入对象
input InputAuthor {
    id: Int!
    name: String!
    age: Int
}
input InputBook {
    id: Int!
    name: String!
    author: InputAuthor!
    publisher: String!
}
```

##### 1. 添加
 ```
 public List<Book> addBooks(List<Book> books) {
         bookList.addAll(books);
         return bookList;
 }
 //调用
 mutation addBooks{
   addBooks(books:[{id:2,name:"B2",publisher:"PEP",author:{id:2,name:"a2"}},
   							  {id:3,name:"B3",publisher:"BNPG",author:{id:3,name:"a3"}}]){
   	... bookFields
   }
 }
 ```               
##### 2. 删除
```
public List<Book> removeBooks(List<Integer> bookIds) {
    bookList = bookList.stream().filter(t -> !bookIds.contains(t.getId())).collect(Collectors.toList());
    return bookList;
}
//调用
mutation removeBooks{
  removeBooks(bookIds:[2,3]){
    ... bookFields
  }
}
```
##### 3. 修改
```
public Book updateBook(Integer id,Book book) {
    AtomicReference<Book> returnBook = new AtomicReference<>();
    bookList.stream().filter(t->t.getId().equals(id)).forEach(t->{
        BeanUtils.copyProperties(book,t);
        returnBook.set(t);
    });
    return returnBook.get();
}
//调用
mutation updateBook{
  updateBook(id:4,book:{id:4,name:"updateBook4",publisher:"updateBNUP",author:{id:4,name:"updateAuthor4"}}){
    ... bookFields
  }
}
```
##### 4. 订阅(SprigBoot:subscribtion):
    
##### 5. Java Code API
```
//Servlet 处理
graphql-service\src\main\java\org\kangspace\springcloud\graphql\core\graphql\GraphQlEndPoint.java
org.kangspace.springcloud.graphql.core.graphql.GraphQlEndPoint
URI: http://127.0.0.1:1004/diy/graphql-servlet 
//Controller处理
graphql-service\src\main\java\org\kangspace\springcloud\graphql\core\graphql\GraphQLEndPointController.java
org.kangspace.springcloud.graphql.core.graphql.GraphQLEndPointController
URI: http://127.0.0.1:1004/diy/graphql

config file: \graphql-service\src\main\resources\schema-java.graphqls
test request temp file: \graphql-service\test\java-graphquery
```
###   1.2 Create GraphQL JS Client
