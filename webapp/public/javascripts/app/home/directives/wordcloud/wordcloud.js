home.directive("twWordCloud", function() {
    return {
        restrict: "E",
        scope: {
            words: "=",
            id: "@cloudId",
            type: "@type"
        },
        link: function(scope) {
            scope.$watch("words", function() {
                if(!scope.words || !scope.words.length || scope.words.length <= 1) {
                    $("#" + scope.id + "-message").html("Not enough data.");
                    $("#" + scope.id).addClass("hidden");
                    return;
                }
                else {
                    $("#" + scope.id + "-message").html("");
                    $("#" + scope.id).removeClass("hidden");
                }

                    var word_array = [];
                for(var i=0; i<scope.words.length; i++) {
                    var new_word;
                    if(scope.type == "hashtag")
                        new_word = {
                            text: scope.words[i].hashtag,
                            weight: scope.words[i].count,
                            link: "https://twitter.com/hashtag/" + scope.words[i].hashtag + "?src=hash"
                        };
                    else if(scope.type == "topic")
                        new_word = {
                            text: scope.words[i].name,
                            weight: scope.words[i].count,
                            link: "https://twitter.com/search?q=" + scope.words[i].name + "&src=typd"
                        };

                    word_array.push(new_word);
                }
                $("#" + scope.id + " span").remove();
                setTimeout(function() {$("#" + scope.id).jQCloud(word_array)}, 1000);
            });
        },
        templateUrl: "/assets/javascripts/app/home/directives/wordcloud/wordcloud.html"
    };
});
