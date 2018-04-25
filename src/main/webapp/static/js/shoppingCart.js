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
    let attribute = event.target.dataset.productId;
    let url = "/";
    $.ajax({
        type: "POST",
        data: {"id" : attribute},
        url: url,
        success: function () {
            alert("success")
        }
        })
});

let product1 = {name:"Samsung Galaxy S9",quantity:2,price:200};
let product2 = {name:"Apple iPhone X",quantity:1,price:2000};
let products = [product1,product2];
console.log(product1);
changeCartModal(products);





