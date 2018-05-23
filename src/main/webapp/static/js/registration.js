let registration = {
    $password: $("#password"),
    $confirmPassword: $("#confirm_password"),

    isPasswordValid: function () {
        return registration.$password.val().length > 8;
    },

    arePasswordsMatching: function () {
        return registration.$password.val() === this.$confirmPassword.val();
    },

    canSubmit: function () {
        return registration.isPasswordValid() && registration.arePasswordsMatching();
    },

    passwordEvent: function () {
        if (registration.isPasswordValid()) {
            registration.$password.next().hide();
        } else {
            registration.$password.next().show();
        }
    },

    confirmPasswordEvent: function () {
        if (registration.arePasswordsMatching()) {
            registration.$confirmPassword.next().hide();
        } else {
            registration.$confirmPassword.next().show();
        }
    },

    enableSubmitEvent: function () {
        $("#submit").prop("disabled", !registration.canSubmit());
    },

    initRegistration: function () {
        registration.enableSubmitEvent();
        registration.$password.focus(registration.passwordEvent).keyup(registration.passwordEvent).keyup(registration.confirmPasswordEvent).keyup(registration.enableSubmitEvent);
        registration.$confirmPassword.focus(registration.confirmPasswordEvent).keyup(registration.confirmPasswordEvent).keyup(registration.enableSubmitEvent);
        $("form span").hide();
    }
};
registration.initRegistration();








