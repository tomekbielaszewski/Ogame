'use strict';

var gulp = require('gulp-help')(require('gulp')),
    runSequence = require('run-sequence'),
    taskOptions = {
        options: {
            'api=http://example.com': 'API url'
        }
    };


require('require-dir')('gulp');

gulp.task('default', defaultTask, taskOptions);

function defaultTask(callback) {
    runSequence(
        'tmp',
        'lint',
        'connect',
        'watch',
        'test:unit:watch',
        callback
    );
}
