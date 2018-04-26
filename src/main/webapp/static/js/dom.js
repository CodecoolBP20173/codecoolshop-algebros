function changeCartModal(items) {
    let cartItemsOutput = `<tr>
                                   <th>Product</th> 
                                       <th>Quantity</th>
                                        <th>Price</th>
                                   </tr>`;

    let totalPrice = 0;
    for (let item of items) {
        totalPrice += item["defaultPrice"];
        cartItemsOutput += `<tr>
                                <td>${item["name"]}</td>
                                <td><img src="/static/img/minus_sign.png" alt=""> 1 <img src="/static/img/plus_sign.png"
                                                                                         alt=""></td>
                                <td>${item["defaultPrice"]}</td>
                            </tr>`;
    }
    document.getElementById("cartTableBody").innerHTML = cartItemsOutput;
    document.getElementById("totalPrice").innerHTML = "<strong id=\"totalPrice\"> Total price : " + totalPrice.toString() + "<strong>";
}

function changePaymentToPayPal() {
    document.getElementById("paymentMethod").innerHTML = `<div class="col" align="center">
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
                        </div>`;
}

function changePaymentToCard() {
    document.getElementById("paymentMethod").innerHTML = `<div class="col" align="center">
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
                        </div>`;
}
