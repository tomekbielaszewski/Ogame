(function (angular) {
    'use strict';

    angular
        .module('ogame.homepage.login')
        .directive('login', login);

    function login() {
        return {
            restrict: 'E',
            scope: false,
            templateUrl: 'app/login/views/login.html'
        };
    }
})(angular);
