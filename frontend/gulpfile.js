'use strict';

var gulp = require('gulp-help')(require('gulp')),
    runSequence = require('run-sequence'),
    taskOptions = {
        options: {
            'apiBaseUrl=': 'API base url'
        }
    };


require('require-dir')('gulp');

gulp.task('default', defaultTask, taskOptions);

function defaultTask(callback) {
    runSequence(
        'tmp',
        callback
    );
}
