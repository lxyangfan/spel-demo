package top.frank;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import top.frank.model.po.EsgAltPO;
import top.frank.spel.util.LocalDateTimeCompareTool;

public class SpELTest {

    @Test
    public void test_GivenObjectAndSpEL_WhenValidate_ThenPass() {

        String spEl = " (#root.age >= 18 or #root.textValue matches '^中国.*$' )"
            + " and #root.size > 0.1 ";

        EsgAltPO po = new EsgAltPO();
        po.setAge(19);
        po.setTextValue("中国人");
        po.setSize(0.5d);
        po.setTimestamp(LocalDateTime.now(ZoneOffset.UTC));

        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setRootObject(po);

        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(spEl);
        ((SpelExpression) exp).setEvaluationContext(context);

        Assert.assertTrue((Boolean) exp.getValue());
    }


    private static final LocalDateTimeCompareTool DATE_TIME_COMPARE_TOOL = new LocalDateTimeCompareTool();

    @Test
    public void GivenMapAndSpEL_WhenValidateDateTime_ThenPass() {

        Map<String, Object> po = new HashMap<>();
        po.put("age", 19);
        po.put("textValue", "中国人");
        po.put("size", 0.3);
        po.put("timestampTz", ZonedDateTime.now(ZoneOffset.UTC));
        po.put("timestamp", LocalDateTime.now(ZoneOffset.UTC));

        StandardEvaluationContext context = new StandardEvaluationContext(DATE_TIME_COMPARE_TOOL);
        context.setVariables(po);

        String spEl = " (#age >= 18 or #textValue matches '^中国.*$' )"
            + " and #size > 0.1 "
            + " and compareTimestampTz(#timestampTz, '2021-05-20T11:19:00+08:00') > 0 "
            // ZonedDateTime则是ISO格式的DateTime时间字符串
            + " and compareTimestamp(#timestamp, '2021-05-20T03:19:00') > 0 "; // LocalTime也是UTC时间

        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(spEl);
        ((SpelExpression) exp).setEvaluationContext(context);

        Assert.assertTrue((Boolean) exp.getValue());
    }

    @Test
    public void GivenPositveData_WhenValidate_ThenTrue() throws Exception {
        Map<String, Object> po = new HashMap<>();
        po.put("age", 19);
        po.put("textValue", "中国人");
        po.put("size", 0.3);
        po.put("timestampTz", ZonedDateTime.now(ZoneOffset.UTC));
        po.put("timestamp", LocalDateTime.now(ZoneOffset.UTC));

        ObjectMapper objectWriter = JsonMapper.builder()
            .findAndAddModules()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .build();
        System.out.println(objectWriter.writeValueAsString(po));

        String positiveData = "{\"textValue\":\"中国人\",\"size\":0.3,\"age\":19,\"timestampTz\":\"2021-05-20T08:46:11.779Z\",\"timestamp\":\"2021-05-20T08:46:11.783\"}";

        EsgAltPO po2 = objectWriter
            .readValue(positiveData, EsgAltPO.class);

        StandardEvaluationContext context = new StandardEvaluationContext(DATE_TIME_COMPARE_TOOL);
        context.setRootObject(po2);

        String spEl = " (#age >= 18 or #textValue matches '^中国.*$' )"
            + " and #size > 0.1 "
            + " and compareTimestampTz(#timestampTz, '2021-05-20T11:19:00+08:00') > 0 "
            // ZonedDateTime则是ISO格式的DateTime时间字符串
            + " and compareTimestamp(#timestamp, '2021-05-20T03:19:00') > 0 "; // LocalTime也是UTC时间

        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(spEl);
        ((SpelExpression) exp).setEvaluationContext(context);

        Assert.assertTrue((Boolean) exp.getValue());
    }

}
