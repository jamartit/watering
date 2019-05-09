$(document).ready(function () {

    var reloadContent = function () {
        $("#watering-container").load(location.href + " #watering-container>*", "");
    }

    var executeCommand = function (command) {
        $.get(command), function () {
            reloadContent();
        }
    }

    $("#button-abort").click(function () {
        executeCommand("/api/water/abort");
    });
    $("#button-execute").click(function () {
        executeCommand("/api/water/execute");
    });

    $(".sectionBtn").click(function () {
        executeCommand("/water/toggle/" + this.attr('data-section'));
    });

    var timer = setInterval(function () {
        reloadContent();
    }, 10000);

});

