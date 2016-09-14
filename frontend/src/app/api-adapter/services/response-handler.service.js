(function (angular) {
    'use strict';

    angular
        .module('ogame.apiAdapter')
        .service('ResponseHandler', ResponseHandler);

    function ResponseHandler($q, $state, User) {
        var $this = this;

        $this.handleResponse = handleResponse;

        function handleResponse(response) {
            var deferred = $q.defer();

            response
                .success(deferred.resolve)
                .error(handleError);

            function handleError(error, status, headers) {
                if (!isLoginRequest() && status === 401) {
                    logOutUser();
                } else if (!isResponseJson(headers)) {
                    deferred.reject(createErrorObject(status));
                } else {
                    error.code = status;
                    deferred.reject(error);
                }
            }

            return deferred.promise;
        }

        function logOutUser() {
            User.logOut();
            $state.go('homepage.login');
        }

        function isResponseJson(headers) {
            return headers('Content-Type') && headers('Content-Type').indexOf('application/json') > -1;
        }

        function createErrorObject(status) {
            return {
                code: status
            };
        }

        function isLoginRequest() {
            return $state.current.name.indexOf('.login') > -1;
        }
    }
})(angular);
