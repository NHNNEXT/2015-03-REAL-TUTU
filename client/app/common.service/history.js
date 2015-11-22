angular.module('clientApp')
  .factory('history', function ($location) {
    var history = [];
    history.back = function () {
      var url = history[history.length - 2];
      if (url) {
        $location.path(url);
        return;
      }
      $location.path('/');
    };
    return history;
  });
