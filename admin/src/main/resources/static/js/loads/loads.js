var subjectId = $('#subjectId').val();
console.log(subjectId + " subjectId");
$(document).ready(function () {
    var loadTable = $("#loadTable").DataTable({
        "ajax": {
            "url": "/loads/load-list.json/" + subjectId,
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "dataType": 'json',
            "headers": {
                'Content-Type': 'application/json', /*or whatever type is relevant */
                'Accept': 'application/json' /* ditto */
            },
            "data": function (json) {
                return JSON.stringify(json);
            }
        },
        "columns": [
            {"data": "loadField.fieldName"},
            {"data": "semester"},
            {"data": "subjectHour"},
            {
                "data": "loadSubject.subjectName",
                "render": function (data, type, row) {
                    return '<a class="mb-2 mr-2 btn-icon btn-icon-only btn-shadow btn-outline-2x btn btn-outline-warning" href="/loads/edit.do/' + row.id + '" title="Tahrirlash"> <i class="fa fa-pencil-alt"></i></a>';
                }
            }
        ],
        "serverSide": true,
        "processing": true,
        "ordering": false,
        "pageLength": 100
    });
});

function reload_table() {
    $('#loadTable').DataTable().ajax.reload();
}