const expenseAjaxUrl = "profile/expenses/";

const categoryAjaxUrl = "profile/categories/"

let categoryFilter;

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: expenseAjaxUrl,
    updateTable: function () {
        const selectedCategory = getSelectedCategory();
        $.ajax({
            type: "GET",
            url: expenseAjaxUrl + "between",
            data: $("#filter").serialize()
                + (selectedCategory === "all" ? "" : "&category=" + selectedCategory)
        }).done(updateTableByData);
        $("#totalAmount").html(ctx.datatableApi.column(2).data().sum());
        updateMonthTotal();
    }
};

// http://api.jquery.com/jQuery.ajax/#using-converters
$.ajaxSetup({
    converters: {
        "text json": function (stringData) {
            const json = JSON.parse(stringData);
            if (typeof json === 'object') {
                $(json).each(function () {
                    if (this.hasOwnProperty('dateTime')) {
                        this.dateTime = this.dateTime.substr(0, 16).replace('T', ' ');
                    }
                });
            }
            return json;
        }
    }
});

function clearFilter() {
    $("#filter")[0].reset();
    const selectedCategory = getSelectedCategory();
    const url = expenseAjaxUrl + (selectedCategory === "all" ? "" : "by?category=" + selectedCategory)
    $.get(url, updateTableByData);
}

function getSelectedCategory() {
    return categoryFilter.find("option:selected")[0].value;
}

$(function () {
    makeEditable({
        "columns": [
            {
                "data": "category",
                "render": function (data, type, row) {
                    if (type === 'display') {
                        return data === '' ? i18n["without.category"] : data;
                    }
                    return data
                }
            },
            {
                "data": "dateTime"
            },
            {
                "data": "amount"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": function (data, type, row) {
                    if (type === 'display') {
                        return currency
                    }
                }
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
        ],
        "initComplete": function(settings, json) {
            $("#totalAmount").html(ctx.datatableApi.column(2).data().sum());
        }
    });

    $.datetimepicker.setLocale(localeCode);
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

    updateMonthTotal();

    categoryFilter = $("#categoryFilter");
});

function fillCategories() {
    $("#category, #categoryFilter").empty()
    $.get(categoryAjaxUrl, function (data) {
        let options = "";
        data.forEach(e => options += `<option value='${e.name}'>${e.name}</option>\n`);
        $("#category").html("<option value=''>" + i18n["without.category"] + "</option>\n" + options);
        $("#categoryFilter").html(
            "<option value='all'>" + i18n["all.categories"] + "</option>\n" +
            "<option value=''>" + i18n["without.category"] + "</option>\n" + options
        );
    });
}

function updateMonthTotal() {
    $.get(expenseAjaxUrl + "month-total", function (data) {
        $("#monthTotal").val(data);
        $("#monthTotalPanel").attr("data-monthlyLimitExcess", data > monthlyLimit)
    });
}

function jsonValue2SelectValue(key, value) {
    return key === "category" ? (value == null ? "" : value.name) : value
}