
$(".addToCart").on("click", function (event) {
    const attribute = event.target.dataset.productId;
    const url = "/";
    $.ajax({
        type: "POST",
        data: {"id": attribute, "process": "add"},
        url: url,
        success: function () {
        }
    })
});

$("#cartButton").on("click", function () {
    const url = "/";
    $.ajax({
        type: "POST",
        data: {"id": 0, "process": "openCart"},
        url: url,
        success: function (productsJSONString) {
            const products = JSON.parse(productsJSONString);
            changeCartModal(products);
            addShoppingCartButtonListeners();
            $("#totalPricePlace").html("<strong>" + Math.round(products[0]["totalPrice"]) + " USD</strong>")

        }
    })
});
