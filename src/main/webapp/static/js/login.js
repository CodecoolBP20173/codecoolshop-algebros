let login = {


    loginInit: function () {
        login.loginAjax();
    },

    loginTrue: function (userinfo) {
        $('#loginPlace').html("<button id=\"profile-bn\">" + userinfo[0]["username"] + "\'s profile</button>" +
            "<button id=\"logout-bn\">Logout</button>");
    },

    logoutReplace: function () {
        $('#loginPlace').html(loginButton);
    },


    loginAjax : function () {
        $('#login').click(function()
        {
            let user=$('#useremail').val();
            let pwd=$('#userpassword').val();
            $.ajax({
                type: "POST",
                url:"/login",
                data:{"user":user,"password":pwd},
                success: function (data) {
                    if (JSON.parse(data)[0]["auth"] == "True") {
                        login.loginTrue(JSON.parse(data));
                        login.logoutAjax();
                    } else {
                        login.loginFailedAjax();
                        login.loginAjax();
                    }
                }
            });
        });
    },

    loginFailedAjax: function () {
        $('#authFail').html("Wrong e-mail or password.");
    },


    logoutAjax : function () {
        $('#logout-bn').click(function()
        {
            $.ajax({
                type: "POST",
                url:"/logout",
                data:{"user": "logging-out"},
                success: function () {
                    login.logoutReplace();
                    login.loginAjax();
                }
            });
        });
    },

};

login.loginInit();

var loginButton = `<li class="dropdown">
                    <button id="login-bn" class="dropdown-toggle" data-toggle="dropdown">Login</button>
                    <ul id="login-dp" class="dropdown-menu">
                        <li>
                            <div class="row">
                                <div class="col-md-12">
                                    <form class="form" role="form" method="post" action="/login" accept-charset="UTF-8" id="login-nav">
                                        <div class="form-group">
                                            <label class="sr-only" for="useremail">Email address</label>
                                            <input type="email" class="form-control" name="useremail" id="useremail" placeholder="Email address" required>
                                        </div>
                                        <div class="form-group">
                                            <label class="sr-only" for="userpassword">Password</label>
                                            <input type="password" class="form-control" name="userpassword" id="userpassword" placeholder="Password" required>
                                        </div>
                                        <div class="form-group">
                                            <button id="login" type="button" name="login" class="btn btn-success btn-block">Sign in</button>
                                        </div>
                                    </form>
                                </div>
                                <div class="bottom text-center">
                                    New here ? <a href="/registration"><b>Join Us</b></a>
                                </div>
                            </div>
                        </li>
                    </ul>
                </li>`;




