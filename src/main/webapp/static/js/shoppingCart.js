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
        $(".incrementButton").on("click", function (event) {
            let productId = event.target.dataset.productId;
            let url = "/";
            $.ajax({
                type: "POST",
                data: {"id": productId, "process": "increment"},
                url: url,
                success: function (quantityJSONString) {
                    const quantity = JSON.parse(quantityJSONString);
                    this.incrementNumberOfProduct(productId, quantity["quantity"])
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
                        this.decrementNumberOfProduct(productId, quantity["quantity"])
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
                    this.removeProductFromCart(quantity);
                }
            })
        })
    },

    incrementNumberOfProduct: function (productId, quantity) {
        const filter = "[data-product-id='" + productId + "']";
        $(".quantity").filter(filter).html(quantity);
        const defaultPrice = $(".defaultPrice").filter(filter).html();
        const totalPrice = parseFloat($("#totalPrice").html()) + parseFloat(defaultPrice);
        $("#totalPrice").html(Number((totalPrice).toFixed(2)).toString());
    },

    decrementNumberOfProduct: function (productId, quantity) {
        const filter = "[data-product-id='" + productId + "']";
        if (quantity == null) {
            quantity = 0
        }
        $(".quantity").filter(filter).html(quantity);
        const defaultPrice = $(".defaultPrice").filter(filter).html();
        const totalPrice = parseFloat($("#totalPrice").html()) - parseFloat(defaultPrice);
        $("#totalPrice").html(Number((totalPrice).toFixed(2)).toString());
    },

    removeProductFromCart: function (products) {
        if (products != null) {
            this.changeCartModal(products);
            this.addShoppingCartButtonListeners();
        }
        else {
            $("#cartTableBody").empty();
        }
    }
}
