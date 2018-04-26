function changeCartModal(items) {
    let cartItemsOutput =`<tr>
                                   <th>Product</th> 
                                       <th>Quantity</th>
                                        <th>Price</th>
                                   </tr>`;

    let totalPrice = 0;
    for (let item of items) {
        totalPrice+=item["defaultPrice"];
        cartItemsOutput+=`<tr>
                                <td>${item["name"]}</td>
                                <td><img src="/static/img/minus_sign.png" alt=""> 1 <img src="/static/img/plus_sign.png"
                                                                                         alt=""></td>
                                <td>${item["defaultPrice"]}</td>
                            </tr>`;
    }
    document.getElementById("cartTableBody").innerHTML=cartItemsOutput;
    document.getElementById("totalPrice").innerHTML="<strong id=\"totalPrice\"> Total price : " + totalPrice.toString()+"<strong>";
}