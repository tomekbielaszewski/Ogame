(function (angular) {
    'use strict';

    angular
        .module('ogame.apiAdapter')
        .service('APIAdapter', APIAdapter);

    function APIAdapter($http, ResponseHandler, lodash) {
        var $this = this,
            baseUrl = '{% apiBaseUrl %}';

        $this.sendGETRequest = sendGETRequest;
        $this.sendPUTRequest = sendPUTRequest;
        $this.sendPOSTRequest = sendPOSTRequest;
        $this.sendDELETERequest = sendDELETERequest;

        function sendGETRequest(resourceName, params) {
            var url = getGETRequestUrl(resourceName, params),
                response = $http.get(url);

            return ResponseHandler
                .handleResponse(response);
        }

        function sendPUTRequest(resourceName, params) {
            var url = getRequestUrl(resourceName),
                response = $http.put(url, params);

            return ResponseHandler
                .handleResponse(response);
        }

        function sendPOSTRequest(resourceName, params) {
            var url = getRequestUrl(resourceName),
                response = $http.post(url, params);

            return ResponseHandler
                .handleResponse(response);
        }

        function sendDELETERequest(resourceName) {
            var url = getRequestUrl(resourceName),
                response = $http.delete(url);

            return ResponseHandler
                .handleResponse(response);
        }

        // Private methods

        function getGETRequestUrl(resourceName, params) {
            var url = getRequestUrl(resourceName);

            if (params) {
                url += '?' + joinParams(params);
            }

            return url;
        }

        function getRequestUrl(resourceName) {
            return baseUrl + resourceName;
        }

        function joinParams(params) {
            var serializedParamsTable = lodash.map(params, processParam);

            console.log(serializedParamsTable);

            return serializedParamsTable.join('&');
        }

        function processParam(paramValue, paramName) {
            var processedParam;

            if (angular.isObject(paramValue)) {
                processedParam = encodeURIComponent(paramName) + '={' + joinParams(paramValue) + '}';
            } else {
                processedParam = encodeURIComponent(paramName) + '=' + encodeURIComponent(paramValue);
            }

            return processedParam;
        }
    }
})(angular);
