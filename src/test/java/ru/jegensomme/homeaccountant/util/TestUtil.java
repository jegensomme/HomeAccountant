package ru.jegensomme.homeaccountant.util;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import ru.jegensomme.homeaccountant.web.json.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static ru.jegensomme.homeaccountant.web.json.JsonUtil.readValue;
import static ru.jegensomme.homeaccountant.web.json.JsonUtil.readValues;

public class TestUtil {
    public static String getContent(MvcResult result) throws UnsupportedEncodingException {
        return result.getResponse().getContentAsString();
    }

    public static <T> T readFromJson(ResultActions action, Class<T> clazz) throws UnsupportedEncodingException {
        return readValue(getContent(action.andReturn()), clazz);
    }

    public static <T> T readFromJsonMvcResult(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException {
        return readValue(getContent(result), clazz);
    }

    public static <T> List<T> readListFromJsonMvcResult(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException {
        return readValues(getContent(result), clazz);
    }
}
