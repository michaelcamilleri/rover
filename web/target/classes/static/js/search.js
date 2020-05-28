$(document).ready(function() {
   $.ajax({
       url: "http://localhost/results",
       type: "GET",
       success: function(result) {
           $("#results").html(result);
       },
       error: function(error) {
           console.log(error);
       }
   })
});

function filter(minRatingsScore) {
    let resultsUrl = "http://localhost/results";
    if (document.getElementById("checkbox-" + minRatingsScore).checked ) {
        resultsUrl = "http://localhost/results?minRatingsScore=" + minRatingsScore;
        for (let i=1; i<=5; i++) {
            document.getElementById("checkbox-" + i).checked = i === minRatingsScore;
        }
    }
    $.ajax({
        url: resultsUrl,
        type: "GET",
        success: function(result) {
            $("#results").html(result);
        },
        error: function(error) {
            console.log(error);
        }
    })
}
