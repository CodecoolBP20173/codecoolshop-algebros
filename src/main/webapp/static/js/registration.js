let registration = {
    $password: $("#password"),
    $confirmPassword: $("#confirm_password"),

    isPasswordValid: function () {
        return this.$password.val().length > 8;
    },

    arePasswordsMatching: function () {
        return this.$password.val() === this.$confirmPassword.val();
    },

    canSubmit: function () {
        return registration.isPasswordValid() && payment.arePasswordsMatching();
    },

    passwordEvent: function () {
        if (registration.isPasswordValid()) {
            this.$password.next().hide();
        } else {
            this.$password.next().show();
        }
    },

    confirmPasswordEvent: function () {
        if (payment.arePasswordsMatching()) {
            this.$confirmPassword.next().hide();
        } else {
            this.$confirmPassword.next().show();
        }
    },

    enableSubmitEvent: function () {
        $("#submit").prop("disabled", !payment.canSubmit());
    },

    initRegistration: function () {
        payment.enableSubmitEvent();
        this.$password.focus(payment.passwordEvent).keyup(payment.passwordEvent).keyup(payment.confirmPasswordEvent).keyup(payment.enableSubmitEvent);
        this.$confirmPassword.focus(payment.confirmPasswordEvent).keyup(payment.confirmPasswordEvent).keyup(payment.enableSubmitEvent);
        $("form span").hide();
    }
};
registration.initRegistration();








