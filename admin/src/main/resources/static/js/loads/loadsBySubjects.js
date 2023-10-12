$(document).ready(function () {
    const url = "/dashboard?action=load_by_subject_list";
    $.getJSON(url, function (data) {
        $.each(data.loadBySubject, function(i) {
            $('#myTbody').append($('<tr>')
                .append($('<td>')
                    .append($('<a>')
                        .append(data.loadBySubject[i].subjectName)
                        .attr('href', 'list.do/' + data.loadBySubject[i].subjectId)))
                .append($('<td>')
                    .append(data.loadBySubject[i].totalHour)));
        })
    });
});