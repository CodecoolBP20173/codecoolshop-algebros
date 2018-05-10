let index = {

    loadIndexPage: function () {
        this.modifyFilterButtonNames();
        this.addListeners();
    },

    modifyFilterButtonNames : function(){
        const url = utils.getURL();
        let category = url.searchParams.get("category");
        let supplier = url.searchParams.get("supplier");
        category = category !== null ? category : `Category`;
        supplier = supplier !== null ? supplier : `Supplier`;
        const categoryArrow = '<div id="categoryArrow" class="arrow">&#x25BC;</div>';
        const supplierArrow = '<div id="supplierArrow" class="arrow">&#x25BC;</div>';
        $("#categoryDropdownButton").html(category).append(categoryArrow);
        $("#supplierDropdownButton").html(supplier).append(supplierArrow);
    },

    addListeners: function () {

        $("#categoryDropdownButton").on("click", function () {
            const categoriesDropdown = $("#CategoriesDropdown");
            const categoriesArrow = $("#categoryArrow");
            categoriesDropdown.toggleClass("show");
            if (categoriesDropdown.hasClass("show")) {
                categoriesArrow.html("&#x25B2;")
            } else {
                categoriesArrow.html("&#x25BC;")

            }
        });

        

        $("#supplierDropdownButton, #supplierArrow").on("click", function () {
            const supplierDropdown = $("#SuppliersDropdown");
            const supplierArrow = $("#supplierArrow");
            supplierDropdown.toggleClass("show");
            if (supplierDropdown.hasClass("show")) {
                supplierArrow.html("&#x25B2;")
            } else {
                supplierArrow.html("&#x25BC;")

            }
        });

        $(window).on("click", function (event) {
            if (!event.target.matches('.dropbtn') && !event.target.matches('.arrow')) {

                const dropDowns = $(".dropdown-content");
                let i;
                for (i = 0; i < dropDowns.length; i++) {
                    let openDropdown = dropDowns[i];
                    if (openDropdown.classList.contains('show')) {
                        openDropdown.classList.remove('show');
                        $("#categoryArrow").html("&#x25BC;");
                        $("#supplierArrow").html("&#x25BC;")
                    }
                }
            }
        });

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
                    shoppingCart.loadCart(products);
                }
            })
        });

        $(".categorySelector").on("click", function (event) {
            const url = utils.getURL();
            const supplier = url.searchParams.get("supplier");
            let category = event.target.dataset.category;
            category = category === "None" ? null : category;
            utils.createSearchURL(url, category, supplier);
            window.location.replace(url.toString());
        });

        $(".supplierSelector").on("click", function (event) {
            const url = utils.getURL();
            const category = url.searchParams.get("category");
            let supplier = event.target.dataset.supplier;
            supplier = supplier === "None" ? null : supplier;
            utils.createSearchURL(url, category, supplier);
            window.location.replace(url.toString());
        });
    }
};

index.loadIndexPage();