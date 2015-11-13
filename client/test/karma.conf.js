// Karma configuration
// http://karma-runner.github.io/0.12/config/configuration-file.html
// Generated on 2015-10-13 using
// generator-karma 1.0.0

module.exports = function(config) {
  'use strict';

  config.set({
    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,

    // base path, that will be used to resolve files and exclude
    basePath: '../',

    // testing framework to use (jasmine/mocha/qunit/...)
    // as well as any additional frameworks (requirejs/chai/sinon/...)
    frameworks: [
      "jasmine"
    ],

    // list of files / patterns to load in the browser
    files: [
      // bower:js
      'bower_components/jquery/dist/jquery.js',
      'bower_components/angular/angular.js',
      'bower_components/angular-animate/angular-animate.js',
      'bower_components/angular-cookies/angular-cookies.js',
      'bower_components/angular-resource/angular-resource.js',
      'bower_components/angular-sanitize/angular-sanitize.js',
      'bower_components/angular-touch/angular-touch.js',
      'bower_components/angular-ui-router/release/angular-ui-router.js',
      'bower_components/bootstrap/dist/js/bootstrap.js',
      'bower_components/lodash/lodash.js',
      'bower_components/angular-datepicker/dist/angular-datepicker.js',
      'bower_components/ng-file-upload/ng-file-upload.js',
      'bower_components/restangular/dist/restangular.js',
      'bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
      'bower_components/angular-toastr/dist/angular-toastr.tpls.js',
      'bower_components/bowser/bowser.js',
      'bower_components/freewall/freewall.js',
      'bower_components/angular-bootstrap-colorpicker/js/bootstrap-colorpicker-module.js',
      'bower_components/angular-ui-router-anim-in-out/anim-in-out.js',
      'bower_components/moment/moment.js',
      'bower_components/fullcalendar/dist/fullcalendar.js',
      'bower_components/angular-aria/angular-aria.js',
      'bower_components/angular-material/angular-material.js',
      'bower_components/angular-messages/angular-messages.js',
      'bower_components/FroalaWysiwygEditor/js/froala_editor.min.js',
      'bower_components/FroalaWysiwygEditor/js/plugins/block_styles.min.js',
      'bower_components/FroalaWysiwygEditor/js/plugins/char_counter.min.js',
      'bower_components/FroalaWysiwygEditor/js/plugins/colors.min.js',
      'bower_components/FroalaWysiwygEditor/js/plugins/entities.min.js',
      'bower_components/FroalaWysiwygEditor/js/plugins/file_upload.min.js',
      'bower_components/FroalaWysiwygEditor/js/plugins/font_family.min.js',
      'bower_components/FroalaWysiwygEditor/js/plugins/font_size.min.js',
      'bower_components/FroalaWysiwygEditor/js/plugins/fullscreen.min.js',
      'bower_components/FroalaWysiwygEditor/js/plugins/inline_styles.min.js',
      'bower_components/FroalaWysiwygEditor/js/plugins/lists.min.js',
      'bower_components/FroalaWysiwygEditor/js/plugins/media_manager.min.js',
      'bower_components/FroalaWysiwygEditor/js/plugins/tables.min.js',
      'bower_components/FroalaWysiwygEditor/js/plugins/urls.min.js',
      'bower_components/FroalaWysiwygEditor/js/plugins/video.min.js',
      'bower_components/angular-froala/src/angular-froala.js',
      'bower_components/angular-mocks/angular-mocks.js',
      // endbower
      "app/scripts/**/*.js",
      "test/mock/**/*.js",
      "test/spec/**/*.js"
    ],

    // list of files / patterns to exclude
    exclude: [
    ],

    // web server port
    port: 8080,

    // Start these browsers, currently available:
    // - Chrome
    // - ChromeCanary
    // - Firefox
    // - Opera
    // - Safari (only Mac)
    // - PhantomJS
    // - IE (only Windows)
    browsers: [
      "PhantomJS"
    ],

    // Which plugins to enable
    plugins: [
      "karma-phantomjs-launcher",
      "karma-jasmine"
    ],

    // Continuous Integration mode
    // if true, it capture browsers, run tests and exit
    singleRun: false,

    colors: true,

    // level of logging
    // possible values: LOG_DISABLE || LOG_ERROR || LOG_WARN || LOG_INFO || LOG_DEBUG
    logLevel: config.LOG_INFO,

    // Uncomment the following lines if you are using grunt's server to run the tests
    // proxies: {
    //   '/': 'http://localhost:9000/'
    // },
    // URL root prevent conflicts with the site root
    // urlRoot: '_karma_'
  });
};
