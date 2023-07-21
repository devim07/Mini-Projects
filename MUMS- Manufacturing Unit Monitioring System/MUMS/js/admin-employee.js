class Employee {
    constructor(eno, ename, dept, bsalary, hra, tsalary, hdate, holiday, city) {
        this.eno = eno;
        this.ename = ename;
        this.dept = dept;
        this.city = city;
        this.bsalary = bsalary;
        this.hra = hra;
        this.tsalary = tsalary;
        this.hdate = hdate;
        this.holiday = holiday;
    }
}


class Idea {
    constructor() {
        this.emp = [
            new Employee(1, 'AKSHAY JAGTAP', 'PROD', 80000, 0.5, 84000, '11-04-2010', 0, 'BOM'),
            new Employee(2, 'DEVI DINESH', 'MAIN.', 90000, 0.5, 95000, '22-09-1990', 0, 'BOMB'),
            new Employee(3, 'ANIKET PATTIWAR', 'QUALITY', 60000, 0.5, 66000, '09-09-2020', 0, 'PUNE'),
            new Employee(4, 'AKSHAY SHINDE ', 'DESIGN', 40000, 0.4, 50000, '08-09-2019', 0, 'CHEN.'),
            new Employee(5, 'AKASH SHINDE', 'PACKAGING', 70000, 0.5, 77000, '09-08-2020', 0, "THANE"),
            new Employee(6, 'AKASH SHARMA', 'DISPATCH', 50000, 0.4, 55000, '06-07-2020', 1, "NAGAR"),
            new Employee(7, 'AKSHAY SHENDE', 'QUALITY', 10000, 0.3, 13000, '07-07-2018', 2, "DHULE"),
            new Employee(8, 'ANIKET KHACHANE', 'PROD', 30000, 0.8, 40000, '22-01-2016', 2, "BEED")
        ]
    }


    find_emp(in_eno) {
        let output = new Employee(false, -1, {});

        for (let i = 0; i < this.emp.length; i++) {
            if (in_eno == this.emp[i].eno) {
                output.status = true;
                output.object = this.emp[i];
                output.index = i;
                break;
            }
        }
        console.log("eno " + in_eno + " found: " + output.status);
        return output;
    }


    displayAll() {
        var tblc = document.querySelector("#tblc");
        tblc.style.visibility = "visible";
        for (let i = tblc.rows.length - 1; i > 0; i--) {
            tblc.deleteRow(i);
        }
        for (let i = 0; i < this.emp.length; i++) {
            var r = tblc.insertRow();
            var eno = r.insertCell();
            var ename = r.insertCell();
            var dept = r.insertCell();
            var bsalary = r.insertCell();
            var hra = r.insertCell();
            var tsalary = r.insertCell();
            var hdate = r.insertCell();
            var holiday = r.insertCell();
            var city = r.insertCell();

            eno.innerHTML = this.emp[i].eno;
            ename.innerHTML = this.emp[i].ename;
            dept.innerHTML = this.emp[i].dept;
            bsalary.innerHTML = this.emp[i].bsalary;
            hra.innerHTML = this.emp[i].hra;
            tsalary.innerHTML = this.emp[i].tsalary;
            hdate.innerHTML = this.emp[i].hdate;
            holiday.innerHTML = this.emp[i].holiday;
            city.innerHTML = this.emp[i].city;
        }
        return true;
    }
}


window.addEventListener('DOMContentLoaded', () => {
    console.log("DOM load sucessful");
    let idea = new Idea();
    document.querySelector("#view").disabled = false;
    let in_eno = 0, in_ename = "", in_bsalary = 0, in_hra = 0, in_tsalary = 0, in_dept = "", in_hdate = "", in_holiday = 0, in_city = "";
    const eno = document.querySelector("#eno");
    const ename = document.querySelector("#ename");
    const bsalary = document.querySelector("#bsalary");
    const hra = document.querySelector("#hra");
    const holiday = document.querySelector("#holiday");
    const city = document.querySelector("#city");


    eno.addEventListener("blur", () => {
        in_eno = eno.value;
        console.log("Blur event: entered eno = " + in_eno);
        let c_out = idea.find_emp(in_eno);
        if (c_out.status == true) {
            document.querySelector("#found").innerHTML = "Employee Found";
            document.querySelector("#add").disabled = true;
            document.querySelector("#delete").disabled = false;
            document.querySelector("#modify").disabled = false;
            document.querySelector("#view").disabled = false;
            ename.value = c_out.object.ename;
            bsalary.value = c_out.object.bsalary;
            hra.value = c_out.object.hra;
            holiday.value = c_out.object.holiday;
            eno.value = c_out.object.eno;
            city.value = c_out.object.city;
        } else {
            document.querySelector("#found").innerHTML = "Employee Not Found!";
            document.querySelector("#add").disabled = false;
            document.querySelector("#delete").disabled = false;
            document.querySelector("#modify").disabled = false;
            document.querySelector("#view").disabled = false;
            ename.value = "";
            bsalary.value = "";
            hra.value = "";
            holiday.value = "";
            city.value = "";
        }
    });


    const add = document.querySelector("#add");
    add.addEventListener('click', () => {
        in_ename = ename.value;
        in_bsalary = bsalary.value;
        in_hra = hra.value;
        in_holiday = holiday.value;
        in_eno = eno.value;
        in_city = city.value;
        console.log(in_eno, in_ename, in_bsalary, in_hra, in_holiday, in_city);
        if (in_city === "" || in_ename === "" || in_eno === 0 || in_bsalary === 0 || in_hra === 0 || in_holiday === 0) {
            document.querySelector("#found").innerHTML = "Please enter all the details";
            console.log("Add Fail: Details missing");
        } else {
            let c_out = idea.find_emp(in_eno);
            if (c_out.status == true) {
                document.querySelector("#found").innerHTML = "Employee already exist";
            } else {
                idea.emp.push(new Employee(in_eno, in_ename, in_bsalary, in_hra, in_holiday, in_city));
                document.querySelector("#found").innerHTML = "Employee added successfully";
                let output = idea.displayAll();
                console.log("Add Success: for " + idea.emp[(idea.find_emp(in_eno)).index]);
            }
        }
    });


    const del = document.querySelector("#delete");
    del.addEventListener('click', () => {
        let c_out = idea.find_emp(in_eno);
        if (c_out.status == true) {
            idea.emp.splice(c_out.index, 1);
            console.log("Delete Success: for " + idea.emp[c_out.index]);
            document.querySelector("#found").innerHTML = "Employee Deleted";
            ename.value = "";
            eno.value = "";
            bsalary.value = "";
            hra.value = "";
            holiday.value = "";
            city.value = "";
            let output = idea.displayAll();
        } else {
            document.querySelector("#found").innerHTML = "Employee does not exist";
            let output = idea.displayAll();
        }
    });


    const modify = document.querySelector("#modify");
    modify.addEventListener('click', () => {
        let c_out = idea.find_emp(in_eno);
        document.querySelector("#found").innerHTML = "Employee details modiified successfully!";
        c_out.object.ename = ename.value;
        c_out.object.holiday = holiday.value;
        c_out.object.bsalary = bsalary.value;
        c_out.object.hra = hra.value;
        c_out.object.city = city.value;
        let output = idea.displayAll();
        console.log("Modify Success: for " + idea.emp[c_out.index]);
    });


    const view = document.querySelector("#view");
    view.addEventListener('click', () => {
        let output = idea.displayAll();
    });
});