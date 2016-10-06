module.exports = function (config) {
    'use strict';

    config.set({
        basePath: '',
        frameworks: ['jasmine'],
        colors: true,
        plugins: [
            'karma-phantomjs-launcher',
            'karma-jasmine',
            'karma-junit-reporter',
            'karma-nested-reporter'
        ],
        files: [
            '.tmp/bower_components/angular/angular.js',
            '.tmp/bower_components/angular-mocks/angular-mocks.js',
            '.tmp/bower_components/ng-lodash/build/ng-lodash.js',

            '.tmp/app/**/*.module.js',
            '.tmp/app/**/*.js',

            'tests/unit/globals.js',
            'tests/unit/**/*.js'
        ],
        browsers: ['PhantomJS'],
        nestedReporter: {
            color: {
                should: 'red',
                browser: 'yellow'
            },
            icon: {
                failure: '✘ ',
                indent: 'ட ',
                browser: ''
            }
        },
        reporters: ['nested'], //or progress
        exclude: [
        ],
        preprocessors: {
        },
        port: 8080
    });
};
