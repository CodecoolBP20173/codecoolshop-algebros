let shoppingCart = {

    loadCart: function (items) {
        this.changeCartModal(items);
        this.addShoppingCartButtonListeners();
    },

    changeCartModal: function (items) {
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
        $("#totalPricePlace").html(`<strong data-total-price="${utils.calculateTotalPrice(items)}" id='totalPrice'>${utils.calculateTotalPrice(items)}</strong>`);

    },

    addShoppingCartButtonListeners: function () {
        $("#checkOutButton").on("click", function () {
            window.location.replace("/checkout")
        });

        $(".incrementButton").on("click", function (event) {
            let productId = event.target.dataset.productId;
            let url = "/";
            $.ajax({
                type: "POST",
                data: {"id": productId, "process": "increment"},
                url: url,
                success: function (quantityJSONString) {
                    const quantity = JSON.parse(quantityJSONString);
                    shoppingCart.loadCart(quantity);
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
                        shoppingCart.loadCart(quantity);
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
                    shoppingCart.loadCart(quantity);
                }
            })
        })
    },

    hideCartButton: function () {
        $("#cartButton").hide()
    },

    showCartButton: function () {
        $("#cartButton").show()
    }
};
