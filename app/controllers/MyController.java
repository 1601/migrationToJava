package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.*;

public class MyController extends Controller{

    @BodyParser.Of(BodyParser.Json.class)
    public Result submit() {
        JsonNode json = request().body().asJson();
        String name = json.findPath("fname").textValue();
        if(name == null) {
            return badRequest("Missing parameter [fname]");
        } else {
            return ok("Hello " + name);
        }
    }

    public Result sayHello() {
        ObjectNode result = Json.newObject();
        result.put("exampleField1", "foobar");
        result.put("exampleField2", "Hello world!");
        return ok(result);
    }


}
