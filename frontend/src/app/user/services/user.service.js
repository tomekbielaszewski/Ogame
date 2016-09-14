(function (angular) {
    'use strict';

    angular
        .module('ogame.user')
        .service('User', User);

    function User($localStorage, Token, usersRoles) {
        var $this = this;

        $this.getToken = getToken;
        $this.isLogged = isLogged;
        $this.logIn = logIn;
        $this.changeToken = changeToken;
        $this.getId = getId;
        $this.setInfo = setInfo;
        $this.getInfo = getInfo;
        $this.logOut = logOut;
        $this.getRole = getRole;
        $this.getConversationalistType = getConversationalistType;

        function getToken() {
            if (angular.isDefined($localStorage.planestry)) {
                return $localStorage.planestry.user.token;
            }
            throw new Error('User is not logged');
        }

        function isLogged() {
            try {
                return !Token.isExpired(getToken());
            } catch (error) {
                return false;
            }
        }

        function logIn(token) {
            $localStorage.planestry = {
                user: {
                    token: token
                }
            };
        }

        function changeToken(token) {
            $localStorage.planestry.user.token = token;
        }

        function getId() {
            if (isLogged()) {
                return Token.decode(getToken())
                    .userId;
            }
            return 0;
        }

        function setInfo(info) {
            if ($localStorage.planestry && $localStorage.planestry.user) {
                $localStorage.planestry.user.info = info;
            }
        }

        function getInfo() {
            return $localStorage.planestry.user.info;
        }

        function logOut() {
            delete $localStorage.planestry;
        }

        function getRole() {
            if (isLogged()) {
                return Token.decode(getToken())
                    .role;
            }
            return 'anonymous';
        }

        function getConversationalistType() {
            if (getRole() === usersRoles.couple) {
                return usersRoles.vendor;
            }
            return usersRoles.couple;
        }
    }
})(angular);
