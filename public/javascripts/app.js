
var BASEURL_PARTIAL = "web/partials/";
var BASEURL_SERVER = "http://localhost:9000/";

var testapp = angular.module('test', ['ngRoute','ngAnimate',"ngFlash"])

    .config(function($routeProvider){
        $routeProvider.when("/",
            {
                templateUrl: BASEURL_PARTIAL + "login.html",
                controller: "LoginController",
                controllerAs: "login"
            })
            .when("/home:uname",
            {
                templateUrl: BASEURL_PARTIAL + "home.html",
                controller: "HomeController",
                controllerAs: "home"
            }
            ).when("/register",
            {
                templateUrl: BASEURL_PARTIAL + "register.html",
                controller: "RegisterController",
                controllerAs: "register"
            }
            ).when("/userlist",
            {
                templateUrl: BASEURL_PARTIAL + "userlist.html",
                controller: "UserlistController",
                controllerAs: "userlist"
            }
        );
    });

testapp.factory('usersFactory',function ($http) {
  var factory = {};
    var users= [];
    
    // $http({
    //     method: 'GET',
    //     url: BASEURL_SERVER  + "user/home"
    // }).then(function successCallback(response) {
    //     console.log("obelieve success");
    //     console.log(response.data.result);
    //     console.log(response.data);
    //     console.log(response);
    //     users = JSON.parse(response.data.result);
    //     console.log(users);
    // }, function errorCallback(response) {
    //     console.log("gonna try again");
    // });

    factory.getUser = function (uname) {
        for (var i = 0; i < users.length; i++) {
            if (users[i].uname === uname) {
                return users[i];
            }
        }
        return null;
    };

    factory.getUsers = function () {
        return users;
    };

    factory.addAUser = function (uname, fname, lname, password) {
        var data = {
            fname: fname,
            lname:lname,
            uname: uname,
            password: password
        };

        users.push(data); // frontend

        $.ajax({  // server
            type: "POST",
            url: BASEURL_SERVER + "user/register",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(data),
            success: function (response) {
                console.log(response);
            }
        });
    };

    factory.removeAUser = function (uname) {

        for (var i =0; i < users.length; i++)
            if (users[i].uname === uname) {
                users.splice(i,1);
                break;
            }
    };
    return factory;
});


function searchUser(nameKey, password){
    // TODO
    $.ajax({
        type: "POST",
        url: BASEURL_SERVER + "user/login",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
            uname: nameKey,
            password: password
        }),
        success: function (response) {

            console.log(response.result);
        }
    });
    // for (var i=0; i < myArray.length; i++) {
    //     if (myArray[i].uname === nameKey) {
    //         return myArray[i];
    //     }
    // }
    // return 0;
}

