function changeCartModal(items) {
    let cartItemsOutput = ``;
    for (let item of items) {
        cartItemsOutput += `<tr class="product" data-product-id="${item["id"]}">
                                <td>${item["name"]}</td>
                                <td><button class="incrementButton" data-product-id="${item["id"]}">+</button> <span class="quantity" data-product-id="${item["id"]}" data-quantity="${item["quantity"]}">${item["quantity"]}</span> <button class="decrementButton" data-product-id="${item["id"]}">-</button></td>
                                <td class="defaultPrice" data-product-id="${item["id"]}" data-quantity="${item["quantity"]}" >${item["price"]}</td>
                                <td><button class="removeProductButton" data-product-id="${item["id"]}" title="remove">x</button></td>
                            </tr>`;
    }
    $("#cartTableBody").html(cartItemsOutput);
    $("#totalPricePlace").html(`<strong data-total-price="${calculateTotalPrice(items)}" id='totalPrice'>${calculateTotalPrice(items)}</strong>`);
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
                incrementNumberOfProduct(productId, quantity["quantity"])
            }
        })
    });
    $(".decrementButton").on("click", function (event) {
        const productId = event.target.dataset.productId;
        const filter = "[data-product-id='" + productId + "']";
        const quant = $(".quantity").filter(filter).html();
        if (quant > 0) {
            const url = "/";
            $.ajax({
                type: "POST",
                data: {"id": productId, "process": "decrement"},
                url: url,
                success: function (quantityJSONString) {
                    const quantity = JSON.parse(quantityJSONString);
                    decrementNumberOfProduct(productId, quantity["quantity"])
                }
            })
        }
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
                removeProductFromCart(quantity);
            }
        })
    })
}

function incrementNumberOfProduct(productId, quantity) {
    const filter = "[data-product-id='" + productId + "']";
    $(".quantity").filter(filter).html(quantity);
    const defaultPrice = $(".defaultPrice").filter(filter).html();
    const totalPrice = parseFloat($("#totalPrice").html()) + parseFloat(defaultPrice);
    $("#totalPrice").html(Number((totalPrice).toFixed(2)).toString());
}

function decrementNumberOfProduct(productId, quantity) {
    const filter = "[data-product-id='" + productId + "']";
    if (quantity == null) {
        quantity = 0
    }
    $(".quantity").filter(filter).html(quantity);
    const defaultPrice = $(".defaultPrice").filter(filter).html();
    const totalPrice = parseFloat($("#totalPrice").html()) - parseFloat(defaultPrice);
    $("#totalPrice").html(Number((totalPrice).toFixed(2)).toString());
}

function changePaymentToPayPal() {
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
}

function changePaymentToCard() {
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
}

function removeProductFromCart(products) {
    if (products != null){
    changeCartModal(products);
    addShoppingCartButtonListeners();
    }
    else{
        $("#cartTableBody").empty();
    }
}

function calculateTotalPrice(products) {
    let totalPrice = 0;
    for (let i = 0; i < products.length; i++) {
        totalPrice += products[i]["defaultPrice"] * products[i]["quantity"];
    }
    return totalPrice;
}