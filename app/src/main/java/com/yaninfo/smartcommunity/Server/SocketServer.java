package com.yaninfo.smartcommunity.Server;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * @Author: zhangyan
 * @Date: 2019/4/30 17:37
 * @Description: 服务端接收数据
 * @Version: 1.0
 */
public class SocketServer {

    public static void main(String[] args) {
        openPort();
    }

    private static void openPort() {
        /**
         * 收发文字
         */
        new Thread() {
            public void run() {
                try {
                    ServerSocket ss = new ServerSocket(30001);
                    while(true) {
                        final Socket s = ss.accept();
                        System.out.println("连接了30001");
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                                    OutputStream os = s.getOutputStream();
                                    String content = "";
                                    while((content = br.readLine())!=null) {
                                        System.out.println("客户端：" + content);
                                        os.write(("我收到了你的消息：" + content + "\r\n").getBytes());
                                    }
                                    os.close();
                                    br.close();
                                    s.close();
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.start();

        /**
         * 发送图片
         */
        new Thread() {
            public void run() {
                try {
                    ServerSocket server = new ServerSocket(30002);
                    while(true) {
                        Socket socket = server.accept();
                        System.out.println("连接了30002");
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                        final String path = "D:/ybs.png";
                        //1、得到数据文件
                        File file = new File(path);
                        //2、建立数据通道
                        FileInputStream fileInputStream = new FileInputStream(file);

                        int size = fileInputStream.available();
                        byte[] data = new byte[size];
                        fileInputStream.read(data);
                        dos.writeInt(size);
                        dos.write(data);
                        dos.flush();
                        fileInputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        /**
         * 接收图片
         */
        new Thread() {
            public void run() {
                try {
                    ServerSocket ss = new ServerSocket(30003);
                    while(true) {
                        final Socket s = ss.accept();
                        System.out.println("链接30003");
                        byte[] inputByte = null;
                        int length = 0;
                        DataInputStream dis = new DataInputStream(s.getInputStream());
                        String createtime = (new Date()).getTime() +"";
                        String url = "D:/" + createtime +".jpg";
                        System.out.println(url+"###########");
                        FileOutputStream fos = new FileOutputStream(new File(url));
                        inputByte = new byte[1024];
                        System.out.println("开始接收数据...");
                        while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {
                            System.out.println(length);
                            fos.write(inputByte, 0, length);
                            fos.flush();
                        }
                        fos.close();
                        System.out.println("完成接收");
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }.start();
    }


}

