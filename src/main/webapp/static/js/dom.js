function changeCartModal(items) {
    let cartItemsOutput =`<tr>
                                   <th>Product</th> 
                                       <th>Quantity</th>
                                        <th>Price</th>
                                   </tr>`;

    let totalPrice = 0;
    for (let item of items) {
        totalPrice+=item.price;
        cartItemsOutput+=`<tr>
                                <td>${item["name"]}</td>
                                <td><img src="/static/img/minus_sign.png" alt=""> ${item.quantity} <img src="/static/img/plus_sign.png"
                                                                                         alt=""></td>
                                <td>${item.price}</td>
                            </tr>`;
    }
    document.getElementById("cartTableBody").innerHTML=cartItemsOutput;
    document.getElementById("totalPrice").innerHTML="Total price : " + totalPrice.toString();
}