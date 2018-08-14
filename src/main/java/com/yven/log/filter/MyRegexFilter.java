package com.yven.log.filter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yven
 * @date 2818/8/14
 * @desc 日志过滤
 */
@Plugin(
        name = "MyRegexFilter",
        category = "Core",
        elementType = "filter",
        printObject = true
)
public final class MyRegexFilter extends AbstractFilter {
    private static final int DEFAULT_PATTERN_FLAGS = 0;
    private final Pattern pattern;
    private final boolean useRawMessage;

    private MyRegexFilter(boolean raw, Pattern pattern, Result onMatch, Result onMismatch) {
        super(onMatch, onMismatch);
        this.pattern = pattern;
        this.useRawMessage = raw;
    }

    /**
     * 加载设备白名单列表
     */
    static private  List<String> deviceList ;
    static{
        deviceList = new ArrayList<>();
        deviceList.add("4798");
        deviceList.add("1244");
        deviceList.add("4566");
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String msg, Object... params) {
        return this.filter(msg);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
        return msg == null ? this.onMismatch : this.filter(msg.toString());
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
        if (msg == null) {
            return this.onMismatch;
        } else {
            String text = this.useRawMessage ? msg.getFormat() : msg.getFormattedMessage();
            return this.filter(text);
        }
    }

    @Override
    public Result filter(LogEvent event) {
        String text = this.useRawMessage ? event.getMessage().getFormat() : event.getMessage().getFormattedMessage();
        return this.filter(text);
    }

    private Result filter(String msg) {
        if (msg == null) {
            return this.onMismatch;
        } else {
            Matcher m = this.pattern.matcher(msg);
            return m.matches() ? this.onMatch : this.onMismatch;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("useRaw=").append(this.useRawMessage);
        sb.append(", pattern=").append(this.pattern.toString());
        return sb.toString();
    }

    @PluginFactory
    public static MyRegexFilter createFilter(@PluginAttribute("regex") String regex, @PluginElement("PatternFlags") String[] patternFlags, @PluginAttribute("useRawMsg") Boolean useRawMsg, @PluginAttribute("onMatch") Result match, @PluginAttribute("onMismatch") Result mismatch) throws IllegalArgumentException, IllegalAccessException {
        /*if (regex == null) {
            LOGGER.error("A regular expression must be provided for RegexFilter");
            return null;
        } else {
            return new MyRegexFilter(useRawMsg, Pattern.compile(regex, toPatternFlags(patternFlags)), match, mismatch);
        }*/

        // 根据设备的白名单进行过滤 from=1111   to=2222
        if (regex == null) {
            if (deviceList == null ||deviceList.isEmpty()){
                // 拒绝所有的info
                LOGGER.info("没有获取到白名单！");
                return null;
            }else{
                StringBuffer sb = new StringBuffer();
                for (String str : deviceList) {
                    sb.append(".*from=\"").append(str).append(".*").append("|").append(".*to=\"").append(str).append(".*").append("|");
                }
                regex = sb.toString();
            }
        }

        System.out.println(regex);
        return new MyRegexFilter(useRawMsg, Pattern.compile(regex, toPatternFlags(patternFlags)), match, mismatch);

    }

    private static int toPatternFlags(String[] patternFlags) throws IllegalArgumentException, IllegalAccessException {
        if (patternFlags != null && patternFlags.length != 0) {
            Field[] fields = Pattern.class.getDeclaredFields();
            Comparator<Field> comparator = new Comparator<Field>() {
                @Override
                public int compare(Field f1, Field f2) {
                    return f1.getName().compareTo(f2.getName());
                }
            };
            Arrays.sort(fields, comparator);
            String[] fieldNames = new String[fields.length];

            int flags;
            for(flags = 0; flags < fields.length; ++flags) {
                fieldNames[flags] = fields[flags].getName();
            }

            flags = 0;
            String[] arr$ = patternFlags;
            int len$ = patternFlags.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String test = arr$[i$];
                int index = Arrays.binarySearch(fieldNames, test);
                if (index >= 0) {
                    Field field = fields[index];
                    flags |= field.getInt(Pattern.class);
                }
            }

            return flags;
        } else {
            return 0;
        }
    }
}
