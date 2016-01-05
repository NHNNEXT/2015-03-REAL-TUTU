angular.module('clientApp')

  /* @ngInject */
  .service('hangul', function () {
    this.getERO = function (word) {
      var last = word.charAt(word.length - 1);
      console.log(word, last);
      if (!is_hangul_char(last))
        return word + "(으)로";
      if (tSound(last) === "ᆧ")
        return word + "로";
      return word + "으로";
    };

    //function iSound(a) {
    //  var r = ((a.charCodeAt(0) - parseInt('0xac00',16)) /28) / 21;
    //  var t = String.fromCharCode(r + parseInt('0x1100',16));
    //  return t;
    //}

    //function mSound(a) {
    //  var r = ((a.charCodeAt(0)- parseInt('0xac00',16)) / 28) % 21;
    //  var t = String.fromCharCode(r + parseInt('0x1161',16));
    //  return t;
    //}

    function tSound(a) {
      var r = (a.charCodeAt(0) - parseInt('0xac00', 16)) % 28;
      var t = String.fromCharCode(r + parseInt('0x11A8') - 1);
      return t;
    }

    function is_hangul_char(ch) {
      var c = ch.charCodeAt(0);
      if( 0x1100<=c && c<=0x11FF ) return true;
      if( 0x3130<=c && c<=0x318F ) return true;
      if( 0xAC00<=c && c<=0xD7A3 ) return true;
      return false;
    }
  });
