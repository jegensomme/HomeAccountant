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
                "data": "limit",
            },
            {
                "data": "period",
                "render": function (data, type, row) {
                    if (type === "display") {
                        return data.number + " " + data.unit;
                    }
                    return data;
                }
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