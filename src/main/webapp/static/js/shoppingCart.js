// When the user scrolls the page, execute myFunction


window.onscroll = function() {myFunction()};

// Get the navbar
let navbar = document.getElementById("navbar");

// Get the offset position of the navbar
let sticky = navbar.offsetTop;

// Add the sticky class to the navbar when you reach its scroll position. Remove "sticky" when you leave the scroll position
function myFunction() {
    if (window.pageYOffset >= sticky) {
        navbar.classList.add("sticky")
    } else {
        navbar.classList.remove("sticky");
    }
}


$(".addToCart").on("click", function (event) {
    const attribute = event.target.dataset.productId;
    const url = "/";
    $.ajax({
        type: "POST",
        data: {"id" : attribute, "process": "add"},
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
            const products =  JSON.parse(productsJSONString);
            changeCartModal(products);
            addShoppingCartButtonListeners(); $("#totalPricePlace").html("<strong>" + Math.round(products[0]["totalPrice"]) + " USD</strong>")

        }
    })
});
