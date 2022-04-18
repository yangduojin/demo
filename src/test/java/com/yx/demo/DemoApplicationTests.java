package com.yx.demo;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        System.out.println("hello");
        redisTemplate.boundValueOps("hello").set("yx");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name","yx");
        hashMap.put("sex","man");
        hashMap.put("age","30");

        redisTemplate.boundHashOps("helloHash").putAll(hashMap);

        Object hello = redisTemplate.boundValueOps("hello").get();
        System.out.println(hello);

        Map helloHash = redisTemplate.opsForHash().entries("helloHash");
        System.out.println(helloHash);
    }

    public static void main(String[] args) {
//        Date date = new Date();
//        try {
//            TimeUnit.SECONDS.sleep(1);} catch (Exception e) {e.printStackTrace();}
//        Date date1 = new Date();
//        System.out.println(date1.compareTo(date));

        String regex = "^[A-Za-z\\d@$!%*#?&.]{6,20}$";
        String value = "Yangx19940714.";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(value);
        System.out.println(m.matches());
    }

    @Test
    public void testPalindrome(){
        String a = "abccbad";
        boolean palindrome = isPalindrome2(a);
        System.out.println(palindrome);
    }

    public boolean isPalindrome(String s) {
        if(s.length() == 0) return true;

        int left = 0, right = s.length() - 1;
        while(left < right){
            while(left < right && !Character.isLetterOrDigit(s.charAt(left))){
                left++;
            }
            while(left < right && !Character.isLetterOrDigit(s.charAt(right))){
                right--;
            }
            if(Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))){
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
    public boolean isPalindrome2(String s) {
        String actual = s.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
        String rev = new StringBuffer(actual).reverse().toString();
        return actual.equals(rev);
    }

    public static void server() throws Exception {
        ServerSocket serverSocket = new ServerSocket(520);
        int i = 1;
        while (true) {
            Socket socket = serverSocket.accept();
            int left = 0;
            while (left >= 0) {
                InputStream io = socket.getInputStream();
                byte[] bytes = new byte[1024];
                left = io.read(bytes);
            }
        }
    }

   // @GetMapping(path = "hi")
    public String hi() throws Exception {
        client();
        return "end";
    }

    public void client() throws Exception {
        Socket socket = new Socket("127.0.0.1", 520);
        //向服务器端第一次发送字符串
        OutputStream netOut = socket.getOutputStream();
        InputStream io = new FileInputStream("D:\\photo\\编程一生.JPG");
        long begin = System.currentTimeMillis();
        byte[] bytes = new byte[1024];
        while (io.read(bytes) >= 0) {
            netOut.write(bytes);
        }
        System.out.println("耗时为" + (System.currentTimeMillis() - begin) + "ms");
        netOut.close();
        io.close();
        socket.close();
    }


    //@GetMapping(path = "hi")
    public String hi2() throws Exception {
        client2();
        return "end";
    }

    public void client2() throws Exception {
        SocketChannel socket = SocketChannel.open();
        socket.connect(new InetSocketAddress("127.0.0.1", 520));
        FileChannel io = new FileInputStream("D:\\photo\\编程一生.JPG").getChannel();
        long begin = System.currentTimeMillis();
        io.transferTo(0, io.size(), socket);
        System.out.println("耗时为" + (System.currentTimeMillis() - begin) + "ms");
        io.close();
        socket.close();
    }




}
