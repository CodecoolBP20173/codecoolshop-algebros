$(document).ready(function(){
    $('#login').click(function()
    {
        let user=$('#useremail').val();
        let pwd=$('#userpassword').val();
        $.ajax({
            type: "POST",
            url:"/login",
            data:{"user":user,"password":pwd},
            success: function (data) {
                if (data=='True') {

                    loginTrue();
                } else {
                    alert('Fail....');
                }
            }
        });
    });
});


function loginTrue() {
    $('#login-bn').parent().remove();
    $('#loginPlace').html("You are logged in as Hole" + "<a href='/logout'>Logout</a>");
}