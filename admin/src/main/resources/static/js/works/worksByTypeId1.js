$(document).ready(function () {
    var workTable1 = $("#workTable1").DataTable({
        "ajax": {
            "url": "/works/work-list-type-id1.json",
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
            {"data": "workTitle"},
            {"data": "workTeacher.fullName"},
            {"data": "workDeadline"},
            {"data": "workDoiLink",
                "render": function (data, type, row) {
                return '<a href="' + data +'">' + data + '</a>'
            }
            },
            {
                "data": "workStatus.id",
                "render": function (data, type, row) {
                    return '<div class="custom-radio custom-control"><input id="' + (row.id+"s1") +'" type="radio" name="' + row.id +'" value="1" class="custom-control-input"' + (data===1 ? 'checked' : '') + '><label for="' + (row.id+"s1") +'" class="custom-control-label" style="color: red">Bajarilmadi</label></div>' +
                           '<div class="custom-radio custom-control"><input id="' + (row.id+"s2") +'" type="radio" name="' + row.id +'" value="2" class="custom-control-input"' + (data===2 ? 'checked' : '') + '><label for="' + (row.id+"s2") +'" class="custom-control-label" style="color: orange">Bajarilmoqda</label></div>' +
                           '<div class="custom-radio custom-control"><input id="' + (row.id+"s3") +'" type="radio" name="' + row.id +'" value="3" class="custom-control-input"' + (data===3 ? 'checked' : '') + '><label for="' + (row.id+"s3") +'" class="custom-control-label" style="color: green">Bajarildi</label></div>'
                }
            },
            {"data": "workDescription"},
            {
                "data": "workTitle",
                "render": function (data, type, row) {
                    return '<a href="/works/view.do/' + row.id + '" class="mb-2 mr-2 btn-icon btn-icon-only btn-shadow btn-outline-2x btn btn-outline-primary" title="Ko`rish"><i class="fa fa-search"></i></a>' +
                        '<a class="mb-2 mr-2 btn-icon btn-icon-only btn-shadow btn-outline-2x btn btn-outline-warning" href="/works/edit.do/' + row.id + '" title="Tahrirlash"> <i class="fa fa-pencil-alt"></i></a>';
                }
            }
        ],
        "serverSide": true,
        "processing": true,
        "ordering": false,
        "pageLength": 100
    });
    workTable1.on('draw', function () {
        $('input[type=radio]').click(function (e) {
            doActiveOrDeactiveUser(this.checked, this.name, this.value);
        });
    });
});

function reload_table() {
    $('#workTable1').DataTable().ajax.reload();
}

function doActiveOrDeactiveUser(checked, workId, statusId) {
    $.ajax({
        url: "/works/status.json",
        type: "POST",
        contentType: "application/json; chartset=utf-8",
        dataType: 'json',
        cache: false,
        headers: {
            'Content-Type': 'application/json', /*or whatever type is relevant */
            'Accept': 'application/json' /* ditto */
        },
        data: JSON.stringify({entityId: workId, enable: checked, statusId: statusId}),
        success: function (res) {
            if (checked) {
                Swal.fire(
                    'Muvaffaqqiyatli!!!',
                    'Pedagogik ish statusi muvaffaqqiyatli o`zgartirildi!',
                    'success'
                )
            } else {
            }
        },
        error: function (req, err) {
            $('#' + workId).prop('checked', !checked);
            Swal.fire(
                'Xatolik!',
                'Serverda qandaydir xatolik ro`y berdi.',
                'error'
            );
        }
    });
}