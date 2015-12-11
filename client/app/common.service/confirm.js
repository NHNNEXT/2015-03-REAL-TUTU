angular.module('clientApp').factory('confirm', function ($mdDialog) {
  return function (title, description, success, cancel) {
    var window = $mdDialog.confirm()
      .title(title)
      .content(description)
      .ok('확인')
      .cancel('취소');
    $mdDialog.show(window).then(success, cancel);
  };
});
