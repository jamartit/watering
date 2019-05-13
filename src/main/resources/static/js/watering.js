$(document).ready(function () {

    var reloadContent = function () {
        window.location.reload(false);
    }

    var executeCommand = function (command) {
        $.get(command), function () {
        }
    }

    $("#button-abort").click(function () {
        executeCommand("/api/water/abort");
    });
    $("#button-execute").click(function () {
        executeCommand("/api/water/execute");
    });

    $(".sectionBtn").click(function () {
        executeCommand("/api/water/toggle/" + $(this).data('section'));
    });

    var timer = setInterval(function () {
        reloadContent();
    }, 10000);

});

