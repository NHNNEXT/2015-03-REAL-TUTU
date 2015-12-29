angular.module('clientApp').service('Auth', function ($window, $http) {

  var self = this;
  this.google = {};

  this.google.logged = function(){
    return self.google.id !== undefined;
  };

  $window.oauth2Callback = function(authResult) {
    if (authResult.access_token) {
      self.google.accessToken = authResult.access_token;

      $http({
        url: 'https://www.googleapis.com/youtube/v3/channels',
        method: 'GET',
        headers: {
          Authorization: 'Bearer ' + self.google.accessToken
        },
        params: {
          part: 'snippet',
          mine: true
        }
      }).success(function(response) {
        console.log(response);
        self.google.kind = response.kind;
        self.google.id = response.items[0].id;
      });
    }

  };
});
