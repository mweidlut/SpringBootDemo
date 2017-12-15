package org.test.web.utest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import jersey.repackaged.com.google.common.collect.ImmutableList;
import jersey.repackaged.com.google.common.collect.ImmutableListMultimap;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.test.web.domain.User;

import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.time.LocalDate;

public class TestCase {

    private static Logger logger = LoggerFactory.getLogger(TestCase.class);

    @Test
    public void testMultimap() {
        ImmutableListMultimap.Builder<Integer, String> builder = ImmutableListMultimap.builder();
        builder.put(1, "a").put(1, "b").put(2, "c");

        ImmutableListMultimap<Integer, String> immutableListMultimap = builder.build();
        ImmutableList<String> valueList = immutableListMultimap.get(1);

        System.out.println(valueList.toString());
    }

    @Test
    public void testInheritJson(){
        User user = new User("Anna", "F", LocalDate.now().minusYears(20))
                .children(new User("Mike", "M", LocalDate.now().minusYears(20)));

        String jsonStr = new Gson().toJson(user);
        logger.info("json string ={}", jsonStr);

        User userFromJson = new Gson().fromJson(jsonStr, User.class);
        logger.info("user is {}", userFromJson);
    }

    @Test
    public void testByteBuffer(){
        String str = "abcde";

        //1. 分配一个指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        System.out.println("-----------------allocate()----------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //2. 利用 put() 存入数据到缓冲区中
        buf.put(str.getBytes());

        System.out.println("-----------------put()----------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //3. 切换读取数据模式
        buf.flip();

        System.out.println("-----------------flip()----------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //4. 利用 get() 读取缓冲区中的数据
        byte[] dst = new byte[buf.limit()];
        buf.get(dst);
        System.out.println(new String(dst, 0, dst.length));

        System.out.println("-----------------get()----------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //5. rewind() : 可重复读
        buf.rewind();

        System.out.println("-----------------rewind()----------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //6. clear() : 清空缓冲区. 但是缓冲区中的数据依然存在，但是处于“被遗忘”状态
        buf.clear();

        System.out.println("-----------------clear()----------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        System.out.println((char)buf.get());

    }

    @Test
    public void testJsonDate(){
        User user = new User("Anna", "F", LocalDate.now().minusYears(20));

        JsonSerializer<LocalDate> ser = new JsonSerializer<LocalDate>() {
            @Override
            public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext
                    context) {
                logger.info("serialize-{}, value is {}", typeOfSrc.getTypeName(), src.toString());
                return src == null ? null : new JsonPrimitive(src.getYear());
            }
        };

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, ser).create();


        logger.info(gson.toJson(user));
    }

}
