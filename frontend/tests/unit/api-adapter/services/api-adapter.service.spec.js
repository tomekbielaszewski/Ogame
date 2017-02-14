describe('APIAdapter Service', function () {
    'use strict';

    var APIAdapterService;

    beforeEach(module('ogame.apiAdapter'));

    beforeEach(inject(function () {
        APIAdapterService = $injector.get('APIAdapter');
    }));

    describe('function sendGETRequest', function () {
       it('should send get request to api', function () {
           APIAdapterService.sendGETRequest('method');
       });
    });
});