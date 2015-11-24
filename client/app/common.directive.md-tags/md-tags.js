(function () {
  "use strict";

  angular
    .module('clientApp')
    .directive('mdTags', MdContactChips);


  var MD_CONTACT_CHIPS_TEMPLATE = '\
      <md-chips class="md-contact-chips"\
          ng-model="chips"\
          md-require-match="false">\
          {{$mdChipsCtrl.selectedChip}}\
          <md-autocomplete\
              md-menu-class="md-contact-chips-suggestions"\
              md-items="item in items"\
              md-selected-item="selected"\
              md-search-text="text"\
              md-selected-item-change="select(selected, $mdChipsCtrl.selectedChip)"\
              placeholder="{{placeholder}}">\
            <div class="md-contact-suggestion">\
              <span class="md-contact-name" md-highlight-text="searchText">\
                {{item}}{{$mdChipsCtrl.selectedChip}}\
              </span>\
            </div>\
          </md-autocomplete>\
          <md-chip-template>\
            <div class="md-contact-name">\
              {{item}}\
            </div>\
          </md-chip-template>\
      </md-chips>\
      {{chips}}{{selected}}\
      \
      ';


  function MdContactChips() {
    return {
      template: function () {
        return MD_CONTACT_CHIPS_TEMPLATE;
      },
      restrict: 'E',
      controller: function ($scope) {
        $scope.chips = [1, 2, 3];
        var searchText;
        $scope.chips.add = $scope.chips.push;
        $scope.chips.push = function (input) {
          if (searchText === undefined) {
            searchText = input;
            return;
          }
          var selectedValue = input;
          console.log(searchText, selectedValue);
          searchText = undefined;

        };
        $scope.items = [1, 2, 3, 4];
        $scope.select = function (a, b) {
          console.log(a,b);
          //$scope.chips.push(a);
        };
      },
      scope: {
        contactQuery: '&mdContacts',
        placeholder: '@',
        secondaryPlaceholder: '@',
        contactName: '@mdContactName',
        contactImage: '@mdContactImage',
        contactEmail: '@mdContactEmail',
        contacts: '=ngModel',
        requireMatch: '=?mdRequireMatch',
        highlightFlags: '@?mdHighlightFlags'
      }
    };

  }

})();
