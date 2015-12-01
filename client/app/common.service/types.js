angular.module('clientApp')
  .factory('types', function () {
    return {
      majorTypes: ["전공필수", "전공선택", "교양"],
      registerPoilicyTypes: ["신청시 가입", "승인 후 가입", "초대 받은 멤버만 가입"]
    };
  });
