document.addEventListener("DOMContentLoaded", function() {

    //summary elements
    let lastButton = document.getElementById("lastButton");

    lastButton.addEventListener("click", function () {

        //step 1, 3 - get checkbox value
        let tableOfCheckboxesValues = [];
        let checkboxes = document.forms.namedItem("donationForm");
        for (let i = 0; i < checkboxes.length; i++) {
            if(checkboxes[i].checked) {
                tableOfCheckboxesValues.push(checkboxes[i].dataset.name);
            }
        }

        let categories = "-" + tableOfCheckboxesValues[0];
        for (let i = 1; i < tableOfCheckboxesValues.length-1; i++) {
            categories = categories + "<br>" + "-" + (tableOfCheckboxesValues[i]);
        }

        document.getElementById("sum_categories").innerHTML = categories;
        document.getElementById("sum_institutionName").innerHTML =
            tableOfCheckboxesValues[tableOfCheckboxesValues.length-1];

        //step 2 and 4
        let formData = ['noOfBags', 'streetName', 'city', 'zipCode', 'phoneNumber', 'pickUpDate', 'pickUpTime',
            'comment'];

        formData.forEach(el => {
            let formElement = document.getElementById(el);
            let sumElement = document.getElementById("sum_"+el);
            sumElement.innerHTML = formElement.value;
        })

    })



})