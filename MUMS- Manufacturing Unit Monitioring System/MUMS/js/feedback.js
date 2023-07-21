
$(() => {
    console.log("Feedback Section Loading Success");
    const alpha = new RegExp('^[a-zA-Z ]*$');
    const numeric = new RegExp('^[0-9]*$');
    const mail = new RegExp('^([a-zA-Z0-9_.+-])+\@([a-zA-Z0-9-])+\.{1}([a-zA-Z0-9]{2,4})+$');

    const bt_feedback = document.querySelector("#feedback-button");
    bt_feedback.addEventListener("click", () => {
        let e = document.getElementById('feedback-main');
        if (e.style.display == 'block')
            $(e).hide(1000);
        else
            $(e).show(1000);

        let blck = document.getElementsByClassName("feedback_hide");
        blck.style.visibility = 'hidden';

    });

    $("#submit_feedback").click(() => {
        const name = $("#feedback-name").val();
        const cont_feedback = $("#feedback-comment").val();
        const email = $("#feedback-email").val();
        var details = { name: name, email: email, feedback: cont_feedback };
        console.log(details);
        if (name && cont_feedback && email) {
            if (alpha.test(name)) {
                console.log("Name proper");
            } else {
                console.log("Name improper");
                alert("Name's can only contain alphabets");
                return;
            }
            if (mail.test(email)) {
                console.log("Email proper");
            } else {
                console.log("Email improper");
                alert("Please enter a valid e-mail");
                return;
            }
            let got = JSON.parse(localStorage.getItem("Feedbacks") || "[]");
            got.push(details);
            localStorage.setItem("Feedbacks", JSON.stringify(got));
            console.log("Feedback successful: ");
            alert("Thanks a lot for your feedback!!");
        } else {
            console.log("Feedback submission Failed: Incomplete data");
            alert("Kindly fill all fields");
        }
    });

});

