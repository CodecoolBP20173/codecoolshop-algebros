let payment = {
    setEventListeners : function () {
        $(".pay-pal").on("click", function () {
            payment.changePaymentToPayPal();
        });
        $(".credit-card").on("click", function () {
            payment.changePaymentToCard();
        });
        $("#sub").on('click', function () {
            alert("Your payment was successful!");
        });
    },

    changePaymentToPayPal : function (){
        $("#paymentMethod").html($("#paymentPayPal").html())
    },

    changePaymentToCard: function () {
        $("#paymentMethod").html($("#paymentCard").html());
    },

    initPage : function () {
        payment.changePaymentToPayPal();
        payment.setEventListeners()
    }
};

payment.initPage();
