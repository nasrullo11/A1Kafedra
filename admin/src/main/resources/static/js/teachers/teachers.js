$(document).ready(function () {
    var teacherTable = $("#teacherTable").DataTable({
        "ajax": {
            "url": "/teachers/teacher-list.json",
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
                "data": "teacherFileLogo",
                "render": function (data) {
                    if(data != null) {
                        return '<div class="figure text-center" ><img src="/file/get/'+data.id+'" ' +
                            'title="'+data.fileName+'" class="img-thumbnail1 img-avatar img-avatar48" alt="teacher Image"/></div> ';
                    }else {
                        return '<div class="figure text-center"><img class="img-thumbnail1 img-avatar img-avatar48" src="/media/avatars/avatar2.jpg" alt="teacher Image"/></div> ';
                    }
                }
            },
            {"data": "teacherName",
                render: function (data, type, row) {
                    return "<a href='/teachers/view.do/" + row.id + "'>" + row.teacherName + "</a>"
                }
            },
            {"data": "teacherSurname",
                render: function (data, type, row) {
                    return "<a href='/teachers/view.do/" + row.id + "'>" + row.teacherSurname + "</a>"
                }
            },
            {"data": "teacherUsername"},
            {
                "data": "teacherUsername",
                "render": function (data, type, row) {
                    return '<a href="/teachers/view.do/' + row.id + '" class="mb-2 mr-2 btn-icon btn-icon-only btn-shadow btn-outline-2x btn btn-outline-primary" title="Посмотреть"><i class="fa fa-search"></i></a>' +
                        '<a class="mb-2 mr-2 btn-icon btn-icon-only btn-shadow btn-outline-2x btn btn-outline-warning" href="/teachers/edit.do/' + row.id + '" title="Редактировать"> <i class="fa fa-pencil-alt"></i></a>';
                }
            }
        ],
        "serverSide": true,
        "processing": true,
        "ordering": false,
        "pageLength": 100
    });
    uploadTeacherImage();
    showUploadImgModal();
});

function reload_table() {
    $('#teacherTable').DataTable().ajax.reload();
}

$(document).ready(function(){
    $('input[type="file"]').change(function(e){
        var fileName = e.target.files[0].name;
        $('#fileNameModal').val(fileName);
    });
});

function uploadTeacherImage() {
    $("form#teacherImageForm").submit(function(e) {
        $("#uploadTeacherImage").attr( "disabled", "disabled" );
        e.preventDefault();
        var formData = new FormData(this);
        $('#uploadTeacherImage').addClass('disabled');
        $.ajax({
            url: '/file/upload.do',
            type: 'POST',
            data: formData,
            success: function (data) {

                var file = $('#teacherImage')[0].files[0].name;
                if (file){
                    $('#fileNameInput').val(file);
                }

                $("input[name='teacherFileLogo.id']").val(data);
                Swal.fire(
                    'Зугрузка было успешно!',
                    'Ваше Фото успешно загрузылся.',
                    'success'
                );
                $('#addTeacherPhotoModal').modal('hide');
                $('body').removeClass('modal-open');
                $('.modal-backdrop').remove();
                $('#uploadTeacherImage').removeClass('disabled');
                $("#uploadTeacherImage").removeAttr( "disabled", "disabled" );
            },
            error: function (request, status, error) {
                var jsonValue = $.parseJSON(request.responseText);
                Swal.fire(
                    'Ошибка!',
                    jsonValue.message,
                    'error'
                );
                $('#uploadTeacherImage').removeClass('disabled');
                $("#uploadTeacherImage").removeAttr( "disabled", "disabled" );
            },
            cache: false,
            contentType: false,
            processData: false
        });
    });
}

function showUploadImgModal() {
    $('#addTeacherPhotoModal').on('shown.bs.modal', function (e) {
        $("#addTeacherPhotoModal").removeAttr("style");
        $("#addTeacherPhotoModal").css("display","block");
        $('form#teacherImageForm')[0].reset();
    });
}


