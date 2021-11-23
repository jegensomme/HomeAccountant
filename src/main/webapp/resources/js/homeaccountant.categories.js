const categoryAjaxUrl = "profile/categories/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: categoryAjaxUrl,
    updateTable: function () {
        $.get(categoryAjaxUrl, updateTableByData);
    }
};

$(function () {
    makeEditable({
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "limit"
            },
            {
                "data": "period",
                "render": function (data, type, row) {
                    if (type === "display") {
                        return data == null ? ""
                            : data.number === 1 && data.unit === 'DAYS' ? i18n["period.day"]
                            : data.number === 1 && data.unit === 'WEEKS' ? i18n["period.week"]
                            : data.number === 1 && data.unit === 'DECADES' ? i18n["period.decade"]
                            : data.number === 1 && data.unit === 'MONTHS' ? i18n["period.month"]
                            : data.number === 6 && data.unit === 'MONTHS' ? i18n["period.half.year"]
                            : data.number === 1 && data.unit === 'YEARS' ? i18n["period.year"]
                            : data.number + " " + data.unit;
                    }
                    return data;
                },
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
});

function jsonValue2SelectValue(key, value) {
    return key === "period" ? (value == null ? "" : value.number + "$" + value.unit) : value
}