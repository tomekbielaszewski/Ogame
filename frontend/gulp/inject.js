'use strict';

var gulp = require('gulp-help')(require('gulp')),
    inject = require('gulp-inject'),

    jsFiles = [
        '.tmp/app/**/*.module.js',
        '.tmp/app/**/*.js',
        '.tmp/assets/scripts/**/*.js',
        '.tmp/assets/styles/**/*.css'
    ],

    bowerFiles = [
        '.tmp/bower_components/jquery/**/*',
        '.tmp/bower_components/angular/**/*',
        '.tmp/bower_components/**'
    ];

gulp.task('inject', injectTask);

function injectTask() {
    const sources = gulp.src(jsFiles, {
            read: false
        }),
        bowerSources = gulp.src(bowerFiles, {
            read: false
        });

    return gulp.src('src/index.html')
        .pipe(gulp.dest('.tmp'))
        .pipe(inject(bowerSources, {
            name: 'bower',
            relative: true
        }))
        .pipe(inject(sources, {
            relative: true
        }))
        .pipe(gulp.dest('.tmp'));
}
