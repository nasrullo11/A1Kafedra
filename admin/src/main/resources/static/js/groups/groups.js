$(document).ready(function () {
    var groupTable = $("#groupTable").DataTable({
        "ajax": {
            "url": "/groups/group-list.json",
            "type": "POST",
            "contentType": "application/json; chartset=utf-8",
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
            {
                "data": "groupName",
                "render": function (data, type, row) {
                    // return '<a href="/groups/view.do/' + row.id + '">" + row.groupName + "</a>'
                    return "<a href='/groups/view.do/" + row.id + "'>" + row.groupName + "</a>"

                }
            },
            {"data": "entranceYear"},
            {"data": "groupField.fieldCode"},
            {"data": "groupEducationType.educationTypeName"},
            {"data": "amountStudents"},
            {
                "data": "groupName",
                "render": function (data, type, row) {
                    return '<a class="mb-2 mr-2 btn-icon btn-icon-only btn-shadow btn-outline-2x btn btn-outline-warning" href="/groups/edit.do/' + row.id + '" title="Tahrirlash"> <i class="fa fa-pencil-alt"></i></a>' +
                        '<div class="mb-2 mr-2 btn-icon btn-icon-only btn-shadow btn-outline-2x btn btn-outline-danger" id="' + row.id + '"><i class="fa fa-trash-alt"></i></div>' ;
                }
            }
        ],
        "serverSide": true,
        "processing": true,
        "ordering": false,
        "pageLength": 100
    });
    groupTable.on('draw', function () {
        $('div[class="mb-2 mr-2 btn-icon btn-icon-only btn-shadow btn-outline-2x btn btn-outline-danger"]').click(function (e) {
            deletePopup(this.id);
        });
    });
});

function reload_table() {
    $('#groupTable').DataTable().ajax.reload();
}

function deletePopup(userid) {
    Swal.fire({
        title: 'Ishonchingiz komilmi?',
        text: "Ushbu guruhni o`chirish!",
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
    $.ajax({
        url: "/groups/delete.json",
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
                'Guruh muvaffaqiyatli o`chirildi!',
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