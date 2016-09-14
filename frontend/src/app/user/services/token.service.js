(function (angular) {
    'use strict';

    angular
        .module('ogame.user')
        .service('Token', Token);

    function Token() {
        var $this = this;

        $this.decode = getDecodedToken;
        $this.isExpired = isTokenExpired;

        // Private methods

        function getDecodedToken(token) {
            return token;
        }

        function isTokenExpired(token) {
            return token;
        }
    }
})(angular);
