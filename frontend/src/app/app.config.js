(function (angular) {
    'use strict';

    angular
        .module('ogame')
        .config(ogameConfig);

    function ogameConfig($stateProvider) {
        $stateProvider
            .state('otherwise', {
                url: '*path',
                onEnter: onEnter
            });

        function onEnter($state) {
            $state.go('homepage');
        }
    }
})(angular);
