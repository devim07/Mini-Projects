class Customer {
    constructor(cnum, cname, city, rating) {
        this.cnum = cnum;
        this.cname = cname;
        this.rating = rating;
        this.city = city;
    }
}

class ItemStatus {
    constructor(status, index, object) {
        this.status = status;
        this.object = object;
        this.index = index;
    }
}

class Orders {
    constructor(onum, cnum, unit, amt, odate, status, batch) {
        this.onum = onum;
        this.cnum = cnum;
        this.unit = unit;
        this.amt = amt;
        this.odate = odate;
        this.status = status;
        this.batch = batch;
    }
}

class Idea {
    constructor() {
        this.cust = [
            new Customer(1, 'AKSHAY JAGTAP', 'BOMB', 8),
            new Customer(2, 'DEVI DINESH', 'DELH', 9),
            new Customer(3, 'ANIKET PATTIWAR', 'PUNE', 6),
            new Customer(4, 'DISHI KANADE ', 'CHEN', 4),
            new Customer(5, 'DIVYANI INGLE', 'NAGP', 7),
            new Customer(6, 'ASHOK PATE', 'BOMB', 5),
            new Customer(7, 'ANUJ GAONKAR', 'BEED', 10),
            new Customer(8, 'ARVIND BIRADAR', 'DELH', 3)
        ]

        this.orders = [
            new Orders(91731, 6, 800, 400000, "2022-05-21", "ORDER_PLACED", "NULL "),
            new Orders(53894, 1, 600, 300000, "2022-05-15", "ORDER_PLACED", "NULL "),
            new Orders(25912, 3, 900, 450000, "2022-05-12", "PROCESSING  ", "NULL "),
            new Orders(58557, 4, 750, 375000, "2022-05-01", "IN_TRANSIT  ", "11,12"),
            new Orders(55623, 5, 500, 250000, "2022-04-24", "IN_TRANSIT  ", "10,11"),
            new Orders(47881, 4, 250, 125000, "2021-11-15", "DELIVERED   ", "9    "),
            new Orders(66517, 6, 500, 250000, "2021-09-15", "DELIVERED   ", "8,9  "),
            new Orders(94902, 1, 800, 400000, "2021-06-30", "DELIVERED   ", "6,7,8"),
            new Orders(35997, 2, 750, 375000, "2021-04-05", "DELIVERED   ", "5,6  "),
            new Orders(39853, 2, 300, 150000, "2021-03-12", "DELIVERED   ", "10   "),
            new Orders(57492, 8, 300, 150000, "2021-03-05", "DELIVERED   ", "4,5  "),
            new Orders(41921, 7, 700, 350000, "2021-01-01", "DELIVERED   ", "3,4  "),
            new Orders(50705, 3, 450, 225000, "2020-12-01", "DELIVERED   ", "2,3  "),
            new Orders(37202, 1, 500, 250000, "2020-11-15", "DELIVERED   ", "1,2  "),
            new Orders(45101, 3, 250, 125000, "2020-09-01", "DELIVERED   ", "1    ")
        ]
    }


    find_cust(in_cnum) {
        let output = new ItemStatus(false, -1, {});

        for (let i = 0; i < this.cust.length; i++) {
            if (in_cnum == this.cust[i].cnum) {
                output.status = true;
                output.object = this.cust[i];
                output.index = i;
                break;
            }
        }
        console.log("cnum " + in_cnum + " found: " + output.status);
        return output;
    }


    displayOrder(in_cnum) {
        var tblo = document.querySelector("#tblo");
        var tblc = document.querySelector("#tblc");
        tblo.style.visibility = "visible";
        tblc.style.visibility = "hidden";
        for (let i = tblo.rows.length - 1; i > 0; i--) {
            tblo.deleteRow(i);
        }
        for (let i = 0; i < this.orders.length; i++) {
            if (in_cnum == this.orders[i].cnum) {
                var r = tblo.insertRow();
                var onum = r.insertCell();
                var cnum = r.insertCell();
                var unit = r.insertCell();
                var amt = r.insertCell();
                var odate = r.insertCell();
                var status = r.insertCell();
                var batch = r.insertCell();

                onum.innerHTML = this.orders[i].onum;
                cnum.innerHTML = this.orders[i].cnum;
                unit.innerHTML = this.orders[i].unit;
                amt.innerHTML = this.orders[i].amt;
                odate.innerHTML = this.orders[i].odate;
                status.innerHTML = this.orders[i].status;
                batch.innerHTML = this.orders[i].batch;
            }
        }
        return true;
    }


