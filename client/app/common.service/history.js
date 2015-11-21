angular.module('clientApp')
  .factory('history', function ($location) {
    var history = [];
    history.back = function () {
      var url = history[history.length - 2];
      console.log('1');
      if (url) {
        $location.path(url);
        return;
      }
      $location.path('/');
    };
    return history;
  });
