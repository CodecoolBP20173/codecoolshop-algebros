let utils = {
    createSearchURL: function (url, category, supplier) {
        url.searchParams.delete("category");
        url.searchParams.delete("supplier");
        if (category !== null) {
            url.searchParams.append("category", category)
        }
        if (supplier !== null) {
            url.searchParams.append("supplier", supplier)
        }
    },

    calculateTotalPrice: function (products) {
        let totalPrice = 0;
        for (let i = 0; i < products.length; i++) {
            totalPrice += products[i]["price"] * products[i]["quantity"];
        }
        return totalPrice;
    },

    getURL: function () {
        let url_string = window.location.href;
        return new URL(url_string);
    }
};