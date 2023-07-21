$(() => {
    console.log("Paged loaded sucessfully");
    const alpha = new RegExp('^[a-zA-Z ]*$');
    const numeric = new RegExp('^[0-9]*$');
    const mail = new RegExp('^([a-zA-Z0-9_.+-])+\@([a-zA-Z0-9-])+\.{1}([a-zA-Z0-9]{2,4})+$');


    $("#register").click(() => {
        const name = $("#name").val();
        const cname = $("#cname").val();
        const city = $("#city").val();
        const mobile = $("#mobile").val();
        const email = $("#email").val();
        var details = { name: name, cname: cname, city: city, mobile: mobile, email: email };
        if (name && cname && city && mobile && email) {
            if (alpha.test(name) && alpha.test(cname) && alpha.test(city)) {
                console.log("Name proper");
            } else {
                console.log("Name improper");
                alert("Name's can only contain alphabets");
                return;
            }
            if (numeric.test(mobile)) {
                if (mobile.length == 10) {
                    console.log("Mobile number proper");
                } else {
                    console.log("Mobile number improper");
                    alert("Mobile number should contain 10 digits");
                    return;
                }
            } else {
                console.log("Mobile number improper");
                alert("Mobile number can only contain numbers");
                return;
            }
            if (mail.test(email)) {
                console.log("Email proper");
            } else {
                console.log("Email improper");
                alert("Please enter a valid e-mail");
                return;
            }
            let got = JSON.parse(localStorage.getItem("Registrations") || "[]");
            got.push(details);
            localStorage.setItem("Registrations", JSON.stringify(got));
            console.log("Registration successful: " + JSON.stringify(got));
            alert("Total registrations '" + got.length + "' and counting!!")
        } else {
            console.log("Registration Failed: Incomplete data");
            alert("Kindly fill all fields");
        }
    });
});