    displayAll() {
        var tblo = document.querySelector("#tblo");
        var tblc = document.querySelector("#tblc");
        tblo.style.visibility = "hidden";
        tblc.style.visibility = "visible";
        for (let i = tblc.rows.length - 1; i > 0; i--) {
            tblc.deleteRow(i);
        }
        for (let i = 0; i < this.cust.length; i++) {
            var r = tblc.insertRow();
            var cnum = r.insertCell();
            var cname = r.insertCell();
            var city = r.insertCell();
            var rating = r.insertCell();

            cnum.innerHTML = this.cust[i].cnum;
            cname.innerHTML = this.cust[i].cname;
            city.innerHTML = this.cust[i].city;
            rating.innerHTML = this.cust[i].rating;
        }
        return true;
    }
}


window.addEventListener('DOMContentLoaded', () => {
    console.log("DOM load sucessful");
    let idea = new Idea();
    document.querySelector("#view").disabled = false;
    let in_cnum = 0, in_cname = "", in_rating = -1; in_city = "";
    const cnum = document.querySelector("#cnum");
    const cname = document.querySelector("#cname");
    const rating = document.querySelector("#rating");
    const city = document.querySelector("#city");


    cnum.addEventListener("blur", () => {
        in_cnum = cnum.value;
        console.log("Blur event: entered cnum = " + in_cnum);
        let c_out = idea.find_cust(in_cnum);
        if (c_out.status == true) {
            document.querySelector("#found").innerHTML = "Customer Found";
            document.querySelector("#add").disabled = true;
            document.querySelector("#delete").disabled = false;
            document.querySelector("#modify").disabled = false;
            document.querySelector("#ord").disabled = false;
            document.querySelector("#view").disabled = false;
            cname.value = c_out.object.cname;
            rating.value = c_out.object.rating;
            cnum.value = c_out.object.cnum;
            city.value = c_out.object.city;
        } else {
            document.querySelector("#found").innerHTML = "Customer Not Found!";
            document.querySelector("#add").disabled = false;
            document.querySelector("#delete").disabled = false;
            document.querySelector("#modify").disabled = false;
            document.querySelector("#ord").disabled = false;
            document.querySelector("#view").disabled = false;
            cname.value = "";
            rating.value = "";
            city.value = "";
        }
    });


    const add = document.querySelector("#add");
    add.addEventListener('click', () => {
        in_cname = cname.value;
        in_rating = rating.value;
        in_cnum = cnum.value;
        in_city = city.value;
        console.log(in_cname, in_cnum, in_rating, in_city);
        if (in_city === "" || in_cname === "" || in_cnum === 0 || in_rating === -1) {
            document.querySelector("#found").innerHTML = "Please enter all the details";
            console.log("Add Fail: Details missing");
        } else {
            let c_out = idea.find_cust(in_cnum);
            if (c_out.status == true) {
                document.querySelector("#found").innerHTML = "Customer already exist";
            } else {
                idea.cust.push(new Customer(in_cnum, in_cname, in_city, in_rating));
                document.querySelector("#found").innerHTML = "Customer added successfully";
                let output = idea.displayAll();
                console.log("Add Success: for " + idea.cust[(idea.find_cust(in_cnum)).index]);
            }
        }
    });


    const del = document.querySelector("#delete");
    del.addEventListener('click', () => {
        let c_out = idea.find_cust(in_cnum);
        if (c_out.status == true) {
            idea.cust.splice(c_out.index, 1);
            console.log("Delete Success: for " + idea.cust[c_out.index]);
            document.querySelector("#found").innerHTML = "Customer Deleted";
            cname.value = "";
            rating.value = "";
            city.value = "";
            cnum.value = "";
            let output = idea.displayAll();
        } else {
            document.querySelector("#found").innerHTML = "Customer does not exist";
            let output = idea.displayAll();
        }

    });


    const modify = document.querySelector("#modify");
    modify.addEventListener('click', () => {
        let c_out = idea.find_cust(in_cnum);
        document.querySelector("#found").innerHTML = "Customer details modiified successfully!";
        c_out.object.cname = cname.value;
        c_out.object.rating = rating.value;
        c_out.object.city = city.value;
        let output = idea.displayAll();
        console.log("Modify Success: for " + idea.cust[c_out.index]);
    });


    const view = document.querySelector("#view");
    view.addEventListener('click', () => {
        let output = idea.displayAll();
    });


    const ord = document.querySelector("#ord");
    ord.addEventListener('click', () => {
        let output = idea.displayOrder(in_cnum);
    });

});