'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:BoardCtrl
 * @description
 * # BoardCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
  .controller('BoardCtrl', function ($scope, $stateParams, http) {

    $scope.$watch(function () {
      return $stateParams.id;
    }, function () {
      http.post('/api/v1/article', {boardId: $stateParams.id}, function () {

      });
    });


    var articles = $scope.articles = [];
    articles.push(new Article("제목", new User(1, "image.png", "황")));
    articles.push(new Article("제목", new User(1, "image.png", "황")));
    articles.push(new Article("제목", new User(1, "image.png", "황")));
    articles.push(new Article("제목", new User(1, "image.png", "황")));

    function Article(title, writer) {
      this.title = title;
      this.writer = writer;
    }

    function User(id, profile, name) {
      this.id = id;
      this.profile = profile;
      this.name = name;
    }


  });
