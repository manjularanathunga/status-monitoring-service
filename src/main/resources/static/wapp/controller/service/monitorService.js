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
                if (response.status == 201 || response.status == 200) {
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
            $scope.service.status = 'true';
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
        $scope.service.serviceStatus = '';
        if ('add' === $scope.actionType || 'edit' === $scope.actionType) {
            Http.Create('/service', $scope.service)
                .then(function (response) {
                    console.log('saveModal > ' + JSON.stringify(response));
                    if (response.status == 201 || response.status == 200) {
                        Pop.timeMsg('success', $rootScope.pageTitle, 'Service entry has been added successfully to the system', 2000);
                        loadServices();
                    } else {
                        Pop.timeMsg('error', $rootScope.pageTitle, 'Record saving is unsuccessful', 2000);
                    }
                    $scope.uicompo.loader = false;
                }, function (response) {
                    $scope.uicompo.loader = false;
                }).catch(function () {
                $scope.uicompo.loader = false;
            });

        } else if ('delete' === $scope.actionType) {
            Http.GetAll('/servicedel/' + $scope.service.serviceID).then(function (response) {
                if (response.status == 201 || response.status == 200) {
                    loadServices();
                } else {
                    Pop.timeMsg('error', $rootScope.pageTitle, 'Record removing is unsuccessful', 2000);
                }
                $scope.uicompo.loader = false;
            }, function (response) {
                $scope.uicompo.loader = false;
            }).catch(function () {
                $scope.uicompo.loader = false;
            });
        }
    };

    $scope.record_colour = function (status) {
        if(status === 'FAIL'){
            return "#ffd7d8"
        }else{
            return "#fff9f1";
        }
    }

    loadServices();

    $interval(function () {
        loadServices();
    }, 10000);
});