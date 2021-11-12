const expenseAjaxUrl = "profile/expenses/";

const categoryAjaxUrl = "profile/categories/";

let categoryFilter;

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: expenseAjaxUrl,
    updateTable: function () {
        const selectedCategory = getSelectedCategory();
        const url = selectedCategory === ""
            ? expenseAjaxUrl + "between"
            : categoryAjaxUrl + selectedCategory + "/expenses/between"
        $.ajax({
            type: "GET",
            url: url,
            data: $("#filter").serialize()
        }).done(updateTableByData);
    }
};

function clearFilter() {
    $("#filter")[0].reset();
    const selectedCategory = getSelectedCategory();
    const url = selectedCategory === "" ? expenseAjaxUrl : categoryAjaxUrl + selectedCategory + "/expenses"
    $.get(url, updateTableByData);
}

function getSelectedCategory() {
    return categoryFilter.find("option:selected")[0].value;
}

function clearCategory() {
    $("#categoryFilterForm")[0].reset();
    $.get(expenseAjaxUrl, updateTableByData);
}

$(function () {
    makeEditable({
        "columns": [
            {
                "data": "category",
                "render": function (data, type, row) {
                    if (type === "display") {
                        return data == null ? "" : data.name;
                    }
                    return data;
                }
            },
            {
                "data": "dateTime",
                "render": function (date, type, row) {
                    if (type === 'display') {
                        return formatDate(date);
                    }
                    return date;
                }
            },
            {
                "data": "amount"
            },
            {
                "data": "currency"
            },
            {
                "data": "description"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });

    //  http://xdsoft.net/jqplugins/datetimepicker/
    const startDate = $('#startDate');
    const endDate = $('#endDate');
    startDate.datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y-m-d',
        onShow: function (ct) {
            this.setOptions({
                maxDate: endDate.val() ? endDate.val() : false
            })
        }
    });
    endDate.datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y-m-d',
        onShow: function (ct) {
            this.setOptions({
                minDate: startDate.val() ? startDate.val() : false
            })
        }
    });

    const startTime = $('#startTime');
    const endTime = $('#endTime');
    startTime.datetimepicker({
        datepicker: false,
        format: 'H:i',
        onShow: function (ct) {
            this.setOptions({
                maxTime: endTime.val() ? endTime.val() : false
            })
        }
    });
    endTime.datetimepicker({
        datepicker: false,
        format: 'H:i',
        onShow: function (ct) {
            this.setOptions({
                minTime: startTime.val() ? startTime.val() : false
            })
        }
    });

    $('#dateTime').datetimepicker({
        format: 'Y-m-d H:i'
    });

    fillCategories();

    categoryFilter = $("#categoryFilter");
});

function fillCategories() {
    $("#category, #categoryFilter").empty()
    $.get(categoryAjaxUrl, function (data) {
        let options = "<option value='0'>" + i18n["without.category"] + "</option>\n";
        data.forEach(e => options += `<option value='${e.id}'>${e.name}</option>\n`);
        $("#category").html("<option value=''></option>\n" + options);
        $("#categoryFilter").html("<option value=''>" + i18n["all.categories"] + "</option>\n" + options);
    });
}