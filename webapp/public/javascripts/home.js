$(document).ready(function() {
    $(".tag-it").tagit();
    $('.collapse' ).collapse();
});

function doEntityDelete(entityId) {
    $("#entity-confirm-delete-modal-" + entityId).modal("hide");
    $("#hidden-entity-delete-button-" + entityId).click();
}
