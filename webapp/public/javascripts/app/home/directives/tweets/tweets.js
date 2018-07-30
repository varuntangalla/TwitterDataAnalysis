home.directive("twTweets", function() {
    return {
        restrict: "E",
        scope: {
            tweets: "="
        },
        templateUrl: "/assets/javascripts/app/home/directives/tweets/tweets.html"
    };
});
