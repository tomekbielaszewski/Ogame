'use strict';

var gulp = require('gulp-help')(require('gulp')),
    sass = require('gulp-sass'),

    sassSources = [
        'src/assets/styles/app.scss'
    ];

gulp.task('sass', function () {
    return gulp.src(sassSources, {
        base: 'src/'
    })
    .pipe(sass({
        onError: callback
    }))
    .pipe(gulp.dest('.tmp/'));
});

function callback(err) {
    console.error(err.message);
}
