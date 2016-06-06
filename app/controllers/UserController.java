package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import helpers.ProjectConstants;
import models.db.DBUser;
import models.objects.User;
import play.libs.Json;
import play.mvc.*;
import play.mvc.Result;

import java.util.Date;


public class UserController extends Controller{

    @BodyParser.Of(BodyParser.Json.class)
    public Result registerUser() {
        JsonNode json = request().body().asJson();

        User u = Json.fromJson(json, User.class);
        u.setLastLogin(new Date());
        u.setIsActive(ProjectConstants.ACTIVE);

        ObjectNode result = Json.newObject();
        int res = DBUser.insert(u);
        result.put("result", res);
        if(res == ProjectConstants.SUCCESS){
            result.put("id", u.getId());
        }

        return ok(result);
    }

    public Result listUsers() {
        //JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();
        result.put("result", Json.toJson(DBUser.listUsers()));
       // JsonNode jsonForResult = Json.toJson(DBUser.listUsers());
       // System.out.println(json.toString());
        return ok(result);
    }

    public Result loginUser() {
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();
        result.put("result", DBUser.login(json.get("uname").textValue(), json.get("password").textValue()));
        return ok(result);
    }

}