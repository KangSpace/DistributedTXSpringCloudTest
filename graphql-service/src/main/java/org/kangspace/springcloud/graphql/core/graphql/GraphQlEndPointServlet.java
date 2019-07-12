package org.kangspace.springcloud.graphql.core.graphql;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.servlet.SimpleGraphQLServlet;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.net.URL;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

/**
 * GraphQL接口
 *
 * <a href="https://www.howtographql.com/graphql-java/1-getting-started/">https://www.howtographql.com/graphql-java/1-getting-started/</a>
 * <a href="https://www.graphql-java.com/tutorials/getting-started-with-spring-boot/">https://www.graphql-java.com/tutorials/getting-started-with-spring-boot/</a>
 * 2019/7/11 17:02
 *
 * @author kangxuefeng@etiantian.com
 */
@WebServlet(name = "graphQlEndPointServlet" ,urlPatterns = "/diy/graphql-servlet")
public class GraphQlEndPointServlet extends SimpleGraphQLServlet {
    final static String SCHEMA_FILE = "schema-java.graphqls";

    @Override
    public void init() {
    }

    public GraphQlEndPointServlet() throws IOException {
        super(buildSchema());
    }

    private static GraphQLSchema buildSchema() throws IOException {
        URL url = Resources.getResource(SCHEMA_FILE);
        String sdl = Resources.toString(url, Charsets.UTF_8);
        TypeDefinitionRegistry typeDefinitionRegistry =  new SchemaParser().parse(sdl);
        return new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, buildWiring());
    }

    private static RuntimeWiring buildWiring() {
        GraphQLDataFetchers graphQLDataFetchers = new GraphQLDataFetchers();
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("findBooks", graphQLDataFetchers.findBooks())
                        .dataFetcher("findBook", graphQLDataFetchers.findBook()))
                .build();
    }

}
