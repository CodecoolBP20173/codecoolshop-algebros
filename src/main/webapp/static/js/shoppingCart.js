
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
        }
    })
});

$(".categorySelector").on("click", function (event) {
    let url_string = window.location.href;
    let url = new URL(url_string);
    let supplier = url.searchParams.get("supplier");
    let category = event.target.dataset.category;
    category = category === "None" ? null : category;
    createSearchURL(url, category, supplier);
    window.location.replace(url.toString());
});

function createSearchURL(url, category, supplier) {
    url.searchParams.delete("category");
    url.searchParams.delete("supplier");
    if (category !== null) {
        url.searchParams.append("category", category)
    }
    if (supplier !== null) {
        url.searchParams.append("supplier", supplier)
    }
}

$(".supplierSelector").on("click", function (event) {
    let url_string = window.location.href;
    let url = new URL(url_string);
    let category = url.searchParams.get("category");
    let supplier = event.target.dataset.supplier;
    supplier = supplier === "None" ? null : supplier;
    createSearchURL(url, category, supplier);
    window.location.replace(url.toString());
});
