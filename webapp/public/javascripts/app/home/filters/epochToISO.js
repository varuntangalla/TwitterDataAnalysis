home.filter("epochToISO", function() {
    return function(epochTime) {
        return new Date(epochTime).toISOString();
    }
});
