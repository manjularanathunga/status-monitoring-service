var app = angular.module('app', ['ngRoute', 'ngCookies'])
    .run(['$rootScope', '$location', '$window', '$http', '$cookies', '$parse', function ($rootScope, $location, $window, $http, $cookies, $parse) {

        $rootScope.globals = $cookies.getObject('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata;
        }

        $rootScope.presentDate = new Date();
        $rootScope.mainTitle = "KRY Service Monitoring System";

        $rootScope.pageTitle = "Login";
        $rootScope.show = {};

        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in and trying to access a restricted page
            var restrictedPage = $.inArray($location.path(), ['/login', '/register']) === -1;
            var loggedIn = $rootScope.globals.currentUser;
            if (restrictedPage && !loggedIn) {
                $rootScope.loggedData = {};
                $location.path('/login'); // redirect to login page commit
            }
        });

        var showUserMenus = function () {
            if ($rootScope.loggedData.usrAccess && $rootScope.loggedData.usrAccess.length > 0) {
                $rootScope.loggedData.usrAccess.forEach(function (key) {
                    var model = $parse("show." + key);
                    model.assign($rootScope, true);
                });
            }
        }
    }])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when("/login", {
                templateUrl: 'wapp/view/admin/loginPage.html',
                controller: 'LoginController'
            })
            .when("/services", {
                templateUrl: 'wapp/view/services/monitorService.html',
                controller: 'MonitorService'
            })
    }]);

