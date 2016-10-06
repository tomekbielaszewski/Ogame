describe('APIAdapter Service\n', function () {
    'use strict';

    var APIAdapterService;

    beforeEach(module('ogame.apiAdapter'));

    beforeEach(inject(function () {
        APIAdapterService = $injector.get('APIAdapter');
    }));

    describe('function sendGETRequest\n', function () {
       it('should send get request to api', function () {
           APIAdapterService.sendGETRequest('method');
       });
    });
});