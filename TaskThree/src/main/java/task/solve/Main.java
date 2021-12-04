package task.solve;

import java.util.*;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        Initializations
        Random random = new Random();
        int urlNum = random.nextInt(10) + 5;
        int threadNum = 50;
        int clickNum = 10;

        Map<String, Integer> tempMap = new HashMap<>(urlNum);
        for (int i = 0; i < urlNum; i++) {
            tempMap.put(String.valueOf(i), 0);
        }
        urlStats urlStat = new urlStats(tempMap);

//      UserActivity
        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    userActivity[] users = new userActivity[clickNum];
                    for (int j = 0; j < clickNum; j++) {
                        users[j] = new userActivity(urlStat.getList().get(random.nextInt(urlNum)));
                        users[j].run();
                        urlStat.updateStat(users[j].getUrl());
                    }
                }
            });
        }
        for (int i = 0; i < threadNum; i++) {
            threads[i].start();
        }

//        Result
        try {
            for (int i = 0; i < threadNum; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("urlNum = " + urlNum);
        urlStat.urlStatView();
    }
}


class userActivity implements Runnable {

    private String url;
    public userActivity(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        System.out.println("Thread: " + Thread.currentThread().getName() + " url = " + this.url);
    }

    public String getUrl() {
        return this.url;
    }
}

class urlStats {
    private Map<String, Integer> urlStat;

    public urlStats(Map<String, Integer> urlStat) {
        this.urlStat = urlStat;
    }

    public synchronized void updateStat(String url) {
        this.urlStat.put(url, this.urlStat.get(url) + 1);
    }

    public List<String> getList() {
        List<String> urlList = new ArrayList<>(this.urlStat.size());
        urlList.addAll(this.urlStat.keySet());
        return urlList;
    }

    public void urlStatView() {
        int sum = 0;
        for (String key : this.urlStat.keySet()) {
            sum += this.urlStat.get(key);
        }
        System.out.println("Full clicks = " + sum);
        System.out.println(this.urlStat);
    }
}