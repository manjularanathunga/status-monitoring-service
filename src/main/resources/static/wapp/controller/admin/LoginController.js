app.controller('LoginController', function ($scope, $rootScope, $http, $location, $window, AuthenticationService, UserService, Pop, Http) {
    $rootScope.pageTitle = "KRY Service Morning System";
    $scope.dataLoading = false;
    $scope.auser = {};
    $scope.dataLoading = true;
    $scope.uicompo = {};
    $rootScope.loggedData = {};

    $scope.loginToSystem = function (username, password) {
        $scope.uicompo.loader = true;
        Http.Create('/authenticate', {
            username: $scope.auser.username,
            password: $scope.auser.password
        }).then(function (response) {
            if (response.status == 200) {
                $scope.uicompo.loader = false;
                AuthenticationService.SetCredentials(username, password, response);
                $location.path('/services');
            } else {
                $scope.uicompo.loader = false;
                Pop.timeMsg('warning', 'ERROR LOGGING', response.data.exception, 2000);
                exception;
                $location.path('/');
            }
        }, function (response) {
            $scope.uicompo.loader = false;
            FlashService.Error(response.exception);
            $scope.dataLoading = false;
        });
    };
});
