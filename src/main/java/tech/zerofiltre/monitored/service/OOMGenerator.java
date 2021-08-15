package tech.zerofiltre.monitored.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.springframework.stereotype.Service;

@Service
public class OOMGenerator {


  public void startOOM() {
    new Thread(new Producer(),"producer").start();
    new Thread(new Consumer(),"consumer").start();
  }

  BlockingQueue<byte[]> queue = new LinkedBlockingQueue<>();

  class Producer implements Runnable {

    @Override
    public void run() {
      while (true) {
        queue.offer(new byte[3 * 1024 * 1024]);
        try {
          Thread.sleep(50);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  class Consumer implements Runnable {

    @Override
    public void run() {
      while (true) {
        try {
          queue.take();
          Thread.sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

}
