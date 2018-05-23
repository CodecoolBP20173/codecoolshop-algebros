let login = {

    loginInit: function () {
        login.loginAjax();
        login.logoutAjax();
        login.profileButton();
    },

    loginTrue: function (userinfo) {
        $('#loginPlace').html("<button id=\"profile-bn\">" + userinfo[0]["username"] + "\'s profile</button>" +
            "<button id=\"logout-bn\">Logout</button>");
        shoppingCart.showCartButton();
    },

    logoutReplace: function () {
        $('#loginPlace').html(loginButton);
    },

    loginAjax: function () {
        $('#login').on("click", function () {
            let user = $('#useremail').val();
            let pwd = $('#userpassword').val();
            $.ajax({
                type: "POST",
                url: "/login",
                data: {"user": user, "password": pwd},
                success: function (data) {
                    if (JSON.parse(data)[0]["auth"] === "True") {
                        login.loginTrue(JSON.parse(data));
                        login.profileButton();
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

    logoutAjax: function () {
        $('#logout-bn').on("click", function () {
            $.ajax({
                type: "POST",
                url: "/logout",
                data: {"user": "logging-out"},
                success: function () {
                    login.logoutReplace();
                    login.loginAjax();
                    shoppingCart.hideCartButton();
                }
            });
        });
    },

    profileButton: function () {
        $('#profile-bn').on('click', function () {
            window.location.replace("/user");
        })
    },

    var: loginButton = $("#loginButton").html(),
};

login.loginInit();






