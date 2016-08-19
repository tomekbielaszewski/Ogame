'use strict';

const gulp = require('gulp-help')(require('gulp')),
    del = require('del'),
    getBowerFiles = require('main-bower-files'),
    runSequence = require('run-sequence'),
    path = require('path'),
    browserSync = require('browser-sync').get('7AD server'),
    argv = require('yargs').argv,
    replace = require('gulp-replace'),
    babel = require('gulp-babel'),

    watcherOptions = {
        ignoreInitial: true
    };

gulp.task('watch', watchTask);

function watchTask() {
    browserSync.watch([
        'src/app/**/*.+(js|html)',
        'src/assets/scripts/**/*.js'
    ], watcherOptions, testAndLint)
        .on('add', copyFile)
        .on('change', copyFile)
        .on('delete', removeFile);

    browserSync.watch('src/assets/+(fonts|images)/**', watcherOptions)
        .on('add', copyFile)
        .on('change', copyFile)
        .on('delete', removeFile);

    browserSync.watch('src/bower_components/**', watcherOptions)
        .on('add', copyBower)
        .on('change', copyBower)
        .on('delete', removeFile);

    browserSync.watch('src/assets/**/*.+(scss|sass)', watcherOptions)
        .on('all', sassFiles);

    browserSync.watch('.tmp/**/*.+(js|css)', watcherOptions)
        .on('delete', inject)
        .on('add', inject)
        .on('change', reload);

    browserSync.watch('src/index.html', watcherOptions, inject);
    browserSync.watch('.tmp/**/*.html', watcherOptions, reload);

    browserSync.watch('tests/**/*.js', watcherOptions, testAndLint);
    browserSync.watch('gulp/**/*.js', watcherOptions, lintTask);
}

function copyFile(filePath) {
    var baseApiUrl = argv.api;

    if (!baseApiUrl) {
        baseApiUrl = '';
    }

    gulp.src(filePath, {
        base: 'src/'
    })
        .pipe(replace('{% apiBaseUrl %}', baseApiUrl))
        .pipe(babel({
            presets: ['es2015']
        }))
        .pipe(gulp.dest('.tmp/'));
}

function removeFile(filePath) {
    var processedPath = processPath(filePath);

    del(processedPath);
}

function sassFiles() {
    runSequence('sass');
}

function testAndLint(filepath) {
    var extension = path.extname(filepath);

    if (extension === '.js') {
        runSequence('lint', 'test:unit:watch');
    }
}

function lintTask() {
    runSequence('lint');
}

function reload(filepath) {
    console.log(filepath);

    browserSync.reload(filepath);
}

function inject() {
    runSequence('inject');
}

function copyBower(filepath) {
    var bowerFiles = getBowerFiles(),
        relativePath = path.resolve(filepath);

    if (bowerFiles.indexOf(relativePath) > -1) {
        copyFile(filepath);
    }
}

function processPath(filePath) {
    var relativePath = path.relative('src/', filePath);

    return '.tmp/' + relativePath;
}
