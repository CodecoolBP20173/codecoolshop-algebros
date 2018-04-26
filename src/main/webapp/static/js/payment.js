changePaymentToPayPal();

$(".pay-pal").on("click", function () {
    changePaymentToPayPal();
});
$(".credit-card").on("click", function () {
    changePaymentToCard();
});
$("#sub").on('click',function(){
    alert("success");
})
