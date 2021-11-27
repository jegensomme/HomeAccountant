package ru.jegensomme.homeaccountant;

import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import ru.jegensomme.homeaccountant.model.User;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static ru.jegensomme.homeaccountant.util.JsonUtil.readValue;
import static ru.jegensomme.homeaccountant.util.JsonUtil.readValues;

@UtilityClass
public class TestUtil {
    public static String getContent(@NonNull MvcResult result) throws UnsupportedEncodingException {
        return result.getResponse().getContentAsString();
    }

    public static <T> T readFromJson(@NonNull ResultActions action, @NonNull Class<T> clazz) throws UnsupportedEncodingException {
        return readValue(getContent(action.andReturn()), clazz);
    }

    public static <T> T readFromJsonMvcResult(@NonNull MvcResult result, @NonNull Class<T> clazz) throws UnsupportedEncodingException {
        return readValue(getContent(result), clazz);
    }

    public static <T> List<T> readListFromJsonMvcResult(@NonNull MvcResult result, @NonNull Class<T> clazz) throws UnsupportedEncodingException {
        return readValues(getContent(result), clazz);
    }

    public static RequestPostProcessor userHttpBasic(@NonNull User user) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword());
    }

    public static RequestPostProcessor userAuth(@NonNull User user) {
        return SecurityMockMvcRequestPostProcessors.authentication(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
    }
}
