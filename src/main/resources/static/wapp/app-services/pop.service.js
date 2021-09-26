(function () {
    'use strict';

    angular
        .module('app')
        .service('Pop', Pop);

    Pop.$inject = ['$rootScope', '$window'];


    function Pop($rootScope, $window) {
        var service = {};
        service.msgWithButton = msgWithButton;
        service.msgWithButtonTitle = msgWithButtonTitle;
        service.msgWDelete = msgWDelete;
        service.setFocus = setFocus;
        service.timeMsg = timeMsg;

        return service;

        function msgWithButton(title, text, icon) {
            swal({
                title: title,
                text: text,
                icon: icon,
            });
        }

        function msgWithButtonTitle(title, text, icon, button) {
            swal({
                title: title,
                text: text,
                icon: icon,
                button: button,
            });
        }

        function timeMsg(icon, title, msg, timer) {
            swal({
                icon: icon,
                title: title,
                text: msg,
                buttons: false,
                position: 'top-end',
                timer: timer,
            });
        }

        function msgWDelete(icon, msg, title, timer) {
            swal({
                icon: icon,
                title: msg,
                text: title,
                buttons: false,
                position: 'top-end',
                timer: timer
            });
        }

        function setFocus(obj) {
            var element = $window.getElementById(obj);
            if (element) {
                element.setFocus();
            }

        }

    }
})();
