(function (angular) {
    'use strict';

    angular
        .module('ogame.apiAdapter')
        .service('APIAdapter', APIAdapter);

    function APIAdapter($http, ResponseHandler, User, $state) {
        var $this = this,
            baseUrl = '{% apiBaseUrl %}';

        $this.getBaseUrl = getBaseUrl;
        $this.sendGETRequest = sendGETRequest;
        $this.sendPUTRequest = sendPUTRequest;
        $this.sendPOSTRequest = sendPOSTRequest;
        $this.sendDELETERequest = sendDELETERequest;

        function getBaseUrl() {
            return baseUrl;
        }

        function sendGETRequest(resourceName, params) {
            var url = getGETRequestUrl(resourceName, params),
                config = getConfigObject(),
                response = $http.get(url, config);

            return ResponseHandler
                .handleResponse(response);
        }

        function sendPUTRequest(resourceName, params) {
            var url = getRequestUrl(resourceName),
                config = getConfigObject(),
                response = $http.put(url, params, config);

            return ResponseHandler
                .handleResponse(response);
        }

        function sendPOSTRequest(resourceName, params) {
            var url = getRequestUrl(resourceName),
                config = getConfigObject(),
                response = $http.post(url, params, config);

            return ResponseHandler
                .handleResponse(response);
        }

        function sendDELETERequest(resourceName) {
            var url = getRequestUrl(resourceName),
                config = getConfigObject(),
                response = $http.delete(url, config);

            return ResponseHandler
                .handleResponse(response);
        }

        // Private methods

        function getGETRequestUrl(resourceName, params) {
            var url = getRequestUrl(resourceName);

            if (params) {
                url += '?' + serialize(params);
            }

            return url;
        }

        function getRequestUrl(resourceName) {
            return baseUrl + resourceName;
        }

        function getConfigObject() {
            var config = {
                headers: {
                    'Content-Type': 'application/json'
                }
            };

            setAuthorizationConfig(config.headers);

            return config;
        }

        function setAuthorizationConfig(headers) {
            var isRequestFromPublicState = $state.includes('vendors.*');

            if (!isRequestFromPublicState && User.isLogged()) {
                headers.Authorization = 'Bearer ' + User.getToken();
            }
        }

        function serialize(params) {
            var serializedParamsTable = [],
                paramName;

            for (paramName in params) {
                if (params.hasOwnProperty(paramName)) {
                    serializedParamsTable.push(processParam(params, paramName));
                }
            }

            return serializedParamsTable.join('&');
        }

        function processParam(params, paramName) {
            var paramValue = params[paramName],
                processedParam;

            if (angular.isObject(paramValue)) {
                processedParam = encodeURIComponent(paramName) + '={' + serialize(paramValue) + '}';
            } else {
                processedParam = encodeURIComponent(paramName) + '=' + encodeURIComponent(paramValue);
            }

            return processedParam;
        }
    }
})(angular);
