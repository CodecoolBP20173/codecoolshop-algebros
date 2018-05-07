function changeCartModal(items) {
    let cartItemsOutput = `<tr>
                                   <th>Product</th> 
                                       <th>Quantity</th>
                                        <th>Price</th>
                                        <th> </th>
                                   </tr>`;
    for (let item of items) {
        cartItemsOutput += `<tr class="product" data-product-id="${item["id"]}">
                                <td>${item["name"]}</td>
                                <td><button class="incrementButton" data-product-id="${item["id"]}">+</button> <span class="quantity" data-product-id="${item["id"]}" data-quantity="${item["quantity"]}">${item["quantity"]}</span> <button class="decrementButton" data-product-id="${item["id"]}">-</button></td>
                                <td class="defaultPrice" data-product-id="${item["id"]}" data-quantity="${item["quantity"]}" data-default-price="${item["defaultPrice"]}">${item["price"]}</td>
                                <td><button class="removeProductButton" data-product-id="${item["id"]}" title="remove">x</button></td>
                            </tr>`;
    }
    document.getElementById("cartTableBody").innerHTML = cartItemsOutput;
    document.getElementById("totalPricePlace").innerHTML = `<strong id='totalPrice'>${items[0]['totalPrice']} USD<strong>`;
}

function addShoppingCartButtonListeners() {
    $(".incrementButton").on("click", function (event) {
        let productId = event.target.dataset.productId;
        let url = "/";
        $.ajax({
            type: "POST",
            data: {"id": productId, "process": "increment"},
            url: url,
            success: function (quantityJSONString) {
                const quantity = JSON.parse(quantityJSONString);
                incrementNumberOfProduct(productId, quantity["quantity"], quantity["totalPrice"])
            }
        })
    });
    $(".decrementButton").on("click", function (event) {
        const productId = event.target.dataset.productId;
        const url = "/";
        $.ajax({
            type: "POST",
            data: {"id": productId, "process": "decrement"},
            url: url,
            success: function (quantityJSONString) {
                const quantity = JSON.parse(quantityJSONString);
                decrementNumberOfProduct(productId, quantity["quantity"], quantity["totalPrice"])
            }
        })
    });
    $(".removeProductButton").on("click", function (event) {
        const productId = event.target.dataset.productId;
        const url = "/";
        $.ajax({
            type: "POST",
            data: {"id": productId, "process": "remove"},
            url: url,
            success: function (quantityJSONString) {
                const quantity = JSON.parse(quantityJSONString);
                removeProductFromCart(productId, quantity[0]["totalPrice"]);
                $("#totalPricePlace").html("<strong>" + Math.round(quantity[0]["totalPrice"]) + " USD</strong>")
            }
        })
    })
}

function incrementNumberOfProduct(productId, quantity, totalPrice) {
    const filter = "[data-product-id='" + productId + "']";
    $(".quantity").filter(filter).html(quantity);
    $("#totalPricePlace").html("<strong>" + Math.round(totalPrice) + " USD</strong>")
}

function decrementNumberOfProduct(productId, quantity, totalPrice) {
    const filter = "[data-product-id='" + productId + "']";
    if (quantity > 0) {
        $(".quantity").filter(filter).html(quantity);
        $("#totalPricePlace").html("<strong>" + Math.round(totalPrice) + " USD</strong>")
    }
    else {
        removeProductFromCart(productId, totalPrice)
    }
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

function removeProductFromCart(productId, totalPrice) {
    const filter = "[data-product-id='" + productId + "']";
    $("#totalPricePlace").html("<strong>" + totalPrice + " USD</strong>");
    $(".product").filter(filter).remove();
}