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
        $("#paymentMethod").html(`<div class="col" align="center">
                            <div class="card">
                                <div class="card-body">
                                    <form action="/payment" method="post">
                                    <h2>PayPal</h2><br>
                                    <p>User name</p>
                                    <input type="text" name="userName"><br>
                                    <br><p>Password</p>
                                    <input type="password" name="psw"><br>
                                    <br><button style="font-size:12px" id="sub">Submit</button>
                                    <br>
                                    <br><input id="checkBox" type="checkbox">
                                    <p STYLE="font-size: small">By checking this box, I agree to the Terms & Conditions & Privacy Policy.</p>
                                    </form>
                                </div>
                            </div>
                        </div>`)
    },

    changePaymentToCard: function () {
        $("#paymentMethod").html(`<div class="col" align="center">
                            <form class="card">
                                <div class="card-body">
                                    <form action="/payment" method="post">
                                    <h2>Credit card info</h2><br>
                                    <p>Name on card</p>
                                    <input type="text" name="name"><br>
                                    <br><p>Card number</p>
                                    <input type="text" name="card" value="0000-0000-0000-0000"><br>
                                        <div class="col-6">
                                            <br><p>Expiration</p>
                                            <input type="text" name="month" maxlength="2" size="4" value="1999">
                                            <input type="text" name="year" maxlength="4" size="4" value="01"><br>
                                            <div class="col-6">
                                                <br><p style="font-size: small">CVV number</p>
                                                <input type="password" name="cvv" maxlength="3" size="4" id="cvv" >

                                            </div>
                                        </div>
                                    </div>


                                    <br><button style="font-size:12px" id="sub">Submit</button>
                                    <br>
                                    <br><input id="checkBox" type="checkbox">
                                    <p STYLE="font-size: small">By checking this box, I agree to the Terms & Conditions & Privacy Policy.</p>
                                </form>
                                </div>
                            </div>
                        </div>`);
    },

    initPage : function () {
        payment.changePaymentToPayPal();
        payment.setEventListeners()
    }
};

payment.initPage();
