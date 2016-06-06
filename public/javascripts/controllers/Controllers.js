/**
 * Created by darlomiguelilagan on 31/05/2016.
 */
var BASEURL_SERVER = "http://localhost:9000/";

    testapp.controller('LoginController', function($scope,usersFactory,Flash) {
        $scope.pageName = 'login-page';
        $scope.users=[];
        $scope.message = "";

        init();
        function init() {
            $scope.users = usersFactory.getUsers();
        }

        $scope.loginClick = function loginMethod(){
            if(searchUser($scope.userNameInput, $scope.passwordInput)) {
                var message = '<strong> Success!</strong>  Logging In.';
                var id = Flash.create('success', message, 1000, {class: 'custom-class', id: 'custom-id'}, true);
                setTimeout(function () {
                    window.location.href = "#/home:" + $scope.userNameInput;
                }, 2000);
            } else {
                var message = 'Please sign up - <strong> Account non-existent </strong>';
                var id = Flash.create('danger', message, 1000, {class: 'custom-class', id: 'custom-id'}, true);
            }
        };

        function searchUser(nameKey, password){
            var bool = false;
            $.ajax({
                type: "POST",
                url: BASEURL_SERVER + "user/login",
                contentType: "application/json",
                dataType: "json",
                async: false,
                data: JSON.stringify({
                    uname: nameKey,
                    password: password
                }),
                success: function (response) {
                    bool = response.result;
                }
            });
            return bool;
        }

    });

    testapp.controller('HomeController', function($scope, $routeParams, $http, usersFactory) {
        $scope.pageName = 'home-page';
        $scope.currentUser={};
        $scope.uname = "";
        $scope.lname = "";
        $scope.fname = "";
        $scope.password = "";
        $scope.users = [];
        init();
        function init() {

            var currentUname = $routeParams.uname.substr(1,$routeParams.uname.length);
            $http({
                method: 'GET',
                async : false,
                url: BASEURL_SERVER  + "user/home"
            }).then(function successCallback(response) {
                console.log("Got All Users");
                $scope.users = JSON.parse(response.data.result);
                console.log($scope.users);
                $scope.currentUser = getUser(currentUname);
                console.log( $scope.currentUser);
            }, function errorCallback(response) {
                console.log("Error in GET Users");
            });
           // $scope.users = usersFactory.getUsers();
            $scope.uname = $scope.currentUser.uname;
            $scope.lname = $scope.currentUser.lname;
            $scope.fname = $scope.currentUser.fname;
            $scope.password = $scope.currentUser.password;
        }
      //  console.log($scope.users);
        var self = this;
        self.message = "Welcome Home!";
        $scope.adddd = function () {
            usersFactory.addAUser("Somebody","Some","body","password");
            alert("Adddd Success!");
        }

        function getUser(uname) {
            console.log("indeisde GetUser"+ $scope.users.length);
            for (var i = 0; i < $scope.users.length; i++) {
                if ($scope.users[i].uname === uname) {
                    console.log($scope.users);
                    return $scope.users[i];
                }
            }
            return null;
        };
    });

    testapp.controller('RegisterController', function($scope,usersFactory) {
        $scope.pageName = 'register-page';
        var self = this;
        self.message = "Welcome to your registration!";
        $scope.registerClick = function () {
            usersFactory.addAUser($scope.uname,$scope.fname,$scope.lname,$scope.password);
            alert("Registration Success!");
            window.location.href = "#/"
        }
    });

    testapp.controller('UserlistController', function($scope,usersFactory) {
        $scope.pageName = 'userlist-page';
        var self = this;
        $scope.users = [];
        init();
        function init() {
            $scope.users = usersFactory.getUsers();
        }
        self.message = "View your userlist!";

        $scope.removve = function (username) {
            console.log(username);
            usersFactory.removeAUser(username);
        }
    });
    // testapp.controller('LoginController', ['Flash', function(Flash) {
    //     $scope.successAlert = function () {
    //         var message = '<strong> Well done!</strong>  You successfully read this important alert message.';
    //         var id = Flash.create('success', message, 1000, {class: 'custom-class', id: 'custom-id'}, true);
    //     // First argument (string) is the type of the flash alert.
    //     // Second argument (string) is the message displays in the flash alert (HTML is ok).
    //     // Third argument (number, optional) is the duration of showing the flash. 0 to not automatically hide flash (user needs to click the cross on top-right corner).
    //     // Fourth argument (object, optional) is the custom class and id to be added for the flash message created.
    //     // Fifth argument (boolean, optional) is the visibility of close button for this flash.
    //     // Returns the unique id of flash message that can be used to call Flash.dismiss(id); to dismiss the flash message.
    // }
    // }]);
