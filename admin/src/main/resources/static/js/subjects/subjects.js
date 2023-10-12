$(document).ready(function () {
    var subjectTable = $("#subjectTable").DataTable({
        "ajax": {
            "url": "/subjects/subject-list.json",
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
            {"data": "id"},
            {"data": "subjectName"},
            {"data": "subjectBall"},
            {"data": "subjectDepartment.departmentName"},
            {
                "data": "subjectDepartment.id",
                "render": function (data, type, row) {
                    return '<div class="custom-checkbox custom-control"><input id="' + row.id + '" type="checkbox" class="custom-control-input"><label for="' + row.id + '" class="custom-control-label" >Kafedraga o`tkazish</label></div>'
                }
            },
            {
                "data": "subjectName",
                "render": function (data, type, row) {
                    return '<a class="mb-2 mr-2 btn-icon btn-icon-only btn-shadow btn-outline-2x btn btn-outline-warning" href="/subjects/edit.do/' + row.id + '" title="Tahrirlash"> <i class="fa fa-pencil-alt"></i></a>';
                }
            }
        ],
        "serverSide": true,
        "processing": true,
        "ordering": false,
        "pageLength": 100
    });
    subjectTable.on('draw', function () {
        $('input[type=checkbox]').click(function (e) {
            checkedActiveOrDeactiveUserPopup(this.id);
        });
    });
});

function reload_table() {
    $('#subjectTable').DataTable().ajax.reload();
}

function checkedActiveOrDeactiveUserPopup(userid) {
        Swal.fire({
            title: 'Ishonchingiz komilmi?',
            text: "Ushbu fanni o'z kafedrangizga o'tkazyapsiz!",
            type: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Ha, albatta!',
            cancelButtonText: 'Yo`q, adashdim!',
        }).then((result) => {
            doActiveOrDeactiveUser(userid);
        });
}

function doActiveOrDeactiveUser(userid) {
    $.ajax({
        url: "/subjects/change-department.json",
        type: "POST",
        contentType: "application/json; chartset=utf-8",
        dataType: 'json',
        cache: false,
        headers: {
            'Content-Type': 'application/json', /*or whatever type is relevant */
            'Accept': 'application/json' /* ditto */
        },
        data: JSON.stringify({entityId: userid}),
        success: function (res) {
            var txtElem = $('#' + userid).next('label');
                txtElem.html('Kafedrada');
                Swal.fire(
                    'Muvaffaqiyatli!',
                    'Fan endi sizning kafedrangizga tegishli!',
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
