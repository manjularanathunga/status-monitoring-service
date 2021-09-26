(function () {
    'use strict';

    angular
        .module('app')
        .factory('Http', UserService);

    UserService.$inject = ['$http'];

    function UserService($http) {
        var service = {};
        service.GetAll = GetAll;
        service.Create = Create;
        service.Delete = Delete;
        return service;

        function GetAll(url) {
            return $http.get(url)
                .then(function (response) {
                    return response;
                }).catch(function (data, status) {
                    console.error('Request error', response.status, response.data);
                }).finally(function () {
                    //console.log("finally finished Request");
                });
        }

        function Create(url, item) {
            return $http.post(url, item)
                .then(function (response) {
                    return response;
                }).catch(function (data, status) {
                    console.error('Request error', response.status, response.data);
                }).finally(function () {
                    //console.log("finally finished Request");
                });
        }

        function Delete(url) {
            return $http.delete(url)
                .then(function (response) {
                    return response;
                }).catch(function (data, status) {
                    console.error('Request error', response.status, response.data);
                }).finally(function () {
                    //console.log("finally finished Request");
                });
        }
    }

})();
