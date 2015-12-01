angular.module('clientApp')
  .factory('types', function () {
    return {
      majorTypes: ["전공필수", "전공선택", "교양"],
      registerPoilicyTypes: ["가입 신청시 가입", "관리자 승인 후 가입"]
    };
  });
