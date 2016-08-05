'use strict';

var gulp = require('gulp-help')(require('gulp')),
    runSequence = require('run-sequence'),
    getBowerFiles = require('main-bower-files'),
    del = require('del'),
    ngAnnotate = require('gulp-ng-annotate'),
    argv = require('yargs').argv,
    babel = require('gulp-babel'),

    tasksOptions = {
        options: {
            'api=http://example.com': 'API url'
        }
    };

gulp.task('tmp', tmpTask);

gulp.task('tmp:copy', tmpCopy, tasksOptions);

gulp.task('tmp:copy:bower', copyBower);

gulp.task('tmp:copy:assets', copyAssets);

gulp.task('tmp:copy:app', copyApp, tasksOptions);

gulp.task('tmp:copy:app:js', copyJsApp, tasksOptions);

gulp.task('tmp:copy:app:html', copyHtmlApp, tasksOptions);

gulp.task('tmp:clear', clearTmp);

function tmpTask(callback) {
    runSequence(
        'tmp:clear',
        'tmp:copy',
        'sass',
        'inject',
        callback
    );
}

function tmpCopy(callback) {
    runSequence(
        'tmp:copy:bower',
        'tmp:copy:assets',
        'tmp:copy:app',
        callback
    );
}

function copyBower() {
    return gulp.src(getBowerFiles(), {
        base: 'src/'
    })
        .pipe(gulp.dest('.tmp'));
}

function copyApp(callback) {
    runSequence(
        'tmp:copy:app:js',
        'tmp:copy:app:html',
        callback
    );
}

function copyJsApp() {
    var baseApiUrl = argv.api;

    if (!baseApiUrl) {
        baseApiUrl = '';
    }

    return gulp.src('src/app/**/*.js', {
        base: 'src/'
    })
        .pipe(ngAnnotate())
        .pipe(gulp.dest('.tmp'));
}

function copyHtmlApp() {
    return gulp.src('src/app/**/*.html', {
        base: 'src/'
    })
        .pipe(gulp.dest('.tmp'));
}

function copyAssets() {
    var assetsSrcs = [
        'src/assets/**/*',
        '!src/assets/icons',
        '!src/assets/icons/**',
        '!src/assets/styles/**'
    ];

    return gulp.src(assetsSrcs, {
        base: 'src/'
    })
        .pipe(gulp.dest('.tmp'));
}

function clearTmp() {
    var files = [
        '.tmp/*',
        '.tmp'
    ];

    return del(files);
}
