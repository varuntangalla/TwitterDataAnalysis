home.controller("entitiesCtrl", ["$http", function($http) {
    var self = this;

    self._addTokenToRequest = function(request) {
        return request + "?tk=" + self.accessToken.token + "&tk_sec=" + self.accessToken.tokenSecret;
    };

    self.apiRoot = "http://127.0.0.1:9091/api/";
    self.accessToken = accessToken;
    self._emptyFormInput = {
        name: "",
        handles: [],
        keywords: []
    };
    self.formInput = self._emptyFormInput;
    self.shownEntity = {};
    self.comparedEntity = {};
    self.comparing = false;

    self._showError = function(message) {
        bootbox.alert("<h4 class='text-danger'><strong>Error!</strong> " + message + "</h4>");
    };

    $http.get(self._addTokenToRequest(self.apiRoot + "entities")).
        success(function(data) {
            self.entitiesList = data;
        }).
        error(function() {
            self._showError("Unable to fetch entities.");
        });

    $("#entity-form-modal").on("hidden.bs.modal", function() {
        self._clearFormHelps();
        self.formInput = self._emptyFormInput;
        $("#entity-form").find(".tag-it").tagit("removeAll");
        $("#entity-form-input-name").val("");
    });

    self._clearFormHelps = function() {
        $("#entity-form-input-name-help").addClass("hidden");
        $("#entity-form-input-handles-help").addClass("hidden");
        $("#entity-form-input-keywords-help").addClass("hidden");
    };

    self._parseFormInput = function() {
        var valid = true;
        var name, handles, keywords;

        self._clearFormHelps();

        if(self.formInput.name != "")
            name = self.formInput.name;
        else {
            valid = false;
            $("#entity-form-input-name-help").removeClass("hidden");
        }

        if(self.formInput.handles != "")
            handles = self.formInput.handles.split(",");
        else {
            valid = false;
            $("#entity-form-input-handles-help").removeClass("hidden");
        }

        if(self.formInput.keywords != "")
            keywords = self.formInput.keywords.split(",");
        else {
            valid = false;
            $("#entity-form-input-keywords-help").removeClass("hidden");
        }

        return {valid: valid, name: name, handles: handles, keywords: keywords};
    };

    self.addEntity = function() {
        var parseReturn = self._parseFormInput();
        var valid = parseReturn.valid;
        var name = parseReturn.name;
        var handles = parseReturn.handles;
        var keywords = parseReturn.keywords;
        if(!valid)
            return;

        $http.post(self.apiRoot + "entity/" + self.accessToken.token + "/" + self.accessToken.tokenSecret,
                {"name": name, "handles": handles, "keywords": keywords}).
            success(function(data) {
                self.entitiesList.push({"id": data["entity_id"], "name": name, "handles": handles, "keywords": keywords});
            }).
            error(function() {
                self._showError("Unable to add entity");
            });

        $("#entity-form-modal").modal("hide");
    };

    self.deleteEntity = function(entityId) {
        $http.delete(self.apiRoot + "entity/" + entityId + "/" + self.accessToken.token + "/" + self.accessToken.tokenSecret).
            success(function() {
                for (var i = 0; i < self.entitiesList.length; i++) {
                    if (self.entitiesList[i]["id"] == entityId) {
                        self.entitiesList.splice(i, 1);
                        break;
                    }
                }
            }).
            error(function() {
                self._showError("Unable to delete entity.");
            });
    };

    self.showEntity = function(entityId) {
        self.comparing = false;
        $("#stats-tab-selector").tab("show");
        $(".compared-entity").addClass("hidden");
        $(".shown-entity").removeClass("col-md-6").addClass("col-md-12");
        $(".collapse").collapse("show");
        self.shownEntity.id = entityId;
        self._loadEntity(self.shownEntity);
    };

    self.showEntities = function() {
        $("#entities-tab-selector").tab("show");
    };

    self._getEntityName = function(entityId) {
        for(var i=0; i<self.entitiesList.length; i++) {
            if(self.entitiesList[i]["id"] == entityId)
                return self.entitiesList[i]["name"];
        }
    };

    self._loadEntity = function(entity) {
        entity.name = self._getEntityName(entity.id);

        $http.get(self._addTokenToRequest(self.apiRoot + "entity/mostretweeted/" + entity.id + "/10")).
            success(function(data) {
                entity.mostRetweeted = data;
            }).
            error(function() {
                bootbox.alert("Unable to fetch most retweeted tweets.");
            });

        $http.get(self._addTokenToRequest(self.apiRoot + "entity/relevant_topics/" + entity.id + "/15")).
            success(function(data) {
                entity.relevantTopics = data;
            }).
            error(function() {
                bootbox.alert("Unable to fetch relevant topics.");
            });

        $http.get(self._addTokenToRequest(self.apiRoot + "entity/relevant_hashtags/" + entity.id + "/15")).
            success(function(data) {
                entity.relevantHashtags = data;
            }).
            error(function() {
                bootbox.alert("Unable to fetch relevant hashtags.");
            });

        $http.get(self._addTokenToRequest(self.apiRoot + "entity/followers/" + entity.id) + "&gran=hours").
            success(function(data) {
                entity.followers = $.map(data, function(val) {
                    val.time = new Date(val.time).toISOString();
                    return val;
                });
            }).
            error(function() {
                bootbox.alert("Unable to fetch entity followers.");
            });

        $http.get(self._addTokenToRequest(self.apiRoot + "entity/talkers/" + entity.id) + "&gran=hours").
            success(function(data) {
                entity.talkers = $.map(data, function(val) {
                    val.time = new Date(val.time).toISOString();
                    return val;
                });
            }).
            error(function() {
                bootbox.alert("Unable to fetch entity talkers.");
            });

        $http.get(self._addTokenToRequest(self.apiRoot + "entity/sentiment/" + entity.id) + "&gran=hours").
            success(function(data) {
                entity.sentiment = $.map(data, function(val) {
                    val.time = new Date(val.time).toISOString();
                    val.score = ((val.score.toFixed(2) / 2.0) - 1).toFixed(2);
                    return val;
                });
            }).
            error(function() {
                bootbox.alert("Unable to fetch entity sentiment.");
            });

        $http.get(self._addTokenToRequest(self.apiRoot + "entity/geo_distribution/" + entity.id)).
            success(function(data) {
                entity.geoDistribution = [["Latitude", "Longitude", "Size"]];
                for(var i=0; i<data.length; i++)
                    entity.geoDistribution.push([data[i].latitude, data[i].longitude, 0]);
            }).
            error(function() {
                bootbox.alert("Unable to fetch geo distribution data.");
            });
    };

    self.compareWith = function(entityId) {
        $(".collapse").collapse("show");
        if (entityId == -1) {
            self.comparing = false;
            $(".compared-entity").addClass("hidden");
            $(".shown-entity").removeClass("col-md-6").addClass("col-md-12");
            self._loadEntity(self.shownEntity);
        }
        else {
            self.comparing = true;
            $(".shown-entity").removeClass("col-md-12").addClass("col-md-6");
            $(".compared-entity").removeClass("hidden");
            self._loadEntity(self.shownEntity);
            self.comparedEntity.id = entityId;
            self._loadEntity(self.comparedEntity);
        }
    };

    return self;
}]);
