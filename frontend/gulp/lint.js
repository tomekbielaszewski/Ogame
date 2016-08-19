'use strict';

var gulp = require('gulp-help')(require('gulp')),
    lint = require('gulp-eslint');

gulp.task('lint', lintTask);

function lintTask() {
    return gulp.src(['tests/**/*.js', 'src/app/**/*.js', 'gulp/*.js'])
        .pipe(lint())
        .pipe(lint.format());
}
