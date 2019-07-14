package org.kangspace.springcloud.graphql.core.graphql;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.ExecutionInput;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.servlet.DefaultGraphQLContextBuilder;
import graphql.servlet.DefaultGraphQLRootObjectBuilder;
import graphql.servlet.GraphQLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    private GraphQL graphQL;

    @Autowired
    GraphQLDataFetchers graphQLDataFetchers;

    @PostConstruct
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

    @RequestMapping
    @ResponseBody
    public Object handle(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody String query) {
        JSONObject queryJson = JSON.parseObject(query);
        if (!queryJson.containsKey("query")) {
            throw new IllegalArgumentException("query arg is null");
        }
        String queryStr = queryJson.getString("query");
        String operationName = queryJson.containsKey("operationName")?queryJson.getString("operationName"):null;
        String variablesStr = queryJson.containsKey("variables") ? queryJson.getString("variables") : null;
        GraphQLContext context = new DefaultGraphQLContextBuilder().build(Optional.of(request), Optional.of(response));
        Object rootObject =  new DefaultGraphQLRootObjectBuilder().build(Optional.of(request), Optional.of(response));
        Map<String,Object> variables = (variablesStr != null) ? JSON.parseObject(variablesStr, new TypeReference<Map<String, Object>>(){})
                :new HashMap<>(0);
        return graphQL.execute(new ExecutionInput(queryStr, operationName, context, rootObject, variables)).getData();
    }
}
