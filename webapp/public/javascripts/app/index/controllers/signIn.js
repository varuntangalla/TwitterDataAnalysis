index.controller("signInCtrl", ["$http", "$window", function($http, $window) {
    var self = this;
    var defaultPlaceholderMessage = "<i class='fa fa-lg fa-twitter'></i> Sign in with Twitter to get started";
    var waitingPlaceholderMessage = "<i class='fa fa-lg fa-spinner fa-spin'></i> Contacting Twitter";

    self.placeholderMessage = defaultPlaceholderMessage;
    self.hasError = false;

    self.doSignIn = function() {
        self.placeholderMessage = waitingPlaceholderMessage;
        self.hasError = false;

        $http.get("/request_token").
            success(function(request_token) {
                $window.location.href = "https://api.twitter.com/oauth/authenticate?oauth_token=" + request_token;
            }).
            error(function() {
                self.hasError = true;
                self.placeholderMessage = defaultPlaceholderMessage;
            });
    };

    return self;
}]);
