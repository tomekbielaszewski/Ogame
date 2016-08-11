'use strict';

const gulp = require('gulp-help')(require('gulp')),
    KarmaServer = require('karma').Server,
    path = require('path');

gulp.task('test:unit', unitTest);
gulp.task('test:unit:watch', unitWatchTest);


function unitTest(callback) {
    var newKarmaServer = new KarmaServer({
        configFile: path.resolve('karma.conf.js'),
        singleRun: true
    }, callback);

    newKarmaServer.start();
}

function unitWatchTest(callback) {
    var newKarmaServer = new KarmaServer({
        configFile: path.resolve('karma.conf.js'),
        singleRun: false
    }, callback);

    newKarmaServer.start();
}
