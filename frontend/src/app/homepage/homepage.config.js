(function (angular) {
    'use strict';

    angular
        .module('ogame.homepage')
        .config(homepageConfig);

    function homepageConfig($stateProvider) {
        $stateProvider
            .state({
                name: 'homepage',
                url: '/',
                templateUrl: 'app/homepage/views/homepage.html'
            }
        );
    }
})(angular);
