'use strict';

const gulp = require('gulp-help')(require('gulp')),
    browserSync = require('browser-sync').create('Ogame server');

gulp.task('connect', connectTask);
gulp.task('connect:stop', connectStopTask);
gulp.task('reload', reloadTask);

function connectTask() {
    browserSync.init({
        port: 9000,
        minify: false,
        server: {
            baseDir: '.tmp'
        }
    });
}

function connectStopTask() {
    browserSync.exit();
}

function reloadTask() {
    return gulp.src(['.tmp/**'])
        .pipe(browserSync.reload());
}
