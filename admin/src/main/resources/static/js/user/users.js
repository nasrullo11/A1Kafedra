$(document).ready(function () {
    var userTable = $("#userTable").DataTable({
        "ajax": {
            "url": "/users/user-list.json",
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
            {
                "data": "fileLogo",
                "render": function (data) {
                    if(data != null) {
                        return '<div class="figure text-center" ><img src="/file/get/'+data.id+'" ' +
                            'title="'+data.fileName+'" class="img-thumbnail1 img-avatar img-avatar48" alt="User Image"/></div> ';
                    }else {
                        return '<div class="figure text-center"><img class="img-thumbnail1 img-avatar img-avatar48" src="/media/avatars/avatar2.jpg" alt="User Image"/></div> ';
                    }
                }
            },
            {"data": "fullName",
                render: function (data, type, row) {
                    return "<a href='/users/view.do/" + row.id + "'>" + row.fullName + "</a>"
                }
            },
            {"data": "username"},
            {"data": "department.departmentName"},
            {"data": "role"},
            {
                "data": "isActive",
                "render": function (data, type, row) {
                    return '<div class="custom-checkbox custom-control"><input id="' + row.id + '" type="checkbox" class="custom-control-input"' + (data ? 'checked' : '') + '><label for="' + row.id + '" class="custom-control-label" >' + (data ? 'Faol' : 'Nofaol') + '</label></div>'
                }

            },
            {
                "data": "createDate",
                "render": function (data, row) {
                    return '<label for="' + row.id + '" >' + (data != null ? ((data[0]) + '-' + (data[1]) + '-' + (data[2]) + '&nbsp; &nbsp;' + (data[3]) + ':' + (data[4])) : "no date") + '</label>'
                }
            },
            {
                "data": "username",
                "render": function (data, type, row) {
                    return '<a href="/users/view.do/' + row.id + '" class="mb-2 mr-2 btn-icon btn-icon-only btn-shadow btn-outline-2x btn btn-outline-primary" title="Ko`rish"><i class="fa fa-search"></i></a>' +
                        '<a class="mb-2 mr-2 btn-icon btn-icon-only btn-shadow btn-outline-2x btn btn-outline-warning" href="/users/edit.do/' + row.id + '" title="Tahrirlash"> <i class="fa fa-pencil-alt"></i></a>';
                }
            }
        ],
        "serverSide": true,
        "processing": true,
        "ordering": false,
        "pageLength": 100
    });
    userTable.on('draw', function () {
        $('input[type=checkbox]').click(function (e) {
            checkedActiveOrDeactiveUserPopup(this.checked, this.id);
        });
    });
    uploadUserImage();
    showUploadImgModal();
});

function reload_table() {
    $('#userTable').DataTable().ajax.reload();
}

$(document).ready(function(){
    $('input[type="file"]').change(function(e){
        var fileName = e.target.files[0].name;
        $('#fileNameModal').val(fileName);
    });
});

function uploadUserImage() {
    $("form#userImageForm").submit(function(e) {
        $("#uploadUserImage").attr( "disabled", "disabled" );
        e.preventDefault();
        var formData = new FormData(this);
        $('#uploadUserImage').addClass('disabled');
        $.ajax({
            url: '/file/upload.do',
            type: 'POST',
            data: formData,
            success: function (data) {

                var file = $('#userImage')[0].files[0].name;
                if (file){
                    $('#fileNameInput').val(file);
                }

                $("input[name='fileLogo.id']").val(data);
                Swal.fire(
                    'Muvaffaqqiyatli yuklandi!',
                    'Sizning rasmingiz muvaffaqqiyatli yuklandi.',
                    'success'
                );
                $('#addUserPhotoModal').modal('hide');
                $('body').removeClass('modal-open');
                $('.modal-backdrop').remove();
                $('#uploadUserImage').removeClass('disabled');
                $("#uploadUserImage").removeAttr( "disabled", "disabled" );
            },
            error: function (request, status, error) {
                var jsonValue = $.parseJSON(request.responseText);
                Swal.fire(
                    'Xatolik!',
                    jsonValue.message,
                    'error'
                );
                $('#uploadUserImage').removeClass('disabled');
                $("#uploadUserImage").removeAttr( "disabled", "disabled" );
            },
            cache: false,
            contentType: false,
            processData: false
        });
    });
}

function showUploadImgModal() {
    $('#addUserPhotoModal').on('shown.bs.modal', function (e) {
        $("#addUserPhotoModal").removeAttr("style");
        $("#addUserPhotoModal").css("display","block");
        $('form#userImageForm')[0].reset();
    });
}

function checkedActiveOrDeactiveUserPopup(checked, userid) {
    if (checked) {
        Swal.fire({
            title: 'Ishonchingiz komilmi?',
            text: "Ushbu foydalanuvchini faollashtirish!",
            type: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Ha, albatta!',
            cancelButtonText: 'Yo`q, shartmas!',
        }).then((result) => {
            if (result.value) {
            doActiveOrDeactiveUser(checked, userid);
        } else {
            $('#' + userid).prop('checked', !checked);
        }
    });
    } else {
        Swal.fire({
            title: 'Ishonchingiz komilmi?',
            text: "Ushbu foydalanuvchini nofaollashtirish, keyinchalik u sizning tizimingizga kirolmasligi mumkin!",
            type: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Ha, albatta!',
            cancelButtonText: 'Yo`q, shartmas!'
        }).then((result) => {
            if (result.value) {
            doActiveOrDeactiveUser(checked, userid);
        } else {
            $('#' + userid).prop('checked', !checked);
        }
    });
    }
}

function doActiveOrDeactiveUser(checked, userid) {
    $.ajax({
        url: "/users/active.json",
        type: "POST",
        contentType: "application/json; chartset=utf-8",
        dataType: 'json',
        cache: false,
        headers: {
            'Content-Type': 'application/json', /*or whatever type is relevant */
            'Accept': 'application/json' /* ditto */
        },
        data: JSON.stringify({entityId: userid, enable: checked}),
        success: function (res) {
            var txtElem = $('#' + userid).next('label');
            if (checked) {
                txtElem.html('Faol');
                Swal.fire(
                    'Faollashtirildi!',
                    'Foydalanuvchi muvaffaqqiyatli faollashtirildi.',
                    'success'
                )
            } else {
                txtElem.html('Nofaol');
                Swal.fire(
                    'Nofaollashtirildi!',
                    'Foydalanuvchi muvaffaqqiyatli nofaollashtirildi.',
                    'success'
                );
            }
        },
        error: function (req, err) {
            $('#' + userid).prop('checked', !checked);
            Swal.fire(
                'Xatolik!',
                'Serverda qandaydir xatolik ro`y berdi.',
                'error'
            );
        }
    });

}

