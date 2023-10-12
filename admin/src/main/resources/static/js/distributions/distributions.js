$(document).ready(function () {
    var distributionTable = $("#distributionTable").DataTable({
        "ajax": {
            "url": "/distributions/distribution-list.json",
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
            {"data": "distributionUser.fullName"},
            {"data": "distributionSubject.subjectName"},
            {"data": "distributionGroup.groupName"},
            {"data": "distributionLessonType.lessonTypeName"},
            {"data": "subjectHour"},
            {
                "data": "distributionUser.fullName",
                "render": function (data, type, row) {
                    // '<a class="mb-2 mr-2 btn-icon btn-icon-only btn-shadow btn-outline-2x btn btn-outline-warning" href="/distributions/edit.do/' + row.id + '" title="Tahrirlash"><i class="fa fa-pencil-alt"></i></a>' +
                    return '<div class="mb-2 mr-2 btn-icon btn-icon-only btn-shadow btn-outline-2x btn btn-outline-danger" id="' + row.id + '"><i class="fa fa-trash-alt"></i></div>';
                }
            }
        ],
        "serverSide": true,
        "processing": true,
        "ordering": false,
        "pageLength": 100
    });
    distributionTable.on('draw', function () {
        $('div[class="mb-2 mr-2 btn-icon btn-icon-only btn-shadow btn-outline-2x btn btn-outline-danger"]').click(function (e) {
            deletePopup(this.id);
        });
    });
});

function reload_table() {
    $('#distributionTable').DataTable().ajax.reload();
}

function deletePopup(userid) {
        Swal.fire({
            title: 'Ishonchingiz komilmi?',
            text: "Ushbu taqsimotni o`chirish!",
            type: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Ha, albatta!',
            cancelButtonText: 'Yo`q, shartmas!',
        }).then((result) => {
            if (result.value) {
                //console.log(userid);
                deleteEntity(userid);
            }
        });
}

function deleteEntity(userid) {
    console.log(userid + " oxirgisi");
    $.ajax({
        url: "/distributions/delete.json",
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: 'json',
        cache: false,
        headers: {
            'Content-Type': 'application/json', /*or whatever type is relevant */
            'Accept': 'application/json' /* ditto */
        },
        data: JSON.stringify({entityId: userid}),
        success: function (res) {
                Swal.fire(
                    'Muvaffaqiyatli o`chirildi!',
                    'Taqsimot muvaffaqiyatli o`chirildi!',
                    'success'
                )
            reload_table();
        },
        error: function (req, err) {
            Swal.fire(
                'Xatolik!',
                'Serverda qandaydir xatolik ro`y berdi.',
                'error'
            );
        }
    });

}