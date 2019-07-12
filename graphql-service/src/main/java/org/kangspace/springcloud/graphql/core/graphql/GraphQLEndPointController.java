package org.kangspace.springcloud.graphql.core.graphql;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

/**
 * 2019/7/11 18:20
 *
 * @author kangxuefeng@etiantian.com
 */
@RestController
@RequestMapping(value = "/diy/graphql")
public class GraphQLEndPointController {
    final static String SCHEMA_FILE = "schema-java.graphqls";

    GraphQL graphQL;

    @Autowired
    GraphQLDataFetchers graphQLDataFetchers;


    @Init
    public void init() throws IOException {
        URL url = Resources.getResource(SCHEMA_FILE);
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("findBooks", graphQLDataFetchers.findBooks())
                        .dataFetcher("findBook", graphQLDataFetchers.findBook()))
                .build();
    }

    @ResponseBody
    public Object handle(@RequestBody String query){
        return graphQL.execute(query).getData();
    }
}
