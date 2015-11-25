/**
 * Created by itmnext13 on 2015. 11. 25..
 */
angular.module('clientApp').directive('youtube', function () {
  return {
    restrict: 'E',
    scope: {},
    controller: function() {
      var CLIENT_ID = '255146844316-5kqt3k3sksejf7i1l9alnv1bmv39usom.apps.googleusercontent.com';
      var SCOPE = [
        'https://www.googleapis.com/auth/youtube'
      ];
      var OAUTHURL    =   'https://accounts.google.com/o/oauth2/auth?';
      var REDIRECT    =   'http://localhost:9000';
      var TYPE        =   'token';
      var _url        =   OAUTHURL + 'scope=' + SCOPE + '&client_id=' + CLIENT_ID + '&redirect_uri=' + REDIRECT + '&response_type=' + TYPE;

      function login() {
       window.open(_url, "windowname1", 'width=800, height=600');
      }

// Attempt the immediate OAuth 2.0 client flow as soon as the page loads.
// If the currently logged-in Google Account has previously authorized
// the client specified as the OAUTH2_CLIENT_ID, then the authorization
// succeeds with no user intervention. Otherwise, it fails and the
// user interface that prompts for authorization needs to display.
      function checkAuth() {
        gapi.auth.authorize({
          client_id: CLIENT_ID,
          scope: SCOPE,
          immediate: true
        }, handleAuthResult);
      }

// Handle the result of a gapi.auth.authorize() call.
      function handleAuthResult(authResult) {
        if (authResult && !authResult.error) {
          // Authorization was successful. Hide authorization prompts and show
          // content that should be visible after authorization succeeds.
          $('.pre-auth').hide();
          $('.post-auth').show();
          loadAPIClientInterfaces();
        } else {
          // Make the #login-link clickable. Attempt a non-immediate OAuth 2.0
          // client flow. The current function is called when that flow completes.
          console.log("auth fail.");
          $('#login-link').click(function() {
            //login();
            gapi.auth.authorize({
              client_id: CLIENT_ID,
              scope: SCOPE,
              immediate: false
            }, handleAuthResult);
          });
        }
      }

// Load the client interfaces for the YouTube Analytics and Data APIs, which
// are required to use the Google APIs JS client. More info is available at
// https://developers.google.com/api-client-library/javascript/dev/dev_jscript#loading-the-client-library-and-the-api
      function loadAPIClientInterfaces() {
        gapi.client.load('youtube', 'v3', function() {
          handleAPILoaded();
        });
      }
      // Define some variables used to remember state.
      var playlistId, nextPageToken, prevPageToken;

// After the API loads, call a function to get the uploads playlist ID.
      function handleAPILoaded() {
        requestUserUploadsPlaylistId();
      }

// Call the Data API to retrieve the playlist ID that uniquely identifies the
// list of videos uploaded to the currently authenticated user's channel.
      function requestUserUploadsPlaylistId() {
        // See https://developers.google.com/youtube/v3/docs/channels/list
        var request = gapi.client.youtube.channels.list({
          mine: true,
          part: 'contentDetails'
        });
        request.execute(function(response) {
          playlistId = response.result.items[0].contentDetails.relatedPlaylists.uploads;
          requestVideoPlaylist(playlistId);
        });
      }

// Retrieve the list of videos in the specified playlist.
      function requestVideoPlaylist(playlistId, pageToken) {
        $('#video-container').html('');
        var requestOptions = {
          playlistId: playlistId,
          part: 'snippet',
          maxResults: 10
        };
        if (pageToken) {
          requestOptions.pageToken = pageToken;
        }
        var request = gapi.client.youtube.playlistItems.list(requestOptions);
        request.execute(function(response) {
          // Only show pagination buttons if there is a pagination token for the
          // next or previous page of results.
          nextPageToken = response.result.nextPageToken;
          var nextVis = nextPageToken ? 'visible' : 'hidden';
          $('#next-button').css('visibility', nextVis);
          prevPageToken = response.result.prevPageToken
          var prevVis = prevPageToken ? 'visible' : 'hidden';
          $('#prev-button').css('visibility', prevVis);

          var playlistItems = response.result.items;
          if (playlistItems) {
            $.each(playlistItems, function(index, item) {
              displayResult(item.snippet);
            });
          } else {
            $('#video-container').html('Sorry you have no uploaded videos');
          }
        });
      }

// Create a listing for a video.
      function displayResult(videoSnippet) {
        var title = videoSnippet.title;
        var videoId = videoSnippet.resourceId.videoId;
        $('#video-container').append('<p>' + title + ' - ' + videoId + '</p>');
      }

// Retrieve the next page of videos in the playlist.
      function nextPage() {
        requestVideoPlaylist(playlistId, nextPageToken);
      }

// Retrieve the previous page of videos in the playlist.
      function previousPage() {
        requestVideoPlaylist(playlistId, prevPageToken);
      }
    }
  };
});
<script src="https://apis.google.com/js/client.js?onload=googleApiClientReady"></script>
