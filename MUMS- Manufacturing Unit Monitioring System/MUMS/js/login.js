class UserPass {
    constructor(userName, pass) {
        this.userName = userName;
        this.pass = pass;
    }
}

class Logi {
    constructor() {
        this.cust = [
            new UserPass("CUST1", "cust1"),
            new UserPass("CUST2", "cust2"),
            new UserPass("CUST3", "cust3")
        ]
        this.emp = [
            new UserPass("P002", "p002"),
            new UserPass("S002", "s002")
        ]
        this.admin = [
            new UserPass("P001", "p001"),
            new UserPass("S001", "s001")
        ]
    }

    check(type, user_name, password) {
        console.log("Type: " + type + " UserName: " + user_name + " Password: " + password);
        if (type == "cust") {
            let found = false;
            for (let i = 0; i < this.cust.length; i++) {
                if (this.cust[i].userName == user_name) {
                    if (this.cust[i].pass == password) {
                        console.log("UserName-password match");
                        window.open("customer.html", "_self");
                        found = true;
                    } else {
                        window.alert("XXX Incorrect Password XXX");
                    }
                }
            }
            if (found == false)
                window.alert("XXX Incorrect Credentials XXX");
        }
        if (type == "admin") {
            let found = false;
            for (let i = 0; i < this.admin.length; i++) {
                if (this.admin[i].userName == user_name) {
                    if (this.admin[i].pass == password) {
                        console.log("UserName-password match");
                        window.open("admin-home.html", "_self");
                        found = true;
                    } else {
                        window.alert("XXX Incorrect Password XXX");
                    }
                }
            }
            if (found == false)
                window.alert("XXX Incorrect Credentials XXX");
        }
        if (type == "emp") {
            let found = false;
            for (let i = 0; i < this.emp.length; i++) {
                if (this.emp[i].userName == user_name) {
                    if (this.emp[i].pass == password) {
                        console.log("UserName-password match");
                        window.open("employee.html", "_self");
                        found = true;
                    } else {
                        window.alert("XXX Incorrect Password XXX");
                    }
                }
            }
            if (found == false)
                window.alert("XXX Incorrect Credentials XXX");
        }
    }

}


window.addEventListener("DOMContentLoaded", () => {
    console.log("Page loaded successfully");
    let logi = new Logi();
    const login = document.querySelector("#login");
    const user_name = document.querySelector("#user-name");
    const password = document.querySelector("#password");

    const customer = document.querySelector("#customer");
    const admin = document.querySelector("#admin");
    const employee = document.querySelector("#employee");
    const imgbefore = document.querySelector("#imgbefore");
    const imgcust = document.querySelector("#imgcust");
    const imgadmin = document.querySelector("#imgadmin")
    const imgemp = document.querySelector("#imgemp");
    imgbefore.style.visibility = "visible";
    let select = { type: "none" };
    customer.addEventListener('click', () => {
        imgbefore.style.visibility = "hidden";
        imgcust.style.visibility = "visible";
        imgadmin.style.visibility = "hidden";
        imgemp.style.visibility = "hidden";
        customer.style.color = "rgba(128, 0, 128, 1)";
        admin.style.color = "white";
        employee.style.color = "white";
        select.type = "cust";
    });
    admin.addEventListener('click', () => {
        imgbefore.style.visibility = "hidden";
        imgcust.style.visibility = "hidden";
        imgadmin.style.visibility = "visible";
        imgemp.style.visibility = "hidden";
        customer.style.color = "white";
        admin.style.color = "rgba(128, 0, 128, 1)";
        employee.style.color = "white";
        select.type = "admin";
    });
    employee.addEventListener('click', () => {
        imgbefore.style.visibility = "hidden";
        imgcust.style.visibility = "hidden";
        imgadmin.style.visibility = "hidden";
        imgemp.style.visibility = "visible";
        customer.style.color = "white";
        admin.style.color = "white";
        employee.style.color = "rgba(128, 0, 128, 1)";
        select.type = "emp";
    });

    login.addEventListener("click", () => {
        if (select.type == "none") {
            window.alert("Please select Customer / Employee / Admin");
        } else {
            if (user_name.value == "") {
                window.alert("Please enter valid User Name");
            } else {
                if (password.value != "") {
                    logi.check(select.type, user_name.value, password.value);
                } else {
                    window.alert("Please enter the Password");
                }
            }
        }
        //console.log(select.type);
    });


});

