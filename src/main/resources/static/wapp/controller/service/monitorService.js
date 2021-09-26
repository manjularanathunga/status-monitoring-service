app.controller('MonitorService', function ($scope, $rootScope, $http, $interval, $location, $window, AuthenticationService, Pop, Http) {
    $rootScope.pageTitle = "Kry Monitor Service";

    $scope.servicesList = [];
    $scope.uicompo = {};
    $scope.uicompo.showContent = false;
    $scope.titleList = [];


    let loadServices = function () {
        $scope.uicompo.showContent = false;
        $scope.uicompo.loader = true;
        Http.GetAll("/service/dashboard/" + $rootScope.loggedData.currentUserId)
            .then(function (response) {
                if (response.status == 200) {
                    $scope.servicesList = response.data;
                    $scope.uicompo.showContent = true;
                } else {
                    $scope.servicesList = [];
                    $scope.uicompo.showContent = false;
                }
                $scope.uicompo.loader = false;
            });
    }

    $scope.showUI = function (itm, opType) {
        $scope.actionType = opType;
        if ('add' === $scope.actionType) {
            $scope.heading = 'Add Service Details';
            $scope.uicompo.itemDisabled = false;
            $scope.service = {};
            $scope.service.status = 'ACTIVE';
        } else if ('edit' === $scope.actionType) {
            $scope.heading = 'Edit Service Details :' + itm.id;
            $scope.uicompo.itemDisabled = false;
            $scope.service = itm;
        } else if ('delete' === $scope.actionType) {
            $scope.heading = 'Delete Service Details :' + itm.id;
            $scope.uicompo.itemDisabled = true;
            $scope.service = itm;
        }
        $("#modal-inv").modal("show");
    };

    $scope.saveModal = function () {
        $scope.uicompo.loader = true;
        $scope.service.monitorUserId = $rootScope.loggedData.currentUserId;
        $scope.service.serviceStatus = true;
        if ('add' === $scope.actionType || 'edit' === $scope.actionType) {
            Http.Create('/service', $scope.service)
                .then(function (response) {
                    console.log('saveModal > ' + JSON.stringify(response));
                    if (response.status == 201) {
                        loadServices();
                    } else {
                        Pop.timeMsg('error', $rootScope.pageTitle, ' SAVE/UPDATE RECORD ', 2000);
                    }
                    $scope.uicompo.loader = false;
                }, function (response) {
                    $scope.uicompo.loader = false;
                }).catch(function () {
                $scope.uicompo.loader = false;
            });

        } else if ('delete' === $scope.actionType) {
            Http.Delete('service?id=' + $scope.service.serviceID).then(function (response) {
                if (response.status == 200) {
                    loadServices();
                } else {
                    Pop.timeMsg('error', $rootScope.pageTitle, ' DELETE RECORD ', 2000);
                }
                $scope.uicompo.loader = false;
            }, function (response) {
                $scope.uicompo.loader = false;
            }).catch(function () {
                $scope.uicompo.loader = false;
            });
        }
    };

    loadServices();

    $interval(function () {
        loadServices();
    }, 10000);
